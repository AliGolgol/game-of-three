package game.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HumanTest {

    Human human;

    @Before
    public void setup(){
        human = new Human();
    }

    @Test
    public void shouldSetAnIdAndName(){

        human.register();
        assertEquals("The register method should create a player and then set a Name",
                "PLAYER 2", human.getName());

        assertEquals("The register method should create a player and then set an Id",
                36, human.getId().length());
    }
}