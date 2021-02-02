package gameRound.exception;

public class GameRoundException extends RuntimeException{
    public GameRoundException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
