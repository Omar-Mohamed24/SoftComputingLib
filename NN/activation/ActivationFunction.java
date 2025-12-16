package NN.activation;
import NN.utils.Matrix;

/**
 * Interface for all activation functions.
 * An activation function defines the output of a node given an input or set of
 * inputs.
 */
public interface ActivationFunction {
    Matrix activate(Matrix x);
    Matrix derivative(Matrix x);
}