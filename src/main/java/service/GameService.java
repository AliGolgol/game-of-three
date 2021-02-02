package service;

import game.domain.Player;
import gameRound.domain.InputGameRound;

public interface GameService {
    int playWithPlayerOfType(int playerType);
    OutputDto play(InputGameRound number);
    Player getFirstPlayer();
    Player getSecondPlayer();
    boolean canPlay();
}
