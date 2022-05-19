import java.util.*;

public class BlackJackGame extends CardGame{

    Dealer dealer;
    public int noOfPlayers;
    BlackJackPlayer bjPlayer;
    boolean matchIsDrawn;

    public void setPlayer(){

        System.out.println(" ####### Player Details ######### ");
        System.out.println("Please enter your name  : ");
        Scanner in = new Scanner(System.in);
        String playerName = in.next();
        System.out.println("You are provided with 1000 $");
        bjPlayer = new BlackJackPlayer(playerName,1000,0);
    }

    public void setPlayerDealer(){

        System.out.println(" ####### Dealer Details ######### ");
        System.out.println("Please enter dealer name  : ");
        Scanner in = new Scanner(System.in);
        String dealerName = in.next();
        dealer = new Dealer(dealerName,cardDeck,0);
    }

    public void setComputerDealer(){
        dealer = new Dealer("Computer",cardDeck,0);
    }

    public int getRandomNumber(int start, int limit){

        int randomNum = (int) ((Math.random() * (limit - start)) + start);
        return randomNum;
    }


    public int getNoOfPlayers(){

        System.out.println("Enter number of players : (1/2)");
        Scanner in = new Scanner(System.in);
        int noOfPlayers = in.nextInt();
        return noOfPlayers;
    }

    public BlackJackGame(){

        this.noOfPlayers = getNoOfPlayers();
        if(noOfPlayers==1){
            setPlayer();
            System.out.println("The computer will be the dealer for this game.");
            setComputerDealer();
        } else{
            int randomInt = getRandomNumber(1,2);
            System.out.println("Player " + randomInt + " has been assigned to be a dealer randomly !!!");
            setPlayerDealer();
            setPlayer();
        }
    }

    public List<Integer> getHandsScores(List<Hand> handsList){

        List<Integer> handsScores = new ArrayList<Integer>();
        for(Hand H : handsList){
            handsScores.add(H.returnHandValue(21));
        }
        return handsScores;
    }

    public boolean greaterThan21(List<Integer> handsScores){

        for(int i : handsScores){
            if(i > 21){
                return true;
            }
        }
        return false;
    }

    public int getHandChoice(){

        Scanner in = new Scanner(System.in);
        int handChoice  = in.nextInt();
        while(handChoice > bjPlayer.noOfHands){
            System.out.println("You only have " + bjPlayer.noOfHands + " hand(s)!!!");
            System.out.println("Enter hand again : ");
            handChoice  = in.nextInt();
        }
        return handChoice;
    }


    public boolean checkPlayerWin() {

        for(Hand hand : dealer.playerHands){
            for(Card C : hand.cardList){
                C.faceUp = true;
            }
        }

        System.out.println("Dealer " + dealer.playerName + "'s cards :" );
        dealer.displayHands();

        int dealerSum = getHandsScores(dealer.playerHands).get(0);

        while( dealerSum < 17){
            System.out.println("Dealer " + dealer.playerName + " is hitting himself.");
            Card C = dealer.returnRandomCard(true);
            dealer.hit(C,1);
            dealerSum += C.value;
        }

        System.out.println("Dealer " + dealer.playerName + "'s cards :" );
        dealer.displayHands();

        if(dealerSum > 21){
            System.out.println(dealer.playerName + " has bust !!!");
            return true;
        }

        List<Integer> playerHandsScores = getHandsScores(bjPlayer.playerHands);

        for( int playerScore : playerHandsScores){
            if(playerScore > dealerSum){
                return true;
            }
        }

        for( int playerScore : playerHandsScores){
            if(playerScore == dealerSum){
                matchIsDrawn = true;
                return true;
            }
        }

        return false;
    }

    public void reInitialize(){
        bjPlayer.initialBet = 0;
        bjPlayer.bet = 0;
        bjPlayer.playerHands.removeAll(bjPlayer.playerHands);
        dealer.playerHands.removeAll(dealer.playerHands);
        bjPlayer.noOfHands = 0;
        dealer.noOfHands = 0;
    }

    public void playerHasLost(){

        bjPlayer.cutMoneyFromPurse(bjPlayer.bet);
        dealer.addToPool(pool, bjPlayer.bet);
        System.out.println(bjPlayer.playerName + " now has $" + bjPlayer.purse + " in his/her purse.");
        reInitialize();
    }

    public void playerHasWon(){

         dealer.subtractFromPool(pool, 2 * bjPlayer.bet);
         bjPlayer.addMoneyToPurse(2 * bjPlayer.bet);
         System.out.println(bjPlayer.playerName + " now has $" + bjPlayer.purse + " in his/her purse.");
         reInitialize();
    }

    public void matchIsDrawn(){
        
        System.out.println(bjPlayer.playerName + " now has $" + bjPlayer.purse + " in his/her purse.");
        reInitialize();
    }

    public void naturalBlackjack(List<Hand> handsList){
        List<Integer> playerScores = getHandsScores(handsList);
        for(int score : playerScores){
            if(score == 21){
               System.out.println(bjPlayer.playerName +  "has been dealt a natural Blackjack !!!");
               playerHasWon();
                bjPlayer.isCurrentlyStanding = true;
                break;
            }
        }
    }

    public void printResults(boolean checkWin){

        if (checkWin){
            if(matchIsDrawn){
                System.out.println("The match is drawn !!!");
                matchIsDrawn();
            }
            else{
                System.out.println("Player : " + bjPlayer.playerName + " has won the game !!!!");
                playerHasWon();
            }

        } else {
            System.out.println("Dealer : " + dealer.playerName + " has won the game !!!!");
            playerHasLost();
        }
    }

    public void runGame(){

        matchIsDrawn = false;
        boolean continueGame = true;

        Scanner in = new Scanner(System.in);
        boolean checkWin = false;


        while(continueGame){

            System.out.println();
            System.out.println("Dealer is now dealing 2 cards to the player and (him/her)self.");
            List<Card> playerHand = new ArrayList<Card>();
            Card playerCard1 = dealer.returnRandomCard(true);
            Card playerCard2 = dealer.returnRandomCard(true);
            playerHand.add(playerCard1);
            playerHand.add(playerCard2);
            bjPlayer.createHand(playerHand);

            List<Card> dealerHand = new ArrayList<Card>();
            Card dealerCard1 = dealer.returnRandomCard(true);
            Card dealerCard2 = dealer.returnRandomCard(false);
            dealerHand.add(dealerCard1);
            dealerHand.add(dealerCard2);
            dealer.createHand(dealerHand);

            System.out.println(bjPlayer.playerName + ", how much do you want to bet?");
            bjPlayer.initialBet = in.nextInt();
            bjPlayer.addMoneyToBet(bjPlayer.initialBet);

            System.out.println();
            System.out.println("Dealer's current hands  : ");
            dealer.displayHands();
            System.out.println("Player's current hands  : ");
            bjPlayer.displayHands();
            System.out.println();

           bjPlayer.isCurrentlyStanding = false;
           naturalBlackjack(bjPlayer.playerHands);

           while(!bjPlayer.isCurrentlyStanding){
                System.out.println(bjPlayer.playerName + " do you want to :");
                System.out.println("1. Hit?");
                System.out.println("2. Stand?");
                System.out.println("3. Split?");
                System.out.println("4. Double?");
                int playerChoice = in.nextInt();

                if(playerChoice == 1){
                    System.out.println("Which hand do you want to hit?");
                    int handChoice = getHandChoice();
                    Card C = dealer.returnRandomCard(true);
                    bjPlayer.hit(C, handChoice);
                    bjPlayer.displayHands();
                    List<Integer> handsScores = getHandsScores(bjPlayer.playerHands);
                    boolean bust = greaterThan21(handsScores);
                    if(bust){
                        System.out.println(bjPlayer.playerName + " has bust !!!");
                        playerHasLost();
                        bjPlayer.isCurrentlyStanding = true;
                    }

                } else if (playerChoice == 2){
                    bjPlayer.stand();
                    checkWin = checkPlayerWin();
                    printResults(checkWin);

                } else if (playerChoice == 3){

                    System.out.println("Which hand do you want to split?");
                    int handChoice = getHandChoice();
                    bjPlayer.split(handChoice);
                    bjPlayer.displayHands();

                } else {
                    System.out.println("You need to hit to double your bet amount.");
                    System.out.println("Which hand do you want to hit?");
                    int handChoice = getHandChoice();
                    Card C = dealer.returnRandomCard(true);
                    bjPlayer.Double(C, handChoice);
                    checkWin = checkPlayerWin();
                    printResults(checkWin);
                }

            }

            System.out.println("Do you want to play again (Y/N) : ");
            String continueChoice = in.next();
            if(continueChoice.equalsIgnoreCase("n")){
                continueGame = false;
            }
        }
    }
}
