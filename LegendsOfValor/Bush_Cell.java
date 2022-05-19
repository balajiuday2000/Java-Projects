/* Class representing Bush cell */

public class Bush_Cell implements Cell{
    public String symbol = "B";
    public String type = "Bush";
    public String color = "\033[0;92m" ;

    public Bush_Cell(){
        this.symbol = "B";
        this.type = "Bush";
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
