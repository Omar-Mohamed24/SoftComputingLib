package NN.utils;

import java.util.Random;

/**
 * Utility class for data preprocessing.
 * Handles normalization, shuffling, and splitting of datasets.
 */
public class DataUtils {

    public static void normalize(double[][] data) {
        int rows = data.length;
        if (rows == 0)
            return;
        int cols = data[0].length;

        for (int j = 0; j < cols; j++) {
            double min = Double.MAX_VALUE;
            double max = -Double.MAX_VALUE;

            for (int i = 0; i < rows; i++) {
                if (data[i][j] < min)
                    min = data[i][j];
                if (data[i][j] > max)
                    max = data[i][j];
            }

            double range = max - min;
            if (range == 0)
                continue;

            for (int i = 0; i < rows; i++) {
                data[i][j] = (data[i][j] - min) / range;
            }
        }
    }

    public static void shuffle(double[][] x, double[][] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("X and Y must have the same number of samples.");
        }

        Random rand = new Random();
        for (int i = x.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            double[] tempX = x[i];
            x[i] = x[j];
            x[j] = tempX;

            double[] tempY = y[i];
            y[i] = y[j];
            y[j] = tempY;
        }
    }

    public static class Split {
        public Matrix X_train; // [Features x TrainSamples]
        public Matrix Y_train; // [Classes x TrainSamples]
        public Matrix X_test; // [Features x TestSamples]
        public Matrix Y_test; // [Classes x TestSamples]
    }

    public static Split trainTestSplit(double[][] x, double[][] y, double testRatio) {
        shuffle(x, y);

        int totalSamples = x.length;
        int testSize = (int) (totalSamples * testRatio);
        int trainSize = totalSamples - testSize;

        int inputFeatures = x[0].length;
        int outputClasses = y[0].length;

        Matrix xTrain = new Matrix(inputFeatures, trainSize);
        Matrix yTrain = new Matrix(outputClasses, trainSize);
        Matrix xTest = new Matrix(inputFeatures, testSize);
        Matrix yTest = new Matrix(outputClasses, testSize);

        for (int i = 0; i < trainSize; i++) {
            for (int j = 0; j < inputFeatures; j++)
                xTrain.data[j][i] = x[i][j];
            for (int k = 0; k < outputClasses; k++)
                yTrain.data[k][i] = y[i][k];
        }

        for (int i = 0; i < testSize; i++) {
            int sourceIdx = trainSize + i;
            for (int j = 0; j < inputFeatures; j++)
                xTest.data[j][i] = x[sourceIdx][j];
            for (int k = 0; k < outputClasses; k++)
                yTest.data[k][i] = y[sourceIdx][k];
        }

        Split split = new Split();
        split.X_train = xTrain;
        split.Y_train = yTrain;
        split.X_test = xTest;
        split.Y_test = yTest;
        return split;
    }
}