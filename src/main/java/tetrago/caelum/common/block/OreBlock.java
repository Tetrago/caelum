package tetrago.caelum.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class OreBlock extends Block
{
    public OreBlock()
    {
        super(Properties.of(Material.STONE).strength(2).requiresCorrectToolForDrops());
    }
}
