package game.domain;

import gameRound.domain.InputGameRound;
import gameRound.OutputNumberMem;

public abstract class Player {
    public abstract void register();
    public abstract OutputNumberMem receive(InputGameRound number, String opponent);
    public abstract String getId();
    public abstract String getName();
}
