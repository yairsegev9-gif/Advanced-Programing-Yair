package configs;

import java.util.List;
import java.util.ArrayList;

public class Node {
    private final String name;
    private List<Node> edges = new ArrayList<>();
    public byte[] message;
    private int state = 0;

    public Node(String name){
        this.name = name;

    }


    public String getName(){
        return this.name;
    }
    public List<Node> getEdges() {
        return this.edges;
    }
    public void addEdge(Node neighbor){
        this.edges.add(neighbor);}


    public boolean HasCycles(){
        return dfs();

    }



    public boolean dfs() {
        if (this.state == 1) {
            return true;
        }
        if (this.state == 2){
            return false;
        }
        this.state =1;

        for (Node neighbor: this.edges){
            if(neighbor.dfs()){
                return true;
            }

        }
        this.state = 2;
        return false;
    }
    public void reset_state(){
        this.state =0;
    }


}
