package tetrago.caelum.common.event;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tetrago.caelum.common.Caelum;
import tetrago.caelum.common.recipe.ArcFurnaceRecipe;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Caelum.MODID)
public class SerializerEventBusSubscriber
{
    @SubscribeEvent
    public static void onRegisterRecipeSerializer(final RegistryEvent.Register<RecipeSerializer<?>> event)
    {
        Registry.register(Registry.RECIPE_TYPE, ArcFurnaceRecipe.Type.ID, ArcFurnaceRecipe.Type.INSTANCE);
    }
}
