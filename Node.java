/**
 * @Author Bryan Zen 113252725
 * @version 1.0
 * @since 2021-11-30
 */

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Write a fully-documented class (getters and setters) named Node that
 * represents a City that will be a part of our graph of connected cities.
 * Each city has a name, a HashSet containing which cities it is adjacent to
 * (represented by an edge), and a boolean value to represent if the node has
 * been visited by the graph traversal. Note: a HashSet is similar to a HashMap
 * or HashTable, except that it stores just values instead of mapping
 * keys to values. A consequence of this is that values cannot
 * be repeated in the HashSet.
 */
public class Node {
    private String Name;
    /**
     *  collection of all the Nodes that are adjacent to the current Node.
     */
    private HashSet<Edge> edges;
    /**
     * Boolean which is useful in both Minimum Spanning Tree
     * (Prim's Algorithm) and Dijkstra's
     */
    private boolean visited;
    /**
     * Helps in Dijkstra's Algorithm
     * (it stores the current shortest path from the starting node to this node)
     * Note: The “next” node’s path will be
     * the path of the current node + the current node
     */
    private LinkedList<String> path;
    /**
     * Stores the length of the current known the shortest path from the given
     * node to the starting node in Dijkstra's
     */
    private int distance;

    /**
     * This is the constructor for the node class
     * @param name this is the name of the node that it is initialized with
     */
    public Node(String name){
        this.Name = name;
    }

    /**
     * @return the name of the node
     */
    public String getName() {
        return Name;
    }

    /**
     * @param name is the new name of the node
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * @return the edges of the node as a hash set
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * @param edges is the new hash set of the node
     */
    public void setEdges(HashSet<Edge> edges) {
        this.edges = edges;
    }

    /**
     * @return whether the node was visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited is the new boolean of the node
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * @return the path as a linked list of strings
     */
    public LinkedList<String> getPath() {
        return path;
    }

    /**
     * @param path is the new path to this node
     */
    public void setPath(LinkedList<String> path) {
        this.path = path;
    }

    /**
     * @return the distance to this node from the starting node
     */
    public int getDistance() {
        return distance;
    }

    /**
     * @param distance is the new distance to the node from the starting node.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }
}
