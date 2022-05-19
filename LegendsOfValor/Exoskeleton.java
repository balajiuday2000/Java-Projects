/* Sub-class of Monster, representing Exoskeletons */

public class Exoskeleton extends Monster{

    Exoskeleton(String n, int l, int da, int de, int dod){
        super(n, l, da, de, dod);
    }

    @Override
    public void print_status() { // Method of abstract base class Monster
        System.out.format("%-20s%-15s%-7d%-6d%-10d%-10d%-8d\n", name, "Exoskeleton", level, hp, damage, defence, dodge_chance);
    }
}
