/* Sub-class of gear, representing Spells for heroes */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class Spell extends Gear implements ManaRequirement{

    protected int damage;
    protected int mana;

    Spell(String n, int c, int l, int d,int m){
        super(n,c,l);
        damage = d;
        mana = m;
    }

    @Override
    public boolean is_castable(int m){ // Method of interface ManaRequirement
        return m >= mana;
    }

    public int get_damage(int dexterity){
        return (int) (damage * (0.5 + dexterity / 10000));
    }

    public int get_mana(){
        return mana;
    }

    public abstract void print_spell();
    public abstract void spell_effects(Monster m);
    public abstract void play_spell_music() throws UnsupportedAudioFileException, LineUnavailableException, IOException;
}
