package comp1110.ass2;

import java.util.Random;

public class Dices {
//    Jalal's implementation
private static final String[] COLORS = {"R", "B", "P", "G", "Y", "W"};
    private String[] rolledDice;
    private Random random;

    // Constructor generates 5 random dice colors
    public Dices() {
        rolledDice = new String[5];
        random = new Random();
        rollDice();
    }

    // Method to randomly roll 5 dice and store them in rolledDice
    private void rollDice() {
        for (int i = 0; i < 5; i++) {
            rolledDice[i] = COLORS[random.nextInt(COLORS.length)];
        }
    }

    public void applyPresetDiceD2CP1() {
        rolledDice[0] = "B";
        rolledDice[1] = "B";
        rolledDice[2] = "B";
        rolledDice[3] = "R";
        rolledDice[4] = "W";
    }

    // Method to return all rolled dice
    public String[] getAllDice() {
        return rolledDice;
    }

    public static void main(String[] args) {
        Dices D1 = new Dices();
        String[] diceRoll = D1.getAllDice();

        // Print random dice roll
        System.out.println("Player 1 Random Dice Roll:");
        for (String die : diceRoll) {
            System.out.print(die + " ");
        }

        // Apply preset dice roll
        D1.applyPresetDiceD2CP1();
        diceRoll = D1.getAllDice();

        // Print preset dice roll
        System.out.println("\nPlayer 1 Preset Dice Roll:");
        for (String die : diceRoll) {
            System.out.print(die + " ");
        }
    }
}
