package gameRound;

import junit.framework.TestCase;
import org.junit.Test;

public class OutputCareTakerTest extends TestCase {
    OutputCareTaker careTaker = new OutputCareTaker();

    @Test
    public void addShouldSaveTheOutPutNumber() {
        OutputNumberMem numberMem = new OutputNumberMem(-1,9,false,"Player2");
        careTaker.add(numberMem);
        assertEquals("CareTaker should return the added Number",numberMem,careTaker.getLast());
    }

    @Test
    public void testGet() {
    }

    @Test
    public void shouldGetAlwaysTheLastOutputNumber() {
        OutputNumberMem numberMem1 = new OutputNumberMem(-1,9,false,"Player1");
        OutputNumberMem numberMem2 = new OutputNumberMem(-1,9,false,"Player2");

        careTaker.add(numberMem1);
        careTaker.add(numberMem2);
        assertEquals("CareTaker should return the added Number",numberMem2,careTaker.getLast());
    }
}