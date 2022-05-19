/* Class representing a group of heroes controlled by a single player */

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class HeroTeam {

    private ArrayList<Hero> heroes;

    HeroTeam(){
        heroes = new ArrayList();
    }

    public void add_heroes(Hero h){ heroes.add(h); }
    public int get_hero_team_size(){
        return this.heroes.size();
    }

    public Hero get_hero(int h){
        return heroes.get(h);
    }

    public void reset(){
        System.out.println("You lost! All heroes were reset with half hp and money.");
        for (Hero h : heroes){
            h.reset_hp(1);
            h.reset_mana(1);
        }
    }

    public void reward(){
        System.out.println("An enemy has been slain!");
        for (Hero h : heroes){
            if (h.isAlive()){
                int golds = 100 * this.get_highest_level();
                int exp = 2;
                System.out.println(h.get_name() + " got " + golds + " golds and " + exp + " exp!");
                h.reset_hp(0.1);
                h.reset_mana(0.1);
                h.change_money(golds);
                h.change_exp(exp);
                h.check_level_up();
            }
            else{
                System.out.println(h.get_name() + " got revived by teammates and restored 50% HP.");
                h.reset_hp(0.5);
            }
        }
    }

    public int get_highest_level(){
        int highest = 0;
        for (Hero h : heroes){
            if (h.get_level() > highest){
                highest = h.get_level();
            }
        }
        return highest;
    }

    public boolean isAlive(){
        for (Hero h : heroes){
            if (h.isAlive()){
                return true;
            }
        }
        return false;
    }

    public void print_status(){
        System.out.println("+------------------------------------+");
        System.out.println("|            Player Status           |");
        System.out.println("+------------------------------------+");
        System.out.println("Name                level  HP    Mana       ");
        for (Hero h : heroes){
            h.print_status();
        }
    }

    public void print_detailed_status(){
        for (Hero h : heroes){
            System.out.println(h.get_name());
            System.out.println("---------------------------------------------");
            System.out.println("Name                 Role      level  HP    Mana  Strength  Dexterity Agility   Money Exp   Weapon    Armor");
            h.print_detailed_status();
        }

    }

    public void action(MonsterTeam e){
        Scanner move = new Scanner(System.in);
        Hero h;
        Monster m;
        for (int i = 0; i <heroes.size(); i++){
            h = heroes.get(i);
            m = e.get_monster(i);
            if (h.isAlive()){
                if (!m.isAlive()){
                    if (!e.isAlive()){
                        return;
                    }
                    for (int j = 0; j < heroes.size(); j++) {
                        m = e.get_monster(j);
                        if (m.isAlive()){
                            break;
                        }
                    }
                }

                first_loop:
                while(true){
                    System.out.println("Select action for " + heroes.get(i).get_name() + ": ");
                    System.out.println("a. Attack   b. Magic    c. Potion   d. Change weapon    e. Change armor   i. Show info   q. Quit game");
                    second_loop:
                    while (true){
                        String command = move.nextLine();
                        if (Objects.equals(command, "a")){
                            System.out.println(h.get_name() +" dealt "+ m.take_damage(h.get_damage()) + " to " + m.get_name() + "!");
                            break first_loop;
                        }
                        else if (Objects.equals(command, "b")){
                            System.out.println("Select spell or cancel:");
                            h.get_gears().print_spell();
                            System.out.println("0. Cancel");
                            int num;
                            while(true){
                                num = Integer.parseInt(move.nextLine());
                                if (num == 0){
                                    break second_loop;
                                }
                                else if (num > 0 && num <= h.get_gears().get_spell_num()){
                                    Spell s = h.get_gears().get_spell(num-1);
                                    if (s.is_castable(h.get_mana())){
                                        System.out.println(h.get_name() +" dealt "+ m.take_damage(s.get_damage(h.get_dexterity())) + " to " + m.get_name() + "!");
                                        s.spell_effects(m);
                                        h.change_mana(-s.get_mana());
                                        break first_loop;
                                    }
                                    else{
                                        System.out.println("You don't have enough mana!");
                                    }
                                }
                            }
                        }
                        else if (Objects.equals(command, "c")){
                            System.out.println("Select potion or cancel:");
                            h.get_gears().print_potion();
                            System.out.println("0. Cancel");
                            int num;
                            while(true){
                                try{
                                    num = Integer.parseInt(move.nextLine());
                                } catch (NumberFormatException w) {
                                    System.out.println("The input is not a number. Please re-enter:");
                                    continue;
                                }
                                if (num == 0){
                                    break second_loop;
                                }
                                else if (num > 0 && num <= h.get_gears().get_potion_num()){
                                    Potion s = h.get_gears().get_potion(num-1);
                                    h.drink_potion(s);
                                    break first_loop;
                                }
                            }
                        }
                        else if (Objects.equals(command, "d")){
                            System.out.println("Select weapon or cancel:");
                            h.get_gears().print_weapon();
                            System.out.println("0. Cancel");
                            int num;
                            while(true){
                                try{
                                    num = Integer.parseInt(move.nextLine());
                                } catch (NumberFormatException w) {
                                    System.out.println("The input is not a number. Please re-enter:");
                                    continue;
                                }
                                if (num == 0){
                                    break second_loop;
                                }
                                else if (num > 0 && num <= h.get_gears().get_weapon_num()){
                                    Weaponry s = h.get_gears().get_weapon(num-1);
                                    h.update_weapon(s);
                                    break first_loop;
                                }
                            }
                        }
                        else if (Objects.equals(command, "e")){
                            System.out.println("Select armor or cancel:");
                            h.get_gears().print_armor();
                            System.out.println("0. Cancel");
                            int num;
                            while(true){
                                try{
                                    num = Integer.parseInt(move.nextLine());
                                } catch (NumberFormatException w) {
                                    System.out.println("The input is not a number. Please re-enter:");
                                    continue;
                                }
                                if (num == 0){
                                    break second_loop;
                                }
                                else if (num > 0 && num <= h.get_gears().get_armor_num()){
                                    Armory s = h.get_gears().get_armor(num-1);
                                    h.update_armour(s);
                                    break first_loop;
                                }
                            }
                        }
                        else if (Objects.equals(command, "i")){
                            print_detailed_status();
                            break;
                        }
                        else if (Objects.equals(command, "q")){
                            System.exit(0);
                        }
                        System.out.println("Invalid input. Please re-enter: ");
                    }
                }
            }
        }
    }

    public int[] get_position(){
        int [] pos = new int[heroes.size()];
        for (int i = 0; i < heroes.size(); i++){
            pos[i] = heroes.get(i).get_position();
        }
        return pos;
    }
}
