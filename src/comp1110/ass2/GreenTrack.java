package comp1110.ass2;

public class GreenTrack extends Track {

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
                nextBonus = 4;
                nextAbility = 3;
                break;
            case 2:
                nextBonus = 3;
                nextAbility = 2;
                break;
            case 3:
                nextBonus = 2;
                nextAbility = 1;
                break;
            case 4:
                addAbility();
                nextBonus = 1;
                nextAbility = 3;
                break;
            case 5:
                addBonus();
                nextBonus = 0;
                nextAbility = 2;
                break;
            case 6:
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                break;
            case 8:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    /**
     * Constructs a new GreenTrack with the specified score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public GreenTrack(Score score) {
        super(score);
        nextAbility = 4;
        nextBonus = 1;
    }
}
