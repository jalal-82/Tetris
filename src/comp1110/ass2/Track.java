package comp1110.ass2;

/**
 * abstract class that defines all the variables in methods for a given track
 * intended to be implemented by subclasses representing each colour
 */
public abstract class Track {
    private int ability = 0;
    private int bonus = 0;
    private int track = 0;
    private Score score;
    public Track(Score score) {
        this.score = score;
    }

    /**
     * checks ability is available and then decrements it
     * specifics will need to be added.
     */
    public void useAbility() {
        if (this.ability > 0)
            this.ability--;
        else
            System.out.println("no ability available");
    }
    public void useBonus() {
        if (this.bonus > 0)
            this.bonus--;
        else
            System.out.println("no bonus available");
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
