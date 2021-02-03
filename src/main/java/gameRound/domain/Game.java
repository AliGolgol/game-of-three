package gameRound.domain;

import config.PropertiesConfig;
import game.domain.Human;
import game.domain.Machine;
import game.domain.Player;
import gameRound.OutputNumberMem;
import gameRound.exception.GameRoundException;
import gameRound.validator.InputRangeValidator;
import gameRound.validator.Validator;
import gameRound.winLogic.Winner;
import gameRound.winLogic.WinnerImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);
    private static final String DIVIDE_NUMBER = PropertiesConfig.getProperties().getProperty("game.divide");
    static final Map<Integer, Player> map = new HashMap<>();
    static final int MINIMUM_PLAYERS = 2;
    static String MAX_RANGE;
    static String MIN_RANGE;
    int currentPlayer = 0;
    final int HUMAN = 1;
    int startNumber;
    int playerType = 1;
    boolean isWinner;
    List<Player> players = new ArrayList<>();
    Validator validator = null;
    Player secondPlayer = null;
    Player firstPlayer = null;
    Winner winnerLogic;
    String id;

    static {
        map.put(1, new Human());
        map.put(2, new Machine());
        MAX_RANGE = PropertiesConfig.getProperties().getProperty("game.max-range");
        MIN_RANGE = PropertiesConfig.getProperties().getProperty("game.min-range");
    }

    public Game create() {
        init();
        return this;
    }

    private void init() {
        winnerLogic = new WinnerImp();
        this.id = UUID.randomUUID().toString();
        if (map.get(playerType) instanceof Machine) {
            firstPlayer = map.get(HUMAN);
            firstPlayer.register();
            players.add(firstPlayer);

            secondPlayer = map.get(playerType);
            secondPlayer.register();
            players.add(secondPlayer);
        } else {
            firstPlayer = map.get(HUMAN);
            firstPlayer.register();
            players.add(firstPlayer);
        }
    }

    public void start() {
        startNumber = ThreadLocalRandom.current().nextInt(Integer.parseInt(MIN_RANGE), Integer.parseInt(MAX_RANGE));
    }

    public OutputNumberMem play(InputGameRound number) {
        OutputNumberMem outputNumberMem = null;
        int inputResult;
        try {
            number.validate();
            inputResult = number.sum() / Integer.parseInt(DIVIDE_NUMBER);
            if (winnerLogic.apply(inputResult)) {
                return new OutputNumberMem(number.getAdditionNumber(), number.sum(), true, players.get(currentPlayer).getName());
            }
            InputGameRound input = new InputGameRound(number.additionNumber, inputResult);
//            outputNumberMem = map.get(playerType) instanceof Human
//                    ? next().receive(input)
//                    : secondPlayer.receive(input);
            outputNumberMem = next().receive(input);
            isWinner = winnerLogic.apply(outputNumberMem.getResult());
            outputNumberMem.defineWinnerStatus(isWinner);
            outputNumberMem.definePlayer(players.get(currentPlayer).getName());
            return outputNumberMem;
        } catch (GameRoundException e) {
            LOGGER.info(e.toString());
            throw e;
        }
    }

    public Game withPlayerOfType(int playerType) {
        this.playerType = playerType;
        validator = new InputRangeValidator();
        return this;
    }

    private Player next() {
        currentPlayer = (currentPlayer + 1) % players.size();
        return players.get(currentPlayer);
    }

    public int getStartNumber() {
        return startNumber;
    }

    public boolean canPlay() {
        return players.size() >= MINIMUM_PLAYERS;
    }
    public String getCurrentPlayer() {
        return players.get(currentPlayer).getName();
    }
    public int getPlayerType() {
        return playerType;
    }
}