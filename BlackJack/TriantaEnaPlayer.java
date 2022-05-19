public class TriantaEnaPlayer extends Player{

    public int bet;
    public int initialBet;
    public boolean isCurrentlyStanding = false;
    public boolean isCurrentlyFolding = false;


    public TriantaEnaPlayer(String pn,int purse, int bet){
        super(pn,purse);
        this.bet = bet;
        initialBet = bet;
    }

    public boolean stand() {

        isCurrentlyStanding = true;
        super.displayHands();
        return true;
    }

    public boolean cutMoneyFromPurse(int money){
        purse -= money;
        return true;
    }

    public boolean addMoneyToPurse(int money){
        purse += money;
        return true;
    }

    public boolean addMoneyToBet(int money){
        bet += money;
        return true;
    }

}
