package comp1110.ass2;

import java.util.List;
import java.util.Objects;

enum TrackType {
    RED, BLUE, PURPLE, GREEN, YELLOW
}

/**
 * Represents a track in the game with its type, abilities, bonuses, and position.
 * Tracks are updated as players progress, allowing for bonus points and abilities.
 */
public class Track {
    private final TrackType trackType;
    private int ability = 0; // Current ability points on the track
    private int bonus = 0; // Current bonus points on the track
    private int trackPosition = 0; // The current position on the track
    private int nextAbility;
    private int nextBonus;
    private final Score score; // The player's score associated with the track

    /**
     * Constructor for Track. Initializes the track type and score.
     * Calls initializeTrack() to set up abilities and bonuses for the track.
     *
     * @param trackType The type of the track (RED, BLUE, etc.)
     * @param score     The score associated with this track
     */
    public Track(TrackType trackType, Score score) {
        this.trackType = trackType;
        this.score = score;
        initializeTrack();
    }

    // Private Methods

    /**
     * Initializes the track's starting abilities and bonuses based on its type.
     */
    private void initializeTrack() {
        switch (trackType) {
            case RED -> {
                nextAbility = 2;
                nextBonus = 1;
                this.addAbility();
                this.addAbility();
            }
            case BLUE -> {
                nextAbility = 2;
                nextBonus = 1;
            }
            case PURPLE, YELLOW -> {
                nextAbility = 3;
                nextBonus = 1;
            }
            case GREEN -> {
                nextAbility = 4;
                nextBonus = 1;
            }
            default -> {
                System.out.println(trackType);
                throw new IllegalArgumentException("Unknown track type");
            }
        }
    }

    /**
     * Updates the bonus when a tile is placed. The bonus is reduced based on tile size and
     * the number of dice of matching colors or wildcards.
     *
     * @param selectedDice The dice selected for placing the tile
     * @param tileName     The name of the tile being placed
     */
    private void doUpdateBonus(List<String> selectedDice, String tileName) {
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        int diceOfTileColour = 0;
        for (String s : selectedDice) {
            if (tileName.startsWith(s) || Objects.equals(s, "W"))
                diceOfTileColour++;
        }
        this.bonus = this.bonus - (tileSize - diceOfTileColour);
    }

    /**
     * Updates the track based on its type and current position.
     * Calls the corresponding track update method based on the track type.
     */
    private void update() {
        switch (trackType) {
            case BLUE -> updateBlueTrack();
            case RED -> updateRedTrack();
            case PURPLE -> updatePurpleTrack();
            case GREEN -> updateGreenTrack();
            case YELLOW -> updateYellowTrack();
        }
    }

    /**
     * Updates the BLUE track based on the current track position.
     * Handles change in bonuses, abilities, and score.
     */
    private void updateBlueTrack() {
        switch (trackPosition) {
            case 1 -> {
                addBonus();
                nextAbility = 1;
                nextBonus = 3;
            }
            case 2 -> {
                addAbility();
                nextAbility = 2;
                nextBonus = 3;
            }
            case 3 -> {
                nextAbility = 1;
                nextBonus = 2;
            }
            case 4 -> {
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
            }
            case 5 -> {
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
            }
            case 6 -> {
                addAbility();
                nextAbility = 0;
            }
            case 9 -> updateScore();
        }
    }

    /**
     * Updates the RED track based on the current track position.
     * Handles change in bonuses, abilities, and score.
     */
    private void updateRedTrack() {
        switch (trackPosition) {
            case 1 -> {
                addBonus();
                nextBonus = 2;
                nextAbility = 1;
            }
            case 2 -> {
                addAbility();
                nextBonus = 1;
                nextAbility = 2;
            }
            case 3 -> {
                addBonus();
                nextBonus = 0;
                nextAbility = 1;
            }
            case 4, 5 -> {
                addAbility();
                nextAbility = 1;
            }
            case 6 -> {
                addAbility();
                nextAbility = 0;
            }
            case 9 -> updateScore();
        }
    }

    /**
     * Updates the PURPLE track based on the current track position.
     * Handles change in bonuses, abilities, and score.
     */
    private void updatePurpleTrack() {
        switch (trackPosition) {
            case 1 -> {
                addBonus();
                nextAbility = 2;
                nextBonus = 3;
            }
            case 2 -> {
                nextAbility = 1;
                nextBonus = 2;
            }
            case 3 -> {
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
            }
            case 4 -> {
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
            }
            case 5 -> {
                addAbility();
                nextAbility = 2;
            }
            case 6 -> nextAbility = 1;
            case 7 -> {
                addAbility();
                nextAbility = 0;
            }
            case 9 -> updateScore();
        }
    }

    /**
     * Updates the GREEN track based on the current track position.
     * Handles change in bonuses, abilities, and score.
     */
    private void updateGreenTrack() {
        switch (trackPosition) {
            case 1 -> {
                addBonus();
                nextBonus = 4;
                nextAbility = 3;
            }
            case 2 -> {
                nextBonus = 3;
                nextAbility = 2;
            }
            case 3 -> {
                nextBonus = 2;
                nextAbility = 1;
            }
            case 4 -> {
                addAbility();
                nextBonus = 1;
                nextAbility = 3;
            }
            case 5 -> {
                addBonus();
                nextBonus = 0;
                nextAbility = 2;
            }
            case 6 -> nextAbility = 1;
            case 7 -> addAbility();
            case 8 -> {
                addAbility();
                nextAbility = 0;
            }
            case 9 -> updateScore();
        }
    }

    /**
     * Updates the YELLOW track based on the current track position.
     * Handles change in bonuses, abilities, and score.
     */
    private void updateYellowTrack(){
        switch (trackPosition){
            case 1:
                addBonus();
                nextAbility = 2;
                nextBonus = 3;
                break;
            case 2:
                nextAbility = 1;
                nextBonus = 2;
                break;
            case 3:
                addAbility();
                nextAbility = 3;
                nextBonus = 1;
                break;
            case 4:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 5:
                nextAbility = 1;
                break;
            case 6:
                addAbility();
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                nextAbility = 0;
                break;
            case 8:
                break;
            case 9:
                updateScore();
                break;
        }
    }

    // Public Methods

    /**
     * Public method to calculate and update the bonus based on the tile placed.
     * Calls the private updateBonus method.
     *
     * @param selectedDice The dice selected for placing the tile
     * @param tileName     The name of the tile being placed
     */
    public void updateBonus(List<String> selectedDice, String tileName) {
        doUpdateBonus(selectedDice, tileName);
    }

    /**
     * Gets the current position on the track.
     * @return The current track position.
     */
    public int getTrack() {
        return this.trackPosition;
    }

    /**
     * Gets the current ability points on the track.
     * @return The current ability points.
     */
    public int getAbility() {
        return ability;
    }

    /**
     * Decreases the ability points by one (if ability points are greater than zero).
     */
    public void updateAbility() {
        if (ability > 0)
            ability--;
    }

    /**
     * Gets the current bonus points on the track.
     * @return The current bonus points.
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Increases the track position by one and updates the track state.
     */
    public void addTrack() {
        this.trackPosition++;
        update();
    }

    /**
     * Sets the bonus points on the track to the specified value.
     * @param bonusCount The bonus points to set.
     */
    public void setBonus(int bonusCount) {
        this.bonus = bonusCount;
    }

    /**
     * Increases the ability points by one.
     */
    public void addAbility() {
        this.ability++;
    }

    /**
     * Increases the bonus points by one.
     */
    public void addBonus() {
        this.bonus++;
    }

    /**
     * Updates the score by adding two points.
     */
    public void updateScore() {
        score.addTwoToScore(); // Add two points to the score
    }

    /**
     * Gets the next ability value that will be unlocked on the track.
     * @return The next ability value.
     */
    public int getNextAbility() {
        return nextAbility;
    }

    /**
     * Gets the next bonus value that will be unlocked on the track.
     * @return The next bonus value.
     */
    public int getNextBonus() {
        return nextBonus;
    }

}
