package comp1110.ass2;
import java.util.Random;

public class Tile {

    private static final String[] COLORS = {"Red", "Blue", "Purple", "Green", "Yellow", "White"};
    private final String color;
    private final int[][] tileMatrix;
    private static final Random RANDOM = new Random();

    // Constructor
    public Tile() {
        this.color = getRandomColor();
        this.tileMatrix = generateTile();
    }

    // Method to generate a unique tile matrix
    public int[][] generateTile() {
        return new int[0][0];
    }

    // Method to get a random color from the available options
    private String getRandomColor() {
        int index = RANDOM.nextInt(COLORS.length);
        return COLORS[index];
    }

    // Method to validate the placement of a tile in the game state
    public boolean isValid(int[][] tileShape, int[][] gameState) {
        return false;
    }

    // Method to check if a tile is unique
    private boolean isUniqueTile(int hash) {
        return false;
    }


}


