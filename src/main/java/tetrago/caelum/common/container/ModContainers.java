package tetrago.caelum.common.container;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tetrago.caelum.common.Caelum;

public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Caelum.MODID);

    public static final RegistryObject<MenuType<SolarPanelContainer>> SOLAR_PANEL_CONTAINER = CONTAINERS.register("solar_panel", () -> IForgeMenuType.create(SolarPanelContainer::new));
}
