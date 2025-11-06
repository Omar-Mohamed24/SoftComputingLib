package FL.interfaces;
import FL.FuzzySet;
import FL.LinguisticVariable;

public interface DefuzzifyMethod {
    double defuzzify(LinguisticVariable outputVariable, FuzzySet aggregatedSet);
}