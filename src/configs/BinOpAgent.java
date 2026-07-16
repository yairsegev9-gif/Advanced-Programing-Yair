package configs;

import java.util.function.BinaryOperator;
import graph.Agent;
import graph.Message;
import graph.TopicManagerSingleton;

public class BinOpAgent implements Agent{
    private final String Agent_name;
    private final String Topic1_input;
    private final String Topic2_input;
    private final String Topic_output;
    private final BinaryOperator<Double> op;

    private Double value1 = null;
    private Double value2 = null;

    public BinOpAgent(String name,String topic1,String topic2,String output_topic,BinaryOperator lambda){
        this.Agent_name=name;
        this.Topic1_input=topic1;
        this.Topic2_input=topic2;
        this.Topic_output=output_topic;
        this.op=lambda;
    }

    @Override

    public String getName(){
        return this.Agent_name;
    }
    @Override
    public void callback(String topic, Message msg){
        if(topic.equals(Topic1_input)){
            this.value1 = msg.asDouble;
        }
        if ((topic.equals(Topic2_input))){
            this.value2=msg.asDouble;
        }
        if (value1 != null && value2 != null){
            double result = this.op.apply(value1,value2);
            Message double_msg = new Message(result);
            TopicManagerSingleton.get().getTopic(Topic_output).publish(double_msg);
            this.value1 = null;
            this.value2 = null;
        }


    }
    @Override
    public void reset(){
        this.value1 = null;
        this.value2 = null;
    }
    @Override
    public void close(){
        TopicManagerSingleton.get().getTopic(Topic1_input).unsubscribe(this);
        TopicManagerSingleton.get().getTopic(Topic2_input).unsubscribe(this);

    }


}
