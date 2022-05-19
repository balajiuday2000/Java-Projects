/* Sub-class of abstract base class Spell representing Lightning Spells */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class LightningSpell extends Spell{

    LightningSpell(String n, int c, int l, int d,int m){
        super(n, c, l, d, m);
    }

    @Override
    public void print_spell(){ // Method of abstract base class Spell
        System.out.format("\033[0;93m%-20s%-7d%-16d%-8d%-5d\n\u001B[0m", name, cost, level, damage, mana);
    }

    public void spell_effects(Monster m){
        m.decrease_dodge();
    }

    @Override
    public void play_spell_music() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        Music.play_spell_music();
    }
}
