package comp1110.ass2;

/**
 * Represents a player in the game.
 */
public class Player {
    private Score score;
    private Abilities abilities;

    /**
     * Constructs a new Player
     *
     */
    public Player() {
        this.score = new Score();
        this.abilities = new Abilities();
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
    public Abilities getAbilities() {
        return abilities;
    }

}
