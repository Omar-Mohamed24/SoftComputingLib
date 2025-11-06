package FL.operators;

import FL.interfaces.T_Norm;

public class Min_T_Norm implements T_Norm {

    @Override
    public double evaluate(double a, double b) {
        return Math.min(a, b);
    }
}
