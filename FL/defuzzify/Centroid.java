package FL.defuzzify;

import FL.FuzzySet;
import FL.LinguisticVariable;
import FL.interfaces.DefuzzifyMethod;

public class Centroid implements DefuzzifyMethod {
    private static final int SAMPLES = 1000;
    private static final double EPSILON = 1e-5;

    @Override
    public double defuzzify(LinguisticVariable outputVariable, FuzzySet aggregatedSet) {
        double minDomain = outputVariable.getMinDomain();
        double maxDomain = outputVariable.getMaxDomain();

        if (minDomain >= maxDomain) {
            throw new IllegalArgumentException("Invalid domain: min domain must be less than max domain");
        }

        double stepSize = (maxDomain - minDomain) / SAMPLES;
        double numerator = 0.0;
        double denominator = 0.0;
        for (int i = 0; i <= SAMPLES; i++) {
            double crispValue = minDomain + (i * stepSize);
            double membership = aggregatedSet.getDegreeOfMembership(crispValue);

            numerator += crispValue * membership;
            denominator += membership;
        }

        if (Math.abs(denominator) < EPSILON) {
            return (minDomain + maxDomain) / 2.0;
        }

        return numerator / denominator;
    }
}
