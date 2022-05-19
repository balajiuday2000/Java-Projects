import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {

    public String playerName;
    public int noOfHands;
    public List<Hand> playerHands;
    public int purse;

    public Player(String pn, int purse){
        playerName = pn;
        noOfHands = 0;
        playerHands = new ArrayList<Hand>();
        this.purse = purse;
    }


    public void displayHands(){

        int handNo = 1;

        for (Hand hand : playerHands) {
            System.out.println();
            System.out.println("Hand No : " + handNo);
            System.out.println();
            for (Card C : hand.cardList) {
                C.printCard();
            }
            handNo++;
        }
    }

    public boolean createHand(List<Card> hand){

        if (noOfHands > 4){
            System.out.println("You cannot have more than 4 hands !!!!!");
            return false;
        }

        Hand newHand = new Hand(hand);
        playerHands.add(newHand);
        noOfHands += 1;

        return true;
    }


    public boolean hit(Card C, int handIndex) {

        if(handIndex > noOfHands){
            System.out.println("You have only " + noOfHands + " hand(s) !");
            System.out.println();
            return false;
        }

        Iterator hand = playerHands.iterator();
        Hand playerHand = null;

        for (int i = 1; i < handIndex + 1; i++) {
            playerHand = (Hand) hand.next();
        }

        playerHand.cardList.add(C);

        return true;
    }
}
