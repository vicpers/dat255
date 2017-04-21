package ServiceEntities;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by maxedman on 2017-04-21.
 */

public class MessageBrokerQueue {

    private String queueId;
    private Queue<PortCallMessage> queue;

//    Empty constructor
    public MessageBrokerQueue(){
    }

    public MessageBrokerQueue(String queueId){
        setQueueId(queueId);
    }

    private void setQueueId(String queueId){
        this.queueId = queueId;
    }

    public String getQueueId(){
        return this.queueId;
    }

    public Queue<PortCallMessage> getQueue(){
        return this.queue;
    }

    //TODO implement functionality for polling MessageBrokerQueue
    public void pollQueue(){
    }

}
