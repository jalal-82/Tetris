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

    public void applyPresetDiceD2CP1(String a, String b ,String c ,String d ,String e) {
        rolledDice[0] = a;
        rolledDice[1] = b;
        rolledDice[2] = c;
        rolledDice[3] = d;
        rolledDice[4] = e;
    }
    // Method to return all rolled dice
    public String[] getAllDice() {
        return rolledDice;
    }

    public static void main(String[] args) {
        Dices D1 = new Dices();
        String[] diceRoll = D1.getAllDice();
        System.out.println("Player 1 Dice Roll:");
        for (String die : diceRoll) {
            System.out.print(die + " ");
        }
    }
}
