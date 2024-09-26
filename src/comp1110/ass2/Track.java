package comp1110.ass2;

public abstract class Track {
    private int ability = 0;
    private int bonus = 0;
    private int track = 0;
    private Score score;
    public Track(Score score) {
        this.score = score;
    }
    public void useAbility() {
        this.ability--;
    }
    public void useBonus() {
        this.bonus--;
    }
    public abstract void update();
    public int getTrack() {
        return this.track;
    }
    public int getAbility() {
        return ability;
    }
    public int getBonus() {
        return bonus;
    }
    public void addTrack() {
        this.track++;
        update();
    }
    public void setBonus(int bonusCount) {
        this.bonus = bonusCount;
    }
    public void addAbility() {
        this.ability++;
    }
    public void addBonus() {
        this.bonus++;
    }
    public void updateScore() {
        score.addTwoToScore();
    }
}
