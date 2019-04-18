import java.util.Iterator;

public class Boruvka {

    /*

    Objective
        -Create a minimal spanning tree consisting of Vertices and Edges
        -Each vertex has an identifier and each edge has a weight (cost)
        -Tree cannot have any cycles
        -Must traverse the tree with the MINIMUM (Least sum) of edge weights
        -Can have multiple MSTs with the same sum of weights within the graph

    Algorithm
        -Start with a forest, where each tree is a vertex (node) of the graph and has an edge
        connecting to at least one other tree (node)
        -Find least weighted neighboring tree that does not create a cycle
        -Combine current tree (node) and least cost neighbor into one tree
        -Cycle through each node until each node is connected with its least cost neighbor
        -Must consider the combined trees (nodes) as a single tree (node) from now on
        -If all nodes have been cycled through and no MST has been created,
        cycle through each newly combined tree and find least cost neighbor of the new tree
        -Repeat the previous step until all the trees (nodes) in the forest are
        connected together to create one tree (MST)

     */


    public static Graph Boruvka(Graph graph) {
        int vertNum = graph.getVertCount();

        // initialize mst
        Graph tree = new Graph(vertNum);
        // loop until everything is connected
        while (!tree.isConnected()) {
            // loop through all vertices
            for (int i = 0; i < vertNum; i++) {
                // check if all nodes are reachable
                if (tree.CountReachableNodes(i) < vertNum) {
                    // iterate through all the edges of the current node
                    Iterator<Edge> it = graph.neighbours(i).iterator();
                    while(it.hasNext()){
                        Edge e = it.next();
                        /*
                        Add edge to mst if not added already and make sure
                        endPoint is not reachable from elsewhere so no cycles are created
                        */
                        if(!tree.hasEdge(e) && !tree.Reachable(i, e.getEndPoint())){
                            tree.addEdge(e);
                            break;
                        }
                    }
                }
            }
        }

        return tree;
    }

    public static void main(String[] args) {
        Graph test = new Graph(5);

        System.out.println("Graph:");
        // add Edges
        test.addEdge(0, 1, 5);
        test.addEdge(0, 3, 7);
        test.addEdge(1, 3, 5);
        test.addEdge(1, 4, 3);
        test.addEdge(2, 1, 1);
        test.addEdge(2, 3, 2);
        test.addEdge(3, 4, 8);
        test.addEdge(4, 2, 2);

        // print Graph
        test.printGraph();

        // Boruvka Algorithm
        System.out.println("");
        System.out.println("Boruvka MST:");
        Graph mst = Boruvka(test);
        mst.printMST();
        System.out.println("");
        mst.printMSTWeight();
    }

}