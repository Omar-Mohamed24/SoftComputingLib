package FCaseStudy;

import FL.FuzzySet;
import FL.FuzzySystem;
import FL.LinguisticVariable;
import FL.Rule;
import FL.defuzzify.Centroid;
import FL.inference.MamdaniInference;
import FL.membership_functions.GaussianMF;
import FL.membership_functions.TrapezoidalMF;
import FL.membership_functions.TriangularMF;

import java.util.HashMap;
import java.util.Map;

/**
 * A Case Study for the Fuzzy Logic Library: An Advanced Tipping Calculator.
 *
 * Problem Description:
 * Calculate the tip percentage (0% - 30%) based on three inputs:
 * 1. Waiting Time (0 - 60 minutes)
 * 2. Food Quality (0 - 10 rating)
 * 3. Service Quality (0 - 10 rating)
 */
public class TippingSolver {
    public static void main(String[] args) {
        FuzzySystem fs = new FuzzySystem();

        fs.setInferenceMethod(new MamdaniInference());
        fs.setDefuzzifyMethod(new Centroid());
        LinguisticVariable waitingTime = new LinguisticVariable("WaitingTime", 0, 60);
        waitingTime.addFuzzySet(new FuzzySet("Short", new TrapezoidalMF(0, 0, 5, 15)));
        waitingTime.addFuzzySet(new FuzzySet("Medium", new TriangularMF(10, 20, 35)));
        waitingTime.addFuzzySet(new FuzzySet("Long", new TrapezoidalMF(30, 45, 60, 60)));
        fs.addLinguisticVariable(waitingTime);

        LinguisticVariable food = new LinguisticVariable("Food", 0, 10);
        food.addFuzzySet(new FuzzySet("Bad", new TrapezoidalMF(0, 0, 2, 4)));
        food.addFuzzySet(new FuzzySet("Decent", new TriangularMF(3, 5, 7)));
        food.addFuzzySet(new FuzzySet("Delicious", new TrapezoidalMF(6, 8, 10, 10)));
        fs.addLinguisticVariable(food);

        LinguisticVariable service = new LinguisticVariable("Service", 0, 10);
        service.addFuzzySet(new FuzzySet("Poor", new GaussianMF(0, 2.0)));
        service.addFuzzySet(new FuzzySet("Good", new GaussianMF(5, 1.5)));
        service.addFuzzySet(new FuzzySet("Excellent", new GaussianMF(10, 2.0)));
        fs.addLinguisticVariable(service);

        LinguisticVariable tip = new LinguisticVariable("Tip", 0, 30);
        tip.addFuzzySet(new FuzzySet("Low", new TriangularMF(0, 5, 10)));
        tip.addFuzzySet(new FuzzySet("Average", new TriangularMF(10, 15, 20)));
        tip.addFuzzySet(new FuzzySet("Generous", new TriangularMF(20, 25, 30)));
        fs.addLinguisticVariable(tip);

        Rule r1 = new Rule();
        r1.addAntecedent("Service", "Poor");
        r1.addAntecedent("Food", "Bad");

        // Scenario 1: Everything is bad -> Low Tip
        Rule badRule = new Rule();
        badRule.addAntecedent("Service", "Poor");
        badRule.addAntecedent("Food", "Bad");
        badRule.addConsequent("Tip", "Low");
        fs.addRule(badRule);

        // Scenario 2: Long wait time -> Low Tip (regardless of others)
        Rule waitRule = new Rule();
        waitRule.addAntecedent("WaitingTime", "Long");
        waitRule.addConsequent("Tip", "Low");
        fs.addRule(waitRule);

        // Scenario 3: Average experience -> Average Tip
        Rule avgRule = new Rule();
        avgRule.addAntecedent("Service", "Good");
        avgRule.addAntecedent("Food", "Decent");
        avgRule.addAntecedent("WaitingTime", "Medium");
        avgRule.addConsequent("Tip", "Average");
        fs.addRule(avgRule);

        // Scenario 4: Great food and service -> Generous Tip
        Rule greatRule = new Rule();
        greatRule.addAntecedent("Service", "Excellent");
        greatRule.addAntecedent("Food", "Delicious");
        greatRule.addConsequent("Tip", "Generous");
        fs.addRule(greatRule);

        // Scenario 5: Short wait time boosts tip
        Rule fastRule = new Rule();
        fastRule.addAntecedent("WaitingTime", "Short");
        fastRule.addAntecedent("Food", "Delicious");
        fastRule.addConsequent("Tip", "Generous");
        fs.addRule(fastRule);

        System.out.println("--- Tipping Calculator Case Study ---");
        // Test Case 1: Terrible experience
        evaluate(fs, 50, 2, 1); // Long wait, bad food, poor service

        // Test Case 2: Average experience
        evaluate(fs, 20, 5, 5); // Medium wait, decent food, good service

        // Test Case 3: Fantastic experience
        evaluate(fs, 5, 9, 9.5); // Short wait, delicious food, excellent service
    }

    private static void evaluate(FuzzySystem fs, double waitingTime, double food, double service) {
        System.out.println("\nEvaluating Inputs:");
        System.out.println("  Waiting Time: " + waitingTime + " min");
        System.out.println("  Food Quality: " + food + "/10");
        System.out.println("  Service:      " + service + "/10");

        Map<String, Double> inputs = new HashMap<>();
        inputs.put("WaitingTime", waitingTime);
        inputs.put("Food", food);
        inputs.put("Service", service);

        Map<String, Double> outputs = fs.evaluate(inputs);

        System.out.println("Result:");
        System.out.printf("  Recommended Tip: %.2f%%\n", outputs.get("Tip"));
    }
}