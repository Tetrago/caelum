package tetrago.caelum.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public abstract class MultiblockBaseBlock extends Block
{
    private static class Description
    {
        private final int width;
        private final int height;
        private final int depth;
        private final Predicate<Block>[] blocks;
        private final BlockPos anchorPos;

        private Description(int width, int height, int depth, Predicate<Block>[] blocks, BlockPos anchorPos)
        {
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.blocks = blocks;
            this.anchorPos = anchorPos;
        }

        private boolean isValid(Level level, BlockPos pos)
        {
            final BlockPos absolutePos = pos.offset(anchorPos.multiply(-1));

            for(int i = 0; i < blocks.length; ++i)
            {
                int z = i % depth;
                int y = (i / depth) % height;
                int x = i / (height * depth);

                final BlockPos p = absolutePos.offset(x, y, z);
                if(!blocks[i].test(level.getBlockState(p).getBlock()))
                {
                    return false;
                }
            }

            return true;
        }

        private BoundingBox getBoundingBox(BlockPos pos)
        {
            final BlockPos absolutePos = pos.offset(anchorPos.multiply(-1));
            return new BoundingBox(absolutePos.getX(), absolutePos.getY(), absolutePos.getZ(), absolutePos.getX() + width, absolutePos.getY() + height, absolutePos.getZ() + depth);
        }
    }

    public static class Builder
    {
        private int width = 0;
        private final List<String> layers = new ArrayList<>();
        private final Map<Character, Predicate<Block>> defines = new HashMap<>();
        private BlockPos anchor;

        public Builder layer(String... patterns)
        {
            if(width == 0)
            {
                width = patterns[0].length();
            }

            StringBuilder builder = new StringBuilder();

            for(String pattern : patterns)
            {
                builder.append(pattern);
            }

            layers.add(builder.toString());

            return this;
        }

        public Builder define(char c, Predicate<Block> predicate)
        {
            defines.put(c, predicate);
            return this;
        }

        public Builder define(char c, Block block)
        {
            defines.put(c, b -> b.equals(block));
            return this;
        }

        public Builder anchor(int x, int y, int z)
        {
            anchor = new BlockPos(x, y, z);
            return this;
        }

        public Description build()
        {
            final int depth = layers.get(0).length();
            final int height = layers.size();

            Predicate<Block>[] blocks = new Predicate[width * depth * height];

            for(int y = 0; y < height; ++y)
            {
                String layer = layers.get(y);

                for(int z = 0; z < depth; ++z)
                {
                    String pattern = layer.substring(z * width, (z + 1) * width);

                    for(int x = 0; x < width; ++z)
                    {
                        final char c = pattern.charAt(x);
                        blocks[(depth * z + y) * width + x] = c == ' ' ? (b -> true) : defines.get(pattern.charAt(x));
                    }
                }
            }

            return new Description(width, height, depth, blocks, anchor);
        }
    }

    public static final Property<Boolean> CONSTRUCTED = BooleanProperty.create("constructed");

    private final Description matcher;

    public MultiblockBaseBlock(Properties pProperties, Description matcher)
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
        if(level.isClientSide()) return InteractionResult.SUCCESS;

        if(!state.getValue(CONSTRUCTED))
        {
            construct(state, level, pos);
            return InteractionResult.CONSUME;
        }

        return useMultiblock(state, level, pos, player, hand, hit);
    }

    public boolean construct(BlockState state, Level level, BlockPos pos)
    {
        if(!isValid(level, pos) || state.getValue(CONSTRUCTED)) return false;

        level.setBlock(pos, state.setValue(CONSTRUCTED, true), 2);
        return true;
    }

    public void deconstruct(BlockState state, Level level, BlockPos pos)
    {
        if(!state.getValue(CONSTRUCTED)) return;
        level.setBlock(pos, state.setValue(CONSTRUCTED, false), 2);
    }

    protected boolean isValid(Level level, BlockPos pos)
    {
        return matcher.isValid(level, pos);
    }

    public BoundingBox getBoundingBox(BlockPos pos)
    {
        return matcher.getBoundingBox(pos);
    }

    protected abstract InteractionResult useMultiblock(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit);
}
