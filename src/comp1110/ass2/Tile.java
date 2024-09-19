package comp1110.ass2;
import java.util.*;

public class Tile {

    private Map<String, List<char[][]>> allTiles;
    private String[] tiles;  // Stores the generated tiles
    private Random random;
    private String[] dice;
    private String[] generatedTiles;

    /**
     * Constructor that initializes the Tile object. It generates all the possible tiles and
     * also generates tiles based on the provided dice roll.
     * @param dice The Dice object used to determine which tiles will be generated.
     */
    public Tile(Dices dice) {
        random = new Random();
        allTiles = new HashMap<>();
        this.dice = dice.getAllDice();
        {
            allTiles.put("R2", new ArrayList<>());
            allTiles.get("R2").add(new char[][] { {'R', ' '}, {'R', ' '} });

            allTiles.put("B2", new ArrayList<>());
            allTiles.get("B2").add(new char[][] { {'B', ' '}, {'B', ' '} });

            allTiles.put("P2", new ArrayList<>());
            allTiles.get("P2").add(new char[][] { {'P', ' '}, {'P', ' '} });

            allTiles.put("G2", new ArrayList<>());
            allTiles.get("G2").add(new char[][] { {'G', ' '}, {'G', ' '} });

            allTiles.put("Y2", new ArrayList<>());
            allTiles.get("Y2").add(new char[][] { {'Y', ' '}, {'Y', ' '} });

            allTiles.put("R3", new ArrayList<>());
            allTiles.get("R3").add(new char[][] { {' ', 'R'}, {'R', 'R'} });

            allTiles.put("B3", new ArrayList<>());
            allTiles.get("B3").add(new char[][] { {' ', 'B'}, {'B', 'B'} });

            allTiles.put("P3", new ArrayList<>());
            allTiles.get("P3").add(new char[][] { {'P'}, {'P'}, {'P'} });

            allTiles.put("G3", new ArrayList<>());
            allTiles.get("G3").add(new char[][] { {'G'}, {'G'}, {'G'} });

            allTiles.put("Y3", new ArrayList<>());
            allTiles.get("Y3").add(new char[][] { {' ', 'Y'}, {'Y', 'Y'} });

            allTiles.put("R4", new ArrayList<>());
            allTiles.get("R4").add(new char[][] { {'R', 'R'}, {'R', 'R'} });

            allTiles.put("B4L", new ArrayList<>());
            allTiles.get("B4L").add(new char[][] {{' ', 'B'}, {' ', 'B'}, {'B', 'B'} });

            allTiles.put("B4R", new ArrayList<>());
            allTiles.get("B4R").add(new char[][] {{'B', ' '}, {'B', ' '}, {'B', 'B'} });

            allTiles.put("P4", new ArrayList<>());
            allTiles.get("P4").add(new char[][] { {'P'}, {'P'}, {'P'}, {'P'} });

            allTiles.put("G4L", new ArrayList<>());
            allTiles.get("G4L").add(new char[][] { {' ', 'G'}, {'G', 'G'}, {' ', 'G'} });

            allTiles.put("G4R", new ArrayList<>());
            allTiles.get("G4R").add(new char[][] { {'G', ' '}, {'G', 'G'}, {'G', ' '} });

            allTiles.put("Y4L", new ArrayList<>());
            allTiles.get("Y4L").add(new char[][] { {' ', 'Y'}, {'Y', 'Y'}, {'Y', ' '} });

            allTiles.put("Y4R", new ArrayList<>());
            allTiles.get("Y4R").add(new char[][] { {'Y', ' '}, {'Y', 'Y'}, {' ', 'Y'} });

            allTiles.put("R5", new ArrayList<>());
            allTiles.get("R5").add(new char[][] { {' ', 'R', 'R'}, {'R', 'R', 'R'} });

            allTiles.put("B5", new ArrayList<>());
            allTiles.get("B5").add(new char[][] { {' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', 'B'} });

            allTiles.put("P5", new ArrayList<>());
            allTiles.get("P5").add(new char[][] { {'R', 'R', 'R','R','R'} });

            allTiles.put("G5", new ArrayList<>());
            allTiles.get("G5").add(new char[][] { {' ', 'G', ' '}, {'G', 'G', 'G'}, {' ', 'G', ' '} });

            allTiles.put("Y5", new ArrayList<>());
            allTiles.get("Y5").add(new char[][] { {' ', ' ', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', ' ', ' '} });
        }
        this.generatedTiles = generateTiles(dice);
    }

    /**
     * Checks whether the given tile is a valid selection from the set of generated tiles.
     * @param tileName The name of the tile to check (e.g., "R2", "B3").
     * @return true if the tile is a valid selection, false otherwise.
     * Author:
     */
    public boolean isValidSelection(String tileName) {
        for (String generatedTile : generatedTiles) {
            if (generatedTile.equals(tileName)) {
                return true;
            }
        }

        // If the tile is not in the generated tiles, return false
        return false;
    }

    /**
     * Applies windows to a tile, incrementing the values for any position that should have a window.
     * 'R' becomes 'S', 'B' becomes 'C', 'P' becomes 'Q', 'G' becomes 'H', 'Y' becomes 'Z'.
     * @param key The key for the tile to modify.
     * @param windows A boolean array where each true element represents the presence of a window.
     * Author:
     */
    public void applyWindows(String key, boolean[] windows) {
        char[][] tileArr = allTiles.get(key).get(0);
        int squareCounter = 0; // keeps count of all the squares so as it is easily referenced with the windows input
        for (int i = 0; i < tileArr.length; i++) {
            for (int j = 0; j < tileArr[i].length; j++) {
                if (!String.valueOf(tileArr[i][j]).equals(" ")) // checks if the element is equal to the colour (i.e. there is a square in the matrix)
                    squareCounter++;
                if (!String.valueOf(tileArr[i][j]).equals(" ") && windows[squareCounter - 1])
                    tileArr[i][j]++;
            }
        }

    }

    /**
     * Generates tiles based on the provided dice rolls. Takes into account colors and wild dice.
     * @param rolledDices The dice rolls that determine the available tiles.
     * @return A String array of the selected tiles.
     * Author: Eileen
     */
    public String[] generateTiles(Dices rolledDices) {
        String[] result = new String[4]; // 4 tiles on screen
        String[] color = {"R", "B", "P", "G", "Y"};
        int[] colorsNum = new int[5]; // number of dice of "Red", "Blue", "Purple", "Green", "Yellow"
        int wildCount = 0; // Count of wild (White) dice

        String[] dices = rolledDices.getAllDice();

        for (String s : dices) {
            if (s.equals("W")) {
                wildCount++;
            } else {
                for (int i = 0; i < color.length; i++) {
                    if (s.equals(color[i])) {
                        colorsNum[i]++;
                        break;
                    }
                }
            }
        }

        int ite = 0;
        while (ite < 4 && (Arrays.stream(colorsNum).sum() > 0 || wildCount > 0)) {
            int curMax = getmaxIndex(colorsNum, colorsNum.length);
            int cur = colorsNum[curMax] + wildCount;

            if (cur > 1) {
                for (int i = 2; i <= cur && ite < 4; i++) {
                    result[ite] = color[curMax] + i;
                    ite++;
                    if (colorsNum[curMax] > 0) {
                        colorsNum[curMax]--;
                    } else {
                        wildCount--;
                    }
                }
            }

            if (colorsNum[curMax] == 0) {
                colorsNum[curMax] = -1; // Mark as processed
            }
        }

        // Fill remaining slots with random tiles if needed
        Random rand = new Random();
        while (ite < 4) {
            int y = rand.nextInt(allTiles.keySet().toArray().length);
            result[ite] = String.valueOf(allTiles.keySet().toArray()[y]);
            ite++;
        }

        return result;
    }

    /**
     * Finds the index of the maximum value in an array.
     * @param arr The array to search.
     * @param n The size of the array.
     * @return The index of the maximum value.
     * Author: Eileen
     */
    private int getmaxIndex(int[] arr, int n) {
        int maxIndex = 0;
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * Rotates a tile by 0, 90, 180, or 270 degrees clockwise.
     * @param key The key for the tile to rotate.
     * @param rotation The number of 90-degree clockwise rotations (0 to 3).
     * Author:
     */
    public void rotateTile(String key, int rotation) {
        // Fetch the first tile in the list corresponding to the given key
        List<char[][]> tiles = allTiles.get(key);
        if (tiles == null || tiles.isEmpty()) {
            System.out.println("Tile not found for key: " + key);
            return;
        }

        char[][] tile = tiles.get(0);  // Get the original tile
        int rows = tile.length;
        int cols = tile[0].length;
        char[][] rotatedTile;

        // Determine new dimensions after rotation
        if (rotation % 2 == 1) {
            rotatedTile = new char[cols][rows]; // 90 or 270 degrees
        } else {
            rotatedTile = new char[rows][cols]; // 0 or 180 degrees
        }

        // Fill the rotated tile based on the rotation direction
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (rotation) {
                    case 0:
                        rotatedTile[i][j] = tile[i][j];
                        break;
                    case 1: // 90 degrees clockwise
                        rotatedTile[j][rows - 1 - i] = tile[i][j];
                        break;
                    case 2: // 180 degrees
                        rotatedTile[rows - 1 - i][cols - 1 - j] = tile[i][j];
                        break;
                    case 3: // 270 degrees clockwise
                        rotatedTile[cols - 1 - j][i] = tile[i][j];
                        break;
                }
            }
        }

        // Replace the old tile with the rotated one
        tiles.set(0, rotatedTile);  // Update the first tile in the list with the rotated version
    }

    /**
     * Returns all tiles in the game.
     * @return A copy of the map containing all tiles.
     * Author:
     */
    public Map<String, List<char[][]>> getAllTiles() {
        return new HashMap<>(allTiles);
    }

    /**
     * Prints the tile with the given key.
     * @param key The key of the tile to print.
     * Author:
     */
    public void printTile(String key) {
        List<char[][]> tiles = allTiles.get(key);
        if (tiles != null) {
            for (char[][] tile : tiles) {
                for (int i = 0; i < tile.length; i++) {
                    for (int j = 0; j < tile[i].length; j++) {
                        System.out.print(tile[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
    }

    /**
     * Prints the currently generated tiles.
     * Author:
     */
    public void printGeneratedTiles() {
        System.out.println("Generated Tiles:");
        for (String tile : generatedTiles) {
            System.out.println(tile);
        }
    }

}




