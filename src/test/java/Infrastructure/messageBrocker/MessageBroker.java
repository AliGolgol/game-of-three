package Infrastructure.messageBrocker;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageBroker {

    @Test
    public void brokerShouldPublishAndSubscribeOnDefinedChannel(){
        Subscriber subscriber1 = new Subscriber(1);
        Subscriber subscriber2 = new Subscriber(2);

        Subscriber subscriber3 = new Subscriber(3);
        Subscriber subscriber4 = new Subscriber(4);

        Event.operation.subscribe("action#create", subscriber1);
        Event.operation.subscribe("action#create", subscriber2);

        Event.operation.subscribe("action#update", subscriber3);
        Event.operation.subscribe("action#delete", subscriber4);

        Message message = new Message("Create Action");
        Message message2 = new Message("Update Action");

        Event.operation.publish("action#create", message);
        Event.operation.publish("action#update", message2);

        assertEquals("Should get the publish message on subscribe1",message.message,subscriber1.getMessage());
        assertEquals("Should get the publish message on subscribe3",message2.message,subscriber3.getMessage());
    }
}