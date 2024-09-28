package comp1110.ass2;

public class GreenTrack extends Track {
    public GreenTrack(Score score) {
        super(score);
    }

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
}
