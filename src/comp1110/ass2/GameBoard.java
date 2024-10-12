package comp1110.ass2;

import comp1110.ass2.gui.GameGUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private char[][] gameBoard;
    private GameState gameState;

    // Private methods
    /**
     * Initializes the game board by filling it with '.' characters.
     * This represents an empty board with no tiles placed.
     */
    private void initializeBoard() {
        gameBoard = new char[9][5];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '.';
            }
        }
    }

    /**
     * Checks if a tile placement of the selected tile is valid based on its position, overlap, and support.
     *
     * @param board The current game board.
     * @param col   The column where the tile is to be placed.
     * @param row   The row where the tile is to be placed.
     * @return True if the tile placement is valid, false otherwise.
     */
    private boolean isTilePlacementValid(char[][] board, int row, int col) {
        char[][] tile = gameState.getSelectedTile();

        if (tile == null) {
            System.out.println("Error: No tile is selected.");
            return false;
        }

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

        return hasSupport;
    }

    /**
     * Places the selected tile on the game board at the specified row and column.
     *
     * @param row The row where the tile will be placed.
     * @param col The column where the tile will be placed.
     */
    private void placeTile(int row, int col) {
        if (!isTilePlacementValid(gameBoard, row, col)) {
            System.out.println(gameState.getSelectedTileKey() + " placement is invalid");
            return;
        }

        // Remove the selected tile
        gameState.removeSelectedTile();

        // Update available colors
        gameState.updateAvailableColors(gameState.getSelectedTileKey());

        char[][] tile = gameState.getSelectedTile();
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
     * Updates the CoA based on completed rows and columns.
     *
     * @param gui           The GameGUI object.
     * @param currentPlayer The current player index.
     * @param completedMap  A map of completed rows and columns.
     */
    private void updateCoA(GameGUI gui, int currentPlayer, HashMap<String, List<Integer>> completedMap) {
        if (!completedMap.isEmpty()) {
            List<Integer> rows = completedMap.get("completedRows");
            for (int j = 0; j < rows.size(); j++) {
                gui.setRowCoA(currentPlayer, rows.get(j), true);
            }
            List<Integer> cols = completedMap.get("completedCols");
            for (int j = 0; j < cols.size(); j++) {
                gui.setColumnCoA(currentPlayer, cols.get(j), true);
            }
        }
    }

    // Public interface
    /**
     * Constructor for GameBoard.
     *
     * @param gameState The current GameState object.
     */
    public GameBoard(GameState gameState) {
        this.gameState = gameState;
        initializeBoard();
    }

    /**
     * Updates the selected tile on the GUI.
     *
     * @param tile The tile to select.
     */
    public void updateSelectedTile(String tile) {
        gameState.updateSelectedTile(tile);
    }

    /**
     * Places the selected tile on the game board with a specified rotation and applied windows.
     *
     * @param row      The row where the tile will be placed.
     * @param col      The column where the tile will be placed.
     * @param rotation The degree of rotation to apply to the tile (0-3 for 0, 90, 180, 270 degrees).
     * @param windows  Boolean array indicating which windows to apply to the tile.
     */
    public void placeTileWithRotationWindows(int row, int col, int rotation, boolean[] windows) {
        gameState.applyWindows(windows);
        gameState.rotateTile(rotation);
        placeTile(row, col);
    }

    /**
     * Getter for isTilePlacementValid Method.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if placement is valid, else false.
     */
    public boolean getIsTilePlacementValid(int row, int col) {
        return isTilePlacementValid(getGameBoard(), row, col);
    }

    /**
     * Returns the current state of the game board.
     *
     * @return A 2D character array representing the current game board.
     */
    public char[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Prints the current game board to the console.
     *
     * @param board A 2D character array representing the game board to print.
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
     * @param tile A 2D character array representing the tile to print.
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

    /**
     * Updates the CoA for the current player.
     *
     * @param gui           The GameGUI object.
     * @param currentPlayer The current player index.
     * @param completedMap  A map of completed rows and columns.
     */
    public void getUpdateCoA(GameGUI gui, int currentPlayer, HashMap<String, List<Integer>> completedMap) {
        updateCoA(gui, currentPlayer, completedMap);
    }

    /**
     * Places a single tile on the board.
     *
     * @param row      The row where the tile will be placed.
     * @param col      The column where the tile will be placed.
     * @param rotation The degree of rotation to apply to the tile.
     * @param windows  The windows to apply.
     */
    public void placeSingleTile(int row, int col, int rotation, boolean[] windows) {
        gameState.updateSelectedTile("I1X");
        if (isTilePlacementValid(gameBoard, row, col)) {
            gameState.applyWindows(windows);
            placeTile(row, col);
        }
    }

    /**
     * Retrieves all tiles from the GameState.
     *
     * @return A map of all tiles.
     */
    public Map<String, List<char[][]>> getTilesFromGS() {
        return gameState.getAllTiles();
    }
}
