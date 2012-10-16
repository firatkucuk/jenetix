
package im.firat.jenetix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Calculation {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final Logger LOG = LoggerFactory.getLogger(Calculation.class);



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private int[]   bitCounts;
    private int     chromosomeLength;
    private float   elitsGenMutationRate    = 0.1f;
    private float   elitsGenomeMutationRate = 0.1f;
    private int     geneCount;
    private float   othersMutationRate      = 0.4f;
    private int     population              = 100;
    private Problem problem;
    private double  threshold               = Double.MAX_VALUE;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Calculation(Problem problem, int[] bitCounts) {

        this.problem   = problem;
        this.bitCounts = bitCounts;
        this.geneCount = bitCounts.length;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public void calculatate(int generationCount) {

        chromosomeLength = 0;

        for (Integer bitCount : bitCounts) {
            chromosomeLength += bitCount;
        }

        int mutableBitsCountOfElits    = (int) (chromosomeLength * elitsGenomeMutationRate);
        int mutableElitsGenCount       = (int) (bitCounts.length * elitsGenMutationRate);
        int numberOfElits              = (int) (0.4 * population);
        int numberOfGenomeMatedElits   = (int) (0.1 * population);
        int numberOfGenMatedElits      = (int) (0.1 * population);
        int numberOfGenomeMutatedElits = (int) (0.1 * population);
        int numberOfGenMutatedElits    = (int) (0.1 * population);
        int numberOfShuffledElits      = (int) (0.1 * population);
        int numberOfAllElits           = 0;

        numberOfAllElits += numberOfElits + numberOfGenomeMatedElits + numberOfGenMatedElits;
        numberOfAllElits += numberOfGenomeMutatedElits + numberOfGenMutatedElits;
        numberOfAllElits += numberOfShuffledElits;

        int numberOfRandomizeds = population - numberOfAllElits;

        if (population < 7) {
            LOG.error("Execution Terminated: Population should be greater than 6");

            return;
        }

        // INITIALIZATION STAGE ----------------------------------------------------------------------------------------

        // Bir topluluk oluşturuluyor.
        List<Chromosome> individuals = generateCommunity(population);
        Random           random      = new Random(System.currentTimeMillis());

        // Verilen nesil sayısı kadar iterasyon yapılacak.
        for (int i = 0; i < generationCount; i++) {

            problem.setCurrentGeneration(i);

            // SELECTION STAGE -----------------------------------------------------------------------------------------
            // Selection method: elitist selection

            // Topluluk sağlıklı olma durumuna göre sıraya sokuluyor.
            Collections.sort(individuals);

            // En iyi elemana bakılıyor. En iyi eleman istenileni veriyor ise daha fazla işleme gerek yok.
            Chromosome best = individuals.get(0);

            LOG.debug("Best: " + best);

            if (best.getValue() >= threshold) {
                LOG.info("Chromosome found: " + best);

                break;
            }

            // Bu nesildeki topluluğa ait en iyi kromozomlar seçiliyor.

            // INDIVIDUALS
            // 125-254-128-95-45-86-28-129-134-76-19-38-11-97-56-137-74-20-89-55
            // SORTED INDIVIDUALS
            // 254-137-134-129-128-125-97-95-89-86-76-74-56-55-45-38-28-20-19-11
            // ELITS (0.1)
            // 254-137
            // MATED ELITS (0.1)
            // 254-137 -> 198-85
            // MUTATED ELITS (0.1)
            // 254-137 -> 127-49
            // MATED OTHERS (0.2)
            // 134-129-128-125 -> 28-168-59-129
            // MUTATED OTHERS (0.2)
            // 97-95-89-86 -> 123-83-57-62
            // OTHERS
            // 76-74-56-55-45-38-28-20-19-11

            List<Chromosome> elits              = individuals.subList(0, numberOfElits);
            List<Chromosome> genomeMatedElits   = cloneCommunity(individuals.subList(0, numberOfGenomeMatedElits));
            List<Chromosome> genMatedElits      = cloneCommunity(individuals.subList(0, numberOfGenMatedElits));
            List<Chromosome> genomeMutatedElits = cloneCommunity(individuals.subList(0, numberOfGenomeMutatedElits));
            List<Chromosome> genMutatedElits    = cloneCommunity(individuals.subList(0, numberOfGenMutatedElits));
            List<Chromosome> shuffledElits      = cloneCommunity(individuals.subList(0, numberOfShuffledElits));
            List<Chromosome> randomizeds        = generateCommunity(numberOfRandomizeds);

            // OPERATIONS STAGE ----------------------------------------------------------------------------------------
            // Bu metod kısmını kendime göre uyarladım.

            // Crossing over - genome based mating
            if (numberOfGenomeMatedElits > 1) {

                for (int j = 0; j < numberOfGenomeMatedElits; j += 2) {
                    crossoverGenome(genomeMatedElits.get(j), genomeMatedElits.get(j + 1));
                }
            }

            // Crossing over - gen based mating
            if (numberOfGenMatedElits > 1) {

                for (int j = 0; j < numberOfGenMatedElits; j += 2) {
                    crossoverGens(genMatedElits.get(j), genMatedElits.get(j + 1));
                }
            }


            // Genome based mutation
            for (int j = 0; j < numberOfGenomeMutatedElits; j++) {
                genomeMutatedElits.get(j).mutateGenome(mutableBitsCountOfElits);
            }

            // Gen based mutation
            for (int j = 0; j < numberOfGenMutatedElits; j++) {
                genMutatedElits.set(random.nextInt(geneCount), new Chromosome(bitCounts, problem));
            }

            // Shufling
            Collections.shuffle(shuffledElits);

            individuals = new ArrayList<Chromosome>();
            individuals.addAll(elits);
            individuals.addAll(genomeMatedElits);
            individuals.addAll(genMatedElits);
            individuals.addAll(genomeMutatedElits);
            individuals.addAll(genMutatedElits);
            individuals.addAll(shuffledElits);
            individuals.addAll(randomizeds);
        } // end for

        Collections.sort(individuals);

        Chromosome best = individuals.get(0);

        LOG.info("Found best {}", best);
        LOG.info("Genes: {}", best.getGenes());
        problem.onCompleted(best);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public int getPopulation() {

        return population;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public double getThreshold() {

        return threshold;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setPopulation(int population) {

        this.population = population;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setThreshold(double threshold) {

        this.threshold = threshold;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private List<Chromosome> cloneCommunity(List<Chromosome> elits) {

        List<Chromosome> newList = new ArrayList<Chromosome>();

        for (Chromosome chromosome : elits) {
            newList.add((Chromosome) chromosome.clone());
        }

        return newList;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void crossoverGenome(Chromosome chromosomeA, Chromosome chromosomeB) {

        int        xoverPoint = new Random(System.currentTimeMillis()).nextInt(chromosomeLength);
        List<Byte> genomeA    = chromosomeA.getGenome();
        List<Byte> genomeB    = chromosomeB.getGenome();
        List<Byte> genomeA1   = genomeA.subList(0, xoverPoint);
        List<Byte> genomeA2   = genomeA.subList(xoverPoint, chromosomeLength);
        List<Byte> genomeB1   = genomeB.subList(0, xoverPoint);
        List<Byte> genomeB2   = genomeB.subList(xoverPoint, chromosomeLength);
        List<Byte> newGenomeA = new ArrayList<Byte>(chromosomeLength);
        List<Byte> newGenomeB = new ArrayList<Byte>(chromosomeLength);

        newGenomeA.addAll(genomeA1);
        newGenomeA.addAll(genomeB2);

        newGenomeB.addAll(genomeB1);
        newGenomeB.addAll(genomeA2);

        chromosomeA.setGenome(newGenomeA);
        chromosomeB.setGenome(newGenomeB);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private void crossoverGens(Chromosome chromosomeA, Chromosome chromosomeB) {

        int        xoverPoint = new Random(System.currentTimeMillis()).nextInt(geneCount);
        List<Gene> genesA     = chromosomeA.getGenes();
        List<Gene> genesB     = chromosomeB.getGenes();
        List<Gene> genesA1    = genesA.subList(0, xoverPoint);
        List<Gene> genesA2    = genesA.subList(xoverPoint, geneCount);
        List<Gene> genesB1    = genesB.subList(0, xoverPoint);
        List<Gene> genesB2    = genesB.subList(xoverPoint, geneCount);
        List<Gene> newGenesA  = new ArrayList<Gene>(geneCount);
        List<Gene> newGenesB  = new ArrayList<Gene>(geneCount);

        newGenesA.addAll(genesA1);
        newGenesA.addAll(genesB2);

        newGenesB.addAll(genesB1);
        newGenesB.addAll(genesA2);

        chromosomeA.setGenes(newGenesA);
        chromosomeB.setGenes(newGenesB);
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private List<Chromosome> generateCommunity(int population) {

        List<Chromosome> community = new ArrayList<Chromosome>();

        for (int i = 0; i < population; i++) {
            community.add(new Chromosome(bitCounts, problem));
        }

        return community;
    }
}
