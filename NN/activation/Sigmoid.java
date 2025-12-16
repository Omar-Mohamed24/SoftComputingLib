package NN.activation;

import NN.utils.Matrix;

public class Sigmoid implements ActivationFunction {

    @Override
    public Matrix activate(Matrix x) {
        return x.map(val -> 1.0 / (1.0 + Math.exp(-val)));
    }

    @Override
    public Matrix derivative(Matrix x) {
        Matrix sigmoid = activate(x);
        return sigmoid.map(val -> val * (1 - val));
    }
}