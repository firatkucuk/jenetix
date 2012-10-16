
package im.firat.jenetix.hello;

import im.firat.jenetix.Calculation;
import im.firat.jenetix.Problem;



public class HelloMain {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public HelloMain() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Problem     problem     = new HelloProblem();
        Calculation calculation = new Calculation(problem, new int[] { 8, 8, 8, 8, 8 });

        calculation.setPopulation(1000);
        calculation.setThreshold(255 * 5);
        calculation.calculatate(100);
    }
}
