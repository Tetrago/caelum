package tetrago.caelum.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.recipe.ArcFurnaceRecipe;

import java.util.Objects;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin
{
    @Override
    public ResourceLocation getPluginUid()
    {
        return Caelum.loc("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        registration.addRecipeCategories(new ArcFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        final RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        registration.addRecipes(new RecipeType<>(ArcFurnaceRecipeCategory.UID, ArcFurnaceRecipe.class), rm.getAllRecipesFor(ArcFurnaceRecipe.Type.INSTANCE));
    }
}
