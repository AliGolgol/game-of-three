package Infrastructure.messageBrocker;

public class Subscriber {
    String id;
    public Subscriber(String id){
        this.id = id;
    }

    @OnMessage
    private void onMessage(Message message){
        System.out.println(message.message);
    }
}
