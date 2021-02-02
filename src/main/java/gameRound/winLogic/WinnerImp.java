package gameRound.winLogic;

public class WinnerImp implements Winner{
    private static final int WINNER_NUMBER = 1;
    @Override
    public Boolean apply(final Integer number) {
        return number.equals(WINNER_NUMBER);
    }
}