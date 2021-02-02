import config.PropertiesConfig;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Client;
import server.Server;

import javax.swing.*;
import java.io.IOException;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static String ADDRESS;
    private static String PORT;

    public static void main(String[] args) throws IOException {
        /*
        Player player1 = new Human();
        Player player2 = new Human();

        Subscriber subscriber = new Subscriber(1);
        Subscriber subscriber2 = new Subscriber(2);

        Subscriber subscriber3 = new Subscriber(3);
        Subscriber subscriber4 = new Subscriber(4);

        Event.operation.subscribe(String.valueOf(subscriber.id), subscriber);
        Event.operation.subscribe("action#create", subscriber2);

        Event.operation.subscribe("action#update", subscriber3);
        Event.operation.subscribe("action#delete", subscriber4);

        Message message = new Message("Create Action");
        Message message2 = new Message("Update Action");

        Event.operation.publish("action#create", message);
        Event.operation.publish("action#update", message2);
*/
        try {
            initPropertyConfig();
        Server server = new Server();
            server.start(Integer.parseInt(PORT));
        }catch (Exception e){
            LOGGER.error(e.toString());
        }
    }
    private static void initPropertyConfig() {
        PropertyConfigurator.configure(PropertyConfigurator.class.getClassLoader().getResourceAsStream("log4j.properties"));
        PropertiesConfig.initialize("application.properties");
        ADDRESS = PropertiesConfig.getProperties().getProperty("game.address");
        PORT = PropertiesConfig.getProperties().getProperty("game.port");
    }
}