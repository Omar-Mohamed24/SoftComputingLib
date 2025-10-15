package GA.interfaces;
import java.util.List;
import GA.chromosome.Chromosome;

/**
 * Interface for parent selection strategies.
 * Implementations define how parents are selected for reproduction.
 */
public interface SelectionMethod {
    List<T> select(List<T> population, int numberOfParents);
}
