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
            case 4:
                addBonus();
                break;
            case 2:
            case 5:
            case 6:
                addAbility();
                break;
            case 9:
                updateScore();
                break;
            default:
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
    }

}
