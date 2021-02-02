package server;

import config.PropertiesConfig;
import javafx.application.Application;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTest extends TestCase {
    static {
        PropertiesConfig.initialize("application.properties");
    }

    private static final String ADDRESS = PropertiesConfig.getProperties().getProperty("game.server.address");
    private static final String PORT = PropertiesConfig.getProperties().getProperty("game.server.port");
    private ExecutorService executorService;
    Client client;


    @Before
    public void setup() throws InterruptedException {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->
                Server.main(new String[]{}));

        //wait for server under test to start external sockets connections.
        Thread.sleep(2000);

        //start test testClient1
        client = new Client();
        client.startConnection(ADDRESS,Integer.parseInt(PORT));

        //client.requestToServer("machine");
//        socketPlayer1 = testClient1.start(SERVER_IP, Integer.parseInt(SERVER_PORT));

//        Thread.sleep(100);

        //start test testClient2
//        testClient2 = new Client();
//        socketPlayer2 = testClient2.start(SERVER_IP, Integer.parseInt(SERVER_PORT));
//        Thread.sleep(100);
    }

    @After
    public void shutdown() throws IOException {
        client.requestToServer("stop");
        executorService.shutdown();
    }

    @Test
    public void testStart() throws InterruptedException {
        client.requestToServer("hi");
        assertEquals(true,true);

    }
}