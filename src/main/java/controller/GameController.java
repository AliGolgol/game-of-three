package controller;

import gameRound.domain.InputGameRound;
import gameRound.exception.GameRoundException;
import service.GameService;
import service.OutputDto;

public class GameController {
    private final GameService gameService;
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public int create(int playerType){
         return gameService.playWithPlayerOfType(playerType);
    }

    public OutputDto play(InputGameRound number){
        OutputDto result = null ;
        try {
            result = gameService.play(number);
        }catch (GameRoundException e){
            result = new OutputDto(number.getAdditionNumber(),number.getNumber(),e.getMessage());
        }
        return result;
    }
}
