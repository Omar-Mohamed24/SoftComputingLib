package CaseStudy.NQueens;

import GA.chromosome.Chromosome;
import GA.interfaces.InfeasibilityHandler;

import java.util.*;

public class NQueensInfeasibilityHandler implements InfeasibilityHandler {
    private final int n;
    private final Random random = new Random();

    public NQueensInfeasibilityHandler(int n) {
        this.n = n;
    }

    @Override
    public boolean isFeasible(Chromosome chromosome) {
        Set<Integer> seen = new HashSet<>();

        for(int i = 0;i < chromosome.getLength();i++) {
            int geneVal = ((Number) chromosome.getGene(i)).intValue();

            if(geneVal < 1 || geneVal > n || !seen.add(geneVal)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Chromosome repair(Chromosome chromosome) {
        if (isFeasible(chromosome)) {
            return chromosome;
        }

        Chromosome repaired = chromosome.clone();
        Set<Integer> seen = new HashSet<>();
        List<Integer> missing = new ArrayList<>();

        for(int i = 1;i <= n;i++) {
            missing.add(i);
        }

        for(int i = 0;i < repaired.getLength();i++) {
            int geneVal = ((Number) repaired.getGene(i)).intValue();

            if(seen.contains(geneVal)) {
                repaired.setGene(i, null);
            } else {
              seen.add(geneVal);
              missing.remove((Integer) geneVal);
            }
        }

        Collections.shuffle(missing, random);

        for(int i = 0;i < repaired.getLength() && !missing.isEmpty();i++) {
            if(repaired.getGene(i) == null) {
                repaired.setGene(i, missing.getFirst());
                missing.removeFirst();
            }
        }

        return repaired;
    }
}
