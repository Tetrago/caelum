package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CoilBlock extends Block
{
    private final int strength;

    public CoilBlock(int strength)
    {
        super(Properties.copy(Blocks.IRON_BLOCK));

        this.strength = strength;
    }

    public int getCoilStrength()
    {
        return strength;
    }
}
