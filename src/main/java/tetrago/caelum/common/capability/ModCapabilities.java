package tetrago.caelum.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities
{
    public static final Capability<IMultiblocksRecord> MULTIBLOCKS_RECORD = CapabilityManager.get(new CapabilityToken<>(){});
}
