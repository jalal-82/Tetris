package comp1110.ass2;


/**
 * Represents the state of the game for a specific player, including their board, score, and abilities.
 */
public class GameState {

    private final String[] dice;
    private int[][] board;
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
        board = new int[9][5];
        initializeBoard();
    }

    /**
     * Initializes the board with default values (e.g., 0) to represent an empty board.
     */
    private void initializeBoard() {
        // Loop through the matrix and set each value to 0 (or any default value).
    }

    /**
     *
     * @param tileShape
     * @param gameState
     * @return
     * @author
     */
    public void placeTile(int[][] tileShape, int[][] gameState){

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
