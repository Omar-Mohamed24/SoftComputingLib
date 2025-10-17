package CaseStudy.knapsack;

import GA.chromosome.Chromosome;
import GA.interfaces.FitnessFunction;
import java.util.List;

/**
 * Implementation of the FitnessFunction for the 0/1 Knapsack problem.
 */
public class KnapsackFitness implements FitnessFunction {

    private final List<Item> items;
    private final double maxWeight;

    public KnapsackFitness(List<Item> items, double maxWeight) {
        this.items = items;
        this.maxWeight = maxWeight;
    }

    @Override
    public double evaluateFitness(Chromosome chromosome) {
        double totalWeight = 0;
        int totalValue = 0;

        for (int i = 0; i < chromosome.getLength(); i++) {
            if ((int) chromosome.getGene(i) == 1) {
                totalWeight += items.get(i).getWeight();
                totalValue += items.get(i).getValue();
            }
        }

        if (totalWeight > maxWeight) {
            double penalty = (totalWeight - maxWeight) * 1000;
            return Math.max(0, totalValue - penalty);
        }

        return totalValue;
    }
}
