package comp1110.ass2;

import java.util.Random;

public class Dice {

    private static final String[] COLORS = {"Red", "Blue", "Purple", "Green", "Yellow", "White"};
    private final String color;
    private static final Random RANDOM = new Random();

    public Dice() {
        this.color = getRandomColor();
    }

    /*
     * @return Random colour from available options
     */
    private String getRandomColor() {
        int index = RANDOM.nextInt(COLORS.length);
        return COLORS[index];
    }

    /*
     * @return String of Colour
     */
    public String getColor() {
        return color;
    }

}
