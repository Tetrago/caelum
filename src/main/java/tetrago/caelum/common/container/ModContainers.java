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

    public static final RegistryObject<MenuType<ArcFurnaceControllerContainer>> ARC_FURNACE_CONTROLLER = CONTAINERS.register("arc_furnace_controller", () -> IForgeMenuType.create(ArcFurnaceControllerContainer::new));
    public static final RegistryObject<MenuType<SolarPanelContainer>> SOLAR_PANEL = CONTAINERS.register("solar_panel", () -> IForgeMenuType.create(SolarPanelContainer::new));
}
