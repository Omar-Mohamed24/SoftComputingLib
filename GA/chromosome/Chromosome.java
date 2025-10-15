package GA.chromosome;

import java.util.Arrays;

/**
 * Abstract base class for all chromosome types.
 * Defines the common interface that all chromosomes must implement.
 */
public abstract class Chromosome implements Cloneable {
    protected Object[] genes;
    protected double fitness = -1;

    public Chromosome(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Chromosome length must be a positive integer.");
        }
        this.genes = new Object[length];
    }

    public abstract void initialize();

    public Object[] getGenes() {
        return genes;
    }

    public Object getGene(int index) {
        if (index < 0 || index >= genes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + genes.length);
        }
        return genes[index];
    }

    public void setGene(int index, Object value) {
        if (index < 0 || index >= genes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + genes.length);
        }
        genes[index] = value;
    }

    public int getLength() {
        return genes.length;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public Chromosome clone() {
        try {
            Chromosome cloned = (Chromosome) super.clone();
            cloned.genes = this.genes.clone();
            cloned.fitness = this.fitness;
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("CloneNotSupportedException thrown from a Cloneable class.", e);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
