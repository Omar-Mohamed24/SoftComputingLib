package CaseStudy.knapsack;

public class Item {
    private final String name;
    private final double weight;
    private final int value;

    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
