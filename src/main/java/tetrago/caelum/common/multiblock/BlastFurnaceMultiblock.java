package tetrago.caelum.common.multiblock;

import com.mojang.datafixers.types.Type;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import tetrago.caelum.common.block.ModBlocks;

public class BlastFurnaceMultiblock extends Multiblock
{
    public BlastFurnaceMultiblock()
    {
        super(new Builder()
                .define('R', ModBlocks.REFIRED_BRICKS.get())
                .layer(
                        "RRR",
                        "RRR",
                        "RRR")
                .layer(
                        "RRR",
                        "R R",
                        "RRR")
                .layer(
                        "RRR",
                        "R R",
                        "RRR")
                .layer(
                        "RRR",
                        "RRR",
                        "RRR")
                .build(1, 1, 0));
    }
}
