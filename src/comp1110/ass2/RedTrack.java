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
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 2)
            addAbility();
        else if (getTrack() == 3)
            addBonus();
        else if (getTrack() == 4)
            addAbility();
        else if (getTrack() == 5)
            addAbility();
        else if (getTrack() == 6)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
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
    }
}
