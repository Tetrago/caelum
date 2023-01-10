package tetrago.caelum.common.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.capability.IMultiblocksRecord;
import tetrago.caelum.common.capability.ModCapabilities;

import java.awt.dnd.DropTarget;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public abstract class Multiblock implements IForgeRegistryEntry<Multiblock>
{
    public static class Instance implements INBTSerializable<CompoundTag>
    {
        private BlockPos anchor;
        private List<BoundingBox> boundingBoxes;

        public Instance() {}

        public Instance(BlockPos anchor, List<BoundingBox> boundingBoxes)
        {
            this.anchor = anchor;
            this.boundingBoxes = boundingBoxes;
        }

        public BlockPos getAnchorPosition()
        {
            return anchor;
        }

        public List<BoundingBox> getBoundingBoxes()
        {
            return boundingBoxes;
        }

        @Override
        public CompoundTag serializeNBT()
        {
            CompoundTag tag = new CompoundTag();
            tag.put("anchor", NbtUtils.writeBlockPos(anchor));

            ListTag list = new ListTag();
            boundingBoxes.forEach(box -> {
                CompoundTag nbt = new CompoundTag();
                nbt.put("min", NbtUtils.writeBlockPos(new BlockPos(box.minX(), box.minY(), box.minZ())));
                nbt.put("max", NbtUtils.writeBlockPos(new BlockPos(box.maxX(), box.maxY(), box.maxZ())));
                list.add(nbt);
            });

            tag.put("boxes", list);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt)
        {
            anchor = NbtUtils.readBlockPos(nbt.getCompound("anchor"));
            boundingBoxes = nbt.getList("boxes", CompoundTag.TAG_COMPOUND).stream().map(tag -> (CompoundTag)tag).map(tag -> {
                final BlockPos min = NbtUtils.readBlockPos(tag.getCompound("min"));
                final BlockPos max = NbtUtils.readBlockPos(tag.getCompound("max"));

                return new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
            }).toList();
        }
    }

    private static class Definition
    {
        private final Optional<Predicate<BlockState>>[][][] predicates;
        private final BlockPos anchor;

        public Definition(Optional<Predicate<BlockState>>[][][] predicates, BlockPos anchor)
        {
            this.predicates = predicates;
            this.anchor = anchor;
        }

        public int getWidth()
        {
            return predicates.length;
        }

        public int getHeight()
        {
            return predicates[0].length;
        }

        public int getDepth()
        {
            return predicates[0][0].length;
        }
    }

    public static class Builder
    {
        private int width = 0;
        private final List<String> layers = new ArrayList<>();
        private final Map<Character, Predicate<BlockState>> definitions = new HashMap<>();

        public Builder layer(String... patterns)
        {
            if(width == 0) width = patterns[0].length();

            StringBuilder builder = new StringBuilder();
            for(int i = patterns.length - 1; i >= 0; --i)
            {
                builder.append(patterns[i]);
            }

            layers.add(builder.toString());
            return this;
        }

        public Builder define(char c, Predicate<BlockState> predicate)
        {
            definitions.put(c, predicate);
            return this;
        }

        public Builder define(char c, Block block)
        {
            definitions.put(c, state -> state.is(block));
            return this;
        }

        public Definition build(int ax, int ay, int az)
        {
            final int height = layers.size();
            final int depth = layers.get(0).length() / width;
            Optional<Predicate<BlockState>>[][][] predicates = new Optional[width][height][depth];

            for(int x = 0; x < width; ++x)
            {
                for(int y = 0; y < height; ++y)
                {
                    for(int z = 0; z < depth; ++z)
                    {
                        char c = layers.get(y).charAt(z * width + x);
                        predicates[x][y][z] = c == ' ' ? Optional.empty() : Optional.of(definitions.get(c));
                    }
                }
            }

            return new Definition(predicates, new BlockPos(ax, ay, az));
        }
    }

    private ResourceLocation name;
    protected final Definition definition;

    public Multiblock(Definition definition)
    {
        this.definition = definition;
    }

    public List<BlockPos> getBlockPositions(BlockPos anchor, Rotation rotation)
    {
        final BlockPos absolute = anchor.offset(definition.anchor.rotate(rotation).multiply(-1));

        List<BlockPos> positions = new ArrayList<>();
        for(int x = 0; x < definition.getWidth(); ++x)
        {
            for(int y = 0; y < definition.getHeight(); ++y)
            {
                for(int z = 0; z < definition.getDepth(); ++z)
                {
                    if(definition.predicates[x][y][z].isEmpty()) continue;
                    positions.add(absolute.offset(new BlockPos(x, y, z).rotate(rotation)));
                }
            }
        }

        return positions;
    }

    public Optional<Instance> match(Level level, BlockPos anchor)
    {
        IMultiblocksRecord record = level.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).orElseThrow(() -> new IllegalStateException("No multiblock record holder!"));

        for(Rotation rotation : Rotation.values())
        {
            if(getBlockPositions(anchor, rotation).stream().anyMatch(pos ->
                    definition.predicates[pos.getX()][pos.getY()][pos.getZ()].map(predicate ->
                            !predicate.test(level.getBlockState(pos))).orElse(false)
                            || record.isWithinMultiblock(pos).isPresent()))
                continue;

            final BlockPos absolute = anchor.offset(definition.anchor.rotate(rotation).multiply(-1));
            List<BoundingBox> boxes = getBoundingBoxes().stream().map(box -> {
                final BlockPos min = new BlockPos(box.minX(), box.minY(), box.minZ()).rotate(rotation).offset(absolute);
                final BlockPos max = new BlockPos(box.maxX(), box.maxY(), box.maxZ()).rotate(rotation).offset(absolute);

                int minX = Math.min(min.getX(), max.getX());
                int minY = Math.min(min.getY(), max.getY());
                int minZ = Math.min(min.getZ(), max.getZ());
                int maxX = Math.max(min.getX(), max.getX());
                int maxY = Math.max(min.getY(), max.getY());
                int maxZ = Math.max(min.getZ(), max.getZ());

                return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
            }).toList();

            return Optional.of(new Instance(anchor, boxes));
        }

        return Optional.empty();
    }

    public void onConstruct(Level level, BlockPos pos) {}
    public void onDeconstruct(Level level, BlockPos pos) {}

    public List<BoundingBox> getBoundingBoxes()
    {
        return List.of(new BoundingBox(0, 0, 0, definition.getWidth(), definition.getHeight(), definition.getDepth()));
    }

    @Override
    public Multiblock setRegistryName(ResourceLocation name)
    {
        this.name = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName()
    {
        return name;
    }

    @Override
    public Class<Multiblock> getRegistryType()
    {
        return Multiblock.class;
    }
}
