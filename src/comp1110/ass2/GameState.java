package comp1110.ass2;

import java.util.*;

import comp1110.ass2.gui.*;

public class GameState {

    private Score score;
    protected Track redTrack;
    protected Track blueTrack;
    protected Track greenTrack;
    protected Track yellowTrack;
    protected Track purpleTrack;

    /**
     * Constructor for the GameState class.
     *
     * @param score      The score object to track player's score.
     */
    public GameState(Score score) {
        this.score = score;
        this.redTrack = new RedTrack(score);
        this.blueTrack = new BlueTrack(score);
        this.greenTrack = new GreenTrack(score);
        this.yellowTrack = new YellowTrack(score);
        this.purpleTrack = new PurpleTrack(score);
    }

    /**
     * checks if the tile selection is valid given the selected dice
     * @param selectedDice
     * @param tileName
     * @return
     */
    private boolean isValidTileSelection(List<String> selectedDice, String tileName) {
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
     * Getter for isValidTileSelection
     * @param tileName
     * @return True if valid else false
     */
    public boolean getIsValidTileSelection(String tileName, GameBoard gameBoard){
        boolean res = isValidTileSelection(gameBoard.getSelectedDice(),tileName);
        return res;
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
     * getter for updateBonus
     * @param tileName
     */
    public void getUpdateBonus(String tileName, GameBoard gameBoard){
        updateBonus(gameBoard.getSelectedDice(),tileName);
    }

    /**
     * Updates the score based on the current game board state.
     *
     * @param gameBoard The GameBoard object that holds the current state of the game.
     * @author Hunter
     */
    public void updateScore(GameBoard gameBoard, HashMap<String, List<Integer>> completedMap){
        score.addPoints(gameBoard.getGameBoard(),completedMap);
    }

    /**
     * calls addTrack on the track passed to it
     * @param track should be the selectedTrack
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
     * getter for updateTrack
     * @param track
     */
    public void getUpdateTrack(int track){
        updateTrack(track);
    }


    /**
     * Retrieves the current score from the score object.
     *
     * @return The current score as an integer.
     * @author Jalal
     */
    public int getScore() {return score.getScore();}



}
