package server;

import config.PropertiesConfig;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        initPropertyConfig();
        start(5000);
    }
    public static void start(int port) {
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setReuseAddress(true);
                Socket client;
//                DataOutputStream dataOutputStream;
//                DataInputStream dataInputStream;
//                synchronized (serverSocket) {
                    client = serverSocket.accept();
//                    dataOutputStream = new DataOutputStream(client.getOutputStream());
//                    dataInputStream = new DataInputStream(client.getInputStream());
                    LOGGER.info(String.format("New client is connected on port %s", client.getPort()));
//                }
                 ClientHandler clientHandler = new ClientHandler(client);
                pool.execute(clientHandler);

            } catch (IOException e) {
                LOGGER.error(e.toString());
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        LOGGER.error(e.toString());
                    }
                }
            }
        }
    }

    private static void initPropertyConfig() {
        PropertyConfigurator.configure(PropertyConfigurator.class.getClassLoader().getResourceAsStream("log4j.properties"));
        PropertiesConfig.initialize("application.properties");
    }
}