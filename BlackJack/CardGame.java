import java.util.*;


public class CardGame {

    Card[] cardDeck = new Card[52];
    Pool pool;

    public CardGame(){
        createDeck();
        pool = new Pool(1000000);
    }

    public void createDeck(){

        Hashtable<Integer, String> suiteNames = new Hashtable<Integer, String>();

        suiteNames.put(0, "Hearts");
        suiteNames.put(1, "Diamonds");
        suiteNames.put(2, "Spades");
        suiteNames.put(3, "Clubs");

        for(int i = 0; i < 52; i+=13){
            for(int j = 0; j < 13; j++){
                if( (j >= 10) && (Math.floorDiv(j,11) == 0) ){
                    cardDeck[i+j] = new Card("Jack",suiteNames.get(Math.floorDiv(i,13)),true, Math.min(j+1,10));

                } else if ( (j >= 10) && (Math.floorDiv(j,12) == 0) ){
                    cardDeck[i+j] = new Card("Queen",suiteNames.get(Math.floorDiv(i,13)),true, Math.min(j+1,10));

                } else if ( (j >= 10) && (Math.floorDiv(j,13) == 0) ){
                    cardDeck[i+j] = new Card("King",suiteNames.get(Math.floorDiv(i,13)),true, Math.min(j+1,10));

                } else {
                    cardDeck[i+j] = new Card(String.valueOf(j + 1),suiteNames.get(Math.floorDiv(i,13)),true, Math.min(j+1,10));
                }
            }
        }
    }
}
