package GA.interfaces;
import GA.chromosome.Chromosome;

/**
 * Interface for mutation strategies.
 * Implementations define how to mutate chromosomes to introduce variation.
 */
public interface MutationMethod {
    T mutate(T chromosome);
}
