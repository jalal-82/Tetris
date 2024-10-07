package comp1110.ass2;

public class BlueTrack extends Track {

    /**
     * Updates the state of the BlueTrack based on the current track value.
     *
     * If the track value matches specific conditions, it either adds a bonus,
     * adds an ability, or updates the score.
     *
     * @author Hunter
     */
    @Override
    public void update() {
        switch (getTrack()) {
            case 1:
                addBonus();
                nextAbility = 1;
                nextBonus = 3;
                break;
            case 2:
                addAbility();
                nextAbility = 2;
                nextBonus = 3;
                break;
            case 3:
                nextAbility = 1;
                nextBonus = 2;
                break;
            case 4:
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
                break;
            case 5:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 6:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    /**
     * Constructs a BlueTrack with the given score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public BlueTrack(Score score) {
        super(score);
        nextAbility = 2;
        nextBonus = 1;
    }

}
