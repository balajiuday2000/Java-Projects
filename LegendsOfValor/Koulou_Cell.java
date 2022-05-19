/* Class representing Koulou cell */

public class Koulou_Cell implements Cell{
    public String symbol = "K";
    public String type = "Koulou";
    public String color = "\033[0;95m";

    public Koulou_Cell(){
        this.symbol = "K";
        this.type = "Koulou";
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
