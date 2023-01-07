package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.capability.ModCapabilities;

import java.util.*;
import java.util.function.Predicate;

public abstract class MultiblockBaseBlock extends Block
{
    public static class Matcher
    {
        private final int width;
        private final int height;
        private final int depth;
        private final Predicate<BlockState>[][] blocks;
        private final BlockPos anchorPos;

        private Matcher(int width, int height, int depth, Predicate<BlockState>[][] blocks, BlockPos anchorPos)
        {
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.blocks = blocks;
            this.anchorPos = anchorPos;
        }

        private Optional<BoundingBox> isValid(Level level, BlockPos pos)
        {
            ROTATIONS: for(Rotation rotation : Rotation.values())
            {
                final BlockPos absolutePos = pos.offset(anchorPos.rotate(rotation).multiply(-1));

                for(int y = 0; y < height; ++y)
                {
                    for(int z = 0; z < depth; ++z)
                    {
                        for(int x = 0; x < width; ++x)
                        {
                            final BlockPos p = absolutePos.offset(new BlockPos(x, y, z).rotate(rotation));
                            if(!blocks[y][z * width + x].test(level.getBlockState(p))) continue ROTATIONS;
                        }
                    }
                }

                final BlockPos corner = absolutePos.offset(new BlockPos(width, height, depth).rotate(rotation));
                return Optional.of(new BoundingBox(absolutePos.getX(), absolutePos.getY(), absolutePos.getZ(), corner.getX(), corner.getY(), corner.getZ()));
            }

            return Optional.empty();
        }
    }

    public static class Builder
    {
        private int width = 0;
        private final List<String> layers = new ArrayList<>();
        private final Map<Character, Predicate<BlockState>> defines = new HashMap<>();
        private BlockPos anchor;

        public Builder layer(String... patterns)
        {
            if(width == 0)
            {
                width = patterns[0].length();
            }

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
            defines.put(c, predicate);
            return this;
        }

        public Builder define(char c, Block block)
        {
            defines.put(c, b -> b.is(block));
            return this;
        }

        public Builder define(char c, RegistryObject<Block> block)
        {
            defines.put(c, b -> b.is(block.get()));
            return this;
        }

        public Builder anchor(int x, int y, int z)
        {
            anchor = new BlockPos(x, y, z);
            return this;
        }

        public Matcher build()
        {
            final int depth = layers.get(0).length() / width;
            final int height = layers.size();

            Predicate<BlockState>[][] blocks = new Predicate[height][width * depth];

            for(int y = 0; y < height; ++y)
            {
                Predicate<BlockState>[] slice = blocks[y];
                String layer = layers.get(y);

                for(int z = 0; z < depth; ++z)
                {
                    String pattern = layer.substring(z * width, (z + 1) * width);

                    for(int x = 0; x < width; ++x)
                    {
                        slice[z * width + x] = defines.getOrDefault(pattern.charAt(x), b -> true);
                    }
                }
            }

            return new Matcher(width, height, depth, blocks, anchor);
        }
    }

    public static final Property<Boolean> CONSTRUCTED = BooleanProperty.create("constructed");

    private final Matcher matcher;

    public MultiblockBaseBlock(Properties pProperties, Matcher matcher)
    {
        super(pProperties);

        this.matcher = matcher;

        registerDefaultState(getStateDefinition().any()
                .setValue(CONSTRUCTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        super.createBlockStateDefinition(pBuilder);

        pBuilder.add(CONSTRUCTED);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if(state.getValue(CONSTRUCTED)) return useMultiblock(state, level, pos, player, hand, hit);

        return matcher.isValid(level, pos).map(box -> {
            if(level.isClientSide()) return InteractionResult.SUCCESS;

            constructAndRecord(state, level, pos, box);
            return InteractionResult.CONSUME;
        }).orElse(super.use(state, level, pos, player, hand, hit));
    }

    private static boolean constructAndRecord(BlockState state, Level level, BlockPos pos, BoundingBox box)
    {
        level.setBlockAndUpdate(pos, state.setValue(CONSTRUCTED, true));
        level.getCapability(ModCapabilities.MULTIBLOCKS_RECORD).ifPresent(cap -> cap.add(pos, box));

        return true;
    }

    public static void deconstruct(BlockState state, Level level, BlockPos pos)
    {
        if(!state.getValue(CONSTRUCTED)) return;
        level.setBlockAndUpdate(pos, state.setValue(CONSTRUCTED, false));
    }

    protected InteractionResult useMultiblock(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        return super.use(state, level, pos, player, hand, hit);
    }
}
