package Infrastructure.messageBrocker;

public class Subscriber {
    int id;

    public String getMessage() {
        return message;
    }

    String message;

    public Subscriber(int id){
        this.id = id;
    }

    @OnMessage
    private void onMessage(Message message){
        this.message = message.message;
        System.out.println(message.message);
    }
}
