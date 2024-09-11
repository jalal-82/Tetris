package comp1110.ass2;
import java.util.Collections;
import java.util.Random;

public class Tile {

    private static final String[] COLORS = {"Red", "Blue", "Purple", "Green", "Yellow", "White"};
    private final String color;
    private final int[][] tileMatrix;
    private static final Random RANDOM = new Random();
    private final String name;
    private final int size;


    // Constructor
    //updated to included name and size - Hunter
    //later we could add a method to extract all this info from the name
    public Tile(String name, String color, int size) {
        this.color = color;
        this.name = name;
        this.size = size;
        this.tileMatrix = generateTile();
    }

    // Method to generate a unique tile matrix
    public int[][] generateTile() {
        return new int[0][0];
    }

    // Method to get a random color from the available options
    // don't need this right? -Hunter
   /* private String getRandomColor() {
        int index = RANDOM.nextInt(COLORS.length);
        return COLORS[index];
    }*/

    //Hunter
    public boolean isValidSelection(Dices dice) {
        // I assume maybe later available die will be stored elsewhere in a variable, and we won't need the dice parameter
        int availableDice = 0;
        for(String x:dice.getAvailableDie()){
            if(x.equals(this.color) || x.equals("White"))
                availableDice++;
        }
        if (availableDice >= this.size)
            return true;
        else
            return false;
    }

    // Method to validate the placement of a tile in the game state
    public boolean isValid(int[][] tileShape, int[][] gameState) {
        return false;
    }

    // Method to check if a tile is unique
    private boolean isUniqueTile(int hash) {
        return false;
    }

    public static void main(String[] args) {
        Dices eg = new Dices("Red", "Red", "Red", "Blue", "White");
        Tile r3 = new Tile("R3", "Red", 4);
        System.out.println(r3.isValidSelection(eg));
    }
}




