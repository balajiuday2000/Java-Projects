/* Sub-class of gear, representing Potions for heroes */

public class Potion extends Gear{

    private int increase;
    private String [] attribute;

    Potion(String n, int c, int l, int i, String [] a){
        super(n, c, l);
        increase = i;
        attribute = a;
    }

    public void print_potion(){
        System.out.format("%-20s%-6d%-16d%-10d", name, cost, level, increase);
        System.out.print(attribute[0]);
        for (int i = 1; i < attribute.length; i++){
            System.out.print("/"+attribute[i]);
        }
        System.out.print("\n");

    }

    public int get_increase(){
        return increase;
    }

    public String[] get_attribute(){
        return attribute;
    }
}
