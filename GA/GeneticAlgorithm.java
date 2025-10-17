package GA;

import GA.chromosome.BinaryChromosome;
import GA.chromosome.Chromosome;
import GA.chromosome.FloatChromosome;
import GA.chromosome.IntegerChromosome;
import GA.interfaces.*;
import GA.operator.replacement.ElitismReplacement;
import GA.operator.selection.TournamentSelection;
import GA.operator.crossover.SinglePointCrossover;
import GA.operator.mutation.BitFlipMutation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Main class for the Genetic Algorithm (GA) framework.
 * This class manages the overall GA process including initialization,
 * selection, crossover, mutation, and replacement.
 */
public class GeneticAlgorithm {

    private final Random random = new Random();

    // --- Core GA Parameters ---
    private int populationSize = 100;
    private int generations = 200;
    private double crossoverRate = 0.8;
    private double mutationRate = 0.05;
    private int chromosomeLength = 20;

    // --- Chromosome Configuration ---
    private String chromosomeType = "binary"; // "binary", "integer", or "float"
    private int intMin = 0, intMax = 100;
    private double floatMin = 0.0, floatMax = 1.0;

    // --- Core Components (Strategies) ---
    private FitnessFunction fitnessFunction;
    private SelectionMethod selectionMethod = new TournamentSelection();
    private CrossoverMethod crossoverMethod = new SinglePointCrossover();
    private MutationMethod mutationMethod = new BitFlipMutation();
    private ReplacementStrategy replacementStrategy = new ElitismReplacement(2);
    private InfeasibilityHandler infeasibilityHandler;

    // --- Results ---
    private Chromosome bestChromosome;
    private List<Chromosome> population;

    // Runs the genetic algorithm.
    public void run() {
        if (fitnessFunction == null) {
            throw new IllegalStateException("A FitnessFunction must be set before running the GA.");
        }

        initializePopulation();

        for (int gen = 0; gen < generations; gen++) {
            evaluatePopulation();
            List<Chromosome> parents = selectionMethod.select(population, populationSize);
            List<Chromosome> offspring = new ArrayList<>();

            for (int i = 0; i < parents.size(); i += 2) {
                if (i + 1 < parents.size()) {
                    Chromosome parent1 = parents.get(i);
                    Chromosome parent2 = parents.get(i + 1);
                    if (random.nextDouble() < crossoverRate) {
                        offspring.addAll(crossoverMethod.crossover(parent1, parent2));
                    } else {
                        offspring.add(parent1.clone());
                        offspring.add(parent2.clone());
                    }
                }
            }

            for (Chromosome child : offspring) {
                if (random.nextDouble() < mutationRate) {
                    mutationMethod.mutate(child);
                }
            }

            if (infeasibilityHandler != null) {
                for (int i = 0; i < offspring.size(); i++) {
                    Chromosome child = offspring.get(i);
                    if (!infeasibilityHandler.isFeasible(child)) {
                        offspring.set(i, infeasibilityHandler.repair(child));
                    }
                }
            }

            for (Chromosome child : offspring) {
                child.setFitness(fitnessFunction.evaluateFitness(child));
            }

            population = replacementStrategy.replace(population, offspring);
        }

        evaluatePopulation();
    }

    private void initializePopulation() {
        population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome;
            switch (chromosomeType.toLowerCase()) {
                case "integer":
                    chromosome = new IntegerChromosome(chromosomeLength, intMin, intMax);
                    break;
                case "float":
                    chromosome = new FloatChromosome(chromosomeLength, floatMin, floatMax);
                    break;
                case "binary":
                default:
                    chromosome = new BinaryChromosome(chromosomeLength);
                    break;
            }
            chromosome.initialize();

            if (infeasibilityHandler != null && !infeasibilityHandler.isFeasible(chromosome)) {
                chromosome = infeasibilityHandler.repair(chromosome);
            }

            population.add(chromosome);
        }
    }

    private void evaluatePopulation() {
        for (Chromosome chromosome : population) {
            double fitness = fitnessFunction.evaluateFitness(chromosome);
            chromosome.setFitness(fitness);
        }

        Chromosome currentBest = population.stream()
                .max(Comparator.comparingDouble(Chromosome::getFitness))
                .orElse(null);

        if (bestChromosome == null || (currentBest != null && currentBest.getFitness() > bestChromosome.getFitness())) {
            bestChromosome = currentBest.clone();
        }
    }

    // --- Getters and Setters for Configuration ---

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setChromosomeLength(int chromosomeLength) {
        this.chromosomeLength = chromosomeLength;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setSelectionMethod(SelectionMethod selectionMethod) {
        this.selectionMethod = selectionMethod;
    }

    public void setCrossoverMethod(CrossoverMethod crossoverMethod) {
        this.crossoverMethod = crossoverMethod;
    }

    public void setMutationMethod(MutationMethod mutationMethod) {
        this.mutationMethod = mutationMethod;
    }

    public void setReplacementStrategy(ReplacementStrategy replacementStrategy) {
        this.replacementStrategy = replacementStrategy;
    }

    public void setInfeasibilityHandler(InfeasibilityHandler infeasibilityHandler) {
        this.infeasibilityHandler = infeasibilityHandler;
    }

    public void setChromosomeType(String type, Number min, Number max) {
        this.chromosomeType = type;
        if (type.equalsIgnoreCase("integer")) {
            this.intMin = min.intValue();
            this.intMax = max.intValue();
        } else if (type.equalsIgnoreCase("float")) {
            this.floatMin = min.doubleValue();
            this.floatMax = max.doubleValue();
        }
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }
}