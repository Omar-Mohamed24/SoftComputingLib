package GA.operator.mutation;
import java.util.Random;
import GA.chromosome.BinaryChromosome;
import GA.chromosome.Chromosome;
import GA.interfaces.MutationMethod;

public class BitFlipMutation implements MutationMethod {
    private final Random random = new Random();
    private final double geneMutationProbability;

    public BitFlipMutation() {
        this.geneMutationProbability = 0.01;
    }

    public BitFlipMutation(double geneMutationProbability) {
        this.geneMutationProbability = geneMutationProbability;
    }

    @Override
    public Chromosome mutate(Chromosome chromosome) {
        if (!(chromosome instanceof BinaryChromosome)) {
            throw new IllegalArgumentException("BitFlipMutation can only be applied to BinaryChromosome.");
        }

        for (int i = 0; i < chromosome.getLength(); i++) {
            if (random.nextDouble() <= this.geneMutationProbability) {
                int currentGene = (int) chromosome.getGene(i);
                chromosome.setGene(i, 1 - currentGene);
            }
        }
        return chromosome;
    }
}
