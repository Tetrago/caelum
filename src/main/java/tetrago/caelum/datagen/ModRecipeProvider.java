package tetrago.caelum.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.item.ModItems;

import java.awt.*;
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
        oreRecipes(builder, ImmutableList.of(ModBlocks.BAUXITE_ORE.get(), ModBlocks.DEEPSLATE_BAUXITE_ORE.get(), ModItems.RAW_BAUXITE.get()), ModItems.ALUMINUM_INGOT.get());

        ingotRecipes(builder, ModItems.ALUMINUM_INGOT.get(), ModItems.ALUMINUM_NUGGET.get(), ModItems.ALUMINUM_BLOCK.get());
        ingotRecipes(builder, ModItems.STEEL_INGOT.get(), ModItems.STEEL_NUGGET.get(), ModItems.STEEL_BLOCK.get());
        ingotRecipes(builder, ModItems.TITANIUM_INGOT.get(), ModItems.TITANIUM_NUGGET.get(), ModItems.TITANIUM_BLOCK.get());

        ShapedRecipeBuilder.shaped(ModItems.BASIC_CIRCUIT_BOARD.get(), 4)
                .define('A', ModItems.ALUMINUM_INGOT.get())
                .define('C', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("C")
                .pattern("R")
                .pattern("A")
                .unlockedBy("has_ingots_aluminum", criterion(ModItemTagsProvider.INGOTS_ALUMINUM))
                .unlockedBy("has_ingots_copper", criterion(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_dusts_redstone", criterion(Tags.Items.DUSTS_REDSTONE))
                .save(builder);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BRICKS), ModBlocks.REFIRED_BRICKS.get(), 0, 200);

        ShapedRecipeBuilder.shaped(ModBlocks.MACHINE_FRAME.get())
                .define('A', ModItemTagsProvider.INGOTS_ALUMINUM)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("A A")
                .pattern(" I ")
                .pattern("A A")
                .unlockedBy("has_ingots_aluminum", criterion(ModItemTagsProvider.INGOTS_ALUMINUM))
                .unlockedBy("has_ingots_iron", criterion(Tags.Items.INGOTS_IRON))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.ROLLER.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', ModItemTagsProvider.INGOTS_STEEL)
                .pattern("III")
                .pattern("ISI")
                .pattern("III")
                .unlockedBy("has_ingots_steel", criterion(ModItemTagsProvider.INGOTS_STEEL))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.COPPER_COIL.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Tags.Items.INGOTS_COPPER)
                .pattern("CCC")
                .pattern("CIC")
                .pattern("CCC")
                .unlockedBy("has_ingots_iron", criterion(Tags.Items.INGOTS_IRON))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.BASIC_MOTOR.get())
                .define('M', ModBlocks.MACHINE_FRAME.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("RIR")
                .pattern(" M ")
                .unlockedBy("has_machine_frame", criterion(ModBlocks.MACHINE_FRAME.get()))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.ARC_FURNACE_CONTROLLER.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .define('A', ModItemTagsProvider.INGOTS_ALUMINUM)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('M', ModBlocks.MACHINE_FRAME.get())
                .pattern(" I ")
                .pattern("AMA")
                .pattern("RRR")
                .unlockedBy("has_machine_frame", criterion(ModBlocks.MACHINE_FRAME.get()))
                .save(builder);

        ShapelessRecipeBuilder.shapeless(ModBlocks.MATERIAL_HOPPER.get())
                .requires(ModBlocks.MACHINE_FRAME.get())
                .requires(Blocks.HOPPER)
                .unlockedBy("has_machine_frame", criterion(ModBlocks.MACHINE_FRAME.get()))
                .save(builder);

        ShapelessRecipeBuilder.shapeless(ModBlocks.ENERGY_PORT.get())
                .requires(ModBlocks.MACHINE_FRAME.get())
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_dusts_redstone", criterion(Tags.Items.DUSTS_REDSTONE))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModItems.BASIC_PHOTOVOLTAIC_CELL.get())
                .define('G', Tags.Items.GLASS)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("GGG")
                .pattern("RCR")
                .unlockedBy("has_ingots_copper", criterion(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_dusts_redstone", criterion(Tags.Items.DUSTS_REDSTONE))
                .save(builder);

        ShapedRecipeBuilder.shaped(ModBlocks.BASIC_SOLAR_PANEL.get())
                .define('P', ModItems.BASIC_PHOTOVOLTAIC_CELL.get())
                .define('C', ModItems.BASIC_CIRCUIT_BOARD.get())
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("PPP")
                .pattern("ICI")
                .unlockedBy("has_basic_photovoltaic_cell", criterion(ModItems.BASIC_PHOTOVOLTAIC_CELL.get()))
                .unlockedBy("has_basic_circuit_board", criterion(ModItems.BASIC_CIRCUIT_BOARD.get()))
                .save(builder);
    }

    private static void oreRecipes(Consumer<FinishedRecipe> builder, ImmutableList<ItemLike> smeltables, Item item)
    {
        smeltables.forEach(i -> {
            final String name = i.asItem().getRegistryName().getPath();

            SimpleCookingRecipeBuilder.smelting(Ingredient.of(i), item, 0.7f, 200)
                    .unlockedBy("has_" + name, criterion(i.asItem()))
                    .save(builder, Caelum.loc(name + "_smelting"));

            SimpleCookingRecipeBuilder.blasting(Ingredient.of(i), item, 0.7f, 100)
                    .unlockedBy("has_" + name, criterion(i.asItem()))
                    .save(builder, Caelum.loc(name + "_blasting"));
        });
    }

    private static void ingotRecipes(Consumer<FinishedRecipe> builder, Item ingot, Item nugget, Item block)
    {
        ShapelessRecipeBuilder.shapeless(ingot, 9)
                .requires(block)
                .unlockedBy("has_" + block.getRegistryName().getPath(), criterion(block))
                .save(builder, Caelum.loc(ingot.getRegistryName().getPath() + "_from_block"));

        ShapedRecipeBuilder.shaped(block)
                .define('X', ingot)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_" + ingot.getRegistryName().getPath(), criterion(ingot))
                .save(builder);

        ShapelessRecipeBuilder.shapeless(nugget, 9)
                .requires(ingot)
                .unlockedBy("has_" + nugget.getRegistryName().getPath(), criterion(nugget))
                .save(builder);

        ShapedRecipeBuilder.shaped(ingot)
                .define('X', nugget)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_" + nugget.getRegistryName().getPath(), criterion(nugget))
                .save(builder, Caelum.loc(ingot.getRegistryName().getPath() + "_from_nugget"));
    }

    private static CriterionTriggerInstance criterion(ItemLike item)
    {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).build());
    }

    private static CriterionTriggerInstance criterion(TagKey<Item> tag)
    {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag).build());
    }
}
