package comp1110.ass2;

public class YellowTrack extends Track {
    public YellowTrack(Score score) {
        super(score);
    }

    @Override
    public void update() {
        if (getTrack() == 1)
            addBonus();
        else if (getTrack() == 3)
            addAbility();
        else if (getTrack() == 4)
            addBonus();
        else if (getTrack() == 6)
            addAbility();
        else if (getTrack() == 7)
            addAbility();
        else if (getTrack() == 9)
            updateScore();
    }
}
