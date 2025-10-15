package GA.operator.selection;

/*
 * Implements Tournament selection.
 * Best fitness chromosome is selected from a fixed number of individuals (tournament) chosen randomly.
 */

import GA.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GA.interfaces.SelectionMethod;

public class TournamentSelection implements SelectionMethod {

    private final Random random = new Random();
    private final int tournamentSize;

    public TournamentSelection() {
        this.tournamentSize = 2;
    }

    public TournamentSelection(int tournamentSize) {
        if (tournamentSize < 1) {
            throw new IllegalArgumentException("Tournament size must be at least 1.");
        }
        this.tournamentSize = tournamentSize;
    }

    @Override
    public List<Chromosome> select(List<Chromosome> population, int numberOfParents) {
        if(population == null || population.isEmpty())
            throw new IllegalArgumentException("Population can't be null or empty!");
        if(numberOfParents <= 0)
            throw new IllegalArgumentException("Number of parents must be positive!");

        List<Chromosome> selectedParents = new ArrayList<>();

        for (int i = 0; i < numberOfParents; i++) {
            List<Chromosome> tournamentContestants = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(population.size());
                tournamentContestants.add(population.get(randomIndex));
            }

            Chromosome winner = null;
            double bestFitness = Double.NEGATIVE_INFINITY;
            for (Chromosome contestant : tournamentContestants) {
                if (winner == null || contestant.getFitness() > bestFitness) {
                    bestFitness = contestant.getFitness();
                    winner = contestant;
                }
            }

            if (winner != null) {
                selectedParents.add(winner.clone());
            }
        }

        return selectedParents;
    }
}
