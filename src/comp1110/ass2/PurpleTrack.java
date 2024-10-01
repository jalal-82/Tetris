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
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 3)
            addAbility();
        else if (getTrack() == 4)
            addBonus();
        else if (getTrack() == 5)
            addAbility();
        else if (getTrack() == 7)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
    }

    /**
     * Constructs a new PurpleTrack with the specified score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public PurpleTrack(Score score) {
        super(score);
    }
}
