package server;

import controller.GameController;
import gameRound.*;
import gameRound.domain.Game;
import gameRound.domain.InputGameRound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GameServiceImp;
import service.OutputDto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        start(5000);
    }
    public static void start(int port) {
        ServerSocket serverSocket = null;
        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                serverSocket.setReuseAddress(true);
                Socket client = null;
                DataOutputStream dataOutputStream = null;
                DataInputStream dataInputStream = null;
                synchronized (serverSocket) {
                    client = serverSocket.accept();
                    dataOutputStream = new DataOutputStream(client.getOutputStream());
                    dataInputStream = new DataInputStream(client.getInputStream());
                    LOGGER.info(String.format("New client is connected on port %s", client.getPort()));
                }
                ClientHandler clientSocket = new ClientHandler(client, dataInputStream, dataOutputStream);
                new Thread(clientSocket).start();
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
}

class ClientHandler extends Thread {
    private final Socket socket;
    Game game;
    Map<String, Integer> whichPlayer = new HashMap<>();
    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;
    OutputCareTaker careTaker;
    OutputNumberOriginator originator;
    GameController gameController = new GameController(new GameServiceImp());

    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        this.socket = socket;
        whichPlayer.put("Machine", 2);
        whichPlayer.put("Human", 1);
        whichPlayer.put("machine", 2);
        whichPlayer.put("human", 1);
        originator = new OutputNumberOriginator();
        careTaker = new OutputCareTaker();
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }

    @Override
    public void run() {
        try {
            game = new Game();
            OutputNumberMem last = null;
            int resultNumber = 0;
            OutputDto response = null;

            String stringFromClient = "", stringToClient = "";
            while (!stringFromClient.equals("stop")) {
                stringFromClient = dataInputStream.readUTF();
                System.out.println("server.Client says " + stringFromClient);

                if (stringFromClient.equalsIgnoreCase("machine") || stringFromClient.equalsIgnoreCase("human")) {
                    resultNumber = gameController.create(whichPlayer.get(stringFromClient));
                    stringToClient = resultNumber != 0 ? String.format("The number to start is %s", resultNumber)
                            : "As soon as the next player will be connected, the Game will be started";
                } else {
                    response = gameController.play(new InputGameRound(Integer.parseInt(stringFromClient), resultNumber));
                    if (!response.getMessage().isEmpty()) {
                        stringToClient = response.getMessage();
                    } else {
                        stringToClient = String.valueOf(response.getNumber());
                        resultNumber = response.getNumber();
                    }
                }
                dataOutputStream.writeUTF(stringToClient);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
//            LOGGER.error(e.toString());
            System.out.println(e.toString());
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                    socket.close();
                }
            } catch (IOException e) {
//                LOGGER.error(e.toString());
                System.out.println(e.toString());
            }
        }
    }
}