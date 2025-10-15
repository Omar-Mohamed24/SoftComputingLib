package GA.chromosome;
import java.util.Random;

public class IntegerChromosome extends Chromosome {
    private static final Random random = new Random();
    private final int minValue;
    private final int maxValue;

    public IntegerChromosome(int length, int minValue, int maxValue) {
        super(length);
        if (minValue > maxValue) {
            throw new IllegalArgumentException("minValue cannot be greater than maxValue.");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public void initialize() {
        for (int i = 0; i < getLength(); i++) {
            genes[i] = random.nextInt((maxValue - minValue) + 1) + minValue;
        }
    }

    public int getMin() {
        return minValue;
    }

    public int getMax() {
        return maxValue;
    }
}
