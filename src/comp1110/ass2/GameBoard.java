package comp1110.ass2;

import comp1110.ass2.gui.GameGUI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private char[][] gameBoard;
    private final GameState gameState;

    // Private methods
    /**
     * Core logic to initialize the game board.
     */
    private void doInitializeBoard() {
        gameBoard = new char[9][5];
        for (char[] chars : gameBoard) {
            Arrays.fill(chars, '.');
        }
    }

    /**
     * Core logic to check if a tile placement is valid.
     *
     * @param board The current game board.
     * @param row   The row where the tile is to be placed.
     * @param col   The column where the tile is to be placed.
     * @return True if the tile placement is valid, false otherwise.
     */
    private boolean doIsTilePlacementValid(char[][] board, int row, int col) {
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
     * Core logic to place the tile.
     *
     * @param row The row where the tile will be placed.
     * @param col The column where the tile will be placed.
     */
    private void doPlaceTile(int row, int col) {
        if (!doIsTilePlacementValid(gameBoard, row, col)) {
            System.out.println(gameState.getSelectedTileKey() + " placement is invalid");
            return;
        }

        // Remove the selected tile
        gameState.removeSelectedTile();
        System.out.println("removed Tiles are :" + gameState.getUsedTiles());

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
     * Core logic to update the CoA.
     *
     * @param gui           The GameGUI object.
     * @param currentPlayer The current player index.
     * @param completedMap  A map of completed rows and columns.
     */
    private void doUpdateCoA(GameGUI gui, int currentPlayer, HashMap<String, List<Integer>> completedMap) {
        if (!completedMap.isEmpty()) {
            List<Integer> rows = completedMap.get("completedRows");
            for (Integer row : rows) {
                gui.setRowCoA(currentPlayer, row, true);
            }
            List<Integer> cols = completedMap.get("completedCols");
            for (Integer col : cols) {
                gui.setColumnCoA(currentPlayer, col, true);
            }
        }
    }

    // Public methods
    /**
     * Constructor for GameBoard.
     *
     * @param gameState The current GameState object.
     */
    public GameBoard(GameState gameState) {
        this.gameState = gameState;
        doInitializeBoard();
    }

    /**
     * Checks if a tile placement is valid.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if placement is valid, else false.
     */
    public boolean isTilePlacementValid(int row, int col) {
        return doIsTilePlacementValid(getGameBoard(), row, col);
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
        doPlaceTile(row, col);
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
        for (char[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(chars[j] == '\u0000' ? '.' : chars[j]);
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
        for (char[] chars : tile) {
            for (int j = 0; j < tile[0].length; j++) {
                System.out.print(chars[j] == '\u0000' ? '.' : chars[j]);
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
    public void updateCoA(GameGUI gui, int currentPlayer, HashMap<String, List<Integer>> completedMap) {
        doUpdateCoA(gui, currentPlayer, completedMap);
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
        if (doIsTilePlacementValid(gameBoard, row, col)) {
            gameState.applyWindows(windows);
            doPlaceTile(row, col);
        }
    }

    /**
     * Retrieves all tiles from the GameState.
     *
     * @return A map of all tiles.
     */
    public Map<String, List<char[][]>> getTilesFromGameState() {
        return gameState.getAllTiles();
    }
}
