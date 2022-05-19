import java.util.*;

public class GameChoice {

    public void runCardGame(){

        System.out.println("######### WELCOME TO THE CARD GAME ##########");
        System.out.println();

        boolean playerFlag = false;
        int gameChoice = 0;

        while(!playerFlag){
            System.out.println("Choose which game do you want to play :");
            System.out.println("1. BlackJack");
            System.out.println("2. Trianta Ena");
            Scanner in = new Scanner(System.in);
            gameChoice = in.nextInt();
                if ((gameChoice != 1) && (gameChoice != 2)){
                    System.out.println("The input is invalid. Please check.");
                } else {
                    playerFlag = true;
                }
        }
            if (gameChoice == 1){
                BlackJackGame newBJGame = new BlackJackGame();
                newBJGame.runGame();
            } else {
                TriantaEnaGame newTGGame = new TriantaEnaGame();
                newTGGame.runGame();
            }
    }
}

