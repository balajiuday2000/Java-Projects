/* Class representing Inaccessible cell */

public class Inaccessible_Cell implements Cell{
    //public String symbol = "⠿⠿";
    public String symbol = "###";
    public String type = "Inaccessible";
    public String color = "\033[1;97m";

    public Inaccessible_Cell(){
        this.symbol="###";
        this.type="Inaccessible";
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
