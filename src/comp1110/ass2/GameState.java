package comp1110.ass2;


/**
 * Represents the state of the game for a specific player, including their board, score, and abilities.
 */
public class GameState {

    private final String[] dice;
    private char[][] gameBoard;
    private Player player ;
    private Score score;
    private Abilities abilities;
    private Bonus bonus;

    /**
     * Constructor for GameState.
     * Initializes an empty board with default values
     */
    public GameState(Player player, String[] dice, Score score, Abilities abilities, Bonus bonus) {
        this.player = player;
        this.dice = dice;
        this.score = score;
        this.abilities = abilities;
        this.bonus = bonus;
        gameBoard = new char[9][5];
        initializeBoard();
    }

    /**
     * Initializes the board with default values (e.g., 0) to represent an empty board.
     */
    private void initializeBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '.';
            }
        }
    }

    public static boolean placeTile(char[][] board, char[][] tile, int row, int col) {
        int tileRows = tile.length;
        int tileCols = tile[0].length;

        // Check if the tile fits within the gameboard bounds
        if (row + tileRows > board.length || col + tileCols > board[0].length) {
            return false; // Tile doesn't fit on the board
        }

        // Check for conflicts with existing tiles
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ' && board[row + i][col + j] != '.') {
                    return false; // Conflict detected
                }
            }
        }

        // Place the tile on the board
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ') {
                    board[row + i][col + j] = tile[i][j];
                }
            }
        }
        return true; // Placement successful
    }


    /*
    updates abilites based on previous round
     */
    public void abilitiesUpdate(){

    }

    /*
    updates bonus based on previous round
    */
    public void bonusUpdate(){

    }

    /*
    updates score based on end of round
    */
    public void scoreUpdate(){

    }



}
