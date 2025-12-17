package NN.initialization;

import NN.utils.Matrix;
import java.util.Random;

public class Xavier implements Initializer {

    private final Random random;

    public Xavier() {
        this.random = new Random();
    }

    @Override
    public void init(Matrix weights) {
        double limit = Math.sqrt(6.0 / (weights.rows + weights.cols));

        for (int i = 0; i < weights.rows; i++) {
            for (int j = 0; j < weights.cols; j++) {
                weights.data[i][j] = (2 * limit * random.nextDouble()) - limit;
            }
        }
    }
}