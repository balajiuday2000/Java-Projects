/* Sub-class of abstract base class Spell representing Fire Spells */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class FireSpell extends Spell{

    FireSpell(String n, int c, int l, int d,int m){
        super(n, c, l, d, m);
    }

    @Override
    public void print_spell() { // Method of abstract base class Spell
        System.out.format("\033[0;91m%-20s%-7d%-16d%-8d%-5d\n\u001B[0m", name, cost, level, damage, mana);
    }

    public void spell_effects(Monster m){
        m.decrease_damage();
    }

    @Override
    public void play_spell_music() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music.play_spell_music();
    }
}
