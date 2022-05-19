/* Class representing Nexus cell for heroes */

public class Hero_Nexus_Cell implements Cell{
    public String symbol = "N";
    public String type = "Hero Nexus";
    public String color = "\033[1;96m";

    public Hero_Nexus_Cell(){
        this.symbol = "N";
        this.type = "Hero Nexus";
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
