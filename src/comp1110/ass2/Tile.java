package comp1110.ass2;

import java.util.*;

public class Tile {

    // Fields
    private final Map<String, List<char[][]>> allTiles;
    private final String[] dice;
    private final String[] generatedTiles;
    private char[][] selectedTile;
    private String selectedTileKey;
    private final Map<String, List<char[][]>> usedTiles;
    private int R4Counter = 0;
    private int P4Counter = 0;
    private final List<String> size4And5Tiles;

    // Constructor
    /**
     * Constructor that initializes the Tile object. It generates all the possible tiles and
     * also generates tiles based on the provided dice roll.
     *
     * @param dice The Dice object used to determine which tiles will be generated.
     */
    public Tile(Dices dice) {
        allTiles = new HashMap<>();
        usedTiles = new HashMap<>();
        size4And5Tiles = new ArrayList<>();
        this.dice = dice.getAllDice();
        {
            allTiles.put("I1O", new ArrayList<>());
            allTiles.get("I1O").add(new char[][]{{'I'}});

            allTiles.put("I1X", new ArrayList<>());
            allTiles.get("I1X").add(new char[][]{{'I'}});

            allTiles.put("B2", new ArrayList<>());
            allTiles.get("B2").add(new char[][]{{'B'}, {'B'}});

            allTiles.put("R2", new ArrayList<>());
            allTiles.get("R2").add(new char[][]{{'R'}, {'R'}});

            allTiles.put("P2", new ArrayList<>());
            allTiles.get("P2").add(new char[][]{{'P'}, {'P'}});

            allTiles.put("G2", new ArrayList<>());
            allTiles.get("G2").add(new char[][]{{'G'}, {'G'}});

            allTiles.put("Y2", new ArrayList<>());
            allTiles.get("Y2").add(new char[][]{{'Y'}, {'Y'}});

            allTiles.put("R3", new ArrayList<>());
            allTiles.get("R3").add(new char[][]{{' ', 'R'}, {'R', 'R'}});

            allTiles.put("B3", new ArrayList<>());
            allTiles.get("B3").add(new char[][]{{' ', 'B'}, {'B', 'B'}});

            allTiles.put("P3", new ArrayList<>());
            allTiles.get("P3").add(new char[][]{{'P'}, {'P'}, {'P'}});

            allTiles.put("G3", new ArrayList<>());
            allTiles.get("G3").add(new char[][]{{'G'}, {'G'}, {'G'}});

            allTiles.put("Y3", new ArrayList<>());
            allTiles.get("Y3").add(new char[][]{{' ', 'Y'}, {'Y', 'Y'}});

            // Add size 4 tiles
            addTile("R4", new char[][]{{'R', 'R'}, {'R', 'R'}});
            addTile("B4L", new char[][]{{' ', 'B'}, {' ', 'B'}, {'B', 'B'}});
            addTile("B4R", new char[][]{{'B', ' '}, {'B', ' '}, {'B', 'B'}});
            addTile("P4", new char[][]{{'P'}, {'P'}, {'P'}, {'P'}});
            addTile("G4L", new char[][]{{' ', 'G'}, {'G', 'G'}, {' ', 'G'}});
            addTile("G4R", new char[][]{{'G', ' '}, {'G', 'G'}, {'G', ' '}});
            addTile("Y4L", new char[][]{{' ', 'Y'}, {'Y', 'Y'}, {'Y', ' '}});
            addTile("Y4R", new char[][]{{'Y', ' '}, {'Y', 'Y'}, {' ', 'Y'}});

            // Add size 5 tiles
            addTile("R5", new char[][]{{' ', 'R', 'R'}, {'R', 'R', 'R'}});
            addTile("B5", new char[][]{{' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', 'B'}});
            addTile("P5", new char[][]{{'P', 'P', 'P', 'P', 'P'}});
            addTile("G5", new char[][]{{' ', 'G', ' '}, {'G', 'G', 'G'}, {' ', 'G', ' '}});
            addTile("Y5", new char[][]{{' ', ' ', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', ' ', ' '}});
        }
        this.generatedTiles = doGenerateTiles(dice, new int[5]);
    }

    // Private methods
    /**
     * Helper method to add a tile and, if it's a size 4 or size 5 tile, also add its name to size4And5Tiles.
     *
     * @param key The name of the tile.
     * @param tile The tile's shape.
     */
    private void addTile(String key, char[][] tile) {
        allTiles.put(key, new ArrayList<>());
        allTiles.get(key).add(tile);

        // Add to size4And5Tiles if it's a size 4 or 5 tile
        if (key.contains("4") || key.contains("5")) {
            size4And5Tiles.add(key);
        }
    }

    /**
     * Core logic for checking whether the given tile is a valid selection from the set of generated tiles.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile to check (e.g., "R2", "B3").
     * @return true if the tile is a valid selection, false otherwise.
     */
    private boolean doIsValidSelection(List<String> selectedDice, String tileName) {
        if (tileName == null)
            return false;
        int diceOfTileColour = 0;
        for (String s : selectedDice) {
            if (tileName.startsWith(s) || Objects.equals(s, "W"))
                diceOfTileColour++;
        }
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        return diceOfTileColour >= tileSize;
    }

    /**
     * Core logic for applying windows to the selectedTile, incrementing the values for any position that should have a window.
     * 'R' becomes 'S', 'B' becomes 'C', 'P' becomes 'Q', 'G' becomes 'H', 'Y' becomes 'Z'.
     *
     * @param windows A boolean array where each true element represents the presence of a window.
     */
    private void doApplyWindows(boolean[] windows) {
        if (selectedTile == null || windows == null) {
            System.out.println("Selected tile or windows array is null");
            return;
        }
        char[][] tileArr = selectedTile;
        int squareCounter = 0; // Keeps count of all the squares to reference with the windows input
        int rowL = tileArr.length;
        int colL = tileArr[0].length;
        for (int c = 0; c < colL; c++) {
            for (int i = rowL - 1; i >= 0; i--) {
                if (tileArr[i][c] != ' ') // Checks if there is a square in the matrix
                    squareCounter++;
                if (tileArr[i][c] != ' ' && windows[squareCounter - 1])
                    tileArr[i][c]++;
            }
        }
    }

    /**
     * Core logic for generating tiles based on the provided dice roll.
     *
     * @param rolledDices The Dice object used to determine which tiles will be generated.
     * @return An array of strings representing the generated tiles.
     */
    private String[] doGenerateTiles(Dices rolledDices, int[] bonuses) {
        Set<String> result = new HashSet<>(); // Use a Set to avoid duplicates
        String[] color = {"R", "B", "P", "G", "Y"};
        int[] colorsNum = new int[5]; // Number of dice of "Red", "Blue", "Purple", "Green", "Yellow"
        int wildCount = 0; // Count of wild (White) dice

        // Count the dice for each color and wilds
        String[] dices = rolledDices.getAllDice();
        for (int i = 0; i < bonuses.length; i++) {
            colorsNum[i] = colorsNum[i] + bonuses[i];
        }
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

        // For each tileKey in allTiles
        for (String tileKey : allTiles.keySet()) {
            // Get the color of the tile (first character)
            char tileColor = tileKey.charAt(0);
            String tileColorStr = String.valueOf(tileColor);

            // Get the size of the tile by extracting digits from the key
            String sizeStr = tileKey.replaceAll("[^0-9]", "");
            if (sizeStr.isEmpty()) {
                continue; // Skip if no size found
            }
            int tileSize = Integer.parseInt(sizeStr);

            // Find the index of the tile color in the color array
            int colorIndex = -1;
            for (int i = 0; i < color.length; i++) {
                if (color[i].equals(tileColorStr)) {
                    colorIndex = i;
                    break;
                }
            }

            if (colorIndex == -1) {
                continue; // Unknown color
            }

            int diceCount = colorsNum[colorIndex];
            int maxDiceForColor = diceCount + wildCount;

            // Check if we have enough dice (including wild dice) to form the tile
            if (maxDiceForColor >= tileSize) {
                result.add(tileKey);
            }
        }

        return result.toArray(new String[0]);
    }

    /**
     * Core logic for rotating a tile by 0, 90, 180, or 270 degrees clockwise.
     *
     * @param rotation The number of 90-degree clockwise rotations (0 to 3).
     */
    private void doRotateTile(int rotation) {
        char[][] tile = selectedTile;
        if (tile == null) {
            System.out.println("Selected tile not found");
            return;
        }

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
                    case 0 -> rotatedTile[i][j] = tile[i][j];
                    case 1 -> // 90 degrees clockwise
                            rotatedTile[j][rows - 1 - i] = tile[i][j];
                    case 2 -> // 180 degrees
                            rotatedTile[rows - 1 - i][cols - 1 - j] = tile[i][j];
                    case 3 -> // 270 degrees clockwise
                            rotatedTile[cols - 1 - j][i] = tile[i][j];
                    default -> throw new IllegalArgumentException("Invalid rotation value: " + rotation);
                }
            }
        }

        // Replace the old tile with the rotated one
        selectedTile = rotatedTile; // Update selectedTile with the rotated version
    }

    /**
     * Core logic for updating the selected tile.
     *
     * @param key The key for the selected tile.
     */
    private void doUpdateSelectedTile(String key) {
        if (key == null || !allTiles.containsKey(key)) {
            System.out.println("Invalid tile key");
            return;
        }
        char[][] tileToBeCopied = allTiles.get(key).get(0);
        char[][] copy = new char[tileToBeCopied.length][];
        for (int i = 0; i < tileToBeCopied.length; i++) {
            copy[i] = Arrays.copyOf(tileToBeCopied[i], tileToBeCopied[i].length);
        }
        this.selectedTile = copy;
        this.selectedTileKey = key;
    }


    // Public methods (Getters and Setters)
// ============================================

    /**
     * Checks whether the given tile is a valid selection from the set of generated tiles.
     *
     * @param selectedDice The list of selected dice.
     * @param tileName     The name of the tile to check (e.g., "R2", "B3").
     * @return true if the tile is a valid selection, false otherwise.
     */
    public boolean isValidSelection(List<String> selectedDice, String tileName) {
        return doIsValidSelection(selectedDice, tileName);
    }

    /**
     * Applies windows to the selectedTile, incrementing the values for any position that should have a window.
     * 'R' becomes 'S', 'B' becomes 'C', 'P' becomes 'Q', 'G' becomes 'H', 'Y' becomes 'Z'.
     *
     * @param windows A boolean array where each true element represents the presence of a window.
     */
    public void applyWindows(boolean[] windows) {
        doApplyWindows(windows);
    }

    /**
     * Generates tiles based on the provided dice roll.
     *
     * @param rolledDices The Dice object used to determine which tiles will be generated.
     * @return An array of strings representing the generated tiles.
     */
    public String[] generateTiles(Dices rolledDices, int[] bonuses) {
        return doGenerateTiles(rolledDices, bonuses);
    }

    /**
     * Rotates a tile by 0, 90, 180, or 270 degrees clockwise.
     *
     * @param rotation The number of 90-degree clockwise rotations (0 to 3).
     */
    public void rotateTile(int rotation) {
        doRotateTile(rotation);
    }

    /**
     * Updates the selected tile.
     *
     * @param key The key for the selected tile.
     */
    public void updateSelectedTile(String key) {
        doUpdateSelectedTile(key);
    }

    /**
     * Gets the generated tiles.
     *
     * @return A copy of the generated tiles array.
     */
    public String[] getGeneratedTiles() {
        return generatedTiles != null ? generatedTiles.clone() : new String[0];
    }

    /**
     * Gets the key of the selected tile.
     *
     * @return The key of the currently selected tile.
     */
    public String getSelectedTileKey() {
        return selectedTileKey;
    }

    /**
     * Gets the selected tile.
     *
     * @return A deep copy of the currently selected tile.
     */
    public char[][] getSelectedTile() {
        if (selectedTile == null) {
            return null;
        }
        char[][] copy = new char[selectedTile.length][];
        for (int i = 0; i < selectedTile.length; i++) {
            copy[i] = Arrays.copyOf(selectedTile[i], selectedTile[i].length);
        }
        return copy;
    }

    /**
     * Returns all tiles in the game.
     *
     * @return A deep copy of the map containing all tiles.
     */
    public Map<String, List<char[][]>> getAllTiles() {
        Map<String, List<char[][]>> copy = new HashMap<>();
        for (Map.Entry<String, List<char[][]>> entry : allTiles.entrySet()) {
            List<char[][]> tileListCopy = new ArrayList<>();
            for (char[][] tile : entry.getValue()) {
                char[][] tileCopy = new char[tile.length][];
                for (int i = 0; i < tile.length; i++) {
                    tileCopy[i] = Arrays.copyOf(tile[i], tile[i].length);
                }
                tileListCopy.add(tileCopy);
            }
            copy.put(entry.getKey(), tileListCopy);
        }
        return copy;
    }

    /**
     * Removes a tile from allTiles.
     *
     * @param key The key of the tile to remove.
     */
    public void removeTile(String key) {
        if (key == null || !allTiles.containsKey(key)) {
            return; // If the key is null or doesn't exist, exit early.
        }
        // Special case for R4
        if (key.equals("R4")) {
            R4Counter++;
            if (R4Counter == 2) {
                // Get the tiles from allTiles, move them to usedTiles, and remove from allTiles
                List<char[][]> tiles = allTiles.get(key);
                usedTiles.put(key, tiles);
                allTiles.remove(key);
            }
            return;
        }

        if (key.equals("P4")) {
            P4Counter++;
            if (P4Counter == 2) {
                // Get the tiles from allTiles, move them to usedTiles, and remove from allTiles
                List<char[][]> tiles = allTiles.get(key);
                usedTiles.put(key, tiles);
                allTiles.remove(key);
            }
            return;
        }

        // General case for keys containing '4' or '5'
        if (key.contains("4") || key.contains("5")) {
            // Get the tiles from allTiles, move them to usedTiles, and remove from allTiles
            List<char[][]> tiles = allTiles.get(key);
            usedTiles.put(key, tiles);
            allTiles.remove(key);
        }
    }

    /**
     * returns used Tiles in the game per player
     * @return Hashmap of usedTiles
     */
    public Map<String, List<char[][]>> getUsedTiles(){
        return usedTiles;
    }

    public List<String> getSize4And5Tiles(){
        return size4And5Tiles;
    }
}
