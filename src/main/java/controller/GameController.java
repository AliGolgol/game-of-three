package controller;

import gameRound.domain.InputGameRound;
import gameRound.exception.GameRoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GameService;
import service.OutputDto;

public class GameController {
    private final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public int create(int playerType) {
        LOGGER.info(String.valueOf(playerType));
        return gameService.playWithPlayerOfType(playerType);
    }

    public OutputDto play(InputGameRound number) {
        OutputDto result = null;
        try {
            result = gameService.play(number);
        } catch (GameRoundException e) {
            result = new OutputDto(number.getAdditionNumber(), number.getNumber(), e.getMessage(), "");
        }
        return result;
    }
}
