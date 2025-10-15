package GA.operator.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import GA.chromosome.Chromosome;
import GA.interfaces.SelectionMethod;

public class RouletteWheelSelection implements SelectionMethod {
    private final Random random = new Random();

    @Override
    public List<Chromosome> select(List<Chromosome> population, int numberOfParents) {
        List<Chromosome> selectedParents = new ArrayList<>();

        double totalFitness = 0;
        for (Chromosome chromosome : population) {
            totalFitness += chromosome.getFitness();
        }

        if (totalFitness == 0) {
            for (int i = 0; i < numberOfParents; i++) {
                Chromosome randomParent = population.get(random.nextInt(population.size()));
                Chromosome clonedParent = randomParent.clone();
                selectedParents.add(clonedParent);
            }
            return selectedParents;
        }

        double[] cumulativeFitness = new double[population.size()];
        cumulativeFitness[0] = population.get(0).getFitness();
        for (int i = 1; i < population.size(); i++) {
            cumulativeFitness[i] = cumulativeFitness[i - 1] + population.get(i).getFitness();
        }

        for (int i = 0; i < numberOfParents; i++) {
            double randomPointer = random.nextDouble() * totalFitness;
            for (int j = 0; j < population.size(); j++) {
                if (randomPointer <= cumulativeFitness[j]) {
                    selectedParents.add(population.get(j).clone());
                    break;
                }
            }
        }

        return selectedParents;
    }
}
