package service;

import gameRound.domain.InputGameRound;

public interface GameService {
    int playWithPlayerOfType(int playerType);
    OutputDto play(InputGameRound number);
}
