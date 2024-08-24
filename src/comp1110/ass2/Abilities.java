package comp1110.ass2;


/**
 * Represents the abilities a player can unlock and check.
 */
public class Abilities {
    private boolean redAbility;
    private boolean blueAbility;
    private boolean purpleAbility;
    private boolean greenAbility;
    private boolean yellowAbility;

    /**
     * Unlocks an ability based on the given color.
     *
     * @param color The color representing the ability to unlock.
     */
    public void unlockAbility(String color) {
        switch (color.toLowerCase()) {
            case "red":
                redAbility = true;
                break;
            case "blue":
                blueAbility = true;
                break;
            case "purple":
                purpleAbility = true;
                break;
            case "green":
                greenAbility = true;
                break;
            case "yellow":
                yellowAbility = true;
                break;
            default:
                throw new IllegalArgumentException("Unknown color: " + color);
        }
    }

    /**
     * Checks if the specified ability is unlocked.
     *
     * @param color The color representing the ability to check.
     * @return True if the ability is unlocked, false otherwise.
     */
    public boolean hasAbility(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return redAbility;
            case "blue":
                return blueAbility;
            case "purple":
                return purpleAbility;
            case "green":
                return greenAbility;
            case "yellow":
                return yellowAbility;
            default:
                throw new IllegalArgumentException("Unknown color: " + color);
        }
    }
}
