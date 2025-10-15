package GA.operator.mutation;
import java.util.Random;

import GA.chromosome.Chromosome;
import GA.interfaces.MutationMethod;

public class SwapMutation implements MutationMethod {
    private final Random random = new Random();

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int length = chromosome.getLength();
        if (length < 2) {
            return chromosome;
        }

        int index1 = random.nextInt(length);
        int index2;
        do {
            index2 = random.nextInt(length);
        } while (index1 == index2);

        Object gene1 = chromosome.getGene(index1);
        Object gene2 = chromosome.getGene(index2);
        chromosome.setGene(index1, gene2);
        chromosome.setGene(index2, gene1);

        return chromosome;
    }
}
