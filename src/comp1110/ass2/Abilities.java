package comp1110.ass2;


/**
 * Represents the abilities a player can unlock and check.
 */
public class Abilities {
    //variables to track available abilities (red starts with 2)
    private int redAbility = 2;
    private int blueAbility = 0;
    private int purpleAbility = 0;
    private int greenAbility = 0;
    private int yellowAbility = 0;
    //variables to track available bonuses
    private int redBonus = 0;
    private int blueBonus = 0;
    private int purpleBonus = 0;
    private int greenBonus = 0;
    private int yellowBonus = 0;
    //variables to count each colour track
    private int redTrack = 0;
    private int blueTrack = 0;
    private int purpleTrack = 0;
    private int greenTrack = 0;
    private int yellowTrack = 0;


    public void addTrack(String color) {
        switch (color.toLowerCase()) {
            case "red":
                redTrack++;
                updateRed();
                break;
            case "blue":
                blueTrack++;
                updateBlue();
                break;
            case "purple":
                purpleTrack++;
                updatePurple();
                break;
            case "green":
                greenTrack++;
                updateGreen();
                break;
            case "yellow":
                yellowTrack++;
                updateYellow();
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
                return redAbility > 0;
            case "blue":
                return blueAbility > 0;
            case "purple":
                return purpleAbility > 0;
            case "green":
                return greenAbility > 0;
            case "yellow":
                return yellowAbility > 0;
            default:
                throw new IllegalArgumentException("Unknown color: " + color);
        }
    }
    /**
     * Checks if the specified bonus is available.
     *
     * @param color The color representing the bonus to check.
     * @return True if the bonus is available, false otherwise.
     */
    public boolean hasBonus(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return redBonus > 0;
            case "blue":
                return blueBonus > 0;
            case "purple":
                return purpleBonus > 0;
            case "green":
                return greenBonus > 0;
            case "yellow":
                return yellowBonus > 0;
            default:
                throw new IllegalArgumentException("Unknown color: " + color);
        }
    }

    public void updateBlue() {
        if (blueTrack == 1)
            blueBonus++;
        else if (blueTrack == 2)
            blueAbility++;
        else if (blueTrack == 4)
            blueBonus++;
        else if (blueTrack == 5)
            blueAbility++;
        else if (blueTrack == 6)
            blueBonus++;
        // if == 9 update score
    }
    public void updateRed() {
        if (redTrack == 1)
            redBonus++;
        else if (redTrack == 2)
            redAbility++;
        else if (redTrack == 3)
            redBonus++;
        else if (redTrack == 4)
            redAbility++;
        else if (redTrack == 5)
            redAbility++;
        else if (redTrack == 6)
            redAbility++;
        // if == 9 update score
    }
    public void updatePurple() {
        if (purpleTrack == 1)
            purpleBonus++;
        else if (purpleTrack == 3)
            purpleAbility++;
        else if (purpleTrack == 4)
            purpleBonus++;
        else if (purpleTrack == 5)
            purpleAbility++;
        else if (purpleTrack == 7)
            purpleAbility++;
        // if == 9 update score
    }
    public void updateGreen() {
        if (greenTrack == 1)
            greenBonus++;
        else if (greenTrack == 4)
            greenAbility++;
        else if (greenTrack == 5)
            greenBonus++;
        else if (greenTrack == 7)
            greenAbility++;
        else if (greenTrack == 8)
            greenAbility++;
        // if == 9 update score
    }
    public void updateYellow() {
        if (yellowTrack == 1)
            yellowBonus++;
        else if (yellowTrack == 3)
            yellowAbility++;
        else if (yellowTrack == 4)
            yellowBonus++;
        else if (yellowTrack == 6)
            yellowAbility++;
        else if (yellowTrack == 7)
            yellowAbility++;
        // if == 9 update score
    }
}
