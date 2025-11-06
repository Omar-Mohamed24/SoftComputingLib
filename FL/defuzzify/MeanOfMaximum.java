package FL.defuzzify;

import FL.FuzzySet;
import FL.LinguisticVariable;
import FL.interfaces.DefuzzifyMethod;

public class MeanOfMaximum implements DefuzzifyMethod {
    private static final int SAMPLES = 1000;
    private static final double EPSILON = 1e-9;

    @Override
    public double defuzzify(LinguisticVariable outputVariable, FuzzySet aggregatedSet) {
        double minDomain = outputVariable.getMinDomain();
        double maxDomain = outputVariable.getMaxDomain();

        if (minDomain >= maxDomain) {
            throw new IllegalArgumentException("Invalid domain: min domain must be less than max domain");
        }

        double stepSize = (maxDomain - minDomain) / SAMPLES;
        double maxMembership = 0.0;
        for (int i = 0; i <= SAMPLES; i++) {
            double crispValue = minDomain + (i * stepSize);
            double membership = aggregatedSet.getDegreeOfMembership(crispValue);
            if (membership > maxMembership) {
                maxMembership = membership;
            }
        }

        if (maxMembership <= 0.0) {
            return (minDomain + maxDomain) / 2.0;
        }

        double sumOfMaxPoints = 0.0;
        int countOfMaxPoints = 0;
        for (int i = 0; i <= SAMPLES; i++) {
            double crispValue = minDomain + (i * stepSize);
            double membership = aggregatedSet.getDegreeOfMembership(crispValue);

            if (Math.abs(membership - maxMembership) < EPSILON) {
                sumOfMaxPoints += crispValue;
                countOfMaxPoints++;
            }
        }

        return countOfMaxPoints > 0 ? sumOfMaxPoints / countOfMaxPoints : (minDomain + maxDomain) / 2.0;
    }
}
