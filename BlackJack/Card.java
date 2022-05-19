public class Card{

    public String cardName;
    public String suite;
    public boolean faceUp;
    public int value;
    public int splitFlag = 1;

    public Card(String name, String s, boolean f, int v){
        cardName = name;
        suite = s;
        faceUp = f;
        value = v;
    }

    public void printCard(){
        if(!faceUp){
            System.out.println("This card is face down. Can't show it !");
        } else {
            System.out.println("Rank : " + cardName + "   |   " + "Suite : " + suite);
        }
        System.out.println();
    }
}
