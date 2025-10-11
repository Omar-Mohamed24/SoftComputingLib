package GA.chromosome;

import java.util.Random;

/**
 * Represents solutions as arrays of 0s and 1s.
 * Each gene is a boolean value (true=1, false=0).
 */
public class BinaryChromosome extends Chromosome {
    private static final Random random = new Random();

    public BinaryChromosome(int length) {
        super(length);
    }

    @Override
    public void initialize() {
        for (int i = 0; i < getLength(); i++) {
            genes[i] = random.nextBoolean();
        }
    }
}
