package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Dices {
private static final String[] COLORS = {"R", "B", "P", "G", "Y", "W"};
    private String[] rolledDice;
    private int[] colorCount = {0, 0, 0, 0, 0, 0};
    private Random random;
    private List<String> availableDice;

    private List<String> selectedDice = new ArrayList<>();

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
     * Author: Jalal
     */
    public void rollDice() {
        for (int i = 0; i < 5; i++) {
            rolledDice[i] = COLORS[random.nextInt(COLORS.length)];
        }
    }

    /**
     * sets the color count of dice
     * used in generateTiles as these values are already calculated there
     * @param count array of integers representing the total amount for each color relative to 'COLORS'
     * @param whiteCount the total number of white die
     * @author Eileen
     */
    public void setColorCount(int[] count, int whiteCount) {
        for (int i = 0; i < count.length; i++) {
            colorCount[i] = count[i];
        }
        colorCount[5] = whiteCount;
    }

    /**
     * intended for use after a tile has been placed. calculates the dice leftover for other players to use towards their ability track
     * if the player has selected the correct amount of dice, it will remove the selected dice.
     * if the player has selected more than necessary, it will remove the correct colours first and then white.
     * @param key key value of the tile being placed
     * sets availableColours
     */
    public void setAvailableColors(String key) {
        char color = key.charAt(0);
        int totalDiceUsed = key.charAt(1) - '0';
        List<String> availableColours = new ArrayList<>();
        if (totalDiceUsed < selectedDice.size()) {

            int index = 0;

            //find the index of the colour used
            for (int i = 0; i < COLORS.length; i++) {
                if (String.valueOf(color).equals(COLORS[i])) {
                    index = i;
                }
            }
            //takes away the dice used, starting with color and then moving to white
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
        } else {
            // adds all the rolled dice to available colours before removing those which were selected
            for (int i = 0; i < rolledDice.length; i++) {
                availableColours.add(rolledDice[i]);
            }
            for (String s : selectedDice) {
                availableColours.remove(s);
            }
            this.availableDice = availableColours;
        }
    }

    /**
     * takes the selectedDice input from the gui and determines which colours have been selected
     * @param selectedDice
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

    /**
     * Gets the available colors.
     *
     * @return An array of available colors.
     * @author Jalal
     */
    public List<String> getAvailableColors() {
        return availableDice;
    }

    /**
     * Gets the count of each color.
     *
     * @return An array containing the count of each color.
     * @author Eileen
     */
    public int[] getColorCount() {
        return colorCount;
    }

    /**
     * getter method for SelectedDice
     * @return
     */
    public List<String> getSelectedDice() { return selectedDice;
    }
}
