package FL;

import FL.defuzzify.Centroid;
import FL.inference.MamdaniInference;
import FL.inference.SugenoInference;
import FL.interfaces.*;
import FL.operators.Max_S_Norm;
import FL.operators.Min_T_Norm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzySystem {

    private final List<LinguisticVariable> variables;
    private final RuleBase ruleBase;

    private InferenceMethod inferenceMethod;
    private DefuzzifyMethod defuzzifyMethod;
    private T_Norm andOperator;
    private S_Norm orOperator;

    public FuzzySystem() {
        this.variables = new ArrayList<>();
        this.ruleBase = new RuleBase();

        this.inferenceMethod = new MamdaniInference();
        this.defuzzifyMethod = new Centroid();
        this.andOperator = new Min_T_Norm();
        this.orOperator = new Max_S_Norm();
    }

    public void addLinguisticVariable(LinguisticVariable variable) {
        this.variables.add(variable);
    }

    public void addRule(Rule rule) {
        this.ruleBase.addRule(rule);
    }

    public RuleBase getRuleBase() {
        return ruleBase;
    }

    public void setInferenceMethod(InferenceMethod inferenceMethod) {
        this.inferenceMethod = inferenceMethod;
    }

    public void setDefuzzifyMethod(DefuzzifyMethod defuzzifyMethod) {
        this.defuzzifyMethod = defuzzifyMethod;
    }

    public void setAndOperator(T_Norm andOperator) {
        this.andOperator = andOperator;
    }

    public void setOrOperator(S_Norm orOperator) {
        this.orOperator = orOperator;
    }

    public Map<String, Double> evaluate(Map<String, Double> crispInputs) {

        if (inferenceMethod instanceof MamdaniInference) {
            ((MamdaniInference) inferenceMethod).setVariables(variables);
        } else if (inferenceMethod instanceof SugenoInference) {
            ((SugenoInference) inferenceMethod).setVariables(variables);
        }

        Map<String, Map<String, Double>> fuzzifiedInputs = new HashMap<>();
        for (LinguisticVariable var : variables) {
            if (crispInputs.containsKey(var.getName())) {
                double val = crispInputs.get(var.getName());
                fuzzifiedInputs.put(var.getName(), var.fuzzify(val));
            }
        }

        Map<String, Object> rawOutputs = inferenceMethod.process(
                fuzzifiedInputs,
                ruleBase,
                andOperator,
                orOperator);

        Map<String, Double> crispOutputs = new HashMap<>();
        for (Map.Entry<String, Object> entry : rawOutputs.entrySet()) {
            String outVarName = entry.getKey();
            Object rawResult = entry.getValue();

            if (rawResult instanceof Double) {
                crispOutputs.put(outVarName, (Double) rawResult);
            } else if (rawResult instanceof FuzzySet) {
                FuzzySet aggregatedShape = (FuzzySet) rawResult;

                LinguisticVariable outVar = getVariable(outVarName);
                if (outVar != null) {
                    double result = defuzzifyMethod.defuzzify(outVar, aggregatedShape);
                    crispOutputs.put(outVarName, result);
                }
            }
        }

        return crispOutputs;
    }

    public LinguisticVariable getVariable(String name) {
        for (LinguisticVariable var : variables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }
}