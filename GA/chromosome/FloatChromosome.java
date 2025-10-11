package GA.chromosome;
import java.util.Random;

public class FloatChromosome extends Chromosome {
    private static final Random random = new Random();
    private final double minValue;
    private final double maxValue;

    public FloatChromosome(int length, double minValue, double maxValue) {
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
            genes[i] = minValue + (maxValue - minValue) * random.nextDouble();
        }
    }
}
