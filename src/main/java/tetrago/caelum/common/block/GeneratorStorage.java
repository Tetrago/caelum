package tetrago.caelum.common.block;

import net.minecraftforge.energy.EnergyStorage;

public class GeneratorStorage extends EnergyStorage
{
    private final int generationRate;

    public GeneratorStorage(int capacity, int generationRate)
    {
        super(capacity, 0, generationRate);

        this.generationRate = generationRate;
    }

    public void setEnergyStored(int energy)
    {
        this.energy = energy;
    }

    public void generateEnergy()
    {
        energy += generationRate;
        energy = Math.min(energy, capacity);
    }

    public int getGenerationRate()
    {
        return generationRate;
    }
}
