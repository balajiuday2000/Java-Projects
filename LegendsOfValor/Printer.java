/* Class that defines static methods of printing map/instructions/etc. in the game */

import jdk.swing.interop.SwingInterOpUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
public class Printer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String GREEN_BRIGHT = "\033[0;92m";
    public static final String RED_BRIGHT = "\033[0;91m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN

    private static Scanner input = new Scanner(System.in);

    public Printer(){
    }


    public static void PrintWelcomeMsg(){


        System.out.println(
                RED_BRIGHT+
                        "##       ########  ######   ######## ##    ## ########   ######      #######  ########    ##     ##    ###    ##        #######  ########  \n" +
                        "##       ##       ##    ##  ##       ###   ## ##     ## ##    ##    ##     ## ##          ##     ##   ## ##   ##       ##     ## ##     ## \n" +
                        "##       ##       ##        ##       ####  ## ##     ## ##          ##     ## ##          ##     ##  ##   ##  ##       ##     ## ##     ## \n" +
                        "##       ######   ##   #### ######   ## ## ## ##     ##  ######     ##     ## ######      ##     ## ##     ## ##       ##     ## ########  \n" +
                        "##       ##       ##    ##  ##       ##  #### ##     ##       ##    ##     ## ##           ##   ##  ######### ##       ##     ## ##   ##   \n" +
                        "##       ##       ##    ##  ##       ##   ### ##     ## ##    ##    ##     ## ##            ## ##   ##     ## ##       ##     ## ##    ##  \n" +
                        "######## ########  ######   ######## ##    ## ########   ######      #######  ##             ###    ##     ## ########  #######  ##     ## "+ANSI_RESET);


        System.out.println(" ");
        System.out.println(
                RED_BRIGHT+
        "\t\t\t\t"+"        ##      ## ######## ##        ######   #######  ##     ## ######## \n"+
        "\t\t\t\t"+"        ##  ##  ## ##       ##       ##    ## ##     ## ###   ### ##      \n"+
        "\t\t\t\t"+"        ##  ##  ## ##       ##       ##       ##     ## #### #### ##      \n"+
        "\t\t\t\t"+"        ##  ##  ## ######   ##       ##       ##     ## ## ### ## ######  \n"+
        "\t\t\t\t"+"        ##  ##  ## ##       ##       ##       ##     ## ##     ## ##      \n"+
        "\t\t\t\t"+"        ##  ##  ## ##       ##       ##    ## ##     ## ##     ## ##      \n"+
        "\t\t\t\t"+"         ###  ###  ######## ########  ######   #######  ##     ## ########\n"+
        ANSI_RESET);
    }

    public static void print_character_select_message(){

    }

    public static String padRight(String s, int n) {
	return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
	return String.format("%" + n + "s", s);
    }

    public static void print_LOV_gameboard(Gameboard gameboard){
        System.out.print(" ");  // Offset first line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println();

        for (int i = 0; i < gameboard.N; i++) {
           // System.out.print("|");  // Print board edge before each row.
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + ANSI_RESET+"  ");
                }
            }
           // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ "|" + "    " + "|" + "    " + "|" + ANSI_RESET+"  ");
                }
            }
           // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() +ANSI_RESET+ "  ");
                }
            }
           // System.out.print("|");  // Print board edge after each row.
            System.out.println();
        }

        System.out.print(" ");  // Offset last line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println("\n\n");

    }
    public static void print_LOV_gameboard_With_Positions(Gameboard gameboard, ArrayList<Integer> allowed_positions, int[] hero_positions, int[] monster_positions, int hero_position){

        ArrayList<int[]> heroes = new ArrayList<int[]>();
        for (int i = 0; i < hero_positions.length; i++) {
            heroes.add(new int[]{(int) Math.floorDiv(hero_positions[i],10),(int) hero_positions[i]%10});
        }
        ArrayList<int[]> monsters = new ArrayList<int[]>();
        for (int i = 0; i < monster_positions.length; i++) {
            monsters.add(new int[]{(int) Math.floorDiv(monster_positions[i],10),(int) monster_positions[i]%10});
        }

        System.out.print(" ");  // Offset first line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println();


        for (int i = 0; i < gameboard.N; i++) {
            // System.out.print("|");  // Print board edge before each row.
            for (int j = 0; j < gameboard.N; j++) {

                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + ANSI_RESET+"  ");
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                String h_container="  ";
                String m_container="  ";
                for (int[] value:heroes) {
                    if (value[0]==i && value[1]==j){
                        h_container="H"+(heroes.indexOf(value)+1);
                    }
                }
                for (int[] value:monsters) {
                    if (value[0]==i && value[1]==j){
                        m_container="M"+(monsters.indexOf(value)+1);
                    }
                }

                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    if (allowed_positions.contains((Integer) (i*10)+j)) {
                        System.out.print("  " + gameboard.game_board[i][j].get_color() + "|" + "   " + "\033[1;97m" + i + j + gameboard.game_board[i][j].get_color() + "    " + "|" + ANSI_RESET + "  ");
                    }
                    else if (hero_position == (i*10)+j){
                        System.out.print("  " +gameboard.game_board[i][j].get_color()+ "|" + " "+"\033[0;101m"+h_container+ANSI_RESET+gameboard.game_board[i][j].get_color()+" " + "|" + " "+"\033[1;97m"+m_container+gameboard.game_board[i][j].get_color()+" " + "|" + ANSI_RESET+"  ");
                    }
                    else {
                        System.out.print("  " +gameboard.game_board[i][j].get_color()+ "|" + " "+"\033[1;97m"+h_container+gameboard.game_board[i][j].get_color()+" " + "|" + " "+"\033[1;97m"+m_container+gameboard.game_board[i][j].get_color()+" " + "|" + ANSI_RESET+"  ");
                    }
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() +ANSI_RESET+ "  ");
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            System.out.println();

        }

        System.out.print(" ");  // Offset last line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println("\n\n");

    }

    public static void print_LOV_gameboard(Gameboard gameboard, int[] hero_positions, int[] monster_positions){
        ArrayList<int[]> heroes = new ArrayList<int[]>();
        for (int i = 0; i < hero_positions.length; i++) {
            heroes.add(new int[]{(int) Math.floorDiv(hero_positions[i],10),(int) hero_positions[i]%10});
        }
        ArrayList<int[]> monsters = new ArrayList<int[]>();
        for (int i = 0; i < monster_positions.length; i++) {
            monsters.add(new int[]{(int) Math.floorDiv(monster_positions[i],10),(int) monster_positions[i]%10});
        }

        System.out.print(" ");  // Offset first line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println();

        for (int i = 0; i < gameboard.N; i++) {
            // System.out.print("|");  // Print board edge before each row.
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + ANSI_RESET+"  ");
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                String h_container="  ";
                String m_container="  ";
                for (int[] value:heroes) {
                    if (value[0]==i && value[1]==j){
                        h_container="H"+(heroes.indexOf(value)+1);
                    }
                }
                for (int[] value:monsters) {
                    if (value[0]==i && value[1]==j){
                        m_container="M"+(monsters.indexOf(value)+1);
                    }
                }

                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ "|" + " "+"\033[1;97m"+h_container+gameboard.game_board[i][j].get_color()+" " + "|" + " "+"\033[1;97m"+m_container+gameboard.game_board[i][j].get_color()+" " + "|" + ANSI_RESET+"  ");
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            for (int j = 0; j < gameboard.N; j++) {
                if (gameboard.game_board[i][j].get_type().equals("Inaccessible")){
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+gameboard.game_board[i][j].get_symbol()+ANSI_RESET+"  ");
                }
                else {
                    System.out.print("  " +gameboard.game_board[i][j].get_color()+ gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() + "----" + gameboard.game_board[i][j].get_symbol() +ANSI_RESET+ "  ");
                }
            }
            // System.out.print("|");  // Print board edge after each row.
            System.out.println();
            System.out.println();

        }

        System.out.print(" ");  // Offset last line of board by single space.
        for (int i = 0; i < gameboard.N; i++) {
            System.out.print("+------------");
        }
        System.out.print("+");
        System.out.println("\n\n");



    }


    public static void print_list_of_heroes(ArrayList<? extends Hero> heroes){
        int i=1;
        System.out.println(GREEN_BOLD_BRIGHT+"\t\t\t\t Hero Name \t\t\tStrength \tExp \tAgility \tDexterity"+ANSI_RESET);
        System.out.println(GREEN_BOLD_BRIGHT+"\t\t\t\t ========= \t\t\t======== \t===== \t======= \t======="+ANSI_RESET);
        for (Hero hero: heroes) {
            System.out.println(WHITE_BRIGHT+"Choose <" +i+ "> for   " + padRight(hero.name, 21)
                    + "\t" + hero.strength
                    + "\t\t" + hero.exp
                    + "\t\t" + hero.agility
                    + "\t\t\t" + hero.dexterity+ ANSI_RESET);
            i++;
        }
    }



    public static void quit(){
	System.out.println(
"   _____                 _ _                \n" +
"  / ____|               | | |               \n" +
" | |  __  ___   ___   __| | |__  _   _  ___ \n" +
" | | |_ |/ _ \\ / _ \\ / _` | '_ \\| | | |/ _ \\\n" +
" | |__| | (_) | (_) | (_| | |_) | |_| |  __/\n" +
"  \\_____|\\___/ \\___/ \\__,_|_.__/ \\__, |\\___|\n" +
"                                  __/ |     \n" +
"                                 |___/      "
			   );
	System.exit(0);
    }

    public static void print_options(Hero hero, int i){
        // allowed options is a bit map [move_left, move_right, move_up, move_down, attack, teleport, quit]
        System.out.println(WHITE_BOLD_BRIGHT+"What do you want to do "+GREEN_BOLD_BRIGHT+hero.get_name()+" (H"+(i+1)+")"+WHITE_BOLD_BRIGHT+" ?"+ANSI_RESET);
        System.out.println(WHITE_BOLD_BRIGHT+"(You can't take any option in "+RED_BRIGHT+"RED"+WHITE_BOLD_BRIGHT+" but, you may take any "+GREEN_BRIGHT+"GREEN"+WHITE_BOLD_BRIGHT+" options)"+ANSI_RESET);
        if(hero.allowed_options[0]==1){
            System.out.print(GREEN_BRIGHT+"1. Move Left "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"1. Move Left"+ANSI_RESET);
        }

        if(hero.allowed_options[1]==1){
            System.out.print(GREEN_BRIGHT+"\t"+"2. Move Right "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"\t"+"2. Move Right"+ANSI_RESET);
        }

        if(hero.allowed_options[2]==1){
            System.out.print(GREEN_BRIGHT+"\t"+"3. Move Up "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"\t"+"3. Move Up"+ANSI_RESET);
        }

        if(hero.allowed_options[3]==1){
            System.out.print(GREEN_BRIGHT+"\t\t"+"4. Move Down "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"\t\t"+"4. Move Down"+ANSI_RESET);
        }

        if(hero.allowed_options[4]==1){
            System.out.print(GREEN_BRIGHT+"\t\t"+"5. Attack "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"\t\t"+"5. Attack"+ANSI_RESET);
        }

        if(hero.allowed_options[5]==1){
            System.out.println(GREEN_BRIGHT+"\t\t"+"6. Teleport "+ANSI_RESET);
        }
        else {
            System.out.println(RED_BRIGHT+"\t\t"+"6. Teleport"+ANSI_RESET);
        }

        if(hero.allowed_options[6]==1){
            System.out.print(GREEN_BRIGHT+"7. Quit "+ANSI_RESET);
        }
        else {
            System.out.print(RED_BRIGHT+"7. Quit"+ANSI_RESET);
        }
        System.out.print(GREEN_BRIGHT+"\t\t"+"8. Info" + ANSI_RESET);
        if (hero.allowed_options[7]==0){
            System.out.print(RED_BRIGHT +"\t\t\t"+ "9. Use Potion" + ANSI_RESET);
        }
        else {
            System.out.print(GREEN_BRIGHT+"\t\t\t"+ "9. Use Potion" + ANSI_RESET);
        }
        if (hero.allowed_options[8]==0){
            System.out.print(RED_BRIGHT+"\t"+"10. Go to Market" + ANSI_RESET);
        }
        else {
            System.out.print(GREEN_BRIGHT+"\t"+"10. Go to Market" + ANSI_RESET);
        }
        if (hero.allowed_options[9]==0){
            System.out.print(RED_BRIGHT+"\t"+"11. Change Equipment"+ANSI_RESET);
        }
        else {
            System.out.print(GREEN_BRIGHT+"\t"+"11. Change Equipment"+ANSI_RESET);
        }
        System.out.println(" ");

    }

    public static void print_attack_instruction(Hero h, ArrayList<Monster> monsters, MonsterTeam monster_team) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        System.out.println("Please choose the monster you want to attack: ");
        for (int i = 0; i < monsters.size(); i++){
            System.out.println(i+1 + ". " + monsters.get(i).get_name());
        }
        int pick;
        while(true){
            try {
                pick = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("The input is not a number. Please re-enter:");
                continue;
            }
            if (pick > 0 && pick <= monsters.size()){
                System.out.println("Please choose the way you want to attack:");
                System.out.println("1. Attack with your weapon:");
                System.out.println("2. Attack with a spell:");
                int option;
                while(true){
                    try {
                        option = Integer.parseInt(input.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("The input is not a number. Please re-enter:");
                        continue;
                    }
                    if (option == 1){
                        Monster m = monsters.get(pick-1);
                        System.out.println(h.get_name() + " deals " +
                                m.take_damage(h.get_damage()) +
                                " damage to " + m.get_name() + " with an auto attack!");
                        System.out.println(m.get_name() + " now has " + m.get_hp() + " hp!");
                        Music.play_weapon_attack_music();
                        if (!m.isAlive()){
                            monster_team.remove_monster(m);
                        }
                        return;
                    }
                    else if (option == 2){
                        Inventory h_gear = h.get_gears();
                        if (h_gear.get_spell_num() == 0){
                            System.out.println("You don't have any spells, please use weapon to attack:");
                            continue;
                        }
                        h_gear.print_spell();
                        System.out.println("Please choose the spell you want to use:");
                        int spell_num;
                        while(true) {
                            try {
                                spell_num = Integer.parseInt(input.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("The input is not a number. Please re-enter:");
                                continue;
                            }
                            if (spell_num > 0 && spell_num <= h_gear.get_spell_num()) {
                                Monster m = monsters.get(pick - 1);
                                System.out.println(h.get_name() + " deals " +
                                        m.take_damage(h_gear.get_spell(spell_num-1).get_damage(h.get_dexterity())) +
                                        " damage to " + m.get_name() + " with a spell!");
                                System.out.println(m.get_name() + " now has " + m.get_hp() + " hp!");
                                h_gear.get_spell(spell_num-1).play_spell_music();
                                if (!m.isAlive()){
                                    monster_team.remove_monster(m);
                                }
                                return;
                            }
                        }
                    }
                    System.out.println("The input number is invalid. Please re-enter:");
                }
            }
            System.out.println("The input number is invalid. Please re-enter:");
        }

    }

    public static void print_winner(int winner){
        if (winner==1){
            System.out.println("##     ## ######## ########   #######  ########  ######     ##      ## #### ##    ## #### \n" +
                    "##     ## ##       ##     ## ##     ## ##       ##    ##    ##  ##  ##  ##  ###   ## #### \n" +
                    "##     ## ##       ##     ## ##     ## ##       ##          ##  ##  ##  ##  ####  ## #### \n" +
                    "######### ######   ########  ##     ## ######    ######     ##  ##  ##  ##  ## ## ##  ##  \n" +
                    "##     ## ##       ##   ##   ##     ## ##             ##    ##  ##  ##  ##  ##  ####      \n" +
                    "##     ## ##       ##    ##  ##     ## ##       ##    ##    ##  ##  ##  ##  ##   ### #### \n" +
                    "##     ## ######## ##     ##  #######  ########  ######      ###  ###  #### ##    ## #### \n" +
                    "\n");
            try {
                Music.play_hero_win_music();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

        }
        else if (winner ==0){
            System.out.println("##     ##  #######  ##    ##  ######  ######## ######## ########   ######     ##      ## #### ##    ## #### \n" +
                    "###   ### ##     ## ###   ## ##    ##    ##    ##       ##     ## ##    ##    ##  ##  ##  ##  ###   ## #### \n" +
                    "#### #### ##     ## ####  ## ##          ##    ##       ##     ## ##          ##  ##  ##  ##  ####  ## #### \n" +
                    "## ### ## ##     ## ## ## ##  ######     ##    ######   ########   ######     ##  ##  ##  ##  ## ## ##  ##  \n" +
                    "##     ## ##     ## ##  ####       ##    ##    ##       ##   ##         ##    ##  ##  ##  ##  ##  ####      \n" +
                    "##     ## ##     ## ##   ### ##    ##    ##    ##       ##    ##  ##    ##    ##  ##  ##  ##  ##   ### #### \n" +
                    "##     ##  #######  ##    ##  ######     ##    ######## ##     ##  ######      ###  ###  #### ##    ## #### ");
            try {
                Music.play_monsters_win_music();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public static void print_hero_info(Hero hero){
        System.out.println(" ");
        System.out.println(YELLOW_BOLD_BRIGHT+"\t\t\t\t\t Hero Information Table:"+ANSI_RESET);
        System.out.println(" ");
        System.out.println(WHITE_BOLD_BRIGHT+"Name \t\t\t\t Health \t Strength \t Agility \t Dexterity"+ANSI_RESET);
        System.out.println(WHITE_BOLD_BRIGHT+"================= \t ====== \t ======== \t ======= \t ========="+ANSI_RESET);

        System.out.println(BLUE_BRIGHT+padRight(hero.name, 24) + hero.hp
                + "\t\t " + hero.strength
                + "\t\t " + hero.agility
                + "\t\t " + hero.dexterity+ANSI_RESET);
        System.out.println("  ");


    }

    public static void print_market_welcome(){
        System.out.println(" ");
        System.out.println(CYAN_BRIGHT+
                " __          __  _                            _          _   _            __  __            _        _   \n" +
                " \\ \\        / / | |                          | |        | | | |          |  \\/  |          | |      | |  \n" +
                "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |_| |__   ___  | \\  / | __ _ _ __| | _____| |_ \n" +
                "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | __| '_ \\ / _ \\ | |\\/| |/ _` | '__| |/ / _ \\ __|\n" +
                "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |_| | | |  __/ | |  | | (_| | |  |   <  __/ |_ \n" +
                "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/   \\__|_| |_|\\___| |_|  |_|\\__,_|_|  |_|\\_\\___|\\__|\n" +
                "                                                                                                         \n" +ANSI_RESET);
        System.out.println(" ");
    }
}
