package graph;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Topic {

    public final String name;

    private final Set<Agent> subs;
    private final Set<Agent> pubs;
    private volatile Message lastMessage;

    Topic(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        this.name = name;
        this.subs = ConcurrentHashMap.newKeySet();
        this.pubs = ConcurrentHashMap.newKeySet();
    }

    public void subscribe(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("agent cannot be null");
        }

        subs.add(agent);
    }

    public void unsubscribe(Agent agent) {
        if (agent == null) {
            return;
        }

        subs.remove(agent);
    }

    public void addPublisher(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("agent cannot be null");
        }

        pubs.add(agent);
    }

    public void removePublisher(Agent agent) {
        if (agent == null) {
            return;
        }

        pubs.remove(agent);
    }

    public void publish(Message msg) {
        if (msg == null) {
            throw new IllegalArgumentException("message cannot be null");
        }

        lastMessage = msg;
        for (Agent agent : subs) {
            agent.callback(name, msg);
        }
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public Set<Agent> getSubscribers() {
        return Set.copyOf(subs);
    }

    public Set<Agent> getPublishers() {
        return Set.copyOf(pubs);
    }
}