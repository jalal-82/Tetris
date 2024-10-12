package comp1110.ass2;

import java.util.*;

import comp1110.ass2.gui.*;

public class GameState {

    private final Score score;
    private final Dices dice;
    private final Tile tiles;
    protected Track redTrack;
    protected Track blueTrack;
    protected Track greenTrack;
    protected Track yellowTrack;
    protected Track purpleTrack;

    // Constructor
    /**
     * Constructor for the GameState class.
     *
     * @param score The score object to track player's score.
     */
    public GameState(Score score) {
        this.score = score;
        this.redTrack = new Track(TrackType.RED, score);
        this.blueTrack = new Track(TrackType.BLUE, score);
        this.greenTrack = new Track(TrackType.GREEN, score);
        this.yellowTrack = new Track(TrackType.YELLOW, score);
        this.purpleTrack = new Track(TrackType.PURPLE, score);
        this.dice = new Dices();
        this.tiles = new Tile(dice);
    }

    // Private methods
    /**
     * Core logic to update the track.
     *
     * @param trackNum The track number to update.
     */
    private void doUpdateTrack(int trackNum) {
        Track track = getTrackByNumber(trackNum);
        if (track != null) {
            track.addTrack();
        }
    }

    /**
     * Core logic to check if the tile selection is valid given the selected dice.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile.
     * @return True if the tile selection is valid, else false.
     */
    private boolean doIsValidTileSelection(List<String> selectedDice, String tileName) {
        if (tileName == null)
            return false;
        Track track = getTrackByColor(tileName.charAt(0));
        int diceOfTileColour = track.getBonus();
        for (String diceColor : selectedDice) {
            if (tileName.startsWith(diceColor) || Objects.equals(diceColor, "W"))
                diceOfTileColour++;
        }
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        return diceOfTileColour >= tileSize;
    }

    /**
     * Core logic to update bonus when the tile is placed.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile.
     */
    private void doUpdateBonus(List<String> selectedDice, String tileName) {
        Track track = getTrackByColor(tileName.charAt(0));
        if (track != null) {
            track.updateBonus(selectedDice, tileName);
        }
    }

    // Helper methods to get track by color or number
    private Track getTrackByColor(char color) {
        return switch (color) {
            case 'R' -> redTrack;
            case 'B' -> blueTrack;
            case 'P' -> purpleTrack;
            case 'G' -> greenTrack;
            case 'Y' -> yellowTrack;
            default -> null;
        };
    }

    private Track getTrackByNumber(int trackNum) {
        return switch (trackNum) {
            case 0 -> redTrack;
            case 1 -> blueTrack;
            case 2 -> purpleTrack;
            case 3 -> greenTrack;
            case 4 -> yellowTrack;
            default -> null;
        };
    }

    // Public methods
    /**
     * Updates the track.
     *
//     * @param trackNum The track number to update.
     */
//    public void updateTrack(int trackNum) {
//        doUpdateTrack(trackNum);
//    }

    public void updateTrack(TrackType trackType) {
        switch (trackType) {
            case RED:
                redTrack.addTrack();
                break;
            case BLUE:
                blueTrack.addTrack();
                break;
            case GREEN:
                greenTrack.addTrack();
                break;
            case PURPLE:
                purpleTrack.addTrack();
                break;
            case YELLOW:
                yellowTrack.addTrack();
                break;
        }
    }

    /**
     * Checks if the tile selection is valid.
     *
     * @param tileName The name of the tile.
     * @return True if valid else false.
     */
    public boolean isValidTileSelection(String tileName) {
        return doIsValidTileSelection(getSelectedDice(), tileName);
    }

    /**
     * Updates bonus when the tile is placed.
     *
     * @param tileName The name of the tile.
     */
    public void updateBonus(String tileName) {
        doUpdateBonus(getSelectedDice(), tileName);
    }

    /**
     * Updates the score based on the current game board state.
     *
     * @param gameBoard    The GameBoard object that holds the current state of the game.
     * @param completedMap A map of completed rows and columns.
     */
    public void updateScore(GameBoard gameBoard, HashMap<String, List<Integer>> completedMap) {
        score.addPoints(gameBoard.getGameBoard(), completedMap);
    }

    /**
     *
     * @return true when a player has just unlocked a coa
     */
    public boolean isCOA() {
        return  score.isCoaIndicator();
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
     * @param gui The GameGUI object.
     */
    public void updateDiceAndTiles(GameGUI gui) {
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
//     * @param trackNum The track number to check.
     * @return True if the selected track is in the currently available dice.
     */
//    public boolean isInAvailableDice(int trackNum) {
//        if (getAvailableDice().contains("W"))
//            return true;
//        String colour = "";
//        switch (trackNum) {
//            case 0:
//                colour = "R";
//                break;
//            case 1:
//                colour = "B";
//                break;
//            case 2:
//                colour = "P";
//                break;
//            case 3:
//                colour = "G";
//                break;
//            case 4:
//                colour = "Y";
//                break;
//        }
//        return getAvailableDice().contains(colour);
//    }

    public boolean isInAvailableDice(TrackType trackType) {
        if (getAvailableDice().contains("W"))
            return true;
        String colour = "";
        switch (trackType) {
            case RED:
                colour = "R";
                break;
            case BLUE:
                colour = "B";
                break;
            case GREEN:
                colour = "G";
                break;
            case YELLOW:
                colour = "Y";
                break;
            case PURPLE:
                colour = "P";
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

    /**
     * Returns used Tiles in the game per player.
     *
     * @return HashMap of usedTiles.
     */
    public Map<String, List<char[][]>> getUsedTiles() {
        return tiles.getUsedTiles();
    }

    // Getters for tracks
    public Track getRedTrack() {
        return redTrack;
    }

    public Track getBlueTrack() {
        return blueTrack;
    }

    public Track getGreenTrack() {
        return greenTrack;
    }

    public Track getYellowTrack() {
        return yellowTrack;
    }

    public Track getPurpleTrack() {
        return purpleTrack;
    }
}
