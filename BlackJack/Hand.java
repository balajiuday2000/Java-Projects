import java.util.*;

public class Hand{

    public List<Card> cardList;
    public boolean canSplit;

    public Hand(List<Card> cardList){

        this.cardList = cardList;
        canSplit = false;
    }

    public int returnHandValue(int limit){

        boolean contains1 = false;

        for(Card C : cardList){
            if(C.value == 1){
                contains1 = true;
                break;
            }
        }

        if(!contains1){
            int handValue = 0;

            for(Card C : cardList){
                handValue += C.value;
            }
            return handValue;
        }else{
            int handValue1 = 0;
            int handValue2 = 0;

            for(Card C : cardList){
                handValue1 += C.value;
            }
            for(Card C : cardList){
                if(C.value == 1){
                    handValue2 += 11;
                }else{
                    handValue2 += C.value;
                }
            }
            if(handValue1 > handValue2 && handValue1 <= 21){return handValue1;}
            else if(handValue2 > handValue1 && handValue2 <= 21){return handValue2;}
            else{return(Math.min(handValue1, handValue2));}
        }
    }
}
