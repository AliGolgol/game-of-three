package gameRound.validator;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import gameRound.exception.GameRoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputRangeValidator implements Validator {

    private static final List<Integer> INPUT_NUMBERS = new CopyOnWriteArrayList<>();
    private static  String INPUT_RANGE ;
    static Properties properties = new Properties();

    static {
        INPUT_RANGE = PropertiesConfig.getProperties().getProperty("game.input_range");
        String[] inputs = INPUT_RANGE
                .split("[/s,.]");
        Arrays.stream(inputs).forEach(input -> {
            INPUT_NUMBERS.add(Integer.parseInt(input));
        });
    }


    @Override
    public boolean validate(InputGameRound input) {
        return INPUT_NUMBERS.contains(input.getAdditionNumber());
    }

    @Override
    public void validateOrNot(InputGameRound input) {
        if (!INPUT_NUMBERS.contains(input.getAdditionNumber())){
            throw new GameRoundException("The input number should be in (-1, 0, 1) range");
        }
    }
}
