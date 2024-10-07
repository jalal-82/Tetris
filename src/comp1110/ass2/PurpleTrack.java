package comp1110.ass2;

public class PurpleTrack extends Track {

    /**
     * Updates the state of the track based on its current status.
     * Adds bonuses or abilities, or updates the score depending on the track's value.
     *
     * @author Hunter
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
                nextAbility = 2;
                nextBonus = 1;
                break;
            case 4:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 5:
                addAbility();
                nextAbility = 2;
                break;
            case 6:
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    /**
     * Constructs a new PurpleTrack with the specified score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public PurpleTrack(Score score) {

        super(score);
        nextAbility = 3;
        nextBonus = 1;
    }
}
