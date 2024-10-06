package comp1110.ass2;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score {

    private int score;

    /**
     * Constructs a new Score with an initial score of 0.
     * @author Jalal
     */
    public Score() {
        this.score = 0;
    }

    /**
     * Updates the score based on the game board.
     *
     * Completing a row of their building with windows in all squares of
     * the row: 2 points.
     * Completing a row of their building, but not with windows in all
     * squares of the row: 1 point.
     * Completing a column of their building with windows in all squares of
     * the column: 4 points.
     * Completing a column of their building, but not with windows in all
     * squares of the column: 2 points.
     *
     * @param gameBoard The game board represented as a 2D character array.
     * @author Jalal
     */
    public void addPoints(char[][] gameBoard, HashMap<String, List<Integer>> completedMap) {
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        completedMap.put("completedRows", new ArrayList<>());
        completedMap.put("completedCols", new ArrayList<>());

        // Check each row
        for (int i = 0; i < rows; i++) {
            if (isCompleteRow(gameBoard, i)) {
                // Add points based on whether the row is all windows
                if (isAllWindowsRow(gameBoard, i)) {
                    score += 2;
                } else {
                    score += 1;
                }

                switch (i) {
                    case 7:
                        completedMap.get("completedRows").add(1);
                        break;
                    case 5:
                        completedMap.get("completedRows").add(3);
                        break;
                    case 3:
                        completedMap.get("completedRows").add(5);
                        break;
                    default:
                        break; // If i is not 7, 5, or 3, do nothing
                }

            }
        }

        // Check each column
        for (int j = 0; j < cols; j++) {
            if (isCompleteColumn(gameBoard, j)) {
                // Add points based on whether the column is all windows
                if (isAllWindowsColumn(gameBoard, j)) {
                    score += 4;
                } else {
                    score += 2;
                }

                if (j == 1 || j == 3) {
                    completedMap.get("completedCols").add(j);
                }

            }
        }
    }

    /**
     * Checks if a specific row is complete (i.e., no empty spaces).
     *
     * @param board The game board represented as a 2D character array.
     * @param row The index of the row to check.
     * @return true if the row is complete, false otherwise.
     * @author Jalal
     */
    private boolean isCompleteRow(char[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            if (board[row][j] == '.') {
                return false;  // Found an empty space
            }
        }
        return true;
    }

    /**
     * Checks if a specific row contains only windows.
     *
     * @param board The game board represented as a 2D character array.
     * @param row The index of the row to check.
     * @return true if the row contains only windows, false otherwise.
     * @author Jalal
     */
    private boolean isAllWindowsRow(char[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            if (!isWindow(board[row][j])) {
                return false;  // Found a non-window tile
            }
        }
        return true;
    }

    /**
     * Checks if a specific column is complete (i.e., no empty spaces).
     *
     * @param board The game board represented as a 2D character array.
     * @param col The index of the column to check.
     * @return true if the column is complete, false otherwise.
     * @author Jalal
     */
    private boolean isCompleteColumn(char[][] board, int col) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == '.') {
                return false;  // Found an empty space
            }
        }
        return true;
    }

    /**
     * Checks if a specific column contains only windows.
     *
     * @param board The game board represented as a 2D character array.
     * @param col The index of the column to check.
     * @return true if the column contains only windows, false otherwise.
     * @author Jalal
     */
    private boolean isAllWindowsColumn(char[][] board, int col) {
        for (int i = 0; i < board.length; i++) {
            if (!isWindow(board[i][col])) {
                return false;  // Found a non-window tile
            }
        }
        return true;
    }

    /**
     * Checks if a tile is a window.
     *
     * @param tile The character representing the tile.
     * @return true if the tile is a window, false otherwise.
     * @author Jalal
     */
    private boolean isWindow(char tile) {
        return tile == 'S' || tile == 'C' || tile == 'Q' || tile == 'H' || tile == 'Z';
    }

//    /**
//     * checks row 1,3,5 and col 1,3
//     * return 1 if row is completed, 0 is row else -1
//     * @param board
//     * @return
//     */
//    private HashMap<String, List<Integer>> isCoA(char[][] board) {
//        HashMap<String, List<Integer>> res = new HashMap<>();
//        res.put("completedRows", new ArrayList<>());
//        res.put("completedCols", new ArrayList<>());
//        res.put("completedRowsWindows", new ArrayList<>());
//        res.put("completedColsWindows", new ArrayList<>());
//
//        int[] rows = {1, 3, 5};
//        int[] cols = {1, 3};
//
//        // Check specific rows
//        for (int row : rows) {
//            if (isCompleteRow(board, row)) {
//                res.get("completedRows").add(row);  // Add completed row index
//                if (isAllWindowsRow(board, row)) {
//                    ;
//                    res.get("completedRowsWindows").add(row);  // Add completed row with all windows
//                    System.out.println("Row " + row + " completed with all windows");
//                } else {
//                    res.get("completedRows").add(row);
//                    System.out.println("Row " + row + " completed but not all windows");
//                }
//            } else {
//                System.out.println("Row " + row + " is not complete");
//            }
//        }
//
//        // Check specific columns
//        for (int col : cols) {
//            if (isCompleteColumn(board, col)) {
//                res.get("completedCols").add(col);  // Add completed column index
//                if (isAllWindowsColumn(board, col)) {
//                    res.get("completedColsWindows").add(col);  // Add completed column with all windows
//                    System.out.println("Column " + col + " completed with all windows");
//                } else {
//                    System.out.println("Column " + col + " completed but not all windows");
//                }
//            } else {
//                System.out.println("Column " + col + " is not complete");
//            }
//        }
//
//        return res;
//    }

    /**
     * Adds two points to the score.
     * Intended for use by track classes.
     *
     * @author Jalal
     */
    public void addTwoToScore() {
        this.score += 2;
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     * @author Jalal
     */
    public int getScore() {
        return score;
    }

    /**
     * Checks for completed rows and columns of interest (CoA).
     *
     * @param board The game board represented as a 2D character array.
     * @return A list of integers indicating completed rows and columns.
     * @author Jalal
     */
//    public HashMap<String, List<Integer>> getCoA(char[][] board) {
//        System.out.println("I am in");
//        return isCoA(board);
//    }
}
