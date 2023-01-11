package tetrago.caelum.common.container.data;

import net.minecraft.world.inventory.ContainerData;

public abstract class EnergyData implements ContainerData
{
    @Override
    public int get(int pIndex)
    {
        if(pIndex == 0)
        {
            return getEnergyStored() & 0xffff;
        }
        else
        {
            return (getEnergyStored() >> 16) & 0xffff;
        }
    }

    @Override
    public void set(int pIndex, int pValue)
    {
        if(pIndex == 0)
        {
            setEnergyStored((getEnergyStored() & 0xffff0000) | pValue);
        }
        else
        {
            setEnergyStored((getEnergyStored() & 0xffff) | (pValue << 16));
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
