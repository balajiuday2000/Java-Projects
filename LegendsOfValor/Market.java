/* Class representing the market, capable of selling and purchasing items */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.*;
import java.io.*;

public class Market {
    private static Market singleton_instance;
    private Inventory goods;
    private Hero customer;
    private Parser p = new Parser();
    private Scanner input = new Scanner(System.in);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN

    private Market() throws FileNotFoundException {
        goods = new Inventory(p.parse_armory(), p.parse_weaponry(), p.parse_potion(), p.parse_all_spell());
    }

    public static Market get_single_instance(){
        if (singleton_instance == null){
            try {
                singleton_instance = new Market();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return singleton_instance;
    }

    public void enter_market(Hero h){
        Printer.print_market_welcome();
        customer = h;
        first_loop:
        while(true){
            System.out.println(WHITE_BOLD_BRIGHT+"Do you want to sell or buy?"+ANSI_RESET);
            System.out.print(WHITE_BRIGHT+"1. Sell"+ANSI_RESET);
            System.out.print(WHITE_BRIGHT+"\t\t"+"2. Buy"+ANSI_RESET);
            System.out.print(WHITE_BRIGHT+"\t\t"+"0. Back to previous menu"+ANSI_RESET);
            System.out.println();
            int choice;
            second_loop:
            while(true){
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("The input is not a number. Please re-enter:");
                    continue;
                }
                if (choice == 0){
                    break first_loop;
                }
                else if (choice == 2){
                    System.out.println(WHITE_BOLD_BRIGHT+"What type of merchandise are you looking for?"+ANSI_RESET);
                    System.out.print(WHITE_BRIGHT+"1. Armor"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"2. Weapon"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"3. Potion"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"4. Spell"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"0. Back to previous menu"+ANSI_RESET);
                    System.out.println(" ");
                    int type;
                    while(true){
                        try {
                            type = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("The input is not a number. Please re-enter:");
                            continue;
                        }
                        if (0 < type && type <= 6 ){
                            purchase(type);
                            break;
                        }
                        else if (type == 0){
                            break second_loop;
                        }
                        System.out.println("The input number is invalid. Please re-enter:");
                    }
                    break;
                }
                else if (choice == 1){
                    System.out.println(WHITE_BOLD_BRIGHT+"What type of item are you selling?"+ANSI_RESET);
                    System.out.print(WHITE_BRIGHT+"1. Armor"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"2. Weapon"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"3. Potion"+ANSI_RESET);
                    System.out.print("\t"+WHITE_BRIGHT+"0. Back to previous menu"+ANSI_RESET);
                    System.out.println(" ");
                    int type;
                    while(true){
                        try {
                            type = Integer.parseInt(input.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("The input is not a number. Please re-enter:");
                            continue;
                        }
                        if (0 < type && type <= 3 ){
                            sell(type);
                            break;
                        }
                        else if (type == 0){
                            break second_loop;
                        }
                        System.out.println("The input number is invalid. Please re-enter:");
                    }
                    break;
                }
                System.out.println("The input is invalid. Please re-enter:");
            }
        }
    }

    public void purchase(int type) {
        first_loop:
        while (true) {
            System.out.println(WHITE_BOLD_BRIGHT+"Here's what we have: "+ANSI_RESET);
            if (type == 1) {
                goods.print_armor();
            } else if (type == 2) {
                goods.print_weapon();
            } else if (type == 3) {
                goods.print_potion();
            } else if (type == 4) {
                goods.print_spell();
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.format(WHITE_BRIGHT+"%-45s%d\n"+ANSI_RESET,WHITE_BRIGHT+"You have "+ANSI_RESET, customer.get_money());
            System.out.println(WHITE_BRIGHT+"0. Back to previous menu"+ANSI_RESET);
            int choice;
            Gear g;
            while (true) {
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("The input is not a number. Please re-enter:");
                    continue;
                }
                if (choice == 0) {
                    break first_loop;
                } else {
                    if (type == 1 && choice <= goods.get_armor_num()) {
                        g = goods.get_armor(choice - 1);
                    } else if (type == 2 && choice <= goods.get_weapon_num()) {
                        g = goods.get_weapon(choice - 1);
                    } else if (type == 3 && choice <= goods.get_potion_num()) {
                        g = goods.get_potion(choice - 1);
                    } else if (type == 4 && choice <= goods.get_spell_num()) {
                        g = goods.get_spell(choice - 1);
                    } else {
                        System.out.println("The input is invalid. Please re-enter:");
                        continue;
                    }
                }
                if (!g.is_equipable(customer.get_level())) {
                    System.out.println("The required level is higher than yours. Please choose another one:");
                } else if (g.get_cost() > customer.get_money()) {
                    System.out.println("You do not have enough money to purchase this item. Please choose another one:");
                } else {
                    customer.change_money(-1 * g.get_cost());
                    if (type == 1) {
                        customer.get_gears().add_arm((Armory) g);
                        customer.allowed_options[9]=1;
                    } else if (type == 2) {
                        customer.get_gears().add_weapon((Weaponry) g);
                        customer.allowed_options[9]=1;
                    } else if (type == 3) {
                        customer.get_gears().add_potion((Potion) g);
                        customer.allowed_options[7]=1;
                    } else {
                        customer.get_gears().add_spell((Spell) g);
                        goods.remove_spell((Spell) g);
                    }
                    try {
                        Music.play_cash_music();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    System.out.println(g.get_name() + " has added to your inventory! \n");
                    break;
                }
            }
        }
    }

    public void sell(int type){
        first_loop:
        while (true){
            System.out.println(WHITE_BOLD_BRIGHT+"Here's what you have: "+ANSI_RESET);
            if (type == 1){
                customer.get_gears().print_armor();
                if (customer.gears.get_weapon_num()==0 && customer.gears.get_armor_num()==0){
                    customer.allowed_options[9]=0;
                }
            }
            else if (type == 2){
                customer.get_gears().print_weapon();
                if (customer.gears.get_weapon_num()==0 && customer.gears.get_armor_num()==0){
                    customer.allowed_options[9]=0;
                }
            }
            else if (type == 3){
                customer.get_gears().print_potion();
                if (customer.gears.get_potion_num()==0){
                    customer.allowed_options[7]=0;
                }
            }
            System.out.println("------------------------------------------------------------------");
            System.out.println("0. Back to previous menu");
            int choice;
            Gear g;
            second_loop:
            while(true) {
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("The input is not a number. Please re-enter:");
                    continue;
                }
                if (choice == 0) {
                    break first_loop;
                } else {
                    if (type == 1 && choice <= customer.get_gears().get_armor_num()) {
                        g = customer.get_gears().get_armor(choice-1);
                    } else if (type == 2 && choice <= customer.get_gears().get_weapon_num()) {
                        g = customer.get_gears().get_weapon(choice-1);
                    } else if (type == 3 && choice <= customer.get_gears().get_potion_num()) {
                        g = customer.get_gears().get_potion(choice-1);
                    } else {
                        System.out.println("The input is invalid. Please re-enter:");
                        continue;
                    }
                }
                String confirm;
                System.out.println("You will sell this item for " + g.get_cost()/2 + " golds, are you sure? (y/n)");
                confirm = input.nextLine();
                while (true){
                    if (Objects.equals(confirm, "y")){
                        customer.change_money(g.get_cost()/2);
                        if (type == 1){
                            customer.get_gears().remove_armor((Armory) g);
                        }
                        else if (type == 2){
                            customer.get_gears().remove_weapon((Weaponry) g);
                        }
                        else {
                            customer.get_gears().remove_potion((Potion) g);
                        }
                        try {
                            Music.play_cash_music();
                        } catch (UnsupportedAudioFileException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        }
                        System.out.println("You successfully sold " + g.get_name() + "!");
                        break second_loop;
                    }
                    else if (Objects.equals(confirm, "n")){
                        break second_loop;
                    }
                    else{
                        System.out.println("The input is invalid. Please re-enter:");
                    }
                }
            }
        }
    }
}
