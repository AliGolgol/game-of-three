package game.domain;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import gameRound.OutputNumberMem;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Human extends Player {
    private String id;
    private String name;
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger(1);
    private static final String DIVIDE_NUMBER = PropertiesConfig.getProperties().getProperty("game.divide");

    public void register() {
        id = UUID.randomUUID().toString();
        name = "PLAYER 1" ;
    }

    public OutputNumberMem receive(InputGameRound number, String opponent) {
        int result = number.sum() / Integer.parseInt(DIVIDE_NUMBER);
        return new OutputNumberMem(number.getAdditionNumber(), result, false, name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
