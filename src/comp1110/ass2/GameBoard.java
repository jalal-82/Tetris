package comp1110.ass2;

import comp1110.ass2.gui.GameGUI;
import comp1110.ass2.gui.Placement;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private char[][] gameBoard;
    private final GameState gameState;

    public static List<Integer> usedTiles = new ArrayList<Integer>();

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
     * This method keep record of used large tile
     * that can only be used once
     * @param valid whether the tile placement is valid
     * @return whether is placement valid
     * @author Eileen
     */
    private boolean largeTileCheck(boolean valid) {
        if (valid) {
            String tile = gameState.getSelectedTileKey();
            int pos = 0;
            if (tile.matches(".[4|5]+.*")){
                switch(tile.charAt(0)) {
                    case 'R': break;
                    case 'B':
                        pos = 3;
                        break;
                    case 'P':
                        pos = 6;
                        break;
                    case 'G':
                        pos = 9;
                        break;
                    case 'Y':
                        pos = 12;
                        break;
                }
                if (tile.charAt(1)=='5')
                    pos = pos+2;
                else {
                    try {
                        char end = tile.charAt(2);
                        if (end=='R')
                            pos = pos+1;
                    } catch (Exception e) {
                        if (usedTiles.contains(pos))
                            pos = pos+1;
                    }
                }
                usedTiles.add(pos);
            }
        }
        return valid;
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
//        gameState.removeSelectedTile();
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


    private void updateGUIState(int currentPlayer, GameBoard gameboard, GameGUI gui) {
        System.out.println("currPlayer in updateGUI "+currentPlayer);
        char[][] board = gameboard.getGameBoard();
        for (int y = 0; y < board.length; y++) { // @Eileen: y is gameboard row
            for (int x = 0; x < board[y].length; x++) { // @Eileen: x is gameboard column
                char c = board[y][x];
                boolean isWindow = false; // @Eileen: default is not a window
                if (c != '.') {
                    //to extract colour value from c, decrement if it is representing a window
                    if (c == 'S' || c == 'C' || c == 'Q' || c == 'H' || c == 'Z') { // @Eileen: This is identifying windows on tile
                        c--;
                        isWindow = true; // @Eileen: This is a window
                    }
                    String color = String.valueOf(c);
                    if (String.valueOf(c).equals("I"))
                        color = "Gray";
                    // adjust y-coordinate for GUI
                    int guiY = board.length - 1 - y;
                    gui.setFacadeSquare(currentPlayer, x, guiY, color, isWindow);
                }
            }
        }
    }

    private boolean allWindows(boolean[] windows) {
        for (boolean value : windows) {
            if (!value) {
                return false;
            }
        }
        return true;
    }
    private boolean doHandleWindowValidation(GameGUI gui, Placement p, GameState currentState) {
        if (allWindows(p.getWindows())) {
            if (currentState.getBlueTrack().getAbility() > 0) {
                currentState.getBlueTrack().updateAbility();
            } else {
                gui.setMessage("No blue ability available, choose a different window configuration");
                return false;
            }
        }
        return true;
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

    public boolean handleWindowValidation(GameGUI gui, Placement p, GameState currentState){
        return doHandleWindowValidation(gui,p,currentState);
    }

    /**
     * Checks if a tile placement is valid.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if placement is valid, else false.
     */
    public boolean isTilePlacementValid(int row, int col) {
        return largeTileCheck(doIsTilePlacementValid(getGameBoard(), row, col));
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

    public void getUpdateGUIState(int currentPlayer, GameBoard gameboard,GameGUI gui){
        updateGUIState(currentPlayer,gameboard,gui);
    }
}
