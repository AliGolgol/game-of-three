package server;

import config.PropertiesConfig;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class ServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private static String ADDRESS;
    private static String PORT;
    public DataInputStream dataInputStream = null;
    public DataOutputStream dataOutputStream = null;
    static Socket socket = null;
    public BufferedReader bufferedReader = null;

    public void startConnection(String address, int port) {
        try {
            socket = new Socket(address, port);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public String requestToServer(String request) {
        String response = "";
        try {
            dataOutputStream.writeUTF(request);
            response = dataInputStream.readUTF();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return response;
    }

    public void dispose() throws IOException {
        bufferedReader.close();
        dataInputStream.close();
        dataOutputStream.close();
        socket.close();
    }

    public static void main(String args[]) {
        initPropertyConfig();
//        startConnection(ADDRESS,Integer.parseInt(PORT));
    }

    private static void initPropertyConfig() {
        PropertyConfigurator.configure(PropertyConfigurator.class.getClassLoader().getResourceAsStream("log4j.properties"));
        PropertiesConfig.initialize("application.properties");
        ADDRESS = PropertiesConfig.getProperties().getProperty("game.address");
        PORT = PropertiesConfig.getProperties().getProperty("game.port");
    }
}
