/**
 * @Author Bryan Zen 113252725
 * @version 1.0
 * @since 2021-11-30
 */

import java.util.*;
import big.data.DataSource;

/**
 * Write a fully-documented driver class called RoadCalculator. You will be
 * building a graph of Nodes and Edges by calling the BigData class from
 * within your buildGraph method. Next, you will use the information contained
 * within your graph to build the Minimum Spanning Tree (MST). You should also
 * be able to take in input in the form of two strings from the user
 * (source city and destination city) and use Djikstra’s Algorithm to find the
 * shortest path between their two corresponding Nodes.
 *
 * This is the test file:
 * https://www3.cs.stonybrook.edu/~cse214/hw/hw7-images/hw7.xml
 */
public class RoadCalculator {
    static HashMap<String, Node> graph;
    static LinkedList<Edge> mst;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String location = "";
        System.out.println("Please enter graph URL: ");
        location = sc.nextLine();
        graph = buildGraph(location);
        mst = buildMST(graph);
        while(true){
            System.out.println();
            String startOrQ = "";
            boolean startOrQB = false;
            while(!startOrQB){
                System.out.println("Enter a starting point for shortest path or Q to quit: ");
                startOrQ = sc.nextLine();
                try {
                    if (!graph.containsKey(startOrQ) || !startOrQ.equalsIgnoreCase("Q")) {
                        throw new CityNotFoundException("City not found! ");
                    } else {
                        startOrQB = true;
                    }
                } catch (CityNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (startOrQ.equalsIgnoreCase("Q")){
                System.out.println("Goodbye; PSA, there's a cop on the right in 3 miles!");
                break;
            }
            String dest = "";
            boolean destB = false;
            while(!destB){
                System.out.println("Enter a destination: ");
                dest = sc.nextLine();
                try{
                    if (!graph.containsKey(dest)){
                        throw new CityNotFoundException("City not found! ");
                    } else {
                        destB = true;
                    }
                } catch (CityNotFoundException e) {
                    e.printStackTrace();
                }
            }
            int distance = Djikstra(graph, startOrQ, dest);
            System.out.printf("Distance: %d\n", distance);
            System.out.println("Path: ");
            for (String path : graph.get(dest).getPath()){
                if (Objects.equals(path, graph.get(dest).getPath().getLast())){
                    System.out.printf("%s. ", path);
                } else{
                    System.out.printf("%s, ", path);
                }
            }
            for (Node node : graph.values()){
                node.setPath(null);
            }
            System.out.println();
        }
    }

    /**
     * This method should use the Big Data Sample Program
     * PostConditions:
     * A connected graph of Nodes and Edges has been constructed.
     * @param location is the url of the xml file
     * @return the graph as a hash map, and a printing of cities and roads
     */
    public static HashMap<String, Node> buildGraph(String location){
        HashMap<String,Node> cities = new HashMap<>();
        DataSource ds = DataSource.connectXML(location + " ");
        ds.load();
        String cityNamesStr = ds.fetchString("cities");
        String[] cityNames = cityNamesStr.substring(1,cityNamesStr.length()-1).replace("\"","").split(",");
        String roadNamesStr = ds.fetchString("roads");
        String[] roadNames = roadNamesStr.substring(1,roadNamesStr.length()-1).split("\",\"");
        System.out.println("Loading Map...");
        System.out.println();
        System.out.println("Cities: ");
        System.out.println();
        for (String city : cityNames){
            Node newCity = new Node(city);
            cities.put(city, newCity);
            System.out.println(city);
        }
        System.out.println();
        System.out.println("Roads: ");
        System.out.println();
        for (int i = 0; i < roadNames.length; i++){
            String[] roadInfo = roadNames[i].split(",");
            String a = roadInfo[0];
            String b = roadInfo[1];
            String costS = roadInfo[2];
            String[] aQuote = a.split("");
            if (i == 0){
                StringBuilder fix = new StringBuilder();
                for (int j = 1; j < aQuote.length; j++){
                    fix.append(aQuote[j]);
                }
                a = String.valueOf(fix);
            }
            String[] costQuote = costS.split("");
            if (i == roadNames.length - 1){
                StringBuilder fix = new StringBuilder();
                for (int j = 0; j < costQuote.length - 1; j++){
                    fix.append(costQuote[j]);
                }
                costS = String.valueOf(fix);
            }
            Node A = cities.get(a);
            Node B = cities.get(b);
            int cost = Integer.parseInt(costS);
            Edge newEdge = new Edge(A, B, cost);
            if (cities.get(a).getEdges() == null){
                HashSet<Edge> set = new HashSet<>(Collections.singleton(newEdge));
                cities.get(a).setEdges(set);
            } else{
                cities.get(a).getEdges().add(newEdge);
            }
            if (cities.get(b).getEdges() == null){
                HashSet<Edge> set = new HashSet<>(Collections.singleton(newEdge));
                cities.get(b).setEdges(set);
            } else{
                cities.get(b).getEdges().add(newEdge);
            }
            System.out.printf("%s to %s %s \n", a, b, costS);
        }
        return cities;
    }

    /**
     * PostConditions:
     * A connected Minimum Spanning Tree in the form of a
     * Linked List has been constructed
     * @param graph is the hash map that we are building the mst for
     * @return the mst as a linked list and print it as well
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph){
        int nodes = 0;
        Edge lowestCost = new Edge();
        lowestCost.setCost(Integer.MAX_VALUE);
        for (Node node : graph.values()){
            nodes++;
            if (node.getEdges() != null){
                for (Edge edge : node.getEdges()){
                    if (edge.getCost() < lowestCost.getCost()){
                        lowestCost = edge;
                    }
                }
            }
        }
        LinkedList<Edge> retList = new LinkedList<>(Collections.singleton(lowestCost));
        lowestCost.getA().setVisited(true);
        lowestCost.getB().setVisited(true);
        int connections = 1;
        while(connections != nodes - 1){
            Edge nextLowest = new Edge();
            nextLowest.setCost(Integer.MAX_VALUE);
            for (Node node : graph.values()){
                if (node.getEdges() != null && node.isVisited()){
                    for (Edge edge : node.getEdges()){
                        if ((edge.getA().isVisited() && !edge.getB().isVisited())
                                || !edge.getA().isVisited() && edge.getB().isVisited()){
                            if (edge.getCost() < nextLowest.getCost()){
                                nextLowest = edge;
                            }
                        }
                    }
                }
            }
            retList.add(nextLowest);
            nextLowest.getA().setVisited(true);
            nextLowest.getB().setVisited(true);
            connections++;
        }
        for (Node node : graph.values()){
            node.setVisited(false);
        }
        System.out.println();
        System.out.println("Minimum Spanning Tree: ");
        System.out.println();
        HashMap<String,Node> visited = new HashMap<>();
        for (Edge edge : retList){
            if (visited.isEmpty()){
                visited.put(edge.getA().getName(), edge.getA());
                visited.put(edge.getB().getName(), edge.getB());
                System.out.printf("%s to %s %d\n", edge.getA().getName(), edge.getB().getName(), edge.getCost());
            } else{
                String A;
                String B;
                if (visited.containsKey(edge.getA().getName())){
                    A = edge.getA().getName();
                    B = edge.getB().getName();
                } else{
                    A = edge.getB().getName();
                    B = edge.getA().getName();
                }
                System.out.printf("%s to %s %d\n", A, B, edge.getCost());
            }
        }
        for (Node node : visited.values()){
            node.setVisited(false);
        }
        return retList;
    }

    /**
     *
     * @param graph is the hash map graph that we are finding the shortest path for
     * @param source is the city that we start at
     * @param dest is the city that we end at
     * @return The cost of the cheapest path from source to dest.
     */
    public static int Djikstra(HashMap<String, Node> graph, String source, String dest){
        Node start = graph.get(source);
        Node end = graph.get(dest);
        HashSet<Node> unvisited = null;
        for (Node node : graph.values()){ //Initialize
            if (node.equals(start)){
                node.setDistance(0);
                node.setVisited(true);
                LinkedList<String> path = new LinkedList<>(Collections.singleton(node.getName()));
                node.setPath(path);
            } else{
                node.setDistance(Integer.MAX_VALUE);
                node.setVisited(false);
                if (unvisited == null) {
                    unvisited = new HashSet<>(Collections.singleton(node));
                } else{
                    unvisited.add(node);
                }
            }
        }
        for (Edge edge : start.getEdges()){ //Set distance of the nearest nodes and path
            if (start.equals(edge.getA())){
                edge.getB().setDistance(edge.getCost());
                LinkedList<String> path = LLCopy(start.getPath());
                path.addLast(edge.getB().getName());
                edge.getB().setPath(path);
            } else if (start.equals(edge.getB())){
                edge.getA().setDistance(edge.getCost());
                LinkedList<String> path = LLCopy(start.getPath());
                path.addLast(edge.getA().getName());
                edge.getA().setPath(path);
            }
        }
        while (!end.isVisited()){ //Till the shortest path to the end is not found yet
            Node lowestDist = null;
            for (Node node : Objects.requireNonNull(unvisited)){ //Go through unvisited list and get the shortest distance
                if (lowestDist == null){
                    lowestDist = node;
                } else if (node.getDistance() < lowestDist.getDistance()){
                    lowestDist = node;
                }
            }
            for (Edge edge : Objects.requireNonNull(lowestDist).getEdges()){ //Relax all connections and take the initial node out of visited
                Node from;
                Node to;
                if (edge.getA().equals(lowestDist)){
                    from = edge.getA();
                    to = edge.getB();
                    if (Objects.requireNonNull(from).getDistance() + edge.getCost() < to.getDistance()){
                        to.setDistance(from.getDistance() + edge.getCost());
                        LinkedList<String> path = LLCopy(from.getPath());
                        path.addLast(to.getName());
                        to.setPath(path);
                    }
                } else if (edge.getB().equals(lowestDist)){
                    from = edge.getB();
                    to = edge.getA();
                    if (Objects.requireNonNull(from).getDistance() + edge.getCost() < to.getDistance()){
                        to.setDistance(from.getDistance() + edge.getCost());
                        LinkedList<String> path = LLCopy(from.getPath());
                        path.addLast(to.getName());
                        to.setPath(path);
                    }
                }
            }
            lowestDist.setVisited(true);
            unvisited.remove(lowestDist);
        }
        return end.getDistance();
    }

    /**
     * This method is used in Dijkstra's algorithm to set a new path
     * @param list is the linked list that we are performing a deep copy of
     * @return the deep copied linked list
     */
    public static LinkedList<String> LLCopy(LinkedList<String> list){
        LinkedList<String> newList = null;
        for (String string : list){
            if (newList == null){
                newList = new LinkedList<>(Collections.singleton(string));
            } else{
                newList.addLast(string);
            }
        }
        return newList;
    }
}
