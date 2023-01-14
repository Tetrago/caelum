package tetrago.caelum.common.container.data;

import net.minecraft.world.inventory.ContainerData;

public abstract class EnergyData implements ContainerData
{
    @Override
    public int get(int pIndex)
    {
        return switch(pIndex) {
            default -> getEnergyStored() & 0xffff;
            case 1 -> (getEnergyStored() >> 16) & 0xffff;
        };
    }

    @Override
    public void set(int pIndex, int pValue)
    {
        switch(pIndex)
        {
        default -> setEnergyStored((getEnergyStored() & 0xffff0000) | pValue);
        case 1 -> setEnergyStored((getEnergyStored() & 0xffff) | ((pValue << 16)) & 0xffff0000);
        }
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    protected abstract int getEnergyStored();
    protected abstract void setEnergyStored(int energy);
}
