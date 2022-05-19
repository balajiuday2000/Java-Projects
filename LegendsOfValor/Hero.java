/* Sub-class of character representing all the characteristics of heroes */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public abstract class Hero extends Character{
    protected int mana;
    protected int exp;
    protected int strength;
    protected int dexterity;
    protected int agility;
    protected int money;
    protected Inventory gears;
    protected int max_mana;
    protected int max_hp;
    protected int position;
    protected int ori_position;
    protected Weaponry weapon;
    protected Armory armour;
    protected int[] allowed_options;
    protected int increase_due_to_cell;

    Hero(String n, int l, int m, int s, int a, int d, int mo, int e){
        super(n, l);
        mana = m;
        strength = s;
        dexterity = d;
        agility = a;
        exp = e;
        money = mo;
        gears = new Inventory();
        max_mana = mana;
        max_hp = 100;
        this.allowed_options = new int[]{1,1,1,1,1,1,1,1,1,1}; // Keeps track of the possible moves of the hero for each round
        this.increase_due_to_cell = 0;

        weapon = new Weaponry("Stick", 0, 1, 0, 1);
        armour = new Armory("Plain Clothes", 0, 1, 0);
    }

    // Method to get effect on hero due to current cell
    public int get_increase_due_to_cell(){
        return this.increase_due_to_cell;
    }

    // Method to reflect effect on hero due to current cell
    public void set_increase_due_to_cell(int increase){
        this.increase_due_to_cell = increase;
    }

    public int get_mana(){
        return mana;
    }

    public int get_dexterity(){
        return dexterity;
    }

    public int get_damage(){
        return (int)((strength + weapon.get_damage()) * 0.05);
    }

    public int get_money(){
        return money;
    }

    public int get_position(){
        return position;
    }

    public Inventory get_gears(){
        return gears;
    }

    public void reset_hp(double ratio){
        hp += max_hp * ratio;
    }

    public void reset_mana(double ratio){
        mana += max_mana * ratio;
    }

    public int[] get_allowed_options(){
        return this.allowed_options;
    }

    public void set_allowed_options(int[] allowed_options){
        this.allowed_options = allowed_options;
    }

    public void set_ori_position(int p){
        ori_position = p;
    }

    public void reset(){
        hp = max_hp;
        mana = max_mana;
        position = ori_position;
    }

    public int take_damage(int d){
        if (Math.random() < agility * 0.0002){
            return 0;
        }
        int actual_damage = (int)(d * (1 - ((dexterity + armour.get_damage_reduction()) * 0.0002)));
        if (actual_damage < 0){
            actual_damage = 0;
        }
        hp -= actual_damage;
        if (hp <= 0){
            hp = 0;
        }
        return actual_damage;
    }

    public void change_money(int num){
        money += num;
    }

    public void change_mana(int num){
        mana += num;
    }

    public void change_exp(int num){
        exp += num;
    }

    public void change_position(int p){
        position = p;
    }

    public void update_weapon(Weaponry w){
        gears.add_weapon(weapon);
        weapon = w;
        gears.remove_weapon(w);
    }

    public void update_armour(Armory a){
        gears.add_arm(armour);
        armour = a;
        gears.remove_armor(a);
    }

    public void print_status(){
        System.out.format("%-20s%-7d%-6d%-16d\n", name, level, hp, mana);
    }

    abstract public void print_detailed_status();

    public void check_level_up(){
        if (level == 10){
            return;
        }
        if (exp >= level * 10){
            System.out.println(name + " leveled up! All attributes increased!");
            this.increase_attributes();
            max_hp += 100;
            max_mana += 50;
            exp = exp % (level * 10);
            level += 1;
        }
    }

    abstract public void increase_attributes();

    public void drink_potion(Potion p){
        try {
            Music.play_potion_music(name);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        String [] att = p.get_attribute();
        for (int i = 0; i < att.length; i++){
            if (Objects.equals(att[i], "Health")){
                hp += p.get_increase();
                System.out.println(name + " drank a health potion. HP increased by " + p.get_increase());
            }
            else if (Objects.equals(att[i], "Mana")){
                mana += p.get_increase();
                System.out.println(name + " drank a Mana potion. Mana increased by " + p.get_increase());
            }
            else if (Objects.equals(att[i], "Strength")){
                strength += p.get_increase();
                System.out.println(name + " drank a Strength potion. Strength increased by " + p.get_increase());
            }
            else if (Objects.equals(att[i], "Dexterity")){
                dexterity += p.get_increase();
                System.out.println(name + " drank a Dexterity potion. Dexterity increased by " + p.get_increase());
            }
            else if (Objects.equals(att[i], "Agility")){
                agility += p.get_increase();
                System.out.println(name + " drank an Agility potion. Agility increased by " + p.get_increase());
            }
        }
        System.out.println();
        gears.remove_potion(p);
        if (gears.get_potion_num() == 0){
            allowed_options[7] = 0;
        }
    }

    public boolean isAlive(){
        if (hp == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean detect_enemy(Monster m){ // Function to detect monsters in the neighbouring cells
        if (!(m.get_position() == this.position-1) && !(m.get_position() == this.position+1)
                    && !(m.get_position() == this.position-10)  && !(m.get_position() == this.position+10)
                    && !(m.get_position() == this.position-11)  && !(m.get_position() == this.position-9)
                    && !(m.get_position() == this.position+9)  && !(m.get_position() == this.position+11)
                    && !(m.get_position() == this.position)){
            return false;
        }
        else{
            return true;
        }
    }

    public void check_equips(){
        Scanner option = new Scanner(System.in);
        first_loop:
        while (true){
            System.out.println(name);
            System.out.println("\nArmors:");
            gears.print_armor();
            System.out.println("------------------------------------------------------------------");
            System.out.println("\nWeapons:");
            gears.print_weapon();
            System.out.println("------------------------------------------------------------------");
            System.out.println("What do you want to do?");
            System.out.println("1.Change Armor    2.Change Weapon     3.Back     ");

            String command;
            while (true) {
                command = option.nextLine();
                if (Objects.equals(command, "1") || Objects.equals(command, "2")) {
                    break;
                }
                else if (Objects.equals(command, "3")){
                    break first_loop;
                }
                System.out.println("Invalid input, please re-enter. ");
            }
            System.out.println("Equip/Use the item by entering the number in front (Enter 0 to go back to last menu): ");
            int num;
            while (true) {
                try {
                    num = Integer.parseInt(option.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("The input is not a number. Please re-enter:");
                    continue;
                }
                if (num == 0){
                    break;
                }
                if (command.equals("1")){
                    if (num > 0 && num <= gears.get_armor_num()){
                        try {
                            Music.play_change_armor_music(name);
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        }
                        update_armour(gears.get_armor(num-1));
                        break;
                    }
                }
                else if (command.equals("2")){
                    if (num > 0 && num <= gears.get_weapon_num()){
                        try {
                            Music.play_change_weapon_music(name);
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        }
                        update_weapon(gears.get_weapon(num-1));
                        break;
                    }
                }
                System.out.println("Input is invalid. Please re-enter: ");
            }
        }
    }
}
