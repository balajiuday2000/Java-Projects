/* Sub-class of RPG, a file that contains the main logic and element interaction of game Legends of Valor */

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class LOV extends RPG{

    private Gameboard map;
    private Market market;
    private HeroTeam heroes;
    private MonsterTeam monsters;
    private int round_counter;
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    public static final String ANSI_RESET = "\u001B[0m";


    private Scanner input = new Scanner(System.in);
    private Parser p = new Parser();
    private ArrayList<Integer> explored_positions; // Keeps track of explored regions for teleportation


    LOV() throws FileNotFoundException {
        super();
        market = Market.get_single_instance(); // Singleton pattern implemented
        heroes = new HeroTeam();
        monsters = new MonsterTeam();
        map = Gameboard.get_single_instance(); // Singleton pattern implemented
        round_counter = 0;
        explored_positions = new ArrayList<Integer>();
    }

    // Method to select characters
    @Override
    public void character_selection() throws FileNotFoundException {
        ArrayList<Warrior> warriors = p.parse_warrior();
        ArrayList<Sorcerer> sorcerers = p.parse_sorcerer();
        ArrayList<Paladin> paladins = p.parse_paladin();

        String [] order  = {"first", "second", "third"};
        String role_num;
        for (int i = 0; i < 3; i++){
            System.out.println(WHITE_BOLD_BRIGHT+"Please choose the role of your " + order[i] + " hero (by entering the number): "+ANSI_RESET);
            System.out.print(WHITE_BRIGHT+"1. Warrior"+ANSI_RESET);
            System.out.print("      ");
            System.out.print(WHITE_BRIGHT+"2. Sorcerer"+ANSI_RESET);
            System.out.print("      ");
            System.out.print(WHITE_BRIGHT+"3. Paladin"+ ANSI_RESET);
            System.out.print("      ");
            System.out.println(WHITE_BRIGHT+"q. Quit Game"+ ANSI_RESET);
            while (true){
                role_num = input.nextLine();
                if (Objects.equals(role_num, "1")){
                    generate_hero(warriors);
                    break;
                }
                else if (Objects.equals(role_num, "2")){
                    generate_hero(sorcerers);
                    break;
                }
                else if (Objects.equals(role_num, "3")){
                    generate_hero(paladins);
                    break;
                }
                else if (Objects.equals(role_num, "q")){
                    Printer.quit();
                }
                System.out.println(WHITE_BOLD_BRIGHT+"The input number is incorrect. Please re-enter:"+ANSI_RESET);
            }
        }
        System.out.println(WHITE_BOLD_BRIGHT+"You have formed your team! Now it's the time to start your adventure! \n"+ANSI_RESET);
        this.set_hero_positions();
    }

    // Method to set initial position of heroes
    private void set_hero_positions(){
        for (int i = 0; i < this.heroes.get_hero_team_size(); i++) {
            this.heroes.get_hero(i).change_position(70 + (3 * i));
            heroes.get_hero(i).set_ori_position(70 + (3 * i));
        }
    }

    // Method to set initial position of monsters
    private void set_monster_positions(){
        int counter = 0;
        for (int i = this.monsters.get_monster_team_size()-3; i < this.monsters.get_monster_team_size(); i++) {
            this.monsters.get_monster(i).position = (3*counter)+1;
            counter ++;
        }
    }

    // Method to generate the selected hero
    private void generate_hero(ArrayList<? extends Hero> heroes){
        int hero_num;
        Printer.print_list_of_heroes(heroes);
        System.out.println(WHITE_BOLD_BRIGHT+"\n Please choose one of the heroes above (by entering the number): "+ANSI_RESET);
        while(true){
            try {
                hero_num = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("The input is not a number. Please re-enter:");
                continue;
            }
            if (0 < hero_num && hero_num <= heroes.size()){
                this.heroes.add_heroes(heroes.get(hero_num - 1));
                System.out.println(WHITE_BRIGHT+"You have recruited "+WHITE_BOLD_BRIGHT+heroes.get(hero_num - 1).get_name()+WHITE_BRIGHT+" to your team! \n"+ANSI_RESET);
                heroes.remove(hero_num - 1);
                break;
            }
            System.out.println("The input number does not have a corresponding hero. Please re-enter:");
        }
    }

    //Method to generate the monster team
    private void generate_monsters() throws FileNotFoundException {
        this.monsters.add_monster(this.heroes.get_hero(0).level);
        this.monsters.add_monster(this.heroes.get_hero(1).level);
        this.monsters.add_monster(this.heroes.get_hero(2).level);
        this.set_monster_positions();
    }

    // Method where the game is executed
    @Override
    public void startGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Printer.PrintWelcomeMsg();
        //Music.play_welcome_music();
        this.character_selection();
        this.generate_monsters();
        Printer.print_LOV_gameboard(map,this.heroes.get_position(),this.monsters.get_position());
        boolean winner_found = false;
        while(!winner_found) {
            winner_found=this.round();
            round_counter += 1;
        }
        System.out.println(WHITE_BOLD_BRIGHT+"Do you want to play again? 1.Yes and 2.No"+ANSI_RESET);
        String player_choice = input.next();
        while (!player_choice.equals("1") &&!player_choice.equals("2") ){
            System.out.println("Not a valid input! Re-enter.");
            System.out.println(WHITE_BOLD_BRIGHT+"Do you want to play again? 1.Yes and 2.No"+ANSI_RESET);
            player_choice = input.next();
        }
        if (player_choice.equals("1")){
            this.startGame();
        }
        else {
            Printer.quit();
        }
    }

    // Method where the allowed options for each hero is calculated per round
    private void update_options(int hero_index) {
        // allowed options is a bit map [move_left, move_right, move_up, move_down, attack, teleport, quit, potion, market, change equip]
        Hero h = this.heroes.get_hero(hero_index);
        int[] allowed_options = h.get_allowed_options();
        Integer hero_position = h.position;
        int hero_row = (int) hero_position / 10;
        int hero_col = (int) hero_position % 10;

        List<Integer> hero_positions = new ArrayList<Integer>();
        List<Integer> monster_positions = new ArrayList<Integer>();
        for (int position : this.monsters.get_position()) {
            monster_positions.add(position);
        }

        for (int position : this.heroes.get_position()) {
            hero_positions.add(position);
        }

        if (hero_col == 0 || hero_col == 3 || hero_col == 6 || hero_positions.contains(hero_position - 1)) {
            //if hero in left column or another hero in left cell, set move_left to 0
            allowed_options[0] = 0;

        }
        if (hero_col == 1 || hero_col == 4 || hero_col == 7 || hero_positions.contains(hero_position + 1)) {
            //if hero in right column or another hero in right cell, set move_right to 0
            allowed_options[1] = 0;
        }
        if (hero_row == 7 || hero_positions.contains(hero_position + 10)) {
            //if hero in nexus or another hero in cell row below, set move_down to 0
            allowed_options[3] = 0;
        }
        if (monster_positions.contains(hero_position - 1) || monster_positions.contains(hero_position + 1) || monster_positions.contains(hero_position)) {
            //if monster in same level, set move up to 0
            allowed_options[2] = 0;
        }
        if (!monster_positions.contains(hero_position - 1) && !monster_positions.contains(hero_position + 1) && !monster_positions.contains(hero_position - 10) && !monster_positions.contains(hero_position + 10) && !monster_positions.contains(hero_position - 11) && !monster_positions.contains(hero_position - 9) && !monster_positions.contains(hero_position + 9) && !monster_positions.contains(hero_position + 11) && !monster_positions.contains(hero_position)) {
            //if monster not in vicinity, set attack to 0
            allowed_options[4] = 0;

            for (int i = 0; i < this.monsters.get_monster_team_size(); i++) {
                Monster m = monsters.get_monster(i);

                if (m.get_position() == hero_position - 1 || m.get_position() == hero_position + 1) {
                    allowed_options[2] = 0;
                }
                if (!h.detect_enemy(m)) {
                    allowed_options[4] = 0;
                }
            }
        }
        if (can_teleport(hero_index).size() == 0) {
            //if there are no allowed positions, set teleport to 0
            allowed_options[5] = 0;
        }

        if (h.gears.get_potion_num() == 0){
            //if Hero has no potions, set Potion to 0
            allowed_options[7] = 0;
        }
        if (hero_row != 7) {
            //if Hero not in Nexus, set to 0
            allowed_options[8] = 0;
        }
        if (h.gears.get_armor_num() == 0 && h.gears.get_weapon_num() == 0){
            //if Hero has no extra equipment, set to 0
            allowed_options[9] = 0;
        }
        h.set_allowed_options(allowed_options);
    }

    // Method to implement each round
    @Override
    public boolean round() throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        boolean hero_wins = this.hero_round();
        if (hero_wins){
            return true;
        }

        boolean monster_wins = this.monster_round();
        if (monster_wins){
            return true;
        }
        return false;
    }

    // Method to calculate all explored regions
    public ArrayList<Integer> can_teleport(int hero_index){
        List<Integer> hero_positions = new ArrayList<Integer>();
        List<Integer> monster_positions = new ArrayList<Integer>();
        for (int position:this.monsters.get_position()) {
            monster_positions.add(position);
        }

        for (int position:this.heroes.get_position()) {
            hero_positions.add(position);
        }
        int my_position = this.heroes.get_position()[hero_index];
        ArrayList<Integer> allowed_positions = new ArrayList<Integer>();
        for (Integer position:explored_positions) {
            if (my_position%10!=position%10 && my_position%10+1!=position%10 && my_position%10+1!=position%10 && !hero_positions.contains(position) && !monster_positions.contains(position) ){
                allowed_positions.add(position);
            }
        }
        return  allowed_positions;
    }

    // Method to implement teleportation
    public void teleport(int hero_index){
        ArrayList<Integer> allowed_positions = can_teleport(hero_index);
        Printer.print_LOV_gameboard_With_Positions(map,allowed_positions, heroes.get_position(), monsters.get_position(), heroes.get_position()[hero_index]);
        System.out.println(WHITE_BOLD_BRIGHT+"Where do you want to teleport? These are the positions you can teleport to. Enter a number."+ANSI_RESET);
        int new_position = input.nextInt();
        while (!allowed_positions.contains((Integer)new_position)){
            System.out.println("Not a valid input! Re-Enter position.");
            new_position=input.nextInt();
        }
        this.heroes.get_hero(hero_index).position=new_position;
        for (int i = 0; i < this.heroes.get_hero_team_size(); i++) {
          this.heroes.get_hero(i).allowed_options[5] = 0;
        }
    }

    // Method to implement the Heroes turns during each round
    public boolean hero_round() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        for (int i = 0; i < this.heroes.get_hero_team_size(); i++) {
            this.heroes.get_hero(i).allowed_options = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        }
        for (int i = 0; i < this.heroes.get_hero_team_size(); i++) {
            Hero hero = this.heroes.get_hero(i);
            this.update_options(i);
            if (hero.position == 9999){
                continue;
            }

            while(true) {
                //print options according to allowed options. Printer.print_options(int hero_index)
                Printer.print_options(this.heroes.get_hero(i), i);
                String hero_choice = input.next();
                if (hero_choice.equals("1") && hero.allowed_options[0] == 1){ //Hero moves left
                    //moving left
                    this.explored_positions.add(hero.position);
                    old_cell_effect(hero);
                    hero.position -= 1;
                    Music.play_moving_music(hero.name);
                    new_cell_effect(hero);
                    break;
                }
                else if (hero_choice.equals("2") && hero.allowed_options[1] == 1){ //Hero moves right
                    //moving right
                    this.explored_positions.add(hero.position);
                    old_cell_effect(hero);
                    hero.position += 1;
                    Music.play_moving_music(hero.name);
                    new_cell_effect(hero);
                    break;
                }
                else if (hero_choice.equals("3") && hero.allowed_options[2] == 1){ //Hero moves up
                    //moving up
                    this.explored_positions.add(hero.position);
                    if (hero.allowed_options[0] != 1){
                        this.explored_positions.add(hero.position+1);
                    }
                    if (hero.allowed_options[1] != 1){
                        this.explored_positions.add(hero.position-1);
                    }
                    old_cell_effect(hero);
                    hero.position-=10;
                    Music.play_moving_music(hero.name);
                    new_cell_effect(hero);
                    break;
                }
                else if (hero_choice.equals("4") && hero.allowed_options[3] == 1){ //Hero moves down
                    //moving down
                    this.explored_positions.remove((Integer) hero.position);
                    old_cell_effect(hero);
                    hero.position += 10;
                    Music.play_moving_music(hero.name);
                    new_cell_effect(hero);
                    break;
                }
                else if (hero_choice.equals("5") && hero.allowed_options[4] == 1){ //Hero decides to attack
                    ArrayList<Monster> attackable = new ArrayList<>();
                    for (int j = 0; j < this.monsters.get_monster_team_size(); j++){
                        if (hero.detect_enemy(monsters.get_monster(j))){
                            attackable.add(monsters.get_monster(j));
                        }
                    }
                    Printer.print_attack_instruction(hero, attackable, monsters);
                    break;
                }
                else if (hero_choice.equals("6") && hero.allowed_options[5] == 1){ //Hero decides to teleport
                    old_cell_effect(hero);
                    teleport(i);
                    Music.play_teleport_music(hero.name);
                    new_cell_effect(hero);
                    break;
                }

                else if (hero_choice.equals("7")){ //Hero decides to quit
                    Printer.quit();
                }
                else if (hero_choice.equals("8")){ //print Hero info
                    Printer.print_hero_info(hero);
                }
                else if (hero_choice.equals("9") && hero.allowed_options[7] == 1){ //Hero decides to use a Potion
                    hero.gears.print_potion();
                    System.out.println("Enter your choice:");
                    int choice = 0;
                    try {
                        choice = input.nextInt();
                    }
                    catch (NumberFormatException e){
                        System.out.println("Please enter a number.");
                    }
                    hero.drink_potion(hero.gears.get_potion(choice-1));
                }
                else if (hero_choice.equals("10") && hero.allowed_options[8] == 1){ //Hero decides to enter market
                    market.enter_market(hero);
                }
                else if (hero_choice.equals("11") && hero.allowed_options[9] == 1){ //Hero decides to check equipment
                    hero.check_equips();
                }
                else {
                    System.out.println("Not a Valid Input. Try Again!");
                }
            }
            Printer.print_LOV_gameboard(map, this.heroes.get_position(),this.monsters.get_position());
            if (check_winner(hero)){
                return true;
            }
        }
        for (int i = 0; i < this.heroes.get_hero_team_size(); i++) {
            if (heroes.get_hero(i).get_position() == 9999){
                // The fainted hero will be revived after one round.
                heroes.get_hero(i).reset();
            }
        }
        return false;
    }

    // Method to implement the Monsters' turn during each round
    public boolean monster_round() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // actions taken by each monster in a round.
        first_loop:
        for (int i = 0; i < this.monsters.get_monster_team_size(); i++) {
            Monster m = monsters.get_monster(i);
            Integer monster_position = m.get_position();
            int monster_row=(int)monster_position / 10;
            int monster_col = (int) monster_position % 10;
            Boolean movable = true;
            // The monster will attack if it encounters a hero.
            for (int j = 0; j < this.heroes.get_hero_team_size(); j++) {
                Hero h = heroes.get_hero(j);
                if (m.detect_enemy(h)){
                    System.out.println(m.get_name() +" (M"+(i+1)+")"+" deals " + h.take_damage(m.get_damage()) + " damage to " +h.get_name()+" (H"+(j+1)+")");
                    System.out.println(h.get_name()+" (H"+(j+1)+")" + " has " + h.get_hp() + " hp left!");
                    Music.play_monster_attack_music();
                    if (!h.isAlive()){
                        System.out.println(h.get_name()+" (H"+(j+1)+")" + " faints!");
                        // Remove the hero from map by changing its position to somewhere outside the map.
                        h.change_position(9999);
                    }
                    continue first_loop;
                }
            }
            // The monster will not take any action if there's another monster in front of it.
            for (int j = 0; j < this.monsters.get_monster_team_size(); j++){
                if (monsters.get_monster(j).get_position() == m.get_position()+10){
                    movable = false;
                }
            }

            if (movable == true){
                m.position += 10;
                Music.play_moving_music(m.name);
            }

            if (check_winner(m)){
                return true;
            }
        }
        if (round_counter == 8){
            generate_monsters();
        }
        System.out.println();
        Printer.print_LOV_gameboard(map, this.heroes.get_position(),this.monsters.get_position());
        return false;
    }

    // Method to check winner after each turn
    public <T> boolean check_winner(T character){
        if(character instanceof Hero){
            Hero hero = (Hero) character;
            List<Integer> monster_positions = new ArrayList<Integer>();
            for (int position:this.monsters.get_position()) {
                monster_positions.add(position);
            }
            int position= hero.position;
            if (position/10 == 0 && !monster_positions.contains(position-1) && !monster_positions.contains(position+1)){
                // hero is the winner
                Printer.print_winner(1);
                return true;
            }
        }

        if(character instanceof Monster){
            Monster monster = (Monster) character;
            List<Integer> hero_positions = new ArrayList<Integer>();
            for (int position:this.heroes.get_position()) {
                hero_positions.add(position);
            }
            int position=monster.position;
            if (position/10==7 && !hero_positions.contains(position -1) && !hero_positions.contains(position+1)){

                Printer.print_LOV_gameboard(map, this.heroes.get_position(),this.monsters.get_position());
                Printer.print_winner(0);
                return true;
            }
        }
        return false;
    }

    // Method to reflect effect of leaving a cell
    public void old_cell_effect(Hero hero){
        int row = hero.position/10;
        int col = hero.position%10;
        String cell_type= map.game_board[row][col].get_type();
        if (cell_type.equals("Bush")){
            hero.dexterity-=hero.increase_due_to_cell;
            System.out.println("\n Exiting Bush Cell. Dexterity boost deactivated. \n ");
        }
        else if (cell_type.equals("Cave")){
            hero.agility-=hero.increase_due_to_cell;
            System.out.println("\n Exiting Cave Cell. Agility boost deactivated. \n");
        }
        else if (cell_type.equals("Koulou")){
            hero.strength-=hero.increase_due_to_cell;
            System.out.println("\n Exiting Koulou Cell. Strength boost deactivated. \n");
        }
    }

    // Method to reflect effect of entering a cell
    public void new_cell_effect(Hero hero){
        int row = hero.position/10;
        int col = hero.position%10;
        String cell_type= map.game_board[row][col].get_type();
        if (cell_type.equals("Bush")){
            hero.increase_due_to_cell=(int)Math.ceil(0.1*hero.dexterity);
            hero.dexterity+=hero.increase_due_to_cell;
            System.out.println("\n Entering Bush Cell. Dexterity boost activated. \n");
        }
        else if (cell_type.equals("Cave")){
            hero.increase_due_to_cell=(int)Math.ceil(0.1*hero.agility);
            hero.agility+=hero.increase_due_to_cell;
            System.out.println("\n Entering Cave Cell. Agility boost activated. \n");
        }
        else if (cell_type.equals("Koulou")){
            hero.increase_due_to_cell=(int)Math.ceil(0.1*hero.strength);
            hero.strength+=hero.increase_due_to_cell;
            System.out.println("\n Entering Koulou Cell. Strength boost activated. \n");
        }
    }
}
