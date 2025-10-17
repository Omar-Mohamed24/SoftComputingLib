package CaseStudy;

import java.util.ArrayList;
import java.util.List;

import CaseStudy.knapsack.Item;
import CaseStudy.knapsack.KnapsackFitness;
import CaseStudy.knapsack.KnapsackInfeasibilityHandler;
import GA.GeneticAlgorithm;
import GA.chromosome.Chromosome;
import GA.operator.crossover.SinglePointCrossover;
import GA.operator.mutation.BitFlipMutation;
import GA.operator.replacement.ElitismReplacement;
import GA.operator.selection.TournamentSelection;

public class CaseStudyApplication {
    public static void main(String[] args) {

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