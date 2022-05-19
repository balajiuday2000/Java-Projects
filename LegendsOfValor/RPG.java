/* Class that contains the major elements of an RPG game */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public abstract class RPG {

    public RPG() {}

    public abstract void character_selection() throws FileNotFoundException;
    public abstract void startGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException;
    public abstract boolean round() throws IOException, UnsupportedAudioFileException, LineUnavailableException;



}
