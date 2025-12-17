package NN.loss;

import NN.utils.Matrix;

public class CrossEntropy implements LossFunction {

    private static final double EPSILON = 1e-15;

    @Override
    public double calculate(Matrix output, Matrix target) {
        double sum = 0;
        int totalElements = output.rows * output.cols;

        for (int i = 0; i < output.rows; i++) {
            for (int j = 0; j < output.cols; j++) {
                double yPred = output.data[i][j];
                double yTrue = target.data[i][j];

                yPred = Math.max(EPSILON, Math.min(1 - EPSILON, yPred));
                sum += yTrue * Math.log(yPred) + (1 - yTrue) * Math.log(1 - yPred);
            }
        }

        return -sum / totalElements;
    }

    @Override
    public Matrix derivative(Matrix output, Matrix target) {
        Matrix result = new Matrix(output.rows, output.cols);

        for (int i = 0; i < output.rows; i++) {
            for (int j = 0; j < output.cols; j++) {
                double yPred = output.data[i][j];
                double yTrue = target.data[i][j];

                yPred = Math.max(EPSILON, Math.min(1 - EPSILON, yPred));
                result.data[i][j] = (yPred - yTrue) / (yPred * (1 - yPred) * output.rows * output.cols);
            }
        }
        return result;
    }
}