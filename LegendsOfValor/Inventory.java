/* Class representing a group of items carried by a unit (hero or market),
classified by item type */

import java.util.*;

public class Inventory {
    private ArrayList<Armory> arms;
    private ArrayList<Weaponry> weapons;
    private ArrayList<Potion> potions;
    private ArrayList<Spell> spells;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN




    Inventory(){
        arms = new ArrayList<Armory>();
        weapons = new ArrayList<Weaponry>();
        potions = new ArrayList<Potion>();
        spells = new ArrayList<Spell>();
    }

    Inventory(ArrayList<Armory> a, ArrayList<Weaponry> w, ArrayList<Potion> p, ArrayList<Spell> s){
        arms = a;
        weapons = w;
        potions = p;
        spells  = s;
    }

    public void print_armor(){

        System.out.println(GREEN_BOLD_BRIGHT+"\tName \t\t\t\t\t\tCost \tLevel \tDamage Reduction "+ANSI_RESET);
        System.out.println(GREEN_BOLD_BRIGHT+"================= \t\t\t  \t===== \t======\t================ "+ANSI_RESET);
        for (int i = 0; i < arms.size(); i++){
            System.out.print(i+1 + ". ");
            arms.get(i).print_armor();
        }
    }

    public void print_weapon(){

        System.out.println(GREEN_BOLD_BRIGHT+"\tName \t\tCost \tLevel \t\t\tDamage Req. Hands "+ANSI_RESET);
        System.out.println(GREEN_BOLD_BRIGHT+"========== \t\t===== \t======\t\t\t===== ======== "+ANSI_RESET);

        for (int i = 0; i < weapons.size(); i++){
            System.out.print(i+1 + ". ");
            weapons.get(i).print_weapon();
        }
    }

    public void print_potion(){

        System.out.println(GREEN_BOLD_BRIGHT+"\tName   \t\t\tCost Level \t\t\t\tIncrease Effect "+ANSI_RESET);
        System.out.println(GREEN_BOLD_BRIGHT+"=================\t===== ======\t\t\t===== ======== "+ANSI_RESET);

        for (int i = 0; i < potions.size(); i++){
            System.out.print(i+1 + ". ");
            potions.get(i).print_potion();
        }
    }

    public void print_spell(){

        System.out.println(GREEN_BOLD_BRIGHT+"\tName \t\t\t\tCost \tLevel\t\t\tDamage Mana Req. "+ANSI_RESET);
        System.out.println(GREEN_BOLD_BRIGHT+"================ \t\t=====\t======\t\t\t===== ======== "+ANSI_RESET);
        for (int i = 0; i < spells.size(); i++){
            System.out.format("%-2d%s", i+1, ". ");
            spells.get(i).print_spell();
        }
    }



    public void add_arm (Armory a){
        arms.add(a);
    }
    public void add_weapon (Weaponry w){
        weapons.add(w);
    }
    public void add_potion (Potion p){
        potions.add(p);
    }
    public void add_spell (Spell s){spells.add(s); }


    public void remove_armor (Armory a){
        arms.remove(a);
    }
    public void remove_weapon (Weaponry a){
        weapons.remove(a);
    }
    public void remove_potion (Potion a){
        potions.remove(a);
    }
    public void remove_spell (Spell s){
        spells.remove(s);
    }



    public Armory get_armor (int order){
        return arms.get(order);
    }
    public Weaponry get_weapon (int order){
        return weapons.get(order);
    }
    public Potion get_potion (int order){
        return potions.get(order);
    }
    public Spell get_spell (int order) { return spells.get(order); }


    public int get_armor_num (){
        return arms.size();
    }
    public int get_weapon_num (){
        return weapons.size();
    }
    public int get_potion_num (){
        return potions.size();
    }
    public int get_spell_num (){
        return spells.size();
    }
}
