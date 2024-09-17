package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jalalTest {

    static Map<String, List<char[][]>> tiles = new HashMap<>();

    static {
        tiles.put("R2", new ArrayList<>());
        tiles.get("R2").add(new char[][] { {'R', ' '}, {'R', ' '} });

        tiles.put("B2", new ArrayList<>());
        tiles.get("B2").add(new char[][] { {'B', ' '}, {'B', ' '} });

        tiles.put("P2", new ArrayList<>());
        tiles.get("P2").add(new char[][] { {'P', ' '}, {'P', ' '} });

        tiles.put("G2", new ArrayList<>());
        tiles.get("G2").add(new char[][] { {'G', ' '}, {'G', ' '} });

        tiles.put("Y2", new ArrayList<>());
        tiles.get("Y2").add(new char[][] { {'Y', ' '}, {'Y', ' '} });

        tiles.put("R3", new ArrayList<>());
        tiles.get("R3").add(new char[][] { {' ', 'R'}, {'R', 'R'} });

        tiles.put("B3", new ArrayList<>());
        tiles.get("B3").add(new char[][] { {' ', 'B'}, {'B', 'B'} });

        tiles.put("P3", new ArrayList<>());
        tiles.get("P3").add(new char[][] { {'P'}, {'P'}, {'P'} });

        tiles.put("G3", new ArrayList<>());
        tiles.get("G3").add(new char[][] { {'G'}, {'G'}, {'G'} });

        tiles.put("Y3", new ArrayList<>());
        tiles.get("Y3").add(new char[][] { {' ', 'Y'}, {'Y', 'Y'} });

        tiles.put("R4", new ArrayList<>());
        tiles.get("R4").add(new char[][] { {'R', 'R'}, {'R', 'R'} });
        tiles.get("R4").add(new char[][] { {'R', 'R'}, {'R', 'R'} });

        tiles.put("B4L", new ArrayList<>());
        tiles.get("B4L").add(new char[][] {{' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', ' '} });

        tiles.put("B4R", new ArrayList<>());
        tiles.get("B4R").add(new char[][] {{' ', 'B', ' '}, {' ', 'B', ' '}, {' ', 'B', 'B'} });

        tiles.put("P4", new ArrayList<>());
        tiles.get("P4").add(new char[][] { {'P'}, {'P'}, {'P'}, {'P'} });

        tiles.put("G4L", new ArrayList<>());
        tiles.get("G4L").add(new char[][] { {' ', 'G', ' '}, {'G', 'G', ' '}, {' ', 'G', ' '} });

        tiles.put("G4R", new ArrayList<>());
        tiles.get("G4R").add(new char[][] { {' ', 'G', ' '}, {' ', 'G', 'G'}, {' ', 'G', ' '} });

        tiles.put("Y4L", new ArrayList<>());
        tiles.get("Y4L").add(new char[][] { {' ', 'Y', ' '}, {'Y', 'Y', ' '}, {'Y', ' ', ' '} });

        tiles.put("Y4R", new ArrayList<>());
        tiles.get("Y4R").add(new char[][] { {' ', 'Y', ' '}, {' ', 'Y', 'Y'}, {' ', ' ', 'Y'} });

        tiles.put("R5", new ArrayList<>());
        tiles.get("R5").add(new char[][] { {' ', 'R', 'R'}, {'R', 'R', 'R'} });

        tiles.put("B5", new ArrayList<>());
        tiles.get("B5").add(new char[][] { {' ', 'B', ' '}, {' ', 'B', ' '}, {'B', 'B', 'B'} });

        tiles.put("P5", new ArrayList<>());
        tiles.get("P5").add(new char[][] { {'R', 'R', 'R','R','R'} });

        tiles.put("G5", new ArrayList<>());
        tiles.get("G5").add(new char[][] { {' ', 'G', ' '}, {'G', 'G', 'G'}, {' ', 'G', ' '} });

        tiles.put("Y5", new ArrayList<>());
        tiles.get("Y5").add(new char[][] { {' ', ' ', 'Y'}, {'Y', 'Y', 'Y'}, {'Y', ' ', ' '} });
    }

    public static boolean placeTile(char[][] board, char[][] tile, int row, int col) {
        int tileRows = tile.length;
        int tileCols = tile[0].length;

        // Check if the tile fits within the gameboard bounds
        if (row + tileRows > board.length || col + tileCols > board[0].length) {
            return false; // Tile doesn't fit on the board
        }

        // Check for conflicts with existing tiles
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ' && board[row + i][col + j] != '.') {
                    return false; // Conflict detected only if the tile space is not empty
                }
            }
        }

        // Place the tile on the board
        for (int i = 0; i < tileRows; i++) {
            for (int j = 0; j < tileCols; j++) {
                if (tile[i][j] != ' ') {
                    board[row + i][col + j] = tile[i][j];
                }
            }
        }
        return true; // Placement successful
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

    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] == '\u0000' ? '.' : board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void printTile(char[][] tile) {
        for (int i = 0; i < tile.length; i++) {
            for (int j = 0; j < tile[0].length; j++) {
                System.out.print(tile[i][j] == '\u0000' ? '.' : tile[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        char[][] gameBoard = new char[9][5];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = '.';
            }
        }

        // Example usage of rotateTile method
        char[][] tileToRotate = tiles.get("Y5").get(0); // Choose the first instance

        char[][] rotatedTile = rotateTile(tileToRotate, 3);

        System.out.println("Original Tile:");
        printTile(tileToRotate);
        System.out.println("Rotated Tile (" + 3 * 90 + " degrees):");
        printTile(rotatedTile);

        if (placeTile(gameBoard, rotatedTile, 2, 2)) {  // Place rotated tile starting at (2, 2)
            System.out.println("Tile placed successfully");
        } else {
            System.out.println("Tile placement failed");
        }
        printBoard(gameBoard);
    }
}


