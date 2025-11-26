package FL.inference;
import FL.*;
import FL.interfaces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SugenoInference implements InferenceMethod {

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

        Map<String, LinguisticVariable> varMap = new HashMap<>();
        for (LinguisticVariable var : this.variables) {
            varMap.put(var.getName(), var);
        }

        Map<String, Double> numerators = new HashMap<>();
        Map<String, Double> denominators = new HashMap<>();
        for (Rule rule : ruleBase.getActiveRules()) {
            double w = 1.0;
            boolean first = true;

            for (Map.Entry<String, String> entry : rule.getAntecedents().entrySet()) {
                String varName = entry.getKey();
                String setName = entry.getValue();

                Double inputVal = fuzzifiedInputs.containsKey(varName) ? fuzzifiedInputs.get(varName).get(setName) : 0.0;

                if (first) {
                    w = inputVal;
                    first = false;
                } else {
                    w = andOperator.evaluate(w, inputVal);
                }
            }
            w *= rule.getWeight();

            if (w > 0) {
                for (Map.Entry<String, String> entry : rule.getConsequents().entrySet()) {
                    String outVarName = entry.getKey();
                    String outSetName = entry.getValue();

                    LinguisticVariable outVar = varMap.get(outVarName);
                    if (outVar != null) {
                        Optional<FuzzySet> setOpt = outVar.getFuzzySets().stream()
                                .filter(s -> s.getName().equals(outSetName))
                                .findFirst();

                        if (setOpt.isPresent()) {
                            double z = findPeak(setOpt.get(), outVar.getMinDomain(), outVar.getMaxDomain());

                            numerators.put(outVarName, numerators.getOrDefault(outVarName, 0.0) + (w * z));
                            denominators.put(outVarName, denominators.getOrDefault(outVarName, 0.0) + w);
                        }
                    }
                }
            }
        }

        for (String varName : numerators.keySet()) {
            double num = numerators.get(varName);
            double den = denominators.get(varName);
            double finalOutput = (den == 0) ? 0 : (num / den);
            results.put(varName, finalOutput);
        }

        return results;
    }

    private double findPeak(FuzzySet set, double min, double max) {
        double bestX = min;
        double maxVal = -1;
        double step = (max - min) / 100.0;
        for (double x = min; x <= max; x += step) {
            double y = set.getDegreeOfMembership(x);
            if (y > maxVal) {
                maxVal = y;
                bestX = x;
            }
        }
        return bestX;
    }
}