package FL.membership_functions;

import FL.interfaces.MembershipFunction;

public class GaussianMF implements MembershipFunction {
    private final double mean;
    private final double standardDeviation;

    public GaussianMF(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    @Override
    public double getDegreeOfMembership(double crispValue) {
        double exponent = -Math.pow(crispValue - mean, 2) / (2 * Math.pow(standardDeviation, 2));
        return Math.exp(exponent);
    }
}
