package GA.operator.selection;

/*
 * Implements Roulette Wheel (fitness-proportionate) selection.
 * Each chromosome's selection probability is proportional to its fitness.
 * Uses binary search for O(log n) selection.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import GA.chromosome.Chromosome;
import GA.interfaces.SelectionMethod;

public class RouletteWheelSelection implements SelectionMethod {
    private final Random random = new Random();

    @Override
    public List<Chromosome> select(List<Chromosome> population, int numberOfParents) {
        if(population == null || population.isEmpty())
            throw new IllegalArgumentException("Population can't be null or empty!");
        if(numberOfParents <= 0)
            throw new IllegalArgumentException("Number of parents must be positive!");

        List<Chromosome> selectedParents = new ArrayList<>();

        double totalFitness = 0;
        for (Chromosome chromosome : population) {
            totalFitness += chromosome.getFitness();
        }

        if (totalFitness == 0) {
            for (int i = 0; i < numberOfParents; i++) {
                Chromosome randomParent = population.get(random.nextInt(population.size()));
                Chromosome clonedParent = randomParent.clone();
                selectedParents.add(clonedParent);
            }
            return selectedParents;
        }

        double[] cumulativeProbabilities = new double[population.size()];
        for(int i = 0;i < population.size();i++) {
            cumulativeProbabilities[i] = (population.get(i).getFitness() / totalFitness) + (i == 0 ? 0 : cumulativeProbabilities[i - 1]);
        }

        for (int p = 0;p < numberOfParents;p++) {
            double r = random.nextDouble();
            int index = Arrays.binarySearch(cumulativeProbabilities, r);
            if(index < 0) index = -index - 1;
            selectedParents.add(population.get(index).clone());
        }

        return selectedParents;
    }
}
