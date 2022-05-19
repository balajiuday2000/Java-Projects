/* Class representing Nexus cell for monsters */

public class Monster_Nexus_Cell implements Cell{
    public String symbol = "N";
    public String type = "Monster Nexus";
    public String color = "\033[1;91m";

    public Monster_Nexus_Cell(){
        this.symbol = "N";
        this.type = "Monster Nexus";
    }

    public String get_symbol(){

        return this.symbol;
    }
    public String get_type(){
        return this.type;
    }

    public String get_color(){
        return this.color;
    }
}
