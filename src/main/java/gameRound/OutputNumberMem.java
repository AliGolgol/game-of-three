package gameRound;

public class OutputNumberMem {
    int addition;
    int result;
    boolean isWinner;
    String playerName;

    public OutputNumberMem(int addition, int result, boolean isWinner, String playerName) {
        this.addition = addition;
        this.result = result;
        this.isWinner = isWinner;
        this.playerName = playerName;
    }

    public void defineWinnerStatus(boolean isWinner) {
        this.isWinner = isWinner;
    }
public void definePlayer(String playerName){
        this.playerName = playerName;
}
    public int getAddition() {
        return addition;
    }

    public int getResult() {
        return result;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "OutputNumber{" +
                "addition=" + addition +
                ", result=" + result +
                '}';
    }
}
