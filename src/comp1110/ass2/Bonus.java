package comp1110.ass2;


/**
 * Represents a bonus that a player can use in the game.
 */
public class Bonus {
    private final String color;
    private int remainingUses;

    /**
     * Constructs a new Bonus with the specified color and number of uses.
     *
     * @param color The color of the bonus.
     * @param uses  The number of times the bonus can be used.
     */
    public Bonus(String color, int uses) {
        this.color = color;
        this.remainingUses = uses;
    }

    /**
     * Gets the color of the bonus.
     *
     * @return The color of the bonus.
     */
    public String getColor() {
        return color;
    }

    /**
     * Uses the bonus if there are remaining uses.
     *
     * @return True if the bonus was successfully used, false if no uses remain.
     */
    public boolean useBonus() {
        if (remainingUses > 0) {
            remainingUses--;
            return true;
        }
        return false;
    }
}
