/* Class used for user to select different games */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// GameManager class is like a "menu" for all games available. Only contains gameList which is a list of games.
public class GameManager {
    private RPG[] gameList;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN

    // The entire list will have 2 default games for now :)
    public GameManager() throws FileNotFoundException {
        LOV g = new LOV();
        this.gameList = new RPG[]{g};
    }

    // get the current game list
    public RPG[] getGameList() {
        return this.gameList;
    }

    // prompts the user to select a game from the game list.
    public RPG selection() {
        Scanner in = new Scanner(System.in);
        String pattern = "[1]";
        String bORt = "";
        while(true) {
            bORt = in.next();
            if(!bORt.matches(pattern)) {
                System.out.println("Error: Wrong input! Please input again.");
                continue;
            }
            else
                break;
        }
        return getGameList()[0];
    }

    // the GameManager system starts. It allows user to select and play the games in game list.
    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        System.out.println(GREEN_BOLD_BRIGHT+
                " _    _      _                            _          _   _            _____                        _____            _            \n" +
                "| |  | |    | |                          | |        | | | |          |  __ \\                      /  __ \\          | |           \n" +
                "| |  | | ___| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  | |  \\/ __ _ _ __ ___   ___  | /  \\/ ___ _ __ | |_ ___ _ __ \n" +
                "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\ | | __ / _` | '_ ` _ \\ / _ \\ | |    / _ \\ '_ \\| __/ _ \\ '__|\n" +
                "\\  / \\  /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/ | |_\\ \\ (_| | | | | | |  __/ | \\__/\\  __/ | | | ||  __/ |   \n" +
                " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___|  \\____/\\__,_|_| |_| |_|\\___|  \\____/\\___|_| |_|\\__\\___|_|   \n" +
                "\n"+ANSI_RESET);
        System.out.println(WHITE_BOLD_BRIGHT+"What would you like to play?"+ANSI_RESET);
        System.out.println(CYAN_BRIGHT+"Press <1> for  Legends of Valor"+ANSI_RESET);
        System.out.println(RED_BRIGHT+"Other games under development..."+ANSI_RESET);
        boolean next = true;

        RPG g = selection();
        g.startGame();

    }

}
