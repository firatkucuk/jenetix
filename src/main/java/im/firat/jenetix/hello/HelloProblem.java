
package im.firat.jenetix.hello;

import im.firat.jenetix.Chromosome;
import im.firat.jenetix.Gene;
import im.firat.jenetix.Problem;
import java.util.List;



public class HelloProblem extends Problem {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final int[] HELLO = new int[] { 0x68, 0x65, 0x6C, 0x6C, 0x6F }; // 104, 101, 108, 108, 111



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public HelloProblem() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public double calculateFitness(Chromosome chromosome) {

        List<Gene> genes    = chromosome.getGenes();
        double     distance = 0;

        for (int i = 0; i < HELLO.length; i++) {
            distance += 255 - Math.abs(HELLO[i] - genes.get(i).getValue());
        }

        return distance;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public void onCompleted(Chromosome chromosome) {

    }
}
