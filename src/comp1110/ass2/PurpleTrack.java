package comp1110.ass2;

public class PurpleTrack extends Track {
    public PurpleTrack(Score score) {
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
        else if (getTrack() == 5)
            addAbility();
        else if (getTrack() == 7)
            addAbility();
        //updatescore
    }
}
