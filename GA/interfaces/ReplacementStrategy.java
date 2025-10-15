package GA.interfaces;
import java.util.List;
import GA.chromosome.Chromosome;

/**
 * Interface for replacement strategies.
 * Implementations define how to replace individuals in the population with offspring.
 */
public interface ReplacementStrategy {
    List<T> replace(List<T> currentPopulation, List<T> offspring, double[] fitnessValues, double[] offspringFitness);
}
