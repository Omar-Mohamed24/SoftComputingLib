package GA.operator.selection;

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
        List<Chromosome> selectedParents = new ArrayList<>();

        for (int i = 0; i < numberOfParents; i++) {
            List<Chromosome> tournamentContestants = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(population.size());
                tournamentContestants.add(population.get(randomIndex));
            }

            Chromosome winner = null;
            double bestFitness = -1.0;
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
package GA.operator.selection;

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
        List<Chromosome> selectedParents = new ArrayList<>();

        for (int i = 0; i < numberOfParents; i++) {
            List<Chromosome> tournamentContestants = new ArrayList<>();
            for (int j = 0; j < tournamentSize; j++) {
                int randomIndex = random.nextInt(population.size());
                tournamentContestants.add(population.get(randomIndex));
            }

            Chromosome winner = null;
            double bestFitness = -1.0;
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
