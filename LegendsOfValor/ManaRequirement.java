/* Interface implemented by Spell to make sure the items can only be used by a hero satisfying the minimum
mana requirement */

public interface ManaRequirement{

    public boolean is_castable(int m);
}
