package GA.operator.replacement;

import GA.chromosome.Chromosome;
import GA.interfaces.ReplacementStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SteadyStateReplacement implements ReplacementStrategy {
    private final int numReplacements;

    public SteadyStateReplacement() {
        this.numReplacements = 1;
    }

    public SteadyStateReplacement(int numReplacements) {
        this.numReplacements = numReplacements;
    }

    @Override
    public List<Chromosome> replace(List<Chromosome> currentPopulation, List<Chromosome> offspring) {
        if (currentPopulation == null || offspring == null)
            throw new IllegalArgumentException("Populations cannot be null.");
        if (currentPopulation.isEmpty())
            throw new IllegalArgumentException("Current population cannot be empty.");

        List<Chromosome> newPopulation = new ArrayList<>(currentPopulation);

        newPopulation.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());
        offspring.sort(Comparator.comparingDouble(Chromosome::getFitness).reversed());

        int replaceCount = Math.min(numReplacements, Math.min(offspring.size(), newPopulation.size()));
        for (int i = 0;i < replaceCount;i++) {
            int index = newPopulation.size() - i - 1;
            newPopulation.set(index, offspring.get(i));
        }

        return newPopulation;
    }
}
