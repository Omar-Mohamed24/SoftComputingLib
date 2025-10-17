package CaseStudy.knapsack;
import GA.chromosome.Chromosome;
import GA.interfaces.InfeasibilityHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implements the advanced InfeasibilityHandler for the 0/1 Knapsack problem.
 * It can both check for and attempt to repair overweight solutions.
 */
public class KnapsackInfeasibilityHandler implements InfeasibilityHandler {

    private final List<Item> items;
    private final double maxWeight;
    private final Random random = new Random();

    public KnapsackInfeasibilityHandler(List<Item> items, double maxWeight) {
        this.items = items;
        this.maxWeight = maxWeight;
    }

    private double calculateWeight(Chromosome chromosome) {
        double totalWeight = 0;
        for (int i = 0; i < chromosome.getLength(); i++) {
            if ((int) chromosome.getGene(i) == 1) {
                totalWeight += items.get(i).getWeight();
            }
        }
        return totalWeight;
    }

    @Override
    public boolean isFeasible(Chromosome chromosome) {
        return calculateWeight(chromosome) <= maxWeight;
    }

    @Override
    public Chromosome repair(Chromosome chromosome) {
        if (isFeasible(chromosome)) {
            return chromosome;
        }

        Chromosome repaired = chromosome.clone();
        double currentWeight = calculateWeight(repaired);

        List<Integer> selectedIndices = new ArrayList<>();
        for (int i = 0; i < repaired.getLength(); i++) {
            if ((int) repaired.getGene(i) == 1) {
                selectedIndices.add(i);
            }
        }

        Collections.shuffle(selectedIndices, random);
        for (int indexToRemove : selectedIndices) {
            if (currentWeight <= maxWeight) {
                break; 
            }
            repaired.setGene(indexToRemove, 0);
            currentWeight -= items.get(indexToRemove).getWeight();
        }
        return repaired;
    }
}
