package service;

public class OutputDto {
    int addition;
    int number;
    String message;

    public OutputDto(int addition, int number, String message) {
        this.addition = addition;
        this.number = number;
        this.message = message;
    }

    public int getAddition() {
        return addition;
    }

    public int getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }
}
