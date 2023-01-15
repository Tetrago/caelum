package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Blocks;

public class MotorBlock extends OmnidirectionalBlock
{
    private final int strength;

    public MotorBlock(int strength)
    {
        super(Properties.copy(Blocks.IRON_BLOCK));

        this.strength = strength;
    }

    public int getStrength()
    {
        return strength;
    }
}
