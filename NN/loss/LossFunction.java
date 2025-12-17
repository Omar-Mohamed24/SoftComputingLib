package NN.loss;

import NN.utils.Matrix;

/**
 * Interface for loss functions.
 * A loss function measures the discrepancy between the predicted output and the
 * true target.
 */
public interface LossFunction {
    double calculate(Matrix output, Matrix target);

    Matrix derivative(Matrix output, Matrix target);
}