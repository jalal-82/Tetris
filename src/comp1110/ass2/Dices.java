package comp1110.ass2;

import java.util.Random;

public class Dices {
private static final String[] COLORS = {"R", "B", "P", "G", "Y", "W"};
    private String[] rolledDice;
    private Random random;

    /**
     * Constructor that generates 5 random dice colors upon object creation.
     * Initializes the rolledDice array and Random object, then rolls 5 dice.
     * Author:
     */
    public Dices() {
        rolledDice = new String[5];
        random = new Random();
        rollDice();
    }

    /**
     * Rolls 5 random dice by selecting colors randomly from the COLORS array.
     * Stores the rolled colors into the rolledDice array.
     * Author:
     */
    private void rollDice() {
        for (int i = 0; i < 5; i++) {
            rolledDice[i] = COLORS[random.nextInt(COLORS.length)];
        }
    }
    /*
    * sets the dice values to the strings given as arguments
    * */
    /**
     * Applies preset dice values, setting the rolled dice to the specified colors.
     * @param a The color for the first dice.
     * @param b The color for the second dice.
     * @param c The color for the third dice.
     * @param d The color for the fourth dice.
     * @param e The color for the fifth dice.
     * Author:
     */
    public void applyPresetDiceD2CP1(String a, String b ,String c ,String d ,String e) {
        rolledDice[0] = a;
        rolledDice[1] = b;
        rolledDice[2] = c;
        rolledDice[3] = d;
        rolledDice[4] = e;
    }

    /**
     * Returns the current values of the rolled dice as a String array.
     * @return A String array containing the values of the 5 rolled dice.
     * Author:
     */
    public String[] getAllDice() {
        return rolledDice;
    }

}
