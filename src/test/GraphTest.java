package test;

import configs.BinOpAgent;
import configs.Graph;
import graph.Message;
import graph.TopicManagerSingleton;

public class GraphTest {

    public static void main(String[] args) {

        TopicManagerSingleton.get().clear();

        BinOpAgent addAgent = new BinOpAgent(
                "AddAgent",
                "input1",
                "input2",
                "output",
                (x, y) -> x + y
        );

        Graph graph = new Graph();
        graph.createFromTopics();

        System.out.println("Number of nodes: " + graph.size());
        System.out.println("Has cycle: " + graph.hasCycles());

        TopicManagerSingleton.get()
                .getTopic("input1")
                .publish(new Message(2.0));

        TopicManagerSingleton.get()
                .getTopic("input2")
                .publish(new Message(3.0));

        addAgent.close();
    }
}