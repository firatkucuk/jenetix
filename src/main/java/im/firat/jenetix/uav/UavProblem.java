
package im.firat.jenetix.uav;

import im.firat.jenetix.Chromosome;
import im.firat.jenetix.Gene;
import im.firat.jenetix.Problem;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class UavProblem extends Problem {



    //~ --- [STATIC FIELDS/INITIALIZERS] -------------------------------------------------------------------------------

    private static final Map<Integer, Node> NODES  = new HashMap<Integer, Node>();
    private static final double[][][][]     RADARS = new double[3][2][2][2];

    private static final int X       = 0;
    private static final int Y       = 1;
    private static final int RADAR_1 = 0;
    private static final int RADAR_2 = 1;
    private static final int RADAR_3 = 2;
    private static final int LINE_1  = 0;
    private static final int LINE_2  = 1;
    private static final int POINT_1 = 0;
    private static final int POINT_2 = 1;

    private static final double TOTAL_FITNESS        = 10000000d;
    private static final double FEE_REAL_NODE_PASS   = 1000000d;
    private static final double FEE_NODE_DUPLICATION = 100000d;
    private static final double FEE_IN_RADAR_RANGE   = 10000d;
    private static final double FEE_DISTANCE_FACTOR  = 1000d;
    private static final double FEE_SPACE            = 100d;
    private static final double FEE_SAME_NEIGHBOURS  = 10d;
    private static final double FEE_VIRTUAL_NODE     = 1d;



    static {
        NODES.put(0, new Node(0, "NODE A", Node.REAL, 323.25d, 694.98d));
        NODES.put(1, new Node(1, "NODE B", Node.REAL, 455.58d, 570.74d));
        NODES.put(2, new Node(2, "NODE C", Node.REAL, 194.96d, 499.02d));
        NODES.put(3, new Node(3, "NODE D", Node.REAL, 318.20d, 424.26d));
        NODES.put(4, new Node(4, "NODE E", Node.REAL, 269.71d, 255.57d));
        NODES.put(5, new Node(5, "NODE F", Node.REAL, 424.26d, 182.84d));
        NODES.put(6, new Node(6, "R1 VIRTUAL 01", Node.VIRTUAL, 344.46d, 604.07d));
        NODES.put(7, new Node(7, "R1 VIRTUAL 02", Node.VIRTUAL, 370.73d, 576.80d));
        NODES.put(8, new Node(8, "R1 VIRTUAL 03", Node.VIRTUAL, 383.86d, 542.45d));
        NODES.put(9, new Node(9, "R1 VIRTUAL 04", Node.VIRTUAL, 374.77d, 505.08d));
        NODES.put(10, new Node(10, "R1 VIRTUAL 05", Node.VIRTUAL, 348.50d, 477.80d));
        NODES.put(11, new Node(11, "R1 VIRTUAL 06", Node.VIRTUAL, 307.09d, 467.70d));
        NODES.put(12, new Node(12, "R1 VIRTUAL 07", Node.VIRTUAL, 266.68d, 478.81d));
        NODES.put(13, new Node(13, "R1 VIRTUAL 08", Node.VIRTUAL, 241.43d, 505.08d));
        NODES.put(14, new Node(14, "R1 VIRTUAL 09", Node.VIRTUAL, 233.35d, 541.44d));
        NODES.put(15, new Node(15, "R1 VIRTUAL 10", Node.VIRTUAL, 242.44d, 577.81d));
        NODES.put(16, new Node(16, "R1 VIRTUAL 11", Node.VIRTUAL, 267.69d, 605.08d));
        NODES.put(17, new Node(17, "R1 VIRTUAL 12", Node.VIRTUAL, 308.10d, 612.15d));
        NODES.put(18, new Node(18, "R2 VIRTUAL 01", Node.VIRTUAL, 476.79d, 506.09d));
        NODES.put(19, new Node(19, "R2 VIRTUAL 02", Node.VIRTUAL, 503.06d, 478.82d));
        NODES.put(20, new Node(20, "R2 VIRTUAL 03", Node.VIRTUAL, 516.19d, 444.47d));
        NODES.put(21, new Node(21, "R2 VIRTUAL 04", Node.VIRTUAL, 507.10d, 407.10d));
        NODES.put(22, new Node(22, "R2 VIRTUAL 05", Node.VIRTUAL, 480.83d, 379.82d));
        NODES.put(23, new Node(23, "R2 VIRTUAL 06", Node.VIRTUAL, 439.42d, 369.72d));
        NODES.put(24, new Node(24, "R2 VIRTUAL 07", Node.VIRTUAL, 402.04d, 380.83d));
        NODES.put(25, new Node(25, "R2 VIRTUAL 08", Node.VIRTUAL, 399.01d, 407.10d));
        NODES.put(26, new Node(26, "R2 VIRTUAL 09", Node.VIRTUAL, 365.68d, 443.46d));
        NODES.put(27, new Node(27, "R2 VIRTUAL 10", Node.VIRTUAL, 374.77d, 479.83d));
        NODES.put(28, new Node(28, "R2 VIRTUAL 11", Node.VIRTUAL, 400.02d, 507.10d));
        NODES.put(29, new Node(29, "R2 VIRTUAL 12", Node.VIRTUAL, 440.43d, 514.17d));
        NODES.put(30, new Node(30, "R3 VIRTUAL 01", Node.VIRTUAL, 334.35d, 409.11d));
        NODES.put(31, new Node(31, "R3 VIRTUAL 02", Node.VIRTUAL, 360.62d, 381.84d));
        NODES.put(32, new Node(32, "R3 VIRTUAL 03", Node.VIRTUAL, 373.75d, 347.49d));
        NODES.put(33, new Node(33, "R3 VIRTUAL 04", Node.VIRTUAL, 364.66d, 310.12d));
        NODES.put(34, new Node(34, "R3 VIRTUAL 05", Node.VIRTUAL, 338.39d, 282.84d));
        NODES.put(35, new Node(35, "R3 VIRTUAL 06", Node.VIRTUAL, 296.98d, 272.74d));
        NODES.put(36, new Node(36, "R3 VIRTUAL 07", Node.VIRTUAL, 259.60d, 283.85d));
        NODES.put(37, new Node(37, "R3 VIRTUAL 08", Node.VIRTUAL, 256.57d, 310.12d));
        NODES.put(38, new Node(38, "R3 VIRTUAL 09", Node.VIRTUAL, 223.24d, 346.48d));
        NODES.put(39, new Node(39, "R3 VIRTUAL 10", Node.VIRTUAL, 232.33d, 382.85d));
        NODES.put(40, new Node(40, "R3 VIRTUAL 11", Node.VIRTUAL, 257.58d, 410.12d));
        NODES.put(41, new Node(41, "R3 VIRTUAL 12", Node.VIRTUAL, 297.99d, 417.19d));
        NODES.put(42, new Node(42, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(43, new Node(43, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(44, new Node(44, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(45, new Node(45, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(46, new Node(46, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(47, new Node(47, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(48, new Node(48, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(49, new Node(49, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(50, new Node(50, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(51, new Node(51, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(52, new Node(52, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(53, new Node(53, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(54, new Node(54, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(55, new Node(55, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(56, new Node(56, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(57, new Node(57, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(58, new Node(58, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(59, new Node(59, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(60, new Node(60, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(61, new Node(61, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(62, new Node(62, "SPACE", Node.SPACE, 0d, 0d));
        NODES.put(63, new Node(63, "SPACE", Node.SPACE, 0d, 0d));

        RADARS[RADAR_1][LINE_1][POINT_1][X] = 305d;
        RADARS[RADAR_1][LINE_1][POINT_1][Y] = 602d;
        RADARS[RADAR_1][LINE_1][POINT_2][X] = 305d;
        RADARS[RADAR_1][LINE_1][POINT_2][Y] = 482d;
        RADARS[RADAR_1][LINE_2][POINT_1][X] = 247d;
        RADARS[RADAR_1][LINE_2][POINT_1][Y] = 541d;
        RADARS[RADAR_1][LINE_2][POINT_2][X] = 367d;
        RADARS[RADAR_1][LINE_2][POINT_2][Y] = 541d;

        RADARS[RADAR_2][LINE_1][POINT_1][X] = 439d;
        RADARS[RADAR_2][LINE_1][POINT_1][Y] = 499d;
        RADARS[RADAR_2][LINE_1][POINT_2][X] = 439d;
        RADARS[RADAR_2][LINE_1][POINT_2][Y] = 379d;
        RADARS[RADAR_2][LINE_2][POINT_1][X] = 378d;
        RADARS[RADAR_2][LINE_2][POINT_1][Y] = 441d;
        RADARS[RADAR_2][LINE_2][POINT_2][X] = 498d;
        RADARS[RADAR_2][LINE_2][POINT_2][Y] = 441d;

        RADARS[RADAR_3][LINE_1][POINT_1][X] = 294d;
        RADARS[RADAR_3][LINE_1][POINT_1][Y] = 402d;
        RADARS[RADAR_3][LINE_1][POINT_2][X] = 294d;
        RADARS[RADAR_3][LINE_1][POINT_2][Y] = 282d;
        RADARS[RADAR_3][LINE_2][POINT_1][X] = 234d;
        RADARS[RADAR_3][LINE_2][POINT_1][Y] = 343d;
        RADARS[RADAR_3][LINE_2][POINT_2][X] = 354d;
        RADARS[RADAR_3][LINE_2][POINT_2][Y] = 343d;
    }



    //~ --- [CONSTRUCTORS] ---------------------------------------------------------------------------------------------

    public UavProblem() {

    }



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @Override
    public double calculateFitness(Chromosome chromosome) {

        List<Gene>   genes             = chromosome.getGenes();
        double[]     totalDistance     = new double[1];
        int          l                 = genes.size();
        int          i                 = 0;
        Set<Integer> uniqueIds         = new HashSet<Integer>();
        Set<Integer> uniqueRealNodeIds = new HashSet<Integer>();
        Node         nodeA             = null;
        Node         nodeB             = null;

        while (i < l) {

            if (nodeA == null) {
                nodeA = calculateAndGetNode(genes.get(i++), uniqueIds, uniqueRealNodeIds, totalDistance);
            }

            if (nodeB == null && i < l) {
                nodeB = calculateAndGetNode(genes.get(i++), uniqueIds, uniqueRealNodeIds, totalDistance);
            }

            if (nodeA != null && nodeB != null) {

                if (nodeA.getId() == nodeB.getId()) {
                    totalDistance[0] += FEE_SAME_NEIGHBOURS; // Eğer iki nokta birbirinin aynı ise
                } else {
                    totalDistance[0] += calculateDistance(nodeA, nodeB);
                }

                nodeA = nodeB;
                nodeB = null;
            }
        }

        totalDistance[0] += (6 - uniqueRealNodeIds.size()) * FEE_REAL_NODE_PASS;

        return TOTAL_FITNESS - totalDistance[0]; // ten million
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    @Override
    public void onCompleted(Chromosome chromosome) {

        List<Gene> genes = chromosome.getGenes();

        // For debugging result
        calculateFitness(chromosome);

        for (Gene gene : genes) {
            System.out.println(NODES.get(gene.getValue()).getName());
        }
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private Node calculateAndGetNode(Gene gene, Set<Integer> ids, Set<Integer> realNodeIds, double[] distance) {

        int  nodeId = gene.getValue();
        Node node   = NODES.get(nodeId);
        byte type   = node.getType();

        if (type != Node.SPACE) {

            if (ids.contains(nodeId)) {
                distance[0] += FEE_NODE_DUPLICATION; // Eğer id daha önceden kullanılmış ise ceza verilir.
            } else {
                ids.add(nodeId);
            }

            if (type == Node.REAL) {
                realNodeIds.add(nodeId);
            } else {
                distance[0] += FEE_VIRTUAL_NODE;
            }

            return node;
        } else {
            distance[0] += FEE_SPACE;
        }

        return null;
    }



    //~ ----------------------------------------------------------------------------------------------------------------

    private double calculateDistance(Node node1, Node node2) {

        double totalDistance = 0;
        double node1x        = node1.getX();
        double node1y        = node1.getX();
        double node2x        = node2.getX();
        double node2y        = node2.getX();

        for (double[][][] RADAR_LINE : RADARS) {

            for (double[][] LINE : RADAR_LINE) {

                if (Line2D.linesIntersect(LINE[POINT_1][X], LINE[POINT_1][Y], LINE[POINT_2][X], LINE[POINT_2][Y],
                            node1x, node1y, node2x, node2y)) {

                    totalDistance += FEE_IN_RADAR_RANGE;
                }
            }
        }

        double distanceX = (node1.getX() - node2.getX());
        double distanceY = (node1.getY() - node2.getY());
        double distance  = Math.sqrt(distanceX * distanceX + distanceY * distanceY) * FEE_DISTANCE_FACTOR;

        return distance + totalDistance;
    }
}
