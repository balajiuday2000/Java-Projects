/* Class representing Cave cell */

public class Cave_Cell implements Cell{
    public String symbol = "C";
    public String type = "Cave";
    public String color = "\033[0;93m";

    public Cave_Cell(){
        this.symbol = "C";
        this.type = "Cave";
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
