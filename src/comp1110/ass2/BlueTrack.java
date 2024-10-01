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
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 2)
            addAbility();
        else if (getTrack() == 4)
            addBonus();
        else if (getTrack() == 5)
            addAbility();
        else if (getTrack() == 6)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
    }

    /**
     * Constructs a BlueTrack with the given score.
     *
     * @param score The Score object to be associated with this track.
     * @author Hunter
     */
    public BlueTrack(Score score) {
        super(score);
    }

}
