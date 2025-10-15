package GA.operator.replacement;

import GA.chromosome.Chromosome;
import GA.interfaces.ReplacementStrategy;

import java.util.List;

public class GenerationalReplacement implements ReplacementStrategy {
    @Override
    public List<Chromosome> replace(List<Chromosome> currentPopulation, List<Chromosome> offspring) {
        if (currentPopulation == null || offspring == null)
            throw new IllegalArgumentException("Populations cannot be null.");
        if (currentPopulation.isEmpty())
            throw new IllegalArgumentException("Current population cannot be empty.");

        return offspring;
    }
}
