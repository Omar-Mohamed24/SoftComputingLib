package GA.operator.replacement;

import GA.chromosome.Chromosome;
import GA.interfaces.ReplacementStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ElitismReplacement implements ReplacementStrategy {
    private final int elitismCount;

    public ElitismReplacement() {
        this.elitismCount = 1;
    }

    public ElitismReplacement(int elitismCount) {
        this.elitismCount = elitismCount;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> currentPopulation, List<Chromosome> offspring) {
        if (currentPopulation == null || offspring == null)
            throw new IllegalArgumentException("Populations cannot be null.");
        if (currentPopulation.isEmpty())
            throw new IllegalArgumentException("Current population cannot be empty.");

        List<Chromosome> newPopulation = new ArrayList<>();

        currentPopulation.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());
        offspring.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        int eliteCount = Math.min(elitismCount, currentPopulation.size());
        for (int i = 0;i < eliteCount;i++) {
            newPopulation.add(currentPopulation.get(i));
        }

        int remaining = currentPopulation.size() - newPopulation.size();
        for (int i = 0;i < remaining && i < offspring.size();i++) {
            newPopulation.add(offspring.get(i));
        }

        return newPopulation;
    }
}
