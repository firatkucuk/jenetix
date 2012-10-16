
package im.firat.jenetix.uav;

import im.firat.jenetix.Calculation;
import im.firat.jenetix.Problem;



public class UavMain {



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public UavMain() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {

        Problem     problem     = new UavProblem();
        int[]       bitCounts   = new int[] { // -
            6, 6, 6, 6, 6,                    // 05
            6, 6, 6, 6, 6,                    // 10
            6, 6, 6, 6, 6,                    // 15
            // 6, 6, 6, 6, 6,                    // 20
            // 6, 6, 6, 6, 6,                    // 25
            // 6, 6, 6, 6, 6,                    // 30
            // 6, 6, 6, 6, 6,                    // 35
            // 6, 6, 6, 6, 6,                    // 40
            // 6, 6, 6, 6, 6,                    // 45
            // 6, 6, 6, 6, 6,                    // 50
            // 6, 6, 6, 6, 6,                    // 55
            // 6, 6, 6, 6, 6,                    // 60
            // 6, 6, 6, 6                        // 64
        };
        Calculation calculation = new Calculation(problem, bitCounts);

        calculation.setPopulation(50000);
        calculation.calculatate(20);
    }
}
