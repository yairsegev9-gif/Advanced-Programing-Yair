package configs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node {

    private final String name;
    private final List<Node> edges = new ArrayList<>();

    public byte[] message;

    private int state = 0;

    public Node(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Node> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void addEdge(Node neighbor) {
        if (neighbor == null) {
            throw new IllegalArgumentException("neighbor cannot be null");
        }

        if (!edges.contains(neighbor)) {
            edges.add(neighbor);
        }
    }

    public boolean hasCycles() {
        return dfs();
    }

    private boolean dfs() {
        if (state == 1) {
            return true;
        }

        if (state == 2) {
            return false;
        }

        state = 1;

        for (Node neighbor : edges) {
            if (neighbor.dfs()) {
                return true;
            }
        }

        state = 2;
        return false;
    }

    public void resetState() {
        state = 0;
    }
}