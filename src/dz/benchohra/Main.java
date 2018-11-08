package dz.benchohra;

import java.util.*;

class Node {
    int id;
    ArrayList<Node> adjacentNodes;
    boolean visited;

    public Node (int id) {
        this.id = id;
        visited = false;
    }

    int degree() {
        return adjacentNodes.size();
    }
}

class Arc {
    int id;
    Node x;
    Node y;

    public Arc(int id, Node x, Node y) {
        this.id = id;
        this.x = x;
        this.y = y;

        x.adjacentNodes.add(y);
        y.adjacentNodes.add(x);
    }
}

class Graph {
    ArrayList<Node> nodes;
    ArrayList<Arc> arcs;

    public Graph(ArrayList<Node> nodes, ArrayList<Arc> arcs) {
        this.nodes = nodes;
        this.arcs = arcs;
    }
}

public class Main {

    public static void main(String[] args) {

    }

    ArrayList<Node> getArticulateNodes(Graph g) {
        ArrayList<Node> acrticulationNodes = new ArrayList<>();
        ArrayList<Node> graphNodes = g.nodes;
        int n, p;

        for (Node e : graphNodes) {
            n = BFS(e);

        }
    }

    int BFS(Node v) {
        int n = 0;
        v.visited = true;
        LinkedList<Node> F = new LinkedList<>();

        F.addLast(v);
        while(F.size() != 0) {
            v = F.pop();
            v.visited = true;
            n++;
            for (Node e : v.adjacentNodes) {
                if (!e.visited)
                    F.addLast(e);
            }
        }
        return n;
    }

}
