package server;

import config.PropertiesConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    public void setup() throws InterruptedException, IOException {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() ->
                Server.main(new String[]{}));
        Thread.sleep(3000);

        client1 = new Client();
        client1.startConnection(ADDRESS, Integer.parseInt(PORT));

        Thread.sleep(3000);

        client2 = new Client();
        client2.startConnection(ADDRESS, Integer.parseInt(PORT));
        Thread.sleep(3000);
    }

    @After
    public void tearDown() throws IOException {
        client1.dispose();
        client2.dispose();
    }

    @Test
    @Ignore
    public void givenClient1_whenServerResponds_thenCorrect() throws InterruptedException {
        Client client1 = new Client();
        String fromServer = client1.requestToServer("machine");
        Thread.sleep(3000);
        String firstAnsFromMachine = client1.requestToServer("-1");
//        String secondAnsFromMachine = client1.requestToServer("0");

        assertEquals("The number to start is 10", fromServer);
        assertEquals("The winner is PLAYER 2", firstAnsFromMachine);
    }

    @Test
    public void twoRealPlayerPlayWithEachOther() throws InterruptedException {
        client1.startConnection(ADDRESS, Integer.parseInt(PORT));
        Thread.sleep(3000);

        client2.startConnection(ADDRESS, Integer.parseInt(PORT));
        Thread.sleep(3000);
        String fromServer = client1.requestToServer("human");
        String frmSrv2 = client2.requestToServer("human");

        String res2 = client1.requestToServer("-1");
        String res1 = client2.requestToServer("-1");

        assertEquals("As soon as the next player will be connected, the Game will be started", fromServer);
        assertEquals("As soon as the next player will be connected, the Game will be started", frmSrv2);
        assertEquals("", "PLAYER 9, you should wait to another player to start", res2);
        assertEquals("", "PLAYER 10, you should wait to another player to start", res1);
    }

    @Test
    @Ignore
    public void client2ShouldSendTheNumber() throws InterruptedException {
        client2.startConnection(ADDRESS, Integer.parseInt(PORT));
        Thread.sleep(3000);
        client2.requestToServer("human");
        Thread.sleep(3000);
        String res2 = client2.requestToServer("-1");

        assertEquals("", "PLAYER 2, you should wait to another player to start", res2);
    }
}
