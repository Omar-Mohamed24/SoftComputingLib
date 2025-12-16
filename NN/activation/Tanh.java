package NN.activation;

import NN.utils.Matrix;

public class Tanh implements ActivationFunction {

    @Override
    public Matrix activate(Matrix x) {
        return x.map(val -> Math.tanh(val));
    }

    @Override
    public Matrix derivative(Matrix x) {
        return x.map(val -> {
            double tanh = Math.tanh(val);
            return 1 - (tanh * tanh);
        });
    }
}