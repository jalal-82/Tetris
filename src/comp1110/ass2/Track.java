package comp1110.ass2;

import java.util.List;
import java.util.Objects;

/**
 * Abstract class that defines all the variables and methods for a given track.
 * Intended to be implemented by subclasses representing each color.
 */
public abstract class Track {
    private int ability = 0;
    private int bonus = 0;
    private int track = 0;
    protected int nextAbility;
    protected int nextBonus;
    private Score score;

    public Track(Score score) {
        this.score = score;
    }

    /**
     * called when tile is placed
     * calculate the bonus points used to place that tile
     * @param selectedDice should be from the dice class
     * @param tileName the tile placed
     */
    public void updateBonus(List<String> selectedDice, String tileName) {
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        int diceOfTileColour = 0;
        for (String s : selectedDice) {
            if (tileName.startsWith(s) || Objects.equals(s, "W"))
                diceOfTileColour++;
        }
        this.bonus = this.bonus - (tileSize - diceOfTileColour);
    }

    /**
     * Abstract method to update the track state.
     * Specifics will need to be added in subclasses.
     *
     * @description: Updates the track state based on specific color logic.
     * @author: Your Name
     */
    public abstract void update();

    /**
     * Returns the current track value.
     *
     * @description: Gets the current track value.
     * @return int: The current track value.
     * @author: Your Name
     */
    public int getTrack() {
        return this.track;
    }

    /**
     * Returns the current ability count.
     *
     * @description: Gets the current ability count.
     * @return int: The current ability count.
     * @author: Your Name
     */
    public int getAbility() {
        return ability;
    }
    //this will reduce the ability
    public void updateAbility() {
        if (ability > 0)
         ability--;
    }

    /**
     * Returns the current bonus count.
     *
     * @description: Gets the current bonus count.
     * @return int: The current bonus count.
     * @author: Your Name
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Increments the track value and calls the update method.
     *
     * @description: Increases the track count and updates the track state.
     * @author: Your Name
     */
    public void addTrack() {
        this.track++;
        update();
    }

    /**
     * Sets the bonus count to a specified value.
     *
     * @description: Sets the bonus count to the provided value.
     * @param bonusCount - The value to set the bonus to.
     * @author: Your Name
     */
    public void setBonus(int bonusCount) {
        this.bonus = bonusCount;
    }

    /**
     * Increments the ability count by one.
     *
     * @description: Increases the ability count by one.
     * @author: Your Name
     */
    public void addAbility() {
        this.ability++;
    }

    /**
     * Increments the bonus count by one.
     *
     * @description: Increases the bonus count by one.
     * @author: Your Name
     */
    public void addBonus() {
        this.bonus++;
    }

    /**
     * Updates the score by adding two to the current score.
     *
     * @description: Adds two to the score.
     * @author: Your Name
     */
    public void updateScore() {
        score.addTwoToScore();
    }
}
