package comp1110.ass2;

/**
 * Represents a player in the game.
 */
public class Player {
    private Score score;

    /**
     * Constructs a new Player
     *
     */
    public Player() {
        this.score = new Score();
    }


    /**
     * Gets the player's score.
     *
     * @return The player's score.
     */
    public Score getScore() {
        return score;
    }

    /**
     * Gets the player's abilities.
     *
     * @return The player's abilities.
     */

}
