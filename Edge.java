/**
 * @Author Bryan Zen 113252725
 * @version 1.0
 * @since 2021-11-30
 */

/**
 * Write a fully-documented class called Edge (getters and setters) that
 * implements the Comparable interface. An Edge represents an adjacency between
 * two cities in our country. In other words, two cities (Nodes) are adjacent
 * if and only if they have an Edge between them. The Edge class will contain
 * two Node references and an integer representing the cost of laying down
 * Internet cable between the two cities. We will need to be able to compare
 * two edges to determine which has a lower cost.
 */
public class Edge {
    private Node A;
    private Node B;
    private int cost;

    /**
     * Empty constructor for the Edge class
     */
    public Edge(){
    }

    /**
     * Constructor for the Edge class
     * @param A is a node that is connected by the edge
     * @param B is a node that is connected by the edge
     * @param cost is the weight of the edge
     */
    public Edge(Node A, Node B, int cost){
        this.A = A;
        this.B = B;
        this.cost = cost;
    }

    /**
     * A ToString() method is recommended, so you can print the
     * roads in the graph after loading the data structure.
     * @return
     */
    public String toString(){
        return String.format("%s to %s %d", A.getName(), B.getName(), cost);
    }

    /**
     * Compares the current edge’s cost to otherEdge’s cost.
     * This method is not used in my implementation
     * @param otherEdge is the edge that is compared to
     * @return -1 if the current edge’s cost is less than otherEdge’s cost,
     * 0 if equal, and 1 if greater than.
     */
    public int compareTo(Edge otherEdge){
        int x = 0;
        if (cost < otherEdge.getCost()){
            x = -1;
        } else if (cost > otherEdge.getCost()){
            x = 1;
        }
        return x;
    }

    /**
     * @return node A
     */
    public Node getA() {
        return A;
    }

    /**
     * @param a is the new node A
     */
    public void setA(Node a) {
        A = a;
    }

    /**
     * @return node B
     */
    public Node getB() {
        return B;
    }

    /**
     * @param b is the new node B
     */
    public void setB(Node b) {
        B = b;
    }

    /**
     * @return the weight of the edge
     */
    public int getCost() {
        return cost;
    }

    /**
     * @param cost is the new cost of the edge
     */
    public void setCost(int cost) {
        this.cost = cost;
    }
}
