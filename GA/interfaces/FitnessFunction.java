package GA.interfaces;
import GA.chromosome.Chromosome;

/**
 * Interface for problem-specific fitness evaluation.
 * Implementations should define how to calculate the fitness value for a given chromosome.
 */
public interface FitnessFunction {
    double evaluateFitness(T chromosome);
}
