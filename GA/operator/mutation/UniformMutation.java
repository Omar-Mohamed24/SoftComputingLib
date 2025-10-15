package GA.operator.mutation;

import java.util.Random;
import GA.chromosome.Chromosome;
import GA.chromosome.FloatChromosome;
import GA.chromosome.IntegerChromosome;
import GA.interfaces.MutationMethod;

public class UniformMutation implements MutationMethod {
    private final Random random = new Random();
    private final double geneMutationProbability;

    public UniformMutation() {
        this.geneMutationProbability = 0.01;
    }

    public UniformMutation(double geneMutationProbability) {
        if (geneMutationProbability < 0.0 || geneMutationProbability > 1.0) {
            throw new IllegalArgumentException("Gene mutation probability must be between 0.0 and 1.0.");
        }
        this.geneMutationProbability = geneMutationProbability;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        if (chromosome instanceof FloatChromosome) {
            mutateFloatChromosome((FloatChromosome) chromosome);
        } else if (chromosome instanceof IntegerChromosome) {
            mutateIntegerChromosome((IntegerChromosome) chromosome);
        }
        return chromosome;
    }

    private void mutateFloatChromosome(FloatChromosome chromosome) {
        double lowerBound = chromosome.getMin();
        double upperBound = chromosome.getMax();

        for (int i = 0; i < chromosome.getLength(); i++) {
            if (random.nextDouble() < this.geneMutationProbability) {
                double currentValue = (double) chromosome.getGene(i);
                double r1 = random.nextDouble();
                double newValue;

                if (r1 <= 0.5) {
                    double deltaL = currentValue - lowerBound;
                    newValue = currentValue - (deltaL * random.nextDouble());
                } else {
                    double deltaU = upperBound - currentValue;
                    newValue = currentValue + (deltaU * random.nextDouble());
                }
                chromosome.setGene(i, newValue);
            }
        }
    }

    private void mutateIntegerChromosome(IntegerChromosome chromosome) {
        int lowerBound = chromosome.getMin();
        int upperBound = chromosome.getMax();

        for (int i = 0; i < chromosome.getLength(); i++) {
            if (random.nextDouble() < this.geneMutationProbability) {
                int currentValue = (int) chromosome.getGene(i);
                double r1 = random.nextDouble();
                int newValue;

                if (r1 <= 0.5) { 
                    double deltaL = currentValue - lowerBound;
                    int step = (int) (deltaL * random.nextDouble());
                    newValue = currentValue - step;
                } else {
                    double deltaU = upperBound - currentValue;
                    int step = (int) (deltaU * random.nextDouble());
                    newValue = currentValue + step;
                }
                chromosome.setGene(i, newValue);
            }
        }
    }
}
