package FL;

import java.util.HashMap;
import java.util.Map;

public class Rule {
    private final Map<String, String> antecedents;
    private final Map<String, String> consequents;
    private double weight;
    private boolean enabled;

    public Rule() {
        this.antecedents = new HashMap<>();
        this.consequents = new HashMap<>();
        this.weight = 1.0;
        this.enabled = true;
    }

    public void addAntecedent(String variableName, String setName) {
        antecedents.put(variableName, setName);
    }

    public void addConsequent(String variableName, String setName) {
        consequents.put(variableName, setName);
    }


    public Map<String, String> getAntecedents() {
        return antecedents;
    }

    public Map<String, String> getConsequents() {
        return consequents;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "IF " + antecedents.toString() + " THEN " + consequents.toString();
    }
}
