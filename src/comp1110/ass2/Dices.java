package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dices {

    private static final String[] COLORS = {"R", "B", "P", "G", "Y", "W"};
    private final String[] rolledDice;
    private final int[] colorCount = {0, 0, 0, 0, 0, 0};
    private final Random random;
    private List<String> availableDice;
    private final List<String> selectedDice = new ArrayList<>();

    /**
     * Rolls 5 random dice by selecting colors randomly from the COLORS array.
     * Stores the rolled colors into the rolledDice array.
     * Author: Jalal
     */
    void rollDice() {
        for (int i = 0; i < 5; i++) {
            rolledDice[i] = COLORS[random.nextInt(COLORS.length)];
        }
    }

    /**
     * Helper method to calculate available colors after dice are selected.
     * Logic separated for better clarity.
     * @param color the key color.
     * @param totalDiceUsed the total dice used.
     */
    private void calculateAvailableColors(String color, int totalDiceUsed) {
        int index = 0;
        List<String> availableColours = new ArrayList<>();

        // Find the index of the color used
        for (int i = 0; i < COLORS.length; i++) {
            if (String.valueOf(color).equals(COLORS[i])) {
                index = i;
            }
        }

        // Takes away the dice used, starting with the specified color and then moving to white
        for (int i = 0; i < totalDiceUsed; i++) {
            if (colorCount[index] > 0)
                colorCount[index]--;
            else
                colorCount[5]--;
        }

        for (int i = 0; i < colorCount.length; i++) {
            if (colorCount[i] > 0)
                availableColours.add(COLORS[i]);
        }

        this.availableDice = availableColours;
    }

    /**
     * Helper method to generate a list of available dice colors after selecting dice.
     * @param selectedDice List of selected dice.
     */
    private void calculateAvailableDiceForOthers(List<String> selectedDice) {
        List<String> availableColours = new ArrayList<>();

        // Adds all rolled dice to available colours before removing selected ones
        Collections.addAll(availableColours, rolledDice);
        availableColours.removeAll(selectedDice);

        this.availableDice = availableColours;
    }

//          Public Methods
//====================================

    /**
     * Constructor that generates 5 random dice colors upon object creation.
     * Initializes the rolledDice array and Random object, then rolls 5 dice.
     * Author: Eileen
     */
    public Dices() {
        rolledDice = new String[5];
        random = new Random();
        rollDice();
    }

    /**
     * Sets the color count of dice used in generateTiles.
     * @param count array of integers representing the total amount for each color relative to 'COLORS'
     * @param whiteCount the total number of white dice
     * @author Eileen
     */
    public void setColorCount(int[] count, int whiteCount) {
        System.arraycopy(count, 0, colorCount, 0, count.length);
        colorCount[5] = whiteCount;
    }

    /**
     * Hard sets the available dice list.
     * @param dice array of dice colors to be set as available.
     */
    public void hardSetAvailableDice(String[] dice) {
        availableDice = List.of(dice);
    }

    /**
     * Sets the available colors after dice are used.
     * If more dice are selected than required, it removes extra dice starting from colored ones.
     * @param key the key value of the tile being placed.
     */
    public void setAvailableColors(String key) {
        char color = key.charAt(0);
        int totalDiceUsed = key.charAt(1) - '0';

        if (totalDiceUsed < selectedDice.size()) {
            calculateAvailableColors(String.valueOf(color), totalDiceUsed);
        } else {
            calculateAvailableDiceForOthers(selectedDice);
        }
    }

    /**
     * Takes the selectedDice input from the GUI and determines which colors have been selected.
     * @param selectedDice List of selected dice indexes.
     */
    public void setSelectedDice(List<Integer> selectedDice) {
        this.selectedDice.clear();
        for (Integer s : selectedDice) {
            this.selectedDice.add(rolledDice[s]);
        }
    }

    /**
     * Applies preset dice values, setting the rolled dice to the specified colors.
     * @param a The color for the first dice.
     * @param b The color for the second dice.
     * @param c The color for the third dice.
     * @param d The color for the fourth dice.
     * @param e The color for the fifth dice.
     * Author: Hunter
     */
    public void applyPresetDiceD2CP1(String a, String b, String c, String d, String e) {
        rolledDice[0] = a;
        rolledDice[1] = b;
        rolledDice[2] = c;
        rolledDice[3] = d;
        rolledDice[4] = e;
    }

    /**
     * Returns the current values of the rolled dice as a String array.
     * @return A String array containing the values of the 5 rolled dice.
     */
    public String[] getAllDice() {
        return rolledDice;
    }

    /**
     * Gets the available colors for other players after dice selection.
     * @return A List of available colors.
     */
    public List<String> getAvailableColors() {
        return availableDice;
    }

    /**
     * Gets the count of each color.
     * @return An array containing the count of each color.
     */
    public int[] getColorCount() {
        return colorCount;
    }

    /**
     * Gets the list of selected dice.
     * @return A List of selected dice.
     */
    public List<String> getSelectedDice() {
        return selectedDice;
    }
}
