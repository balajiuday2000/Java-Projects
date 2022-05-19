/* The main file that calls Game Manager. */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

// Main.java: Main function to run the program.
public class Main {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        GameManager gm = new GameManager();
        gm.run();
    }

}
