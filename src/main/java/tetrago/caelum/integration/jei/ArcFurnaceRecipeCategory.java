package tetrago.caelum.integration.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tetrago.caelum.client.screen.ArcFurnaceControllerScreen;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.block.ModBlocks;
import tetrago.caelum.common.recipe.ArcFurnaceRecipe;

public class ArcFurnaceRecipeCategory implements IRecipeCategory<ArcFurnaceRecipe>
{
    public static final ResourceLocation UID = Caelum.loc("arc_furnace");
    public static final ResourceLocation TEXTURE = Caelum.loc("textures/gui/arc_furnace_controller.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ArcFurnaceRecipeCategory(IGuiHelper helper)
    {
        background = helper.createDrawable(TEXTURE, 4, 4, 172, 82);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ARC_FURNACE_CONTROLLER.get()));
    }

    @Override
    public Component getTitle()
    {
        return new TranslatableComponent(ArcFurnaceControllerScreen.UNLOCALIZED_NAME);
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public ResourceLocation getUid()
    {
        return UID;
    }

    @Override
    public Class<? extends ArcFurnaceRecipe> getRecipeClass()
    {
        return ArcFurnaceRecipe.class;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArcFurnaceRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 35).addItemStack(recipe.getResultItem());
    }
}
