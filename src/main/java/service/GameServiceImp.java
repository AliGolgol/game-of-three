package service;

import gameRound.OutputCareTaker;
import gameRound.OutputNumberMem;
import gameRound.domain.Game;
import gameRound.domain.InputGameRound;

public class GameServiceImp implements GameService {
    Game game = new Game();
    OutputCareTaker careTaker = new OutputCareTaker();

    @Override
    public int playWithPlayerOfType(int playerType) {
        game.withPlayerOfType(playerType).create().start();
        if (game.canPlay()) {
            return game.getStartNumber();
        }
        return 0;
    }

    @Override
    public OutputDto play(InputGameRound number) {
        OutputDto result;
        OutputNumberMem outputNumberMem = null;

        if (game.canPlay()) {
            outputNumberMem = game.play(number);
            careTaker.add(outputNumberMem);
            result = new OutputDto(outputNumberMem.getAddition(), outputNumberMem.getResult(),
                    "",outputNumberMem.getPlayerName());
        } else {
            result = new OutputDto(number.getAdditionNumber(), number.getNumber(),
                    String.format("%s, you should wait to another player to start", game.getCurrentPlayer()),
                    game.getCurrentPlayer());
        }
        if (outputNumberMem != null && outputNumberMem.isWinner()) {
            result = new OutputDto(outputNumberMem.getAddition(), outputNumberMem.getResult(),
                    String.format("The winner is %s", outputNumberMem.getPlayerName()),
                    outputNumberMem.getPlayerName());
        }
        return result;
    }
}
