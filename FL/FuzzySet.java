package FL;

import FL.interfaces.MembershipFunction;

public class FuzzySet {
    private final String name;
    private final MembershipFunction membershipFunction;

    public FuzzySet(String name, MembershipFunction membershipFunction) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("FuzzySet name cannot be null or empty.");
        }
        if (membershipFunction == null) {
            throw new IllegalArgumentException("MembershipFunction cannot be null.");
        }
        this.name = name;
        this.membershipFunction = membershipFunction;
    }

    public double getDegreeOfMembership(double crispValue) {
        return membershipFunction.getDegreeOfMembership(crispValue);
    }

    public String getName() {
        return name;
    }

    public MembershipFunction getMembershipFunction() {
        return membershipFunction;
    }
}
