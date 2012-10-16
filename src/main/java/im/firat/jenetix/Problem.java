
package im.firat.jenetix;


public abstract class Problem {



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    protected int currentGeneration;



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public abstract double calculateFitness(Chromosome chromosome);



    //~ ----------------------------------------------------------------------------------------------------------------

    public abstract void onCompleted(Chromosome chromosome);



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setCurrentGeneration(int currentGeneration) {

        this.currentGeneration = currentGeneration;
    }
}
