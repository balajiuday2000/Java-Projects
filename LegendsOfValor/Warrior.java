/* Sub-class of Hero, representing Warriors */

public class Warrior extends Hero{

    Warrior(String n, int l, int m, int s, int a, int d, int mo, int e){
        super(n, l, m, s, a, d, mo, e);
    }

    @Override
    public void print_detailed_status(){ // Method of the abstract base class Hero
        System.out.format("%-20s%-11s%-7d%-6d%-6d%-10d%-10d%-10d%-6d%-6d%-10s%-15s\n", name, "Warrior", level, hp, mana, strength, dexterity, agility, money, exp, weapon.get_name(), armour.get_name());
    }

    @Override
    public void increase_attributes(){
        this.strength *= 1.1;
        this.dexterity *= 1.05;
        this.agility *= 1.1;
    }
}
