package controller;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import gameRound.exception.GameRoundException;
import org.junit.Before;
import org.junit.Test;
import service.GameService;
import service.GameServiceImp;
import service.OutputDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    GameService gameService;
    GameController gameController;

    static {
        PropertiesConfig.initialize("application.properties");
    }

    @Before
    public void setup(){
        gameService = mock(GameServiceImp.class);
        gameController = new GameController(gameService);
    }

    @Test
    public void shouldCreateAGameAndReturnStartNumber(){
        when(gameService.playWithPlayerOfType(2)).thenReturn(30);
        int result = gameController.create(2);
        assertEquals("Should create a Game and return StartNumber",30,result);
    }

    @Test
    public void shouldCreateAGameAndReturnTheResultWithMessageTheNumberIsNotDividable(){
        InputGameRound input = new InputGameRound(-1,9);
        when(gameService.play(input)).thenThrow(new GameRoundException("The number is not dividable"));
        OutputDto result = gameController.play(input);
//        assertEquals("Game should throw an exception","The number is not dividable",result.getMessage());
        assertTrue(result.getMessage().contains("The number is not dividable"));
    }

    @Test
    public void shouldCreateAGameAndReturnTheResultOfGameRound(){
        InputGameRound input = new InputGameRound(-1,10);
        when(gameService.play(input)).thenThrow(new GameRoundException("The number is not dividable"));
        OutputDto result = gameController.play(input);
        assertEquals("Game should throw an exception","The number is not dividable",result.getMessage());

    }
}