
package im.firat.jenetix.uav;


public class Node {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    public static final byte SPACE   = 0;
    public static final byte REAL    = 1;
    public static final byte VIRTUAL = 2;



    //~ --- [INSTANCE FIELDS] ------------------------------------------------------------------------------------------

    private int    id;
    private String name;
    private byte   type;
    private double x;
    private double y;



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public Node(int id, String name, byte type, double x, double y) {

        this.id   = id;
        this.name = name;
        this.type = type;
        this.x    = x;
        this.y    = y;
    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    public int getId() {

        return id;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public String getName() {

        return name;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public byte getType() {

        return type;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public double getX() {

        return x;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public double getY() {

        return y;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setId(int id) {

        this.id = id;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setName(String name) {

        this.name = name;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setType(byte type) {

        this.type = type;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setX(double x) {

        this.x = x;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    public void setY(double y) {

        this.y = y;
    }
}
