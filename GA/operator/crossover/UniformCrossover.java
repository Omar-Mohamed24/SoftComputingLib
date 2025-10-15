package GA.operator.crossover;

import GA.chromosome.Chromosome;
import GA.interfaces.CrossoverMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniformCrossover implements CrossoverMethod {

    private final double mixingProbability;
    private final Random random = new Random();

    public UniformCrossover() {
        this.mixingProbability = 0.5;
    }

    public UniformCrossover(double mixingProbability) {
        if(mixingProbability <= 0 || mixingProbability >= 1)
            throw new IllegalArgumentException("Mixing probability must be between 0 and 1 (exclusive).");
        this.mixingProbability = mixingProbability;
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        if(parent1 == null || parent2 == null)
            throw new IllegalArgumentException("Parents cannot be null.");
        if(parent1.getGenes().length != parent2.getGenes().length)
            throw new IllegalArgumentException("Parents must have the same chromosome length.");

        Chromosome child1 = parent1.clone();
        Chromosome child2 = parent2.clone();

        List<Chromosome> children = new ArrayList<>();
        int length = parent1.getGenes().length;
        for (int i = 0;i < length;i++) {
            double factor = random.nextDouble();

            if (factor >= mixingProbability) {
                child1.setGene(i, parent2.getGene(i));
                child2.setGene(i, parent1.getGene(i));
            }
        }

        children.add(child1);
        children.add(child2);

        return children;
    }
}
