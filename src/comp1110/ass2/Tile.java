package comp1110.ass2;
import java.util.*;

public class Tile {


    //Hunter


//   Jalal's version
    private Map<String, List<char[][]>> allTiles;
    private Map<String, Integer> tileCounts;  // Stores the count of each color tile
    private String[] tiles;  // Stores the generated tiles
    private Random random;
    private String[] dice;

    // Constructor that takes a Dice object and generates tiles based on the dice roll
    public Tile(Dices dice) {
        tileCounts = new HashMap<>();
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
            allTiles.get("B4L").add(new char[][] {{' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', ' '} });

            allTiles.put("B4R", new ArrayList<>());
            allTiles.get("B4R").add(new char[][] {{' ', 'B', ' '}, {' ', 'B', ' '}, {' ', 'B', 'B'} });

            allTiles.put("P4", new ArrayList<>());
            allTiles.get("P4").add(new char[][] { {'P'}, {'P'}, {'P'}, {'P'} });

            allTiles.put("G4L", new ArrayList<>());
            allTiles.get("G4L").add(new char[][] { {' ', 'G', ' '}, {'G', 'G', ' '}, {' ', 'G', ' '} });

            allTiles.put("G4R", new ArrayList<>());
            allTiles.get("G4R").add(new char[][] { {' ', 'G', ' '}, {' ', 'G', 'G'}, {' ', 'G', ' '} });

            allTiles.put("Y4L", new ArrayList<>());
            allTiles.get("Y4L").add(new char[][] { {' ', 'Y', ' '}, {'Y', 'Y', ' '}, {'Y', ' ', ' '} });

            allTiles.put("Y4R", new ArrayList<>());
            allTiles.get("Y4R").add(new char[][] { {' ', 'Y', ' '}, {' ', 'Y', 'Y'}, {' ', ' ', 'Y'} });

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


        generateTiles(dice.getAllDice());
    }

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

    public boolean isValidSelection(String tileName) {

        int diceOfTileColour = 0;

        for (int i = 0; i < dice.length; i++) {
            if (tileName.startsWith(dice[i]) || Objects.equals(dice[i], "W"))
                diceOfTileColour++;

        }
        String tileSize = tileName.replaceAll("[A-Z]", "");
        if (diceOfTileColour >= Integer.parseInt(tileSize))
            return true;
        else
            return false;
    }
    // adds a new char[][] to the list mapped to the key. I feel like this should work, will be easy modify later if not
    public void applyWindows(String key, boolean[] windows) {
        char[][] tileArr = allTiles.get(key).get(0);
        //creates a clone so to not alter original
        char[][] windowArr = new char[tileArr.length][];
        for (int i = 0; i < tileArr.length; i++) {
            windowArr[i] = tileArr[i].clone();
        }
        int squareCounter = 0;
        for (int i = 0; i < windowArr.length; i++) {
            for (int j = 0; j < windowArr[i].length; j++) {
                if (!String.valueOf(windowArr[i][j]).equals(" "))
                    squareCounter++;
                if (!String.valueOf(windowArr[i][j]).equals(" ") && windows[squareCounter - 1])
                    windowArr[i][j] = 'W';
                else
                    windowArr[i][j] = ' ';
            }
        }
        allTiles.get(key).add(windowArr);
    }


    public int[][] generateTiles(String[] Dices) {

        return new int[0][0];
    }


    public static char[][] rotateTile(char[][] tile, int rotation) {
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
        return rotatedTile;
    }

    public static void main(String[] args) {
        Dices D1 = new Dices();

        //System.out.println(Arrays.toString(D1.getAllDice()));
        Tile T1 = new Tile(D1);
        char[][] tile = new char[][] { {'R', ' '}, {'R', ' '} };
        boolean[] windows = {true, true};
        T1.applyWindows("R2", windows);
        T1.printTile("R2");



    }
}




