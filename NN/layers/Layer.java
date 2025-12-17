package NN.layers;

import NN.activation.ActivationFunction;
import NN.initialization.Initializer;
import NN.utils.Matrix;

/**
 * Represents a fully connected (Dense) layer in the Neural Network.
 * It manages its own weights, biases, and the forward/backward pass logic.
 */
public class Layer {

    public Matrix weights; // Dimensions: [outputSize x inputSize]
    public Matrix biases; // Dimensions: [outputSize x 1]

    public ActivationFunction activation;

    // Cache for Backpropagation
    private Matrix lastInput; // The input 'x' received in forward()
    private Matrix lastZ; // The pre-activation sum 'z' (Wx + b)

    public Layer(int inputSize, int outputSize, ActivationFunction activation, Initializer initializer) {
        this.activation = activation;

        this.weights = new Matrix(outputSize, inputSize);
        initializer.init(this.weights);

        this.biases = new Matrix(outputSize, 1);
    }

    public Matrix forward(Matrix input) {
        this.lastInput = input;

        Matrix weightedSum = Matrix.multiply(this.weights, input);
        addBias(weightedSum, this.biases);
        this.lastZ = weightedSum;

        return this.activation.activate(this.lastZ);
    }

    public Matrix backward(Matrix outputGradient, double learningRate) {
        // 1. Calculate gradient of activation: f'(Z)
        Matrix activationGradient = this.activation.derivative(this.lastZ);

        // 2. Calculate error at this layer (dZ): dZ = dA * f'(Z) (Element-wise)
        Matrix dZ = new Matrix(outputGradient.rows, outputGradient.cols);
        for (int i = 0; i < dZ.rows; i++) {
            for (int j = 0; j < dZ.cols; j++) {
                dZ.data[i][j] = outputGradient.data[i][j] * activationGradient.data[i][j];
            }
        }

        // 3. Calculate Weight Gradient (dW): dW = dZ * Input_Transpose
        Matrix inputT = Matrix.transpose(this.lastInput);
        Matrix dW = Matrix.multiply(dZ, inputT);

        // 4. Calculate Bias Gradient (db): Sum of dZ across columns (for batches)
        Matrix db = new Matrix(this.biases.rows, 1);
        for (int i = 0; i < dZ.rows; i++) {
            double sum = 0;
            for (int j = 0; j < dZ.cols; j++) {
                sum += dZ.data[i][j];
            }
            db.data[i][0] = sum;
        }

        // 5. Calculate Gradient for Previous Layer (dX): dX = Weights_Transpose * dZ
        Matrix weightsT = Matrix.transpose(this.weights);
        Matrix dX = Matrix.multiply(weightsT, dZ);

        // 6. Update Weights and Biases (Gradient Descent)
        // W = W - learningRate * dW
        dW.multiply(learningRate); // Scale by LR
        this.weights = Matrix.subtract(this.weights, dW);

        // b = b - learningRate * db
        db.multiply(learningRate);
        this.biases = Matrix.subtract(this.biases, db);

        return dX;
    }

    private void addBias(Matrix z, Matrix b) {
        for (int i = 0; i < z.rows; i++) {
            for (int j = 0; j < z.cols; j++) {
                z.data[i][j] += b.data[i][0];
            }
        }
    }
}
