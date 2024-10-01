package comp1110.ass2;

/**
 * Represents the yellow track in the game.
 * This class extends the Track class and implements the update logic specific to the yellow track.
 */
public class YellowTrack extends Track {
    public YellowTrack(Score score) {
        super(score);
    }

    /**
     * Updates the track state based on the current track value.
     * - If the track value is 1, it adds a bonus.
     * - If the track value is 3, it adds an ability.
     * - If the track value is 4, it adds a bonus.
     * - If the track value is 6, it adds an ability.
     * - If the track value is 7, it adds an ability.
     * - If the track value is 9, it updates the score.
     *
     * @description: Implements the update logic for the yellow track based on the current track value.
     * @author: Your Name
     */
    @Override
    public void update() {
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 3)
            addAbility();
        else if (getTrack() == 4)
            addBonus();
        else if (getTrack() == 6)
            addAbility();
        else if (getTrack() == 7)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
    }
}
