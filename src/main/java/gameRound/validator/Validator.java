package gameRound.validator;

import gameRound.domain.InputGameRound;

public interface Validator {
    boolean validate(InputGameRound input);
    void validateOrNot(InputGameRound input);
}
