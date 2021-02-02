package Infrastructure.messageBrocker;

public class Message extends Post {
    String message;

    public Message(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
