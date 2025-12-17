package NN;

import NN.layers.Layer;
import NN.loss.LossFunction;
import NN.loss.MSE;
import NN.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

    private final List<Layer> layers;
    private LossFunction lossFunction;

    public NeuralNetwork() {
        this.layers = new ArrayList<>();
        this.lossFunction = new MSE();
    }

    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    public void setLossFunction(LossFunction lossFunction) {
        this.lossFunction = lossFunction;
    }

    public Matrix predict(Matrix input) {
        Matrix output = input;
        for (Layer layer : layers) {
            output = layer.forward(output);
        }
        return output;
    }

    public void fit(Matrix X, Matrix Y, int epochs, double learningRate) {
        if (X.cols != Y.cols) {
            throw new IllegalArgumentException(
                    "Input samples (" + X.cols + ") and Label samples (" + Y.cols + ") must match.");
        }

        System.out.println("Starting training for " + epochs + " epochs...");

        for (int i = 1; i <= epochs; i++) {
            // 1. Forward Pass (through all layers)
            Matrix output = predict(X);

            // 2. Calculate Loss
            if (i % 100 == 0 || i == epochs) {
                double loss = lossFunction.calculate(output, Y);
                System.out.println("Epoch " + i + "/" + epochs + " - Loss: " + String.format("%.6f", loss));
            }

            // 3. Backward Pass (Backpropagation)

            // A. Calculate initial gradient from the Loss Function (dA)
            Matrix error = lossFunction.derivative(output, Y);

            // B. Propagate error backwards through layers
            for (int j = layers.size() - 1; j >= 0; j--) {
                Layer layer = layers.get(j);
                error = layer.backward(error, learningRate);
            }
        }
        System.out.println("Training completed.");
    }
}