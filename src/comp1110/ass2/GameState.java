package comp1110.ass2;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameState {

    private final Dices dice;
    private char[][] gameBoard;
    private Score score;
    private Tile tiles;
    protected Track redTrack;
    protected Track blueTrack;
    protected Track greenTrack;
    protected Track yellowTrack;
    protected Track purpleTrack;

    /**
     * Constructor for the GameState class.
     *
     * @param dice       The dice object used in the game.
     * @param tiles      The tiles available in the game.
     * @param score      The score object to track player's score.
     */
    public GameState(Dices dice, Tile tiles, Score score) {
        this.dice = dice;
        this.score = score;
        this.tiles = tiles;
        this.redTrack = new RedTrack(score);
        this.blueTrack = new BlueTrack(score);
        this.greenTrack = new GreenTrack(score);
        this.yellowTrack = new YellowTrack(score);
        this.purpleTrack = new PurpleTrack(score);
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
     * Checks if a tile placement of the selected tile is valid based on its position, overlap, and support.
     * @param board     The current game board.
     * @param col       The column where the tile is to be placed.
     * @param row       The row where the tile is to be placed.
     * @return          True if the tile placement is valid, false otherwise.
     */
    public boolean isTilePlacementValid(char[][] board, int row, int col) {
        char[][] tile = tiles.getSelectedTile();

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
     * Updates the selectedTile on the GUI
     * @param tile
     */
    public void updateSelectedTile(String tile) {
        this.tiles.updateSelectedTile(tile);
    }

    /**
     * checks if the tile selection is valid given the selected dice
     * @param selectedDice
     * @param tileName
     * @return
     */

    public boolean isValidTileSelection(List<String> selectedDice, String tileName) {
        if (tileName == null)
            return false;
        Track track = null;
        switch (tileName.charAt(0)) {
            case 'R':
                track = redTrack;
            case 'B':
                track = blueTrack;
            case 'P':
                track = purpleTrack;
            case 'G':
                track = greenTrack;
            case 'Y':
                track = yellowTrack;
        }
        int diceOfTileColour = track.getBonus();
        for (int i = 0; i < selectedDice.size(); i++) {
            if (tileName.startsWith(selectedDice.get(i)) || Objects.equals(selectedDice.get(i), "W"))
                diceOfTileColour++;

        }
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        if (diceOfTileColour >= tileSize)
            return true;
        else
            return false;
        //check if valid, return
        //if not valid, check for bonus, use bonus

    }
    /**
     * to be used when the tile is placed
     * calls updateBonus from the track class on the desired track
     */
    public void updateBonus(List<String> selectedDice, String tileName) {
        Track track = null;
        switch (tileName.charAt(0)) {
            case 'R':
                track = redTrack;
                break;
            case 'B':
                track = blueTrack;
                break;
            case 'P':
                track = purpleTrack;
                break;
            case 'G':
                track = greenTrack;
                break;
            case 'Y':
                track = yellowTrack;
                break;
        }

        track.updateBonus(selectedDice, tileName);
    }


    /**
     * updates the selectedDice variable in Dice
     * @param selectedDice should be the selectedDice in the gui
     */
    public void updateSelectedDice(List<Integer> selectedDice){
        dice.setSelectedDice(selectedDice);
    }

    /**
     *
     * @return selectedDice from Dice class
     */
    public List<String> getSelectedDice() {
        return dice.getSelectedDice();
    }


    /**
     * Places the selected tile on the game board at the specified row and column.
     * @param row       The row where the tile will be placed.
     * @param col       The column where the tile will be placed.
     */
    public void placeTile(int row, int col) {
        if (!isTilePlacementValid(gameBoard, row, col)) {
            System.out.println(tiles.getSelectedTileKey() + " placement is invalid");
            return;
        }
        //checks if the selectedTile is of size 4 or larger and adds it to usedTiles.
//        if (tiles.getSelectedTileKey().contains("4") || tiles.getSelectedTileKey().contains("5") )
//            tiles.addToUsedTiles(tiles.getSelectedTileKey());
        //sets the available colors to be used by the other players
        dice.setAvailableColors(tiles.getSelectedTileKey());
        char[][] tile = tiles.getSelectedTile();
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
     * Places selected tile on the game board with a specified rotation and applied windows.
     *
     * @param col       The column where the tile will be placed.
     * @param row       The row where the tile will be placed.
     * @param rotation  The degree of rotation to apply to the tile (0-3 for 0, 90, 180, 270 degrees).
     * @param windows   Boolean array indicating which windows to apply to the tile.
     */
    public void placeTileWithRotationWindows(int row, int col, int rotation, boolean[] windows){
        tiles.applyWindows(windows);
        tiles.rotateTile(rotation);
        placeTile(row,col);
    }

    /**
     *
     * @return the dice remaining after the active player places their tile
     */
    public List<String> getAvailableDice() {
        return dice.getAvailableColors();
    }

    /**
     * Updates the score based on the current game board state.
     *
     * @param gameState The GameState object that holds the current state of the game.
     * @author Hunter
     */
    public void updateScore(GameState gameState, HashMap<String, List<Integer>> completedMap){
        score.addPoints(gameState.getGameBoard(),completedMap);
    }

    /**
     * Rerolls the dice in the current game state.
     *
     * @author Hunter
     */
    public void rerollDice(){
        dice.rollDice();
        dice.hardSetAvailableDice(dice.getAllDice());
    }

    public void setAvailableDice (String[] dice) {
        this.dice.hardSetAvailableDice(dice);
    }

    /**
     * Returns the current state of the game board.
     * @return  A 2D character array representing the current game board.
     */
    public char[][] getGameBoard() {
        return gameBoard;
    }

    /**
     * Retrieves the generated tiles based on the current dice state.
     *
     * @return An array of strings representing the generated tiles.
     * @author Hunter
     */
    public String[] getTiles() {
        return tiles.generateTiles(dice);
    }

    /**
     * Retrieves all the dice that were originally rolled for this turn.
     *
     * @return An array of strings representing all the dice.
     * @author Eileen
     */
    public String[] getDice() {
        return dice.getAllDice();
    }

    /**
     * calls addTrack on the track passed to it
     * @param track should be the selectedTrack
     */
    public void updateTrack(int track) {
        switch (track) {
            case 0:
                redTrack.addTrack();
                break;
            case 1:
                blueTrack.addTrack();
                break;
            case 2:
                purpleTrack.addTrack();
                break;
            case 3:
                greenTrack.addTrack();
                break;
            case 4:
                yellowTrack.addTrack();
                break;

        }
    }

    /**
     * checks if the selected track is in the available dice
     * @param trackNum should take selected track
     * @return true if the selected track is in the currently available die
     */
    public boolean isInAvailableDice(int trackNum) {
        if (getAvailableDice().contains("W"))
            return true;
        String colour = "";
        switch (trackNum){
            case 0:
                colour = "R";
                break;
            case 1:
                colour = "B";
                break;
            case 2:
                colour = "P";
                break;
            case 3:
                colour = "G";
                break;
            case 4:
                colour = "Y";
                break;
        }
        return getAvailableDice().contains(colour);
    }

    /**
     * uses the old presetDiceMethod to update rolledDice
     * @param dice to be applied
     */
    public void setRolledDice(List<String> dice) {

        this.dice.applyPresetDiceD2CP1(dice.get(0), dice.get(1), dice.get(2), dice.get(3), dice.get(4));
    }
    /**
     * Retrieves the current score from the score object.
     *
     * @return The current score as an integer.
     * @author Jalal
     */
    public int getScore() {return score.getScore();}

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



}
