import java.util.*;

public class TriantaEnaGame extends CardGame{

    Dealer dealer;
    public int noOfPlayers;
    TriantaEnaPlayer[] tePlayers;
    boolean matchIsDrawn;

    public int getNoOfPlayers(){
        System.out.println("Enter number of players : ");
        Scanner in = new Scanner(System.in);
        int noOfPlayers = in.nextInt();
        return noOfPlayers;
    }

    public void setPlayer(int playerNo){

        System.out.println(" ####### Player  " + playerNo + " Details ######### ");
        System.out.println("Please enter your name  : ");
        Scanner in = new Scanner(System.in);
        String playerName = in.next();
        System.out.println("You are provided with 1000 $");

        tePlayers[playerNo] = new TriantaEnaPlayer(playerName,1000,0);//commonBet);
    }


    public void setInitialDealer(){

        System.out.println(" ####### Banker Details ######### ");
        System.out.println("Please enter your name  : ");
        Scanner in = new Scanner(System.in);
        String playerName = in.next();

        dealer = new Dealer(playerName,cardDeck,3000);
        System.out.println("The banker is provided with 3000 $");
    }

    public void setNewDealer(int index){

        String playerName = tePlayers[index].playerName;
        int playerPurse = tePlayers[index].purse;

        String dealerName = dealer.playerName;
        int dealerPurse = dealer.purse;

        dealer.playerName = playerName;
        dealer.purse = playerPurse;

        tePlayers[index].playerName = dealerName;
        tePlayers[index].purse = dealerPurse;
    }

    public List<Integer> sortByPurse(){

        HashMap<Integer,Integer> purseMap = new HashMap<>();
        for(int i = 0; i < tePlayers.length; i++){
            purseMap.put(tePlayers[i].purse,i);
        }

        TreeMap<Integer, Integer> sorted = new TreeMap<>();
        sorted.putAll(purseMap);
        List<Integer> sortedValues = new ArrayList<Integer>();
        sortedValues.addAll(sorted.values());

        return sortedValues;
    }


    public TriantaEnaGame(){

        this.noOfPlayers = getNoOfPlayers();
        tePlayers = new TriantaEnaPlayer[this.noOfPlayers];

        for(int i = 0; i< this.noOfPlayers; i++){
            setPlayer(i);
        }
        setInitialDealer();
    }

    public void askPlayerBet(TriantaEnaPlayer tePlayer){

        String seeCard;
        Scanner in = new Scanner(System.in);

        System.out.println("Do you want to see your face down card " + tePlayer.playerName + " ? (Y/N)");
        seeCard = in.next();

        if(seeCard.equalsIgnoreCase("y")){
            for (Hand H : tePlayer.playerHands){
                for (Card C : H.cardList){
                    C.faceUp = true;
                    C.printCard();
                    C.faceUp = false;
                    System.out.println();
                }
            }
        }

        int choice;
        System.out.println("Do you want to :");
        System.out.println("1. Bet ?");
        System.out.println("2. Fold?");
        choice = in.nextInt();

        if(choice == 1){
            int bet;
            System.out.println("How much do you wanna bet ? ");
            bet = in.nextInt();
            tePlayer.addMoneyToBet(bet);
            Card C1 = dealer.returnRandomCard(true);
            Card C2 = dealer.returnRandomCard(true);
            tePlayer.hit(C1,1);
            tePlayer.hit(C2,1);
        } else {
            System.out.println("You are choosing of fold");
            tePlayer.isCurrentlyFolding = true;
        }

    }

    public boolean ifAllStanding(){

        boolean allStanding = true;

        for(int i = 0; i < this.noOfPlayers; i++){
            if(!tePlayers[i].isCurrentlyFolding){
                if( tePlayers[i].isCurrentlyStanding == false ){
                    allStanding = false;
                }
            }
        }
        return allStanding;
    }

    public List<Integer> getHandsScores(List<Hand> handsList){

        List<Integer> handsScores = new ArrayList<Integer>();
        for(Hand H : handsList){
            handsScores.add(H.returnHandValue(31));
        }
        return handsScores;

    }

    public boolean greaterThan31(List<Integer> handsScores){
        for(int i : handsScores){
            if(i > 31){
                return true;
            }
        }
        return false;
    }

    public void playerHasLost(){

        for(int i = 0;i < tePlayers.length;i++ ){
            if(!tePlayers[i].isCurrentlyFolding) {

                tePlayers[i].cutMoneyFromPurse(tePlayers[i].bet);
                dealer.addToPool(pool, tePlayers[i].bet);
                tePlayers[i].initialBet = 0;
                tePlayers[i].bet = 0;
            }
        }
    }

    public void playerHasWon(List<Integer> winningPlayerIndex){

        for(int i : winningPlayerIndex){
            dealer.subtractFromPool(pool, 3 * tePlayers[i].bet);
            tePlayers[i].addMoneyToPurse(3 * tePlayers[i].bet);
            tePlayers[i].initialBet = 0;
            tePlayers[i].bet = 0;
        }
    }


    public int displayDealerCards(){

        System.out.println("All players have stood");

        for(Hand hand : dealer.playerHands){
            for(Card C : hand.cardList){
                C.faceUp = true;
            }
        }

        System.out.println("Dealer " + dealer.playerName + "'s cards :" );
        dealer.displayHands();

        int dealerSum = getHandsScores(dealer.playerHands).get(0);

        while( dealerSum < 27){
            System.out.println("Dealer " + dealer.playerName + " is hitting himself.");
            Card C = dealer.returnRandomCard(true);
            dealer.hit(C,1);
            dealerSum += C.value;
        }

        System.out.println("Dealer " + dealer.playerName + "'s cards :" );
        dealer.displayHands();

        return dealerSum;

    }


    public List<Integer> checkWin() {

        int[] playerScores = new int[tePlayers.length];

        for(int i = 0; i< tePlayers.length;i++){
                List<Integer> score = getHandsScores(tePlayers[i].playerHands);
                playerScores[i] = score.get(0);
            }


        List<Integer> winningPlayerIndex = new ArrayList<Integer>();
        int maximumScore = 0;

        for (int i =0 ;i < tePlayers.length; i++){
            if(!tePlayers[i].isCurrentlyFolding){
                if(playerScores[i] > maximumScore){
                    winningPlayerIndex.removeAll(winningPlayerIndex);
                    winningPlayerIndex.add(i);
                    maximumScore = playerScores[i];
                } else if(playerScores[i] == maximumScore){
                    winningPlayerIndex.add(i);
                }
            }
        }

        return winningPlayerIndex;

    }

    public void reinitialize(){

        for(int i = 0;i < tePlayers.length;i++){
            tePlayers[i].bet = 0;
            tePlayers[i].playerHands.removeAll(tePlayers[i].playerHands);
            tePlayers[i].isCurrentlyStanding = false;
            tePlayers[i].isCurrentlyFolding = false;
            dealer.playerHands.removeAll((dealer.playerHands));
            tePlayers[i].noOfHands = 0;
            dealer.noOfHands = 0;
        }
    }

    public void naturalTriantaEta(){

        List<Integer> winningPlayerIndex = new ArrayList<Integer>();

        for(int i = 0;i < tePlayers.length;i++){

            if(!tePlayers[i].isCurrentlyFolding){
                int score = getHandsScores(tePlayers[i].playerHands).get(0);
                if(score == 31){
                    System.out.println(tePlayers[i]+  " has been dealt a natural TriantaEta!!!");
                    winningPlayerIndex.add(i);
                }
            }
        }

        if(!winningPlayerIndex.isEmpty()){
            playerHasLost();
            playerHasWon(winningPlayerIndex);

            for(int i = 0; i < tePlayers.length;i++){
                tePlayers[i].isCurrentlyStanding = true;
            }
        }
    }

    public void runGame(){

        matchIsDrawn = false;
        boolean continueGame = true;

        Scanner in = new Scanner(System.in);
        boolean checkWin = false;

        while(continueGame){

            System.out.println();
            System.out.println("Dealer is now dealing a card to each player.");

            // Dealing cards to players
            for(int i = 0; i < this.noOfPlayers; i++){
                List<Card> playerHand = new ArrayList<Card>();
                Card playerCard = dealer.returnRandomCard(false);
                playerHand.add(playerCard);
                tePlayers[i].createHand(playerHand);
            }

            // Dealing hand to banker
            List<Card> dealerHand = new ArrayList<Card>();
            Card dealerCard = dealer.returnRandomCard(true);
            dealerHand.add(dealerCard);
            dealer.createHand(dealerHand);

            for(int i = 0; i < this.noOfPlayers; i++) {
                askPlayerBet(tePlayers[i]);
            }


            System.out.println();
            System.out.println("Banker's current hands  : ");
            dealer.displayHands();

            for(int i = 0; i < this.noOfPlayers; i++) {
                if(!tePlayers[i].isCurrentlyFolding){
                    System.out.println(tePlayers[i].playerName + "'s cards");
                    tePlayers[i].displayHands();
                }
            }

            naturalTriantaEta();

            while(!ifAllStanding()){

                for(int i = 0; i < this.noOfPlayers; i++) {
                    if (!tePlayers[i].isCurrentlyFolding && !tePlayers[i].isCurrentlyStanding ) {
                        System.out.println(tePlayers[i].playerName + " do you want to :");
                        System.out.println("1. Hit?");
                        System.out.println("2. Stand?");
                        int playerChoice = in.nextInt();
                        if (playerChoice == 1) {
                            Card C = dealer.returnRandomCard(true);
                            tePlayers[i].hit(C, 1);
                            tePlayers[i].displayHands();
                            List<Integer> handsScores = getHandsScores(tePlayers[i].playerHands);
                            boolean bust = greaterThan31(handsScores);
                            if (bust) {
                                System.out.println(tePlayers[i].playerName + " has bust !!!");
                                tePlayers[i].isCurrentlyStanding = true;
                            }
                        } else if (playerChoice == 2) {
                            tePlayers[i].stand();
                        }
                    }
                }
            }

            int dealerSum = displayDealerCards();
            List<Integer> winningPlayerIndex = checkWin();

            if(dealerSum > 31){
                System.out.println("Dealer has bust !!!");
                System.out.println("These players have won the game : ");
                for(Integer I : winningPlayerIndex){
                    System.out.print(tePlayers[I].playerName);
                    System.out.print("\t");
                }
            }
            else{
                if(dealerSum >= winningPlayerIndex.get(0)){
                    System.out.println("Dealer has won.");
                    playerHasLost();
                } else {
                    System.out.println("These players have won the game : ");
                    for(Integer I : winningPlayerIndex){
                        System.out.print(tePlayers[I].playerName);
                        System.out.print("\t");
                        playerHasLost();
                        playerHasWon(winningPlayerIndex);
                    }
                }
            }

            reinitialize();

            System.out.println("Do you want to play again (Y/N) : ");
            String continueChoice = in.next();
            if(continueChoice.equalsIgnoreCase("n")){
                continueGame = false;
            } else {
                List<Integer> richPlayers = sortByPurse();

                for(int i : richPlayers){
                    if (tePlayers[i].purse > dealer.purse){
                        System.out.println("Would " + tePlayers[i].playerName + " like to become the banker in the next round ? ");
                        String choice = in.next();
                        if (choice.equalsIgnoreCase("y")){
                            setNewDealer(i);
                        }
                    }
                }
            }
        }
    }
}
