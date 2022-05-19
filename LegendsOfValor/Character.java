/* Base class representing all heroes and monsters, which makes sure each character has a name, level and hp */

public class Character {
    protected String name;
    protected int level;
    protected int hp;

    Character(String n, int l){
        name = n;
        level = l;
        hp = 100 * l;
    }

    public String get_name(){
        return name;
    }

    public int get_level(){
        return level;
    }

    public int get_hp(){
        return hp;
    }

    public void change_hp(int h){
        hp += h;
    }
}
