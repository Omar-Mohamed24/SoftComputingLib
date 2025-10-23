package GA.operator.crossover;

import GA.chromosome.Chromosome;
import GA.interfaces.CrossoverMethod;
import java.util.*;

public class OrderOneCrossover implements CrossoverMethod {
    private final Random random = new Random();

    private void fillRemainingGenes(Chromosome child, Chromosome donor, Set<Object> used, int start, int end) {
        int cnt = 0;
        for(int i = 0;i < donor.getLength();i++) {
            if(i >= start && i <= end) continue;
            Object gene = donor.getGene(i);
            if(!used.contains(gene)) {
                child.setGene(cnt, gene);
                cnt++;
            }
        }
    }

    @Override
    public List<Chromosome> crossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getLength();
        int p1 = random.nextInt(length);
        int p2 = random.nextInt(length);

        if (p1 > p2) {
            int temp = p1;
            p1 = p2;
            p2 = temp;
        }

        List<Chromosome> children = new ArrayList<>();
        Chromosome child1 = parent1.clone();
        Chromosome child2 = parent2.clone();

        Set<Object> genesChild1 = new HashSet<>();
        Set<Object> genesChild2 = new HashSet<>();

        for (int i = p1; i <= p2; i++) {
            Object g1 = parent1.getGene(i);
            Object g2 = parent2.getGene(i);

            child1.setGene(i, g1);
            child2.setGene(i, g2);

            genesChild1.add(g1);
            genesChild2.add(g2);
        }

        fillRemainingGenes(child1, parent2, genesChild1, p1, p2);
        fillRemainingGenes(child2, parent1, genesChild2, p1, p2);

        children.add(child1);
        children.add(child2);
        return children;
    }
}
