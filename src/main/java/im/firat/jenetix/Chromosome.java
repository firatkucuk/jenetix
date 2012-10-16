
package im.firat.jenetix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Chromosome implements Comparable<Chromosome>, Cloneable {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private int[]      bitCounts;
    private List<Gene> genes;
    private List<Byte> genome;
    private Problem    problem;
    private double     value;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Chromosome(int[] bitCounts, Problem problem) {

        this.bitCounts = bitCounts;
        this.problem   = problem;
        this.genes     = new ArrayList<Gene>(bitCounts.length);
        this.genome    = new ArrayList<Byte>();

        for (int i = 0, l = bitCounts.length; i < l; i++) {
            Gene gene = new Gene(bitCounts[i]);
            genome.addAll(gene.getBits());
            genes.add(gene);
        }

        this.value = problem.calculateFitness(this);
    }



    private Chromosome() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        Chromosome chromosome = new Chromosome();
        chromosome.problem   = this.problem;
        chromosome.bitCounts = this.bitCounts.clone();
        chromosome.genes     = new ArrayList<Gene>();
        chromosome.genome    = new ArrayList<Byte>(genome);
        chromosome.value     = this.value;

        for (Gene gene : genes) {
            chromosome.genes.add((Gene) gene.clone());
        }

        return chromosome;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    /**
     * Azalan sıralama için ayarlandı.
     *
     * @param   other
     *
     * @return
     */
    @Override
    public int compareTo(Chromosome other) {

        double otherValue = other.getValue();

        if (value == otherValue) {
            return 0;
        } else if (value > otherValue) {
            return -1;
        } else {
            return 1;
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public List<Gene> getGenes() {

        return genes;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public List<Byte> getGenome() {

        return genome;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public double getValue() {

        return value;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void mutateGenome(int mutableBitsCount) {

        Random random       = new Random();
        int    genomeLength = genome.size();

        for (int i = 0; i < mutableBitsCount; i++) {
            genome.set(random.nextInt(genomeLength), (byte) random.nextInt(1));
        }

        updateGenome();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setGenome(List<Byte> genome) {

        this.genome = genome;

        updateGenome();
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        // return "Chromosome[value: " + value + ", genome: " + genome + ']';
        return "Chromosome[" + value + ']';
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    void setGenes(List<Gene> genes) {

        this.genes  = genes;
        this.genome = new ArrayList<Byte>();

        for (Gene gene : genes) {
            genome.addAll(gene.getBits());
        }

        this.value = problem.calculateFitness(this);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void updateGenome() {

        genes.clear();

        int k = 0;

        for (int i = 0; i < bitCounts.length; i++) {
            int        length = bitCounts[i];
            List<Byte> bits   = new ArrayList<Byte>(length);

            for (int j = 0; j < length; j++) {
                bits.add(genome.get(k++));
            }

            genes.add(new Gene(bits));
        }

        this.value = problem.calculateFitness(this);
    }
}
