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
    private int[] bonuses = {0,0,0,0,0}; //is updated throughout the game, used to give generateTiles easy access to bonus counts


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
        this.tiles = new Tile();
    }

    // Private methods

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
        if (tileName.equals("I1X")||tileName.equals("I1O"))
            return true;
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
        bonuses[0] = redTrack.getBonus();
        bonuses[1] = blueTrack.getBonus();
        bonuses[2] = purpleTrack.getBonus();
        bonuses[3] = greenTrack.getBonus();
        bonuses[4] = yellowTrack.getBonus();
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


    // Public methods

    public void updateTrack(TrackType trackType) {
        switch (trackType) {
            case RED -> redTrack.addTrack();
            case BLUE -> blueTrack.addTrack();
            case GREEN -> greenTrack.addTrack();
            case PURPLE -> purpleTrack.addTrack();
            case YELLOW -> yellowTrack.addTrack();
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
     * Rolls the dice for the next turn.
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
     * Checks if the given track type is available in the current dice roll.
     * It returns true if the corresponding color for the track is available or if a wildcard ("W") dice is present.
     *
     * @param trackType The type of track to check (RED, BLUE, GREEN, YELLOW, PURPLE).
     * @return true if the corresponding color dice or a wildcard ("W") dice is available, false otherwise.
     */
    public boolean isInAvailableDice(TrackType trackType) {
        if (getAvailableDice().contains("W"))
            return true;
        String colour = switch (trackType) {
            case RED -> "R";
            case BLUE -> "B";
            case GREEN -> "G";
            case YELLOW -> "Y";
            case PURPLE -> "P";
        };
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
        String[] generatedTiles = tiles.generateTiles(dice, this.bonuses);
        //adds single tile if purple ability is active
        if (this.purpleTrack.getAbility() > 0) {
            String[] tilesPlusSingle = Arrays.copyOf(generatedTiles, generatedTiles.length + 1);
            tilesPlusSingle[tilesPlusSingle.length - 1] = "I1X";
            return tilesPlusSingle;
        }
        return generatedTiles;
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
     * Returns String list of all size 4 and 5 tiles
     * @return List<String></String>
     */
    public List<String> getSize4and5Tiles(){return tiles.getSize4And5Tiles();}

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
