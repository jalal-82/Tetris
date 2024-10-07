package comp1110.ass2;

public class RedTrack extends Track {

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
                nextBonus = 2;
                nextAbility = 1;
                break;
            case 2:
                addAbility();
                nextBonus = 1;
                nextAbility = 2;
                break;
            case 3:
                addBonus();
                nextBonus = 0;
                nextAbility = 1;
                break;
            case 4, 5:
                addAbility();
                nextAbility = 1;
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
     * Constructs a new RedTrack with the specified score.
     * Sets the bonus for the track to 2.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public RedTrack(Score score) {
        super(score);
        this.setBonus(2);
        nextAbility = 2;
        nextBonus = 1;
    }
}
