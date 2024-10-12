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
        this.dice = dice.getAllDice();
        {
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

            allTiles.put("R4", new ArrayList<>());
            allTiles.get("R4").add(new char[][]{{'R', 'R'}, {'R', 'R'}});

            allTiles.put("B4L", new ArrayList<>());
            allTiles.get("B4L").add(new char[][]{{' ', 'B'}, {' ', 'B'}, {'B', 'B'}});

            allTiles.put("B4R", new ArrayList<>());
            allTiles.get("B4R").add(new char[][]{{'B', ' '}, {'B', ' '}, {'B', 'B'}});

            allTiles.put("P4", new ArrayList<>());
            allTiles.get("P4").add(new char[][]{{'P'}, {'P'}, {'P'}, {'P'}});

            allTiles.put("G4L", new ArrayList<>());
            allTiles.get("G4L").add(new char[][]{{' ', 'G'}, {'G', 'G'}, {' ', 'G'}});

            allTiles.put("G4R", new ArrayList<>());
            allTiles.get("G4R").add(new char[][]{{'G', ' '}, {'G', 'G'}, {'G', ' '}});

            allTiles.put("Y4L", new ArrayList<>());
            allTiles.get("Y4L").add(new char[][]{{' ', 'Y'}, {'Y', 'Y'}, {'Y', ' '}});

            allTiles.put("Y4R", new ArrayList<>());
            allTiles.get("Y4R").add(new char[][]{{'Y', ' '}, {'Y', 'Y'}, {' ', 'Y'}});

            allTiles.put("R5", new ArrayList<>());
            allTiles.get("R5").add(new char[][]{{' ', 'R', 'R'}, {'R', 'R', 'R'}});

            allTiles.put("B5", new ArrayList<>());
            allTiles.get("B5").add(new char[][]{{' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', 'B'}});

            allTiles.put("P5", new ArrayList<>());
            allTiles.get("P5").add(new char[][]{{'P', 'P', 'P', 'P', 'P'}});

            allTiles.put("G5", new ArrayList<>());
            allTiles.get("G5").add(new char[][]{{' ', 'G', ' '}, {'G', 'G', 'G'}, {' ', 'G', ' '}});

            allTiles.put("Y5", new ArrayList<>());
            allTiles.get("Y5").add(new char[][]{{' ', ' ', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', ' ', ' '}});
        }

        this.generatedTiles = doGenerateTiles(dice);

    }

    // Private methods
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
    private String[] doGenerateTiles(Dices rolledDices) {
        Set<String> result = new HashSet<>(); // Use a Set to avoid duplicates
        String[] color = {"R", "B", "P", "G", "Y"};
        int[] colorsNum = new int[5]; // Number of dice of "Red", "Blue", "Purple", "Green", "Yellow"
        int wildCount = 0; // Count of wild (White) dice

        // Count the dice for each color and wilds
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

        rolledDices.setColorCount(colorsNum, wildCount);

        int totalDice = Arrays.stream(colorsNum).sum() + wildCount;

        // For each color, calculate possible tiles considering wild dice
        for (int i = 0; i < color.length; i++) {
            int diceCount = colorsNum[i];
            int maxDiceForColor = diceCount + wildCount;
            int maxTileSize = Math.min(maxDiceForColor, totalDice);

            // Generate tiles based on the possible dice count for each color
            for (int tileSize = 2; tileSize <= maxTileSize; tileSize++) {
                String tileKey = color[i] + tileSize;
                if (allTiles.containsKey(tileKey)) {
                    result.add(tileKey);
                }
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
    public String[] generateTiles(Dices rolledDices) {
        return doGenerateTiles(rolledDices);
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
        if (key != null && allTiles.containsKey(key)) {
            if (key.contains("4") || key.contains("5")) {
                // Get the tiles from allTiles
                List<char[][]> tiles = allTiles.get(key);
                // Move it to usedTiles
                usedTiles.put(key, tiles);
                // Remove from allTiles
                allTiles.remove(key);
            }
        }
    }

    /**
     * returns used Tiles in the game per player
     * @return Hashmap of usedTiles
     */
    public Map<String, List<char[][]>> getUsedTiles(){
        return usedTiles;
    }
}
