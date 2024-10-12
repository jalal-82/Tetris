package comp1110.ass2;

import java.util.*;

import comp1110.ass2.gui.*;

public class GameState {

    private Score score;
    private Dices dice;
    private Tile tiles;
    protected Track redTrack;
    protected Track blueTrack;
    protected Track greenTrack;
    protected Track yellowTrack;
    protected Track purpleTrack;

    // Private methods
    /**
     * Calls addTrack on the specified track.
     *
     * @param track The track number to update.
     */
    private void updateTrack(int track) {
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
     * Checks if the tile selection is valid given the selected dice.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile.
     * @return True if the tile selection is valid, else false.
     */
    private boolean isValidTileSelection(List<String> selectedDice, String tileName) {
        if (tileName == null)
            return false;
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
        int diceOfTileColour = track.getBonus();
        for (String diceColor : selectedDice) {
            if (tileName.startsWith(diceColor) || Objects.equals(diceColor, "W"))
                diceOfTileColour++;
        }
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        return diceOfTileColour >= tileSize;
    }

    /**
     * Updates bonus when the tile is placed.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile.
     */
    private void updateBonus(List<String> selectedDice, String tileName) {
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

    // Public interface
    /**
     * Constructor for the GameState class.
     *
     * @param score The score object to track player's score.
     */
    public GameState(Score score) {
        this.score = score;
        this.redTrack = new RedTrack(score);
        this.blueTrack = new BlueTrack(score);
        this.greenTrack = new GreenTrack(score);
        this.yellowTrack = new YellowTrack(score);
        this.purpleTrack = new PurpleTrack(score);
        this.dice = new Dices();
        this.tiles = new Tile(dice);
    }

    /**
     * Getter for isValidTileSelection.
     *
     * @param tileName  The name of the tile.
     * @param gameBoard The current game board.
     * @return True if valid else false.
     */
    public boolean getIsValidTileSelection(String tileName, GameBoard gameBoard) {
        return isValidTileSelection(getSelectedDice(), tileName);
    }

    /**
     * Getter for updateBonus.
     *
     * @param tileName  The name of the tile.
     * @param gameBoard The current game board.
     */
    public void getUpdateBonus(String tileName, GameBoard gameBoard) {
        updateBonus(getSelectedDice(), tileName);
    }

    /**
     * Updates the score based on the current game board state.
     *
     * @param gameBoard   The GameBoard object that holds the current state of the game.
     * @param completedMap A map of completed rows and columns.
     */
    public void updateScore(GameBoard gameBoard, HashMap<String, List<Integer>> completedMap) {
        score.addPoints(gameBoard.getGameBoard(), completedMap);
    }

    /**
     * Getter for updateTrack.
     *
     * @param track The track number to update.
     */
    public void getUpdateTrack(int track) {
        updateTrack(track);
    }

    /**
     * Retrieves the current score from the score object.
     *
     * @return The current score as an integer.
     */
    public int getScore() {
        return score.getScore();
    }

    /**
     * Updates the dice and tiles for the next turn.
     *
     * @param gui       The GameGUI object.
     * @param gameBoard The GameBoard object.
     */
    public void updateDiceAndTiles(GameGUI gui, GameBoard gameBoard) {
        rerollDice();
        updateSelectedDice(gui.getSelectedDice());
        gui.setAvailableTiles(List.of(getTiles()));
        System.out.println(List.of(getTiles()));
        gui.setAvailableDice(List.of(getDice()));
    }

    // Dice-related methods
    /**
     * Retrieves the available dice.
     *
     * @return A list of available dice colors.
     */
    public List<String> getAvailableDice() {
        return dice.getAvailableColors();
    }

    /**
     * Updates the selected dice.
     *
     * @param selectedDice The list of selected dice indices.
     */
    public void updateSelectedDice(List<Integer> selectedDice) {
        dice.setSelectedDice(selectedDice);
    }

    /**
     * Retrieves the selected dice.
     *
     * @return A list of selected dice colors.
     */
    public List<String> getSelectedDice() {
        return dice.getSelectedDice();
    }

    /**
     * Rerolls the dice for the next turn.
     */
    public void rerollDice() {
        dice.rollDice();
        dice.hardSetAvailableDice(dice.getAllDice());
    }

    /**
     * Sets the available dice.
     *
     * @param diceArray An array of dice colors.
     */
    public void setAvailableDice(String[] diceArray) {
        dice.hardSetAvailableDice(diceArray);
    }

    /**
     * Retrieves all the dice that were originally rolled for this turn.
     *
     * @return An array of strings representing all the dice.
     */
    public String[] getDice() {
        return dice.getAllDice();
    }

    /**
     * Checks if the selected track is in the available dice.
     *
     * @param trackNum The track number to check.
     * @return True if the selected track is in the currently available dice.
     */
    public boolean isInAvailableDice(int trackNum) {
        if (getAvailableDice().contains("W"))
            return true;
        String colour = "";
        switch (trackNum) {
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
     * Sets the rolled dice based on a preset list.
     *
     * @param diceList The list of dice to be applied.
     */
    public void setRolledDice(List<String> diceList) {
        this.dice.applyPresetDiceD2CP1(diceList.get(0), diceList.get(1), diceList.get(2), diceList.get(3), diceList.get(4));
    }

    /**
     * Retrieves the generated tiles based on the current dice state.
     *
     * @return An array of strings representing the generated tiles.
     */
    public String[] getTiles() {
        return tiles.generateTiles(dice);
    }

    /**
     * Updates the available colors based on the placed tile.
     *
     * @param tileKey The key of the placed tile.
     */
    public void updateAvailableColors(String tileKey) {
        dice.setAvailableColors(tileKey);
    }

    // Tile-related methods
    /**
     * Retrieves the Tile object.
     *
     * @return The Tile object.
     */
    public Tile getTilesObject() {
        return tiles;
    }

    /**
     * Updates the selected tile.
     *
     * @param tile The tile to select.
     */
    public void updateSelectedTile(String tile) {
        tiles.updateSelectedTile(tile);
    }

    /**
     * Applies windows to the selected tile.
     *
     * @param windows The windows to apply.
     */
    public void applyWindows(boolean[] windows) {
        tiles.applyWindows(windows);
    }

    /**
     * Rotates the selected tile.
     *
     * @param rotation The degree of rotation.
     */
    public void rotateTile(int rotation) {
        tiles.rotateTile(rotation);
    }

    /**
     * Retrieves all tiles.
     *
     * @return A map of all tiles.
     */
    public Map<String, List<char[][]>> getAllTiles() {
        return tiles.getAllTiles();
    }

    /**
     * Removes the selected tile.
     */
    public void removeSelectedTile() {
        tiles.removeTile(tiles.getSelectedTileKey());
    }

    /**
     * Retrieves the key of the selected tile.
     *
     * @return The key of the selected tile.
     */
    public String getSelectedTileKey() {
        return tiles.getSelectedTileKey();
    }

    /**
     * Retrieves the selected tile.
     *
     * @return A 2D char array representing the selected tile.
     */
    public char[][] getSelectedTile() {
        return tiles.getSelectedTile();
    }
}
