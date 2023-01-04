package tetrago.caelum.common.capability;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage
{
    public ModEnergyStorage(int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int energy = super.receiveEnergy(maxReceive, simulate);
        if(energy != 0 && !simulate)
        {
            onEnergyChanged();
        }

        return energy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int energy = super.extractEnergy(maxExtract, simulate);
        if(energy != 0 && !simulate)
        {
            onEnergyChanged();
        }

        return energy;
    }

    public void setEnergyStored(int energy)
    {
        this.energy = energy;
        onEnergyChanged();
    }

    protected abstract void onEnergyChanged();
}
