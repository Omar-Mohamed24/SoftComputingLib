package FL.membership_functions;

import FL.interfaces.MembershipFunction;

public class TriangularMF implements MembershipFunction {
    private final double a, b, c;

    public TriangularMF(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getDegreeOfMembership(double crispValue) {
        if (crispValue <= a || crispValue >= c) {
            return 0.0;
        } else if (crispValue == b) {
            return 1.0;
        } else if (crispValue > a && crispValue < b) {
            return (crispValue - a) / (b - a);
        } else {
            return (c - crispValue) / (c - b);
        }
    }
}
