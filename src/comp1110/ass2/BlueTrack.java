package comp1110.ass2;

public class BlueTrack extends Track {
    public BlueTrack(Score score) {
        super(score);
    }

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
}
