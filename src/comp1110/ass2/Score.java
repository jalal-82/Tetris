package comp1110.ass2;

/**
 * Represents the score of a player in the game.
 */
public class Score {

    private int score;

    /**
     * Constructs a new Score with an initial score of 0.
     */
    public Score() {
        this.score = 0;
    }

    /**
     * Adds points to the player's score.
     *
     * @param points The number of points to add.
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }
}
