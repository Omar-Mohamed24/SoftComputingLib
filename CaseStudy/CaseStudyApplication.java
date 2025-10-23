package CaseStudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CaseStudy.NQueens.NQueenFitness;
import CaseStudy.NQueens.NQueensInfeasibilityHandler;
import CaseStudy.knapsack.Item;
import CaseStudy.knapsack.KnapsackFitness;
import CaseStudy.knapsack.KnapsackInfeasibilityHandler;
import GA.GeneticAlgorithm;
import GA.chromosome.Chromosome;
import GA.operator.crossover.NPointCrossover;
import GA.operator.crossover.OrderOneCrossover;
import GA.operator.crossover.SinglePointCrossover;
import GA.operator.mutation.BitFlipMutation;
import GA.operator.mutation.SwapMutation;
import GA.operator.mutation.UniformMutation;
import GA.operator.replacement.ElitismReplacement;
import GA.operator.selection.TournamentSelection;

public class CaseStudyApplication {
    public static void KnapsackApplication() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Laptop", 3, 2500));
        items.add(new Item("Camera", 2, 1500));
        items.add(new Item("Headphones", 0.5, 300));
        items.add(new Item("Book", 1, 50));
        items.add(new Item("Water Bottle", 0.5, 20));
        items.add(new Item("Jacket", 2, 150));
        items.add(new Item("Tablet", 1.5, 1200));
        items.add(new Item("Charger", 0.2, 100));
        items.add(new Item("Snacks", 1, 60));

        double maxWeight = 7.0;

        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulationSize(100);
        ga.setGenerations(150);
        ga.setChromosomeLength(items.size());
        ga.setChromosomeType("binary", 0, 1);

        ga.setFitnessFunction(new KnapsackFitness(items, maxWeight));
        ga.setInfeasibilityHandler(new KnapsackInfeasibilityHandler(items, maxWeight));

        ga.setSelectionMethod(new TournamentSelection());
        ga.setCrossoverMethod(new SinglePointCrossover());
        ga.setMutationMethod(new BitFlipMutation());
        ga.setReplacementStrategy(new ElitismReplacement());
        ga.setCrossoverRate(0.85);
        ga.setMutationRate(0.10);

        System.out.println("--- Running GA to Solve the 0/1 Knapsack Problem ---");
        System.out.println("Number of items: " + items.size());
        System.out.println("Knapsack capacity: " + maxWeight + " kg");
        System.out.println("------------------------------------------------------");

        ga.run();

        Chromosome bestSolution = ga.getBestChromosome();

        if (bestSolution != null) {
            System.out.println("\n--- Best Solution Found ---");
            System.out.println("Gene Sequence: " + bestSolution);

            List<String> selectedItems = new ArrayList<>();
            double totalWeight = 0;

            for (int i = 0; i < bestSolution.getLength(); i++) {
                if ((int) bestSolution.getGene(i) == 1) {
                    Item item = items.get(i);
                    selectedItems.add(item.getName());
                    totalWeight += item.getWeight();
                }
            }

            System.out.println("Selected Items: " + selectedItems);
            System.out.println("Total Weight: " + totalWeight + " kg");
            System.out.println("Knapsack Capacity: " + maxWeight + " kg");

            KnapsackInfeasibilityHandler handler = new KnapsackInfeasibilityHandler(items, maxWeight);
            boolean isFeasible = handler.isFeasible(bestSolution);
            System.out.println("Solution is feasible: " + isFeasible);

            if (!isFeasible) {
                System.out.println("WARNING: Solution exceeds weight capacity by " +
                        String.format("%.2f", totalWeight - maxWeight) + " kg");
            }

            System.out.println("Total Value (Fitness): " + bestSolution.getFitness());
            System.out.println("---------------------------");
        } else {
            System.out.println("No solution was found.");
        }
    }

    public static void NQueensApplication() {
        System.out.println("=== N-Queens Problem ===");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of Queens (N): ");
        int n = scanner.nextInt();

        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.setPopulationSize(100);
        ga.setGenerations(150);
        ga.setChromosomeLength(n);
        ga.setChromosomeType("integer", 1, n);

        ga.setFitnessFunction(new NQueenFitness());
        ga.setInfeasibilityHandler(new NQueensInfeasibilityHandler(n));

        ga.setSelectionMethod(new TournamentSelection());
        ga.setCrossoverMethod(new OrderOneCrossover());
        ga.setMutationMethod(new SwapMutation());
        ga.setReplacementStrategy(new ElitismReplacement());
        ga.setCrossoverRate(0.85);
        ga.setMutationRate(0.10);

        System.out.printf("--- Running GA to Solve the %d Queen Problem ---", n);
        System.out.println("------------------------------------------------------");

        ga.run();

        Chromosome bestSolution = ga.getBestChromosome();
        if (bestSolution != null) {
            System.out.println("\n--- Best Solution Found ---");
            System.out.println("Gene Sequence (Row Positions per Column): " + bestSolution);

            NQueensInfeasibilityHandler handler = new NQueensInfeasibilityHandler(n);
            boolean isFeasible = handler.isFeasible(bestSolution);
            System.out.println("Solution is feasible: " + isFeasible);

            if (!isFeasible) {
                System.out.println("WARNING: The solution violates constraints (duplicate rows or short diagonal conflict).");
                System.out.println("Attempting repair...");
                Chromosome repaired = handler.repair(bestSolution);
                System.out.println("Repaired Sequence: " + repaired);
                if(handler.isFeasible(repaired)) {
                    System.out.println("Repaired Solution is Feasible.");
                } else {
                    System.out.println("Repaired Solution is Not Feasible.");
                }
            }

            System.out.println("Best Fitness Score: " + bestSolution.getFitness());
            System.out.println("---------------------------");
        } else {
            System.out.println("No solution was found.");
        }
    }

    public static void main(String[] args) {
//        KnapsackApplication();
        NQueensApplication();
    }
}

/*
 * --- Best Solution Found ---
 * Gene Sequence: [1, 1, 1, 0, 0, 0, 1, 0, 0]
 * Selected Items: [Laptop, Camera, Headphones, Tablet]
 * Total Weight: 7.0 kg
 * Knapsack Capacity: 7.0 kg
 * Solution is feasible: true
 * Total Value (Fitness): 5500.0
 */