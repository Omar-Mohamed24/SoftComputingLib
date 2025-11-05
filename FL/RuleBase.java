package FL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RuleBase {
    private final List<Rule> rules;

    public RuleBase() {
        this.rules = new ArrayList<>();
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    public Rule getRule(int index) {
        if (index < 0 || index >= rules.size()) {
            throw new IndexOutOfBoundsException("Rule index is out of bounds.");
        }
        return rules.get(index);
    }

    public void editRule(int index, Rule newRule) {
        if (index < 0 || index >= rules.size()) {
            throw new IndexOutOfBoundsException("Rule index is out of bounds.");
        }
        this.rules.set(index, newRule);
    }

    public void disableRule(int index) {
        getRule(index).setEnabled(false);
    }

    public void enableRule(int index) {
        getRule(index).setEnabled(true);
    }

    public void setRuleWeight(int index, double weight) {
        getRule(index).setWeight(weight);
    }

    public List<Rule> getActiveRules() {
        return rules.stream()
                .filter(Rule::isEnabled)
                .collect(Collectors.toList());
    }

    public List<Rule> getAllRules() {
        return rules;
    }

    public int getRuleCount() {
        return rules.size();
    }
}
