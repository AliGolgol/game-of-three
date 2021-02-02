package server;

import config.PropertiesConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    static {
        PropertiesConfig.initialize("application.properties");
    }

    private static final String ADDRESS = PropertiesConfig.getProperties().getProperty("game.server.address");
    private static final String PORT = PropertiesConfig.getProperties().getProperty("game.server.port");
    private ExecutorService executorService;

    Client client1;
    Client client2;

    @Before
    public void setup() throws InterruptedException {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->
                Server.main(new String[]{}));

        Thread.sleep(2000);

        client1 = new Client();
        client1.startConnection(ADDRESS, Integer.parseInt(PORT));

        Thread.sleep(2000);
        client2 = new Client();
        client2.startConnection(ADDRESS, Integer.parseInt(PORT));
    }

    @After
    public void shutdown() throws IOException {
        client1.dispose();
        client2.dispose();
    }

    @Test
    public void givenClient1_whenServerResponds_thenCorrect() throws InterruptedException {
        String fromServer = client1.requestToServer("machine");
        Thread.sleep(3000);
        String firstAnsFromMachine = client1.requestToServer("-1");
        String secondAnsFromMachine = client1.requestToServer("0");

        assertEquals("The number to start is 10", fromServer);
        assertEquals("The winner is PLAYER 2", firstAnsFromMachine);
    }

//    @Test
//    public void twoRealPlayerPlayWithEachOther() throws InterruptedException {
//        client1.requestToServer("human");
//        Thread.sleep(2000);
//        client2.requestToServer("human");
//
//        Thread.sleep(2000);
//        String res1 = client1.requestToServer("-1");
//
//        assertEquals("",1,Integer.parseInt(res1));
//    }

}
