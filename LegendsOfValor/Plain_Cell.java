/* Class representing Plain cell */

import java.security.PublicKey;

public class Plain_Cell implements Cell {
    public String symbol = "P";
    public String type = "Plain";
    public String color = "\033[0;94m";

    public Plain_Cell(){
        this.symbol = "P";
        this.type = "Plain";
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
