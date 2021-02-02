package gameRound.validator;

import config.PropertiesConfig;
import gameRound.domain.InputGameRound;
import gameRound.exception.GameRoundException;

public class InputDivideByThreeValidator implements Validator {
    private static final int DIVIDE;

    static {
        DIVIDE = Integer.parseInt(PropertiesConfig.getProperties().getProperty("game.divide"));
    }

    @Override
    public boolean validate(InputGameRound input) {
        int sum = input.sum();
        int result = sum % DIVIDE;
        return result == 0;
    }

    @Override
    public void validateOrNot(InputGameRound input) {
        int sum = input.sum();
        if (sum % DIVIDE != 0) {
            throw new GameRoundException(String.format("The %s is not dividable by three", input.getNumber()));
        }
    }
}
