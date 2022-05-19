/* Abstract base class which makes sure all sub-classes of items which can be used by the team of heroes,
have a name, cost and level associated with them. */

public class Gear implements LevelRequirement{

    protected String name;
    protected int cost;
    protected int level;

    Gear(String n, int c, int l){
        name = n;
        cost = c;
        level = l;
    }

    public String get_name(){
        return name;
    }

    public int get_cost(){
        return cost;
    }

    public int get_level(){
        return level;
    }

    @Override
    public boolean is_equipable(int l) { // Method of interface LevelRequirement
        if (l >= level){
            return true;
        }
        return false;
    }
}
