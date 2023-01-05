package tetrago.caelum.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.ItemLike;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider
{
    private static final ImmutableList<ItemLike> ALUMINUM_SMELTABLES = ImmutableList.of(ModBlocks.ALUMINUM_ORE.get(), ModBlocks.DEEPSLATE_ALUMINUM_ORE.get(), ModItems.RAW_ALUMINUM.get());

    public ModRecipeProvider(DataGenerator pGenerator)
    {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> builder)
    {
        oreSmelting(builder, ALUMINUM_SMELTABLES, ModItems.ALUMINUM_INGOT.get(), 0.7f, 200, "aluminum_ingot");
        oreBlasting(builder, ALUMINUM_SMELTABLES, ModItems.ALUMINUM_INGOT.get(), 0.7f, 100, "aluminum_ingot");
    }
}
