package comp1110.ass2;

public class RedTrack extends Track {

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
    public RedTrack(Score score) {
        super(score);
        this.setBonus(2);
    }
}
