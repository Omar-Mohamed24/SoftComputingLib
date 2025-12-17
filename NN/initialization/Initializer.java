package NN.initialization;

import NN.utils.Matrix;

public interface Initializer {

    void init(Matrix weights);
}