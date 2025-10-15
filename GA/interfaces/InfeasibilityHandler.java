package GA.interfaces;
import GA.chromosome.Chromosome;

/**
 * Interface to check for and handle invalid solutions.
 * Implementations should define how to detect and handle infeasible chromosomes.
 */
public interface InfeasibilityHandler {
    boolean isFeasible(T chromosome);
    T repair(T chromosome);
}
