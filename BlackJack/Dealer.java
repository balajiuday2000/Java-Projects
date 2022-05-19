import java.util.ArrayList;
import java.util.List;

public class Dealer extends Player{

    public Card[] deck;
    public List<Integer> drawnCards;

    public Dealer(String dn,Card[] cardDeck,int purse){
        super(dn,purse);
        deck = cardDeck;
        drawnCards = new ArrayList<Integer>();
    }

    public boolean addToPool(Pool pool, int money){
        pool.vault += money;
        return true;
    }

    public boolean subtractFromPool(Pool pool, int money){
        pool.vault -= money;
        return true;
    }

    public Card returnRandomCard(boolean faceFlag){
        int randomNum;
        do {
            randomNum = (int) ((Math.random() * (51 - 0)) + 0);
            drawnCards.add(randomNum);
        } while (!drawnCards.contains(randomNum));


        deck[randomNum].faceUp = faceFlag;
        return deck[randomNum];
    }
}
