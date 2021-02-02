package service;

import game.domain.Player;
import gameRound.domain.Game;
import gameRound.domain.InputGameRound;
import gameRound.OutputCareTaker;
import gameRound.OutputNumberMem;

public class GameServiceImp implements GameService {
    static Game game = new Game();
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
                    "");
        } else {
            result = new OutputDto(number.getAdditionNumber(), number.getNumber(),
                    String.format("%s, you should wait to another player to start", game.getFirstPlayer().getName()));
        }
        if (outputNumberMem != null && outputNumberMem.isWinner()) {
            result = new OutputDto(outputNumberMem.getAddition(), outputNumberMem.getResult(),
                    String.format("The winner is %s", outputNumberMem.getPlayerName()));
        }
        return result;
    }

    @Override
    public Player getFirstPlayer() {
        return game.getFirstPlayer();
    }

    @Override
    public Player getSecondPlayer() {
        return game.getSecondPlayer();
    }

    @Override
    public boolean canPlay() {
        return game.canPlay();
    }
}
