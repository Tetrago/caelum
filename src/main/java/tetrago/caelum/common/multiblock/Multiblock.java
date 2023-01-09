package tetrago.caelum.common.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public abstract class Multiblock implements IForgeRegistryEntry<Multiblock>
{
    public static class Instance
    {
        private final BlockPos anchor;
        private final VoxelShape shape;

        public Instance(BlockPos anchor, VoxelShape shape)
        {
            this.anchor = anchor;
            this.shape = shape;
        }

        public BlockPos getAnchorPosition()
        {
            return anchor;
        }

        public VoxelShape getShape()
        {
            return shape;
        }

        public CompoundTag serializeNBT()
        {
            CompoundTag tag = new CompoundTag();
            tag.put("anchor", NbtUtils.writeBlockPos(anchor));

            ListTag list = new ListTag();
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                CompoundTag box = new CompoundTag();
                box.put("min", NbtUtils.writeBlockPos(new BlockPos(minX, minY, minZ)));
                box.put("max", NbtUtils.writeBlockPos(new BlockPos(maxX, maxY, maxZ)));
                list.add(box);
            });

            tag.put("boxes", list);
            return tag;
        }

        public static Instance deserializeNBT(CompoundTag nbt)
        {
            final BlockPos anchor = NbtUtils.readBlockPos(nbt.getCompound("anchor"));
            VoxelShape[] shape = new VoxelShape[]{Shapes.empty()};

            nbt.getList("boxes", CompoundTag.TAG_COMPOUND).stream().map(tag -> (CompoundTag)tag).forEach(tag -> {
                final BlockPos min = NbtUtils.readBlockPos(tag.getCompound("min"));
                final BlockPos max = NbtUtils.readBlockPos(tag.getCompound("max"));

                shape[0] = Shapes.or(shape[0], Shapes.box(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ()));
            });

            return new Instance(anchor, shape[0].optimize());
        }
    }

    private static class Definition
    {
        private final Predicate<BlockState>[][][] predicates;
        private final BlockPos anchor;

        public Definition(Predicate<BlockState>[][][] predicates, BlockPos anchor)
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

        public BlockPos getRelativeAnchorPosition()
        {
            return anchor;
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
            Predicate<BlockState>[][][] predicates = new Predicate[width][height][depth];

            for(int x = 0; x < width; ++x)
            {
                for(int y = 0; y < height; ++y)
                {
                    for(int z = 0; z < depth; ++z)
                    {
                        predicates[x][y][z] = definitions.getOrDefault(layers.get(y).charAt(z * width + x), state -> true);
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

    public Optional<Instance> canConstructAt(Level level, BlockPos anchor)
    {
        ROTATIONS: for(Rotation rotation : Rotation.values())
        {
            final BlockPos absolute = anchor.offset(anchor.rotate(rotation).multiply(-1));

            for(int x = 0; x < definition.getWidth(); ++x)
            {
                for(int y = 0; y < definition.getHeight(); ++y)
                {
                    for(int z = 0; z < definition.getDepth(); ++z)
                    {
                        final BlockPos pos = absolute.offset(new BlockPos(x, y, z).rotate(rotation));
                        if(!definition.predicates[x][y][z].test(level.getBlockState(pos))) continue ROTATIONS;
                    }
                }
            }

            VoxelShape shape = getShape();
            VoxelShape[] rotated = new VoxelShape[]{ Shapes.empty() };

            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                final BlockPos begin = new BlockPos(minX, minY, minZ).rotate(rotation);
                final BlockPos end = new BlockPos(maxX, maxY, maxZ).rotate(rotation);

                rotated[0] = Shapes.or(rotated[0], Shapes.box(begin.getX(), begin.getY(), begin.getZ(), end.getX(), end.getY(), end.getZ()));
            });

            return Optional.of(new Instance(anchor, rotated[0].optimize()));
        }

        return Optional.empty();
    }

    public VoxelShape getShape()
    {
        return Shapes.box(0, 0, 0, definition.getWidth(), definition.getHeight(), definition.getDepth());
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
