package configs;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import test.Agent;
import test.Topic;

public class Graph extends ArrayList<Node> {
    private final ConcurrentHashMap<String, Agent> agents = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Topic> Topics = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Node> nodes = new ConcurrentHashMap<>();

    public Node GetOrCreate_topic(String name){
        return nodes.computeIfAbsent(name, k->new Node(k));
    }
    

}
