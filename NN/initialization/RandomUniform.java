package NN.initialization;

import NN.utils.Matrix;
import java.util.Random;

public class RandomUniform implements Initializer {

    private final double min;
    private final double max;
    private final Random random;

    public RandomUniform(double min, double max) {
        this.min = min;
        this.max = max;
        this.random = new Random();
    }

    public RandomUniform() {
        this(-0.5, 0.5);
    }

    @Override
    public void init(Matrix weights) {
        for (int i = 0; i < weights.rows; i++) {
            for (int j = 0; j < weights.cols; j++) {
                weights.data[i][j] = min + (max - min) * random.nextDouble();
            }
        }
    }
}