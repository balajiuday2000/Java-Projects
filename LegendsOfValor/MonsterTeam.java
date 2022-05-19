/* Class representing a group of monsters controlled by the computer */

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MonsterTeam {

    private ArrayList<Monster> all_monsters;
    private ArrayList<Monster> monsters;
    private Parser p = new Parser();

    MonsterTeam(){
        //all_monsters = p.parse_all_monster();
        monsters = new ArrayList<Monster>();
    }

    public Monster get_monster(int h){
        return monsters.get(h);
    }
    public int get_monster_team_size(){
        return this.monsters.size();
    }

    public void add_monster(int level) throws FileNotFoundException {
        all_monsters = p.parse_all_monster();
        ArrayList<Monster> eligible_monsters = new ArrayList<Monster>();
        for (Monster m : all_monsters) {
            if (m.get_level() <= level){
                eligible_monsters.add(m);
            }
        }

        int random_index = (int)(Math.floor(Math.random()*eligible_monsters.size()));
        monsters.add(eligible_monsters.get(random_index));
        all_monsters.remove(eligible_monsters.get(random_index));
    }

    public void remove_monster(Monster m){
        monsters.remove(m);
    }

    /*public void generate_team(int size, int level){
        monsters = new ArrayList<Monster>();
        ArrayList<Monster> eligible_monsters = new ArrayList<Monster>();
        for (Monster m : all_monsters) {
            if (m.get_level() <= level){
                eligible_monsters.add(m);
            }
        }

        for (int i = 0; i < size; i++){
            monsters.add(eligible_monsters.get((int)(Math.random()*eligible_monsters.size())));
        }
    }*/

    public boolean isAlive(){
        for (Monster m : monsters){
            if (m.isAlive()){
                return true;
            }
        }
        return false;
    }

    public void print_status(){
        System.out.println("+------------------------------------+");
        System.out.println("|            Enemy Status            |");
        System.out.println("+------------------------------------+");
        System.out.println("Name                Type           level  HP    Damage    Defence   Dodgechance");
        for (Monster m : monsters){
            m.print_status();
        }
    }

    public void action(HeroTeam p){
        Hero h;
        Monster m;
        for (int i = 0; i <monsters.size(); i++){
            h = p.get_hero(i);
            m = monsters.get(i);
            if (monsters.get(i).isAlive()){
                int j = i;
                while(!p.isAlive()){
                    h = p.get_hero((j+1)%monsters.size());
                    j += 1;
                }
                System.out.println(m.get_name()+" (M"+monsters.indexOf(m)+")" +" dealt "+ h.take_damage(m.get_damage()) + " to " + h.get_name());
            }

        }
    }

    public int[] get_position(){
        int [] pos = new int[monsters.size()];
        for (int i = 0; i < monsters.size(); i++){
            pos[i] = monsters.get(i).get_position();
        }
        return pos;
    }

}
