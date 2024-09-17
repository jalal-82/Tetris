package comp1110.ass2;


import java.util.List;
import java.util.Map;

/**
 * Represents the state of the game for a specific player, including their board, score, and abilities.
 */
public class GameState {

    private final Dices dice;
    private char[][] gameBoard;
    private Player player ;
    private Score score;
    private Abilities abilities;
    private Bonus bonus;
    private Tile tiles;

    /**
     * Constructor for GameState.
     * Initializes an empty board with default values
     */
    public GameState(Player player, Dices dice, Tile tiles,Score score, Abilities abilities, Bonus bonus) {
        this.player = player;
        this.dice = dice;
        this.score = score;
        this.abilities = abilities;
        this.bonus = bonus;
        this.tiles = tiles;
        gameBoard = new char[9][5];
        initializeBoard();
    }

    /**
     * Initializes the board with default values (.) to represent an empty board.
     */
    private void initializeBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '.';
            }
        }
    }

    public boolean placeTile(char[][] board, char[][] tile, int row, int col) {
        int tileRows = tile.length;
        int tileCols = tile[0].length;

        // Check if the tile fits within the board boundaries (row and col)
        if (row + tileRows > board.length || col + tileCols > board[0].length) {
            System.out.println("Tile doesn't fit on the board");
            return false;
        }

        // Place the tile on the board
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ') {  // Only place non-empty characters
                    board[row + i][col + j] = tile[i][j];
                }
            }
        }
        return true;
    }


    public void applyTilesD2C() {
        Map<String, List<char[][]>> allTiles = tiles.getAllTiles();

        // Place B3 tile
        if (allTiles.containsKey("B3") && !allTiles.get("B3").isEmpty()) {
            char[][] tile1ToPlace = allTiles.get("B3").get(0);
            char[][] tile1ToPlaceRotated = tiles.rotateTile(tile1ToPlace, 1);
            boolean tile1Placed = placeTile(gameBoard, tile1ToPlaceRotated, 7, 0);
            System.out.println("B3 tile placed: " + tile1Placed);
        } else {
            System.out.println("B3 tile not found or empty");
        }

        // Place G4L tile
        if (allTiles.containsKey("G4L") && !allTiles.get("G4L").isEmpty()) {
            char[][] tileToPlace = allTiles.get("G4L").get(0);
            boolean tilePlaced = placeTile(gameBoard, tileToPlace, 6, 3); // Adjusted for row and col
            System.out.println("G4L tile placed: " + tilePlaced);
        }

        System.out.println("Board after placing tiles:");
        printBoard(gameBoard);
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] == '\u0000' ? '.' : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printTile(char[][] tile) {
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[0].length; j++) {
                System.out.print(tile[i][j] == '\u0000' ? '.' : tile[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
