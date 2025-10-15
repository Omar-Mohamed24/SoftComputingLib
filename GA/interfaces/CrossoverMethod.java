package GA.interfaces;
import java.util.List;
import GA.chromosome.Chromosome;

/**
 * Interface for crossover strategies.
 * Implementations define how to combine parent chromosomes to create offspring.
 */
public interface CrossoverMethod {
    List<Chromosome> crossover(Chromosome parent1, Chromosome parent2);
}
