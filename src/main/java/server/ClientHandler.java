package server;

import controller.GameController;
import gameRound.OutputCareTaker;
import gameRound.OutputNumberOriginator;
import gameRound.domain.Game;
import gameRound.domain.InputGameRound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GameServiceImp;
import service.OutputDto;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private final Socket socket;
    Game game;
    Map<String, Integer> whichPlayer = new HashMap<>();
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    OutputCareTaker careTaker;
    OutputNumberOriginator originator;
    GameController gameController = new GameController(new GameServiceImp());

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        whichPlayer.put("Machine", 2);
        whichPlayer.put("Human", 1);
        whichPlayer.put("machine", 2);
        whichPlayer.put("human", 1);
        originator = new OutputNumberOriginator();
        careTaker = new OutputCareTaker();
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.dataInputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            game = new Game();
            int resultNumber = 0;
            OutputDto response;

            String stringFromClient = "";
            String stringToClient = "";

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
                        LOGGER.info(stringToClient);
                    } else {
                        stringToClient = String.valueOf(response.getNumber());
                        resultNumber = response.getNumber();
                    }
                }
                dataOutputStream.writeUTF(stringToClient);
                dataOutputStream.flush();
            }
            dispose();
        } catch (IOException e) {
            LOGGER.error(e.toString());
            System.out.println(e.toString());
        }
//        finally {
//            try {
//                stop();
//            } catch (IOException e) {
//                LOGGER.error(e.toString());
//                System.out.println(e.toString());
//            }
//        }
    }

     void dispose() throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (dataInputStream != null) {
            dataInputStream.close();
            socket.close();
        }
    }
}
