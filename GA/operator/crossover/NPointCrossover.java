package GA.operator.crossover;

import GA.chromosome.Chromosome;
import GA.interfaces.CrossoverMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NPointCrossover implements CrossoverMethod {
    private final int numberOfPoints;
    private final Random random = new Random();

    public NPointCrossover() {
        this.numberOfPoints = 1;
    }

    public NPointCrossover(int numberOfPoints) {
        if(numberOfPoints < 1)
            throw new IllegalArgumentException("Number of crossover points must be at least 1.");
        this.numberOfPoints = numberOfPoints;
    }

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
        int[] points = new int[numberOfPoints];
        for(int i = 0;i < numberOfPoints;i++) {
            points[i] = random.nextInt(length - 1) + 1;
        }
        Arrays.sort(points);

        Chromosome child1 = parent1.clone();
        Chromosome child2 = parent2.clone();

        List<Chromosome> children = new ArrayList<>();

        boolean flag = false;
        for(int i = 0;i < points.length;i++) {
            int start = (i == 0) ? 0 : points[i - 1];
            int end = points[i];

            if(flag) swapGenes(child1, child2, start, end);
            flag = !flag;
        }
        if(flag) swapGenes(child1, child2, points[points.length - 1], length);

        children.add(child1);
        children.add(child2);

        return children;
    }
}
