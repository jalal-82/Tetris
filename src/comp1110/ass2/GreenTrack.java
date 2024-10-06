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
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 4)
            addAbility();
        else if (getTrack() == 5)
            addBonus();
        else if (getTrack() == 7)
            addAbility();
        else if (getTrack() == 8)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
    }

    /**
     * Constructs a new GreenTrack with the specified score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public GreenTrack(Score score) {
        super(score);
    }
}
