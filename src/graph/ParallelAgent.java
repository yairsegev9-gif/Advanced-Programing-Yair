package graph;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParallelAgent implements Agent {
    private final Agent agent;
    private final BlockingQueue<Task> queue;
    private final Thread workerThread;

    private static final Task SHUTDOWN_SIGNAL = new Task(null, null);

    private static class Task {
        final String topic;
        final Message msg;

        Task(String topic, Message msg) {
            this.topic = topic;
            this.msg = msg;
        }
    }

    public ParallelAgent(Agent agent, int capacity) {
        this.agent = agent;
        this.queue = new ArrayBlockingQueue<>(capacity);

        this.workerThread = new Thread(() -> {
            while (true) {
                try {
                    Task task = queue.take();

                    if (task == SHUTDOWN_SIGNAL) {
                        break;
                    }

                    String currentTopic = task.topic;
                    Message currentMsg = task.msg;

                    this.agent.callback(currentTopic, currentMsg);

                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        this.workerThread.start();
    }

    @Override
    public void callback(String topic, Message msg) {
        if (!workerThread.isAlive() || queue.contains(SHUTDOWN_SIGNAL)) {
            return;
        }
        try {
            queue.put(new Task(topic, msg));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String getName() {
        return this.agent.getName();
    }

    @Override
    public void reset() {
        this.agent.reset();
    }

    @Override
    public void close() {
        try {
            if (!queue.contains(SHUTDOWN_SIGNAL)) {
                queue.put(SHUTDOWN_SIGNAL);
            }
            this.workerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.agent.close();
        }
    }
}