package tetrago.caelum.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider
{
    public ModRecipeProvider(DataGenerator pGenerator)
    {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> builder)
    {
        oreRecipes(builder, ImmutableList.of(ModBlocks.ALUMINUM_ORE.get(), ModBlocks.DEEPSLATE_ALUMINUM_ORE.get(), ModItems.RAW_ALUMINUM.get()), ModItems.ALUMINUM_INGOT.get());

        ShapelessRecipeBuilder.shapeless(ModItems.ALUMINUM_INGOT.get(), 9)
                .requires(ModBlocks.ALUMINUM_BLOCK.get())
                .unlockedBy("has_aluminum_block", criterion(ModItems.ALUMINUM_BLOCK.get()))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.ALUMINUM_BLOCK.get())
                .define('X', ModItems.ALUMINUM_INGOT.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_aluminum_ingot", criterion(ModItems.ALUMINUM_INGOT.get()))
                .save(builder, modLoc("aluminum_ingot_from_block"));

        ShapelessRecipeBuilder.shapeless(ModItems.ALUMINUM_NUGGET.get(), 9)
                .requires(ModItems.ALUMINUM_INGOT.get())
                .unlockedBy("has_aluminum_block", criterion(ModItems.ALUMINUM_BLOCK.get()))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModItems.ALUMINUM_INGOT.get())
                .define('X', ModItems.ALUMINUM_NUGGET.get())
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_aluminum_nugget", criterion(ModItems.ALUMINUM_NUGGET.get()))
                .save(builder, modLoc("aluminum_ingot_from_nuggets"));

        ShapedRecipeBuilder.shaped(ModItems.BASIC_CIRCUIT_BOARD.get(), 4)
                .define('A', ModItems.ALUMINUM_INGOT.get())
                .define('C', Items.COPPER_INGOT)
                .define('R', Items.REDSTONE)
                .pattern("C")
                .pattern("R")
                .pattern("A")
                .unlockedBy("has_aluminum_ingot", criterion(ModItems.ALUMINUM_INGOT.get()))
                .unlockedBy("has_copper_ingot", criterion(Items.COPPER_INGOT))
                .unlockedBy("has_redstone", criterion(Items.REDSTONE))
                .save(builder);
    }

    private static ResourceLocation modLoc(String name)
    {
        return new ResourceLocation(Caelum.MODID, name);
    }

    private static void oreRecipes(Consumer<FinishedRecipe> builder, ImmutableList<ItemLike> smeltables, Item item)
    {
        smeltables.forEach(i -> {
            final String name = i.asItem().getRegistryName().getPath();

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(i), item, 0.7f, 200)
                    .unlockedBy("has_" + name, criterion(i.asItem()))
                    .save(builder, modLoc(name + "_from_smelting"));

            SimpleCookingRecipeBuilder.blasting(Ingredient.of(i), item, 0.7f, 100)
                    .unlockedBy("has_" + name, criterion(i.asItem()))
                    .save(builder, modLoc(name + "_from_blasting"));
        });
    }

    private static CriterionTriggerInstance criterion(Item item)
    {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).build());
    }
}
