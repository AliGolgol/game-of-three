package gameRound.validator;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class InputDivideByThreeValidatorTest  {
    InputRangeValidator validator;

    static {
        PropertiesConfig.initialize("application.properties");
    }

    @Before
    public void setup() {
        validator = new InputRangeValidator();
    }

    @Test
    public void additionShouldBeInValidRange() {
        boolean result = validator.validate(new InputGameRound(-1, 9));
        assertEquals("Addition value should be in [-1, 0, 1] range", true, result);
    }

    @Test
    public void returnFalseIfAdditionIsNotInRange() {
        boolean result = validator.validate(new InputGameRound(2, 9));
        assertEquals("Addition value should be in [-1, 0, 1] range", false, result);
    }
}