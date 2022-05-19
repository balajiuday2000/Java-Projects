/* Class representing the game board which includs different cells */

public class Gameboard {
    private static Gameboard singleton_instance;
    public int N;
    public Cell[][] game_board;

    private Gameboard(int N){
        this.N = 8;
        this.game_board = new Cell[N][N];
        this.fill_game_board();
    }

    public static Gameboard get_single_instance(){
        if (singleton_instance == null){
            singleton_instance = new Gameboard(8);
        }
        return singleton_instance;
    }

    public void fill_game_board(){
        int random_number;
        for (int i = 0; i < this.N; i++) {
            for (int j = 0; j < this.N; j++) {
                if (j == 2 || j == 5){
                    this.game_board[i][j] = new Inaccessible_Cell();
                }
                else if (i == 0){
                    this.game_board[i][j] = new Monster_Nexus_Cell();
                }
                else if (i == 7){
                    this.game_board[i][j] = new Hero_Nexus_Cell();
                }
                else {
                    random_number = (int) Math.ceil(Math.random()*4);
                    if (random_number == 1){
                        game_board[i][j] = new Plain_Cell();
                    }
                    if (random_number == 2){
                        game_board[i][j] = new Bush_Cell();
                    }
                    if (random_number == 3){
                        game_board[i][j] = new Koulou_Cell();
                    }
                    if (random_number == 4){
                        game_board[i][j] = new Cave_Cell();
                    }
                }
            }
        }
    }
}
