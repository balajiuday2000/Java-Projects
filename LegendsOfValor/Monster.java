/* Sub-class of character representing all the characteristics of monsters */

abstract public class Monster extends Character{
    protected int damage;
    protected int defence;
    protected int dodge_chance;
    protected int position;

    Monster(String n, int l, int da, int de, int dod){
        super(n, l);
        damage = da;
        defence = de;
        dodge_chance = dod;
    }

    public void change_position(int p){
        position = p;
    }

    public void decrease_damage(){
        damage *= 0.9;
    }

    public void decrease_defence(){
        defence *= 0.9;
    }

    public void decrease_dodge(){
        dodge_chance *= 0.9;
    }

    public int get_damage(){
        return (int)(damage * 0.05);
    }

    public int get_position(){
        return position;
    }

    public int take_damage(int d){
        if (Math.random() < dodge_chance * 0.006){
            return 0;
        }
        int actual_damage = d - (int)(defence * 0.02);
        if (actual_damage < 0){
            actual_damage = 0;
        }
        hp -= actual_damage;
        if (hp < 0){
            hp = 0;
        }
        return actual_damage;
    }

    public boolean isAlive(){
        if (hp == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean detect_enemy(Hero m){ //Checks for the presence of heroes in all the neighbouring cells
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

    abstract public void print_status();
}
