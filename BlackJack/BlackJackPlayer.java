import java.util.*;

public class BlackJackPlayer extends Player{

    public int bet;
    public int initialBet;
    public boolean isCurrentlyStanding = false;

    public BlackJackPlayer(String pn,int purse, int bet){

        super(pn,purse);
        this.bet = bet;
        initialBet = bet;
    }

    public boolean stand() {

        isCurrentlyStanding = true;
        super.displayHands();
        return true;
    }

    public boolean split(int index) {

        Hand playerHand = null;
        Iterator hand = playerHands.iterator();

        for (int i = 1; i < index + 1; i++) {
            playerHand = (Hand) hand.next();
        }

        System.out.println("Hand which the player wants to split : " + index);
        System.out.println();

        for(Card C : playerHand.cardList){
            C.printCard();
        }

        Set<Number> unique = new HashSet<>();
        List<Card> newHandCards = new ArrayList<Card>();

        Iterator card = playerHand.cardList.iterator();

        boolean duplicate = false;

        while (card.hasNext()) {
            Card c = (Card) card.next();
            if (!unique.add((c.value))) {
                duplicate = true;
            }
            if (duplicate) {
                newHandCards.add(c);
                card.remove();
            }
        }

        if(duplicate){
            playerHand.canSplit = false;
            playerHands.add(new Hand(newHandCards));
            System.out.println("Hands after split : ");
            displayHands();
            return true;
        } else {
            System.out.println("This hand can't be split !");
            isCurrentlyStanding = false;
            return false;
        }
    }

    public boolean Double(Card C, int index){
        bet += initialBet;
        super.hit(C,index);
        stand();
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