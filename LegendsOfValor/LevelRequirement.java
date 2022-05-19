/* Interface implemented by Gear to make sure the items can only be used by a hero satisfying the minimum
level requirement */

public interface LevelRequirement{

    public boolean is_equipable(int l);

}


