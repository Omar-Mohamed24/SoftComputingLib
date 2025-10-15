package GA.operator.replacement;

import GA.chromosome.Chromosome;
import GA.interfaces.ReplacementStrategy;

import java.util.List;

public class GenerationalReplacement implements ReplacementStrategy {
    @Override
    public List<Chromosome> replace(List<Chromosome> currentPopulation, List<Chromosome> offspring) {
        return offspring;
    }
}
