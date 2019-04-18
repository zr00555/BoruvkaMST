import java.util.Iterator;
import java.util.PriorityQueue;

public class Graph {
    private int vertCount;

    /*
    PriorityQueue is used for:
        -Order edges based on natural ordering or by comparator
        -Can easily iterate through edges using iterator() method
    */
    private PriorityQueue<Edge>[] edgeGraph; //Initialize empty MST

    //Number of vertices
    public int getVertCount() {
        return vertCount;
    }

    //Create graph with n number of vertices / create n number of edges but do not initialize them
    public Graph(int vertCount) {
        this.vertCount = vertCount;
        edgeGraph = new PriorityQueue[vertCount];
        for (int i = 0; i < vertCount; i++)
            edgeGraph[i] = new PriorityQueue<Edge>();
    }

    //Initialize edges with starting nodes, end nodes, and edge weight
    public void addEdge(int startPoint, int endPoint, int weight) {
        if (!hasEdge(new Edge(startPoint, endPoint, weight))) {
            //Create an edge going both ways to create bidirectional tree
            edgeGraph[startPoint].add(new Edge(startPoint, endPoint, weight));
            edgeGraph[endPoint].add(new Edge(endPoint, startPoint, weight));
        }
    }

    //Creates a new edge between two previously created nodes
    public void addEdge(Edge e) {
        if (!hasEdge(e)) {
            edgeGraph[e.getStartPoint()].add(e);
            edgeGraph[e.getEndPoint()].add(e.opposite());
        }
    }

    //Removes the edge from the tree
    public void removeEdge(int startPoint, int endPoint) {
        //Iterate through PriorityQueue to find specified edge
        Iterator<Edge> edgeIterator = edgeGraph[startPoint].iterator();
        Edge other = new Edge(startPoint, endPoint, 0);
        while (edgeIterator.hasNext()) {
            if (edgeIterator.next().equals(other)) {
                edgeIterator.remove();
                break;
            }
        }

        //Removes opposite edge in the bidirectional tree
        Iterator<Edge> it2 = edgeGraph[endPoint].iterator();
        Edge other2 = new Edge(endPoint, startPoint, 0);
        while (it2.hasNext()) {
            if (it2.next().equals(other2)) {
                it2.remove();
                break;
            }
        }
    }

    //If there are still more edges to be explored from the current node, return true
    public boolean hasEdge(Edge e) {
        Iterator<Edge> it = edgeGraph[e.getStartPoint()].iterator();
        while (it.hasNext()) {
            if (it.next().equals(e)) {
                return true;
            }
        }
        return false;
    }

    //Find all of the neighbors of a node
    public PriorityQueue<Edge> neighbours(int vertex) {
        return edgeGraph[vertex];
    }

    //Check if all nodes are connected as a MST
    public boolean isConnected() {
        // array to store if vertices where visited
        boolean visited[] = new boolean[vertCount];

        // initialze all to non-visited
        int i;
        for (i = 0; i < vertCount; i++) {
            visited[i] = false;
        }

        // check for vertex with non-zero degree
        for (i = 0; i < vertCount; i++) {
            if (neighbours(i).size() == 0)
                return false; // if there is one return false
        }

        // DFS Traversal starting from non-zero vertex
        DFS(0, visited);

        // Check if all vertices have been visited
        for (i = 0; i < vertCount; i++)
            if (visited[i] == false)
                return false;
        // if at least one was not visited false, else return true
        return true;
    }

    //Check to see which edges have been visited for a node
    public void DFS(int sourceVertex, boolean visited[]) {
        // Mark source node as visited
        visited[sourceVertex] = true;
        // recursion for all the vertices adjacent to this one
        Iterator<Edge> it = neighbours(sourceVertex).iterator();
        while (it.hasNext()) {
            Edge nextVertex = it.next();
            if (!visited[nextVertex.getEndPoint()])
                DFS(nextVertex.getEndPoint(), visited);
        }
    }

    //How many nodes are reachable from a specific node
    public int CountReachableNodes(int sourceVertex) {
        // array to store if vertices where visited
        boolean visited[] = new boolean[vertCount];

        // initialze all to non-visited
        for (int i = 0; i < vertCount; i++) {
            visited[i] = false;
        }

        DFS(sourceVertex, visited);

        int count = 0;
        for (int i = 0; i < vertCount; i++) {
            if (visited[i] == true)
                count++;
        }
        return count;
    }

    //See if one node is reachable from another
    public boolean Reachable(int sourceVertex, int destVertex) {
        // array to store if vertices where visited
        boolean visited[] = new boolean[vertCount];

        // initialze all to non-visited
        for (int i = 0; i < vertCount; i++) {
            visited[i] = false;
        }
        DFS(sourceVertex, visited);
        return visited[destVertex];
    }

    //Print the graph before Boruvka
    public void printGraph() {
        for (int i = 0; i < vertCount; i++) {
            PriorityQueue<Edge> edges = neighbours(i);
            Iterator<Edge> it = edges.iterator();
            System.out.print(i + ": ");
            //Shows every edge and node combination
            for (int j = 0; j < edges.size(); j++) {
                System.out.print(it.next() + " ");
            }
            System.out.println();
        }
    }

    //Print the final MST
    public void printMST() {
        for (int i = 0; i < vertCount; i++) {
            PriorityQueue<Edge> edges = neighbours(i);
            Iterator<Edge> it = edges.iterator();
            System.out.print(i + ": ");
            //Only iterate through the edge once so that a MST is given
            for (int j = 0; j < 1; j++) {
                System.out.print(it.next() + " ");
            }
            System.out.println();
        }
    }

    //Sum up the weight (cost) of all edges in the MST and print
    public void printMSTWeight() {
        int weight = 0;
        for (int i = 0; i < vertCount; i++) {
            PriorityQueue<Edge> edges = neighbours(i);
            Iterator<Edge> it = edges.iterator();
            for (int j = 0; j < edges.size(); j++) {
                weight += it.next().getWeight();
                break; //Do not want to have duplicate edges in final weight count
            }
        }
        System.out.println("The total min weight of the MST is: " + weight);
    }
}