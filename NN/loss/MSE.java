package NN.loss;

import NN.utils.Matrix;

public class MSE implements LossFunction {

    @Override
    public double calculate(Matrix output, Matrix target) {
        if (output.rows != target.rows || output.cols != target.cols) {
            throw new IllegalArgumentException("Output and Target dimensions must match.");
        }

        double sum = 0;
        int totalElements = output.rows * output.cols;

        for (int i = 0; i < output.rows; i++) {
            for (int j = 0; j < output.cols; j++) {
                double diff = output.data[i][j] - target.data[i][j];
                sum += diff * diff;
            }
        }

        return sum / totalElements;
    }

    @Override
    public Matrix derivative(Matrix output, Matrix target) {
        Matrix diff = Matrix.subtract(output, target);
        int totalElements = output.rows * output.cols;
        diff.multiply(-2.0 / totalElements);
        return diff;
    }
}