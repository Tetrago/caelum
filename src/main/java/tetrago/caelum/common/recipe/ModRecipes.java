package tetrago.caelum.common.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Caelum.MODID);

    public static final RegistryObject<RecipeSerializer<ArcFurnaceRecipe>> ARC_FURNACE = RECIPE_SERIALIZERS.register("arc_furnace", () -> ArcFurnaceRecipe.Serializer.INSTANCE);
}
