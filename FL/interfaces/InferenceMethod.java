package FL.interfaces;
import java.util.Map;
import FL.RuleBase;

/**
 * Defines the contract for a fuzzy inference engine.
 *
 * The engine's job is to take the fuzzified inputs from all variables,
 * apply them to the rules in the rule base using the specified operators,
 * and produce a result for each output variable.
 */
public interface InferenceMethod {
    Map<String, Object> process(Map<String, Map<String, Double>> fuzzifiedInputs, RuleBase ruleBase, T_Norm andOperator, S_Norm orOperator);
}