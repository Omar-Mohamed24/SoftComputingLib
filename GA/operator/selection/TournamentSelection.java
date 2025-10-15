package GA.operator.selection;

/*
 * Implements Tournament selection.
 * Best fitness chromosome is selected from a fixed number of individuals (tournament) chosen randomly.
 */

import GA.chromosome.Chromosome;
import GA.interfaces.SelectionMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection<T extends Chromosome> implements SelectionMethod<T> {

    private final int tournamentSize;
    private final Random random = new Random();

    public TournamentSelection(int tournamentSize) {
            if(tournamentSize <= 0)
                throw new IllegalArgumentException("Tournament size must be positive!");
            this.tournamentSize = tournamentSize;
    }

    private T runTournament(List<T> population, double[] fitnessValues) {
        T best = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        for(int i = 0;i < tournamentSize;i++) {
            int index = random.nextInt(population.size());
            double fitness = fitnessValues[index];
            if(fitness > bestFitness) {
                best = population.get(index);
                bestFitness = fitness;
            }
        }

        return best;
    }

    @Override
    public List<T> select(List<T> population, double[] fitnessValues, int numberOfParents) {
        if(population == null || population.isEmpty())
            throw new IllegalArgumentException("Population can't be null or empty!");
        if(fitnessValues.length != population.size())
            throw new IllegalArgumentException("Fitness array length must match population size.");
        if(numberOfParents <= 0)
            throw new IllegalArgumentException("Number of parents must be positive!");

        List<T> selectedParents = new ArrayList<>();

        for(int i = 0;i < numberOfParents;i++) {
            T winner = runTournament(population, fitnessValues);
            selectedParents.add(winner);
        }

        return selectedParents;
    }
}
