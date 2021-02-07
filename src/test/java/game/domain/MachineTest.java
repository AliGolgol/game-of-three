package game.domain;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MachineTest{
    Machine machine;

    @Before
    public void setup(){
        machine = new Machine();
    }

    @Test
    public void shouldSetAnIdAndName(){

        machine.register();
        assertEquals("The register method should create a player and then set a Name",
                "PLAYER 2", machine.getName());

        assertEquals("The register method should create a player and then set an Id",
                36, machine.getId().length());
    }
}