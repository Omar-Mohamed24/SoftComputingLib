package CaseStudy.NQueens;

import GA.chromosome.Chromosome;
import GA.interfaces.FitnessFunction;
import java.util.List;

public class NQueenFitness implements FitnessFunction {

    @Override
    public double evaluateFitness(Chromosome chromosome) {
        Object[] genes = chromosome.getGenes();
        int n = genes.length;
        int conflicts = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int r1 = ((Number) chromosome.getGene(i)).intValue();
                int r2 = ((Number) chromosome.getGene(j)).intValue();

                // if(r1 == r2 || (Math.abs(r1 - r2) == Math.abs(i - j)) : for normal NQueens.
                // Modified NQueens (attack row, col and one square diagonal instead of whole diagonal).
                if (r1 == r2 || (Math.abs(r1 - r2) == 1 && Math.abs(i - j) == 1)) {
                    conflicts++;
                }
            }
        }

        int maxConflicts = (n * (n - 1)) / 2;
        return 1.0 - ((double)conflicts / maxConflicts);
    }
}
