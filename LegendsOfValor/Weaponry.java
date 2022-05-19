/* Sub-class of gear, representing Weapons for heroes */

public class Weaponry extends Gear{

    private int damage;
    private int hands;

    Weaponry(String n, int c, int l, int d, int h){
        super(n, c, l);
        damage = d;
        hands = h;
    }

    public void print_weapon(){
        System.out.format("%-17s%-6d%-16d%-8d%-3d\n", name, cost, level, damage, hands);
    }

    public int get_damage(){
        return damage;
    }
}
