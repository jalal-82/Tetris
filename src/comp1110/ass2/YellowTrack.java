package comp1110.ass2;

/**
 * Represents the yellow track in the game.
 * This class extends the Track class and implements the update logic specific to the yellow track.
 */
public class YellowTrack extends Track {
    public YellowTrack(Score score) {
        super(score);
        nextBonus = 1;
        nextAbility = 3;
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
        switch (getTrack()) {
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
}
