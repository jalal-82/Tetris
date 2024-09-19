package comp1110.ass2;

public class GameState {

    private final Dices dice;
    private char[][] gameBoard;
    private Player player;
    private Score score;
    private Abilities abilities;
    private Bonus bonus;
    private Tile tiles;

    /**
     * Constructor for the GameState class.
     *
     * @param player     The player object representing the current player.
     * @param dice       The dice object used in the game.
     * @param tiles      The tiles available in the game.
     * @param score      The score object to track player's score.
     * @param abilities  The abilities object representing player's abilities.
     * @param bonus      The bonus object representing any bonus in the game.
     */
    public GameState(Player player, Dices dice, Tile tiles, Score score, Abilities abilities, Bonus bonus) {
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
     * Initializes the game board by filling it with '.' characters.
     * This represents an empty board with no tiles placed.
     */
    private void initializeBoard() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '.';
            }
        }
    }

    /**
     * Checks if a tile placement is valid based on its position, overlap, and support.
     *
     * @param board     The current game board.
     * @param tileName  The name of the tile to place.
     * @param col       The column where the tile is to be placed.
     * @param row       The row where the tile is to be placed.
     * @return          True if the tile placement is valid, false otherwise.
     */
    public boolean isTilePlacementValid(char[][] board, String tileName, int col, int row) {
        char[][] tile = tiles.getAllTiles().get(tileName).get(0);
        int tileRows = tile.length;
        int tileCols = tile[0].length;

        // Convert the row index to match the new orientation (bottom to top)
        int adjustedRow = board.length - (row + tileRows);

        // Check if the tile fits within the board boundaries (row and col)
        if (adjustedRow < 0 || col + tileCols > board[0].length) {
            return false;
        }

        // Check if the tile overlaps with any existing tiles
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ' && board[adjustedRow + i][col + j] != '.') {
                    return false;
                }
            }
        }

        // Check if the tile "rests on" an existing tile or the base
        boolean hasSupport = false;
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ') {  // Only check non-empty characters
                    int belowRow = adjustedRow + i + 1;  // Check the row below the current tile
                    if (belowRow >= board.length) {
                        // Tile is on the base (bottom row), so it's valid
                        hasSupport = true;
                    } else if (board[belowRow][col + j] != '.') {
                        // Tile is resting on an already placed tile
                        hasSupport = true;
                    }
                }
            }
        }

        return hasSupport;  // Tile placement is valid only if it has support
    }

    /**
     * Places a tile on the game board at the specified row and column.
     *
     * @param tileName  The name of the tile to place.
     * @param row       The row where the tile will be placed.
     * @param col       The column where the tile will be placed.
     */
    public void placeTile(String tileName, int row, int col) {
        if (!isTilePlacementValid(gameBoard, tileName, row, col)) {
            System.out.println("Tile placement is invalid");
            return;
        }

        char[][] tile = tiles.getAllTiles().get(tileName).get(0);
        int tileRows = tile.length;
        int tileCols = tile[0].length;

        // Convert the row index to match the new orientation (bottom to top)
        int adjustedRow = gameBoard.length - (row + tileRows);

        // Place the tile on the board
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ') {  // Only place non-empty characters
                    gameBoard[adjustedRow + i][col + j] = tile[i][j];
                }
            }
        }
    }

    /**
     * Places a tile on the game board with a specified rotation and applied windows.
     *
     * @param tileName  The name of the tile to place.
     * @param col       The column where the tile will be placed.
     * @param row       The row where the tile will be placed.
     * @param rotation  The degree of rotation to apply to the tile (0-3 for 0, 90, 180, 270 degrees).
     * @param windows   Boolean array indicating which windows to apply to the tile.
     */
    public void placeTileWithRotationWindows(String tileName, int col, int row, int rotation, boolean[] windows){
        tiles.rotateTile(tileName,rotation);
        tiles.applyWindows(tileName,windows);
        placeTile(tileName,row,col);
    }

    /**
     * Returns the current state of the game board.
     *
     * @return  A 2D character array representing the current game board.
     */
    public char[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Prints the current game board to the console.
     *
     * @param board  A 2D character array representing the game board to print.
     */
    public void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] == '\u0000' ? '.' : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Prints a tile to the console.
     *
     * @param tile  A 2D character array representing the tile to print.
     */
    public static void printTile(char[][] tile) {
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[0].length; j++) {
                System.out.print(tile[i][j] == '\u0000' ? '.' : tile[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Player P1 = new Player();
        Dices D1 = new Dices();
        D1.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        Tile T1 = new Tile(D1);
        Score S1 = new Score();
        Abilities A1 = new Abilities();
        Bonus B1 = new Bonus("Red", 2);
        GameState G1 = new GameState(P1, D1, T1, S1, A1, B1);

        boolean[] wind = {true,false,true};
        G1.placeTileWithRotationWindows("B3",0,0,1,wind);
        boolean[] wind2 = {true, false, true, true};
        G1.placeTileWithRotationWindows("G4L",3,0,0,wind2);
    }
}
