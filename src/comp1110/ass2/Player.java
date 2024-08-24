package comp1110.ass2;

/**
 * Represents a player in the game.
 */
public class Player {
    private String name;
    private Score score;
    private Abilities abilities;
    private Tracking tracking;

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.score = new Score();
        this.abilities = new Abilities();
        this.tracking = new Tracking();
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
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

    /**
     * Gets the player's tracking.
     *
     * @return The player's tracking.
     */
    public Tracking getTracking() {
        return tracking;
    }
}
