
package im.firat.jenetix;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;



public class ChromosomeTest {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Test
    public void cloneChromosome() {

        Problem mockProblem = mock(Problem.class);
        int[]   bitCounts   = new int[] { 2, 2 };

        Chromosome chromosome = new Chromosome(bitCounts, mockProblem);
        chromosome.setGenome(Arrays.asList((byte) 1, (byte) 0, (byte) 1, (byte) 1));

        Chromosome cloneChromosome = (Chromosome) chromosome.clone();
        cloneChromosome.getGenome().set(0, (byte) 0);

        assertFalse(chromosome.getGenome().get(0) == cloneChromosome.getGenome().get(0));

        List<Gene> genes      = chromosome.getGenes();
        List<Gene> cloneGenes = cloneChromosome.getGenes();

        cloneGenes.get(0).setValue(3);

        assertFalse(genes.get(0).getValue() == cloneGenes.get(0).getValue());
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void initializeChromosome() {

        Problem    mockProblem = mock(Problem.class);
        int[]      bitCounts   = new int[] { 5, 4, 6 };
        Chromosome chromosome  = new Chromosome(bitCounts, mockProblem);

        assert chromosome.getGenes().size() == 3;
        assert chromosome.getGenome().size() == 15;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Test
    public void setGenome() {

        Problem    mockProblem = mock(Problem.class);
        int[]      bitCounts   = new int[] { 5, 4, 6 };
        Chromosome chromosome  = new Chromosome(bitCounts, mockProblem);
        List<Byte> newGenome   = Arrays.asList(                            // Gene List
                (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 1,          // Gene 1
                (byte) 0, (byte) 0, (byte) 1, (byte) 1,                    // Gene 2
                (byte) 1, (byte) 0, (byte) 1, (byte) 1, (byte) 0, (byte) 1 // Gene 3
            );

        chromosome.setGenome(newGenome);

        List<Gene> genes = chromosome.getGenes();

        assert genes.get(0).getValue() == 23;
        assert genes.get(1).getValue() == 3;
        assert genes.get(2).getValue() == 45;
    }

}
