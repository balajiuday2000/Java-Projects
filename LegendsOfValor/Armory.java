/* Sub-class of gear, representing Armours for heroes */

public class Armory extends Gear{

    private int damage_reduction;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE


    Armory(String n, int c, int l, int d){
        super(n, c, l);
        damage_reduction = d;
    }

    public int get_damage_reduction(){
        return damage_reduction;
    }

    public void print_armor(){
        System.out.format(WHITE_BRIGHT+"%-30s%-6d%-16d%-10d\n"+ANSI_RESET, name, cost, level, damage_reduction);
    }
}
