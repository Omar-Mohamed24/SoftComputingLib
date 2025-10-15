package GA.operator.selection;

/*
 * Implements Roulette Wheel (fitness-proportionate) selection.
 * Each chromosome's selection probability is proportional to its fitness.
 * Uses binary search for O(log n) selection.
 */

import GA.chromosome.Chromosome;
import GA.interfaces.SelectionMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RouletteWheelSelection<T extends Chromosome> implements SelectionMethod<T> {

    private final Random random = new Random();

    @Override
    public List<T> select(List<T> population, double[] fitnessValues, int numberOfParents) {
        if(population == null || population.isEmpty())
            throw new IllegalArgumentException("Population can't be null or empty!");
        if(fitnessValues.length != population.size())
            throw new IllegalArgumentException("Fitness array length must match population size.");
        if(numberOfParents <= 0)
            throw new IllegalArgumentException("Number of parents must be positive!");

        double totalFitness = 0.0;
        for(double fitness : fitnessValues)
            totalFitness += fitness;

        if(totalFitness <= 0)
            throw new IllegalArgumentException("Total fitness must be positive!");

        double[] cumulativeProbabilities = new double[population.size()];
        for(int i = 0;i < population.size();i++) {
            cumulativeProbabilities[i] = (fitnessValues[i] / totalFitness) + (i == 0 ? 0 : cumulativeProbabilities[i - 1]);
        }

        List<T> selectedParents = new ArrayList<>();
        for (int p = 0;p < numberOfParents;p++) {
            double r = random.nextDouble();
            int index = Arrays.binarySearch(cumulativeProbabilities, r);
            if(index < 0) index = -index - 1;
            selectedParents.add(population.get(index));
        }

        return selectedParents;
    }
}
