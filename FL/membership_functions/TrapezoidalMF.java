package FL.membership_functions;

import FL.interfaces.MembershipFunction;

public class TrapezoidalMF implements MembershipFunction {
    private final double a, b, c, d;

    public TrapezoidalMF(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public double getDegreeOfMembership(double crispValue) {
        if (crispValue <= a || crispValue >= d) {
            return 0.0;
        } else if (crispValue >= b && crispValue <= c) {
            return 1.0;
        } else if (crispValue > a && crispValue < b) {
            return (crispValue - a) / (b - a);
        } else {
            return (d - crispValue) / (d - c);
        }
    }
}
