package NN.activation;

import NN.utils.Matrix;

public class Linear implements ActivationFunction {

    @Override
    public Matrix activate(Matrix x) {
        return x.map(val -> val);
    }

    @Override
    public Matrix derivative(Matrix x) {
        return x.map(val -> 1);
    }
}