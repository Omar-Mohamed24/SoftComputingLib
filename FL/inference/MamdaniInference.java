package FL.inference;

import FL.*;
import FL.interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MamdaniInference implements InferenceMethod {
    private List<LinguisticVariable> variables = new ArrayList<>();

    public void setVariables(List<LinguisticVariable> variables) {
        this.variables = variables;
    }

    @Override
    public Map<String, Object> process(
            Map<String, Map<String, Double>> fuzzifiedInputs,
            RuleBase ruleBase,
            T_Norm andOperator,
            S_Norm orOperator) {

        Map<String, Object> results = new HashMap<>();

        // Helper map to quickly find variables by name
        Map<String, LinguisticVariable> varMap = new HashMap<>();
        for (LinguisticVariable var : this.variables) {
            varMap.put(var.getName(), var);
        }

        Map<String, List<RuleOutput>> outputsByVar = new HashMap<>();

        for (Rule rule : ruleBase.getActiveRules()) {
            double firingStrength = 1.0;
            boolean first = true;

            for (Map.Entry<String, String> entry : rule.getAntecedents().entrySet()) {
                String varName = entry.getKey();
                String setName = entry.getValue();

                Double inputVal = 0.0;
                if (fuzzifiedInputs.containsKey(varName) && fuzzifiedInputs.get(varName).containsKey(setName)) {
                    inputVal = fuzzifiedInputs.get(varName).get(setName);
                }

                if (first) {
                    firingStrength = inputVal;
                    first = false;
                } else {
                    firingStrength = andOperator.evaluate(firingStrength, inputVal);
                }
            }

            firingStrength *= rule.getWeight();

            if (firingStrength > 0) {
                for (Map.Entry<String, String> entry : rule.getConsequents().entrySet()) {
                    String outVarName = entry.getKey();
                    String outSetName = entry.getValue();

                    LinguisticVariable outVar = varMap.get(outVarName);
                    if (outVar != null) {
                        Optional<FuzzySet> setOpt = outVar.getFuzzySets().stream()
                                .filter(s -> s.getName().equals(outSetName))
                                .findFirst();

                        if (setOpt.isPresent()) {
                            outputsByVar.putIfAbsent(outVarName, new ArrayList<>());
                            outputsByVar.get(outVarName).add(new RuleOutput(firingStrength, setOpt.get()));
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, List<RuleOutput>> entry : outputsByVar.entrySet()) {
            String varName = entry.getKey();
            List<RuleOutput> ruleOutputs = entry.getValue();

            MembershipFunction aggregatedMF = new MembershipFunction() {
                @Override
                public double getDegreeOfMembership(double x) {
                    double maxDegree = 0.0;
                    for (RuleOutput ro : ruleOutputs) {
                        double ruleDegree = Math.min(ro.firingStrength, ro.set.getDegreeOfMembership(x));
                        maxDegree = orOperator.evaluate(maxDegree, ruleDegree);
                    }
                    return maxDegree;
                }
            };

            results.put(varName, new FuzzySet("Aggregated_" + varName, aggregatedMF));
        }

        return results;
    }

    // Helper class to store (Strength, Set) pairs
    private static class RuleOutput {
        double firingStrength;
        FuzzySet set;

        RuleOutput(double f, FuzzySet s) {
            firingStrength = f;
            set = s;
        }
    }
}