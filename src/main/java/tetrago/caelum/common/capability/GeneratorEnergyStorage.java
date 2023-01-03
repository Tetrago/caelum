package tetrago.caelum.common.capability;

public abstract class GeneratorEnergyStorage extends CaelumEnergyStorage
{
    private final int generationRate;

    public GeneratorEnergyStorage(int capacity, int generationRate)
    {
        super(capacity, generationRate * 2);

        this.generationRate = generationRate;
    }

    public void generate()
    {
        this.energy += generationRate;
        onEnergyChanged();
    }

    @Override
    public boolean canReceive()
    {
        return false;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        return 0;
    }
}
