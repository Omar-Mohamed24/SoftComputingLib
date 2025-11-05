package FL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinguisticVariable {
    private final String name;
    private final List<FuzzySet> fuzzySets;
    private double minDomain = Double.NEGATIVE_INFINITY;
    private double maxDomain = Double.POSITIVE_INFINITY;

    public LinguisticVariable(String name) {
        this.name = name;
        this.fuzzySets = new ArrayList<>();
    }

    public LinguisticVariable(String name, double minDomain, double maxDomain) {
        this(name);
        this.minDomain = minDomain;
        this.maxDomain = maxDomain;
    }

    public void addFuzzySet(FuzzySet set) {
        fuzzySets.add(set);
    }


    public Map<String, Double> fuzzify(double crispValue) {
        double clampedValue = clamp(crispValue);

        Map<String, Double> fuzzifiedValues = new HashMap<>();
        for (FuzzySet set : fuzzySets) {
            fuzzifiedValues.put(set.getName(), set.getDegreeOfMembership(clampedValue));
        }
        return fuzzifiedValues;
    }

    private double clamp(double value) {
        if (value < minDomain) {
            return minDomain;
        }
        if (value > maxDomain) {
            return maxDomain;
        }
        return value;
    }

    public String getName() {
        return name;
    }

    public List<FuzzySet> getFuzzySets() {
        return fuzzySets;
    }
}