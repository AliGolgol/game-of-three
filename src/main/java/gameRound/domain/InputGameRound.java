package gameRound.domain;

import gameRound.validator.InputDivideByThreeValidator;
import gameRound.validator.Validator;

import java.util.Objects;

public class InputGameRound {
    int additionNumber;
    int number;

    public InputGameRound(int additionNumber, int number) {
        this.additionNumber = additionNumber;
        this.number = number;
    }

    public int getAdditionNumber() {
        return additionNumber;
    }

    public int getNumber() {
        return number;
    }

    public int sum() {
        return additionNumber + number;
    }

    public void validate(){
        Validator validator = new InputDivideByThreeValidator();
        validator.validateOrNot(this);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputGameRound that = (InputGameRound) o;
        return additionNumber == that.additionNumber && number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(additionNumber, number);
    }

    @Override
    public String toString() {
        return "InputGameRound{" +
                "additionNumber=" + additionNumber +
                ", number=" + number +
                '}';
    }
}
