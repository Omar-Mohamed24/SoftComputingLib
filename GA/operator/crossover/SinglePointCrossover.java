package GA.operator.crossover;

import GA.chromosome.Chromosome;
import GA.interfaces.CrossoverMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointCrossover implements CrossoverMethod {
    private final Random random = new Random();

    private void swapGenes(Chromosome child1, Chromosome child2, int start, int end) {
        Object[] g1 = child1.getGenes();
        Object[] g2 = child2.getGenes();

        for (int i = start;i < end;i++) {
            Object temp = g1[i];
            g1[i] = g2[i];
            g2[i] = temp;
        }
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        if(parent1 == null || parent2 == null)
            throw new IllegalArgumentException("Parents cannot be null.");
        if(parent1.getGenes().length != parent2.getGenes().length)
            throw new IllegalArgumentException("Parents must have the same chromosome length.");

        int length = parent1.getGenes().length;
        int point = random.nextInt(length - 1) + 1;

        List<Chromosome> children = new ArrayList<>();
        Chromosome child1 = parent1.clone();
        Chromosome child2 = parent2.clone();

        swapGenes(child1, child2, point, length);
        children.add(child1);
        children.add(child2);

        return children;
    }
}
