package FL.operators;

import FL.interfaces.S_Norm;

public class Max_S_Norm implements S_Norm {

    @Override
    public double evaluate(double a, double b) {
        return Math.max(a, b);
    }
}
