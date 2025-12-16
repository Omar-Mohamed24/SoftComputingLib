package NN.activation;

import NN.utils.Matrix;

public class ReLU implements ActivationFunction {

    @Override
    public Matrix activate(Matrix x) {
        return x.map(val -> Math.max(0, val));
    }

    @Override
    public Matrix derivative(Matrix x) {
        return x.map(val -> val > 0 ? 1 : 0);
    }
}