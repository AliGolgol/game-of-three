package service;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameServiceTest {

    GameServiceImp gameServiceImp;

    static {
        PropertiesConfig.initialize("application.properties");
    }

    @Before
    public void startup() {
        gameServiceImp = new GameServiceImp();
    }

    @Test
    public void shouldCreateAGameAndReturnStartNumber() {
        int result = gameServiceImp.playWithPlayerOfType(2);
        assertEquals("Should create a Game and return StartNumber", 10, result);
    }

    @Test
    public void shouldCreateAGameAndReturnZeroSinceThereIsJustOnePlayer() {
        int result = gameServiceImp.playWithPlayerOfType(1);
        assertEquals("Should create a Game and return StartNumber", 0, result);
    }

    @Test
    public void shouldCreateAGameAndSayYouCanNotPlayGameThereIsJustOnePlayer() {
        gameServiceImp.playWithPlayerOfType(1);
        boolean canPlay = gameServiceImp.canPlay();
        OutputDto output = gameServiceImp.play(new InputGameRound(-1,10));

        assertEquals("Player should wait to another player to be connected", false, canPlay);
        assertEquals("Player should wait to another player to be connected", "PLAYER 1, you should wait to another player to start", output.getMessage());
    }

    @Test
    public void shouldCreateAGameAndSayYouCanPlayGameThereIsJustOnePlayer() {
        gameServiceImp.playWithPlayerOfType(1);
        gameServiceImp.playWithPlayerOfType(1);
        boolean canPlay = gameServiceImp.canPlay();
        assertEquals("Should say can play now", true, canPlay);
    }

    @Test
    public void shouldPlayTheRoundGameAndReturnTheResult() {
        int startNumber = gameServiceImp.playWithPlayerOfType(2);
        InputGameRound input = new InputGameRound(-1,startNumber);
        OutputDto output = gameServiceImp.play(input);
        assertEquals("Should create a Game and return StartNumber", 1, output.getNumber());
        assertEquals("Should defines a winner", "The winner is PLAYER 2", output.getMessage());
    }

    @Test
    public void shouldReturnTheFirstPlayer() {
        gameServiceImp.playWithPlayerOfType(2);
        assertEquals("Should defines a winner", "PLAYER 1", gameServiceImp.getFirstPlayer().getName());
    }

    @Test
    public void shouldReturnTheSecondPlayer() {
        gameServiceImp.playWithPlayerOfType(2);
        assertEquals("Should defines a winner", "PLAYER 2", gameServiceImp.getSecondPlayer().getName());
    }
}