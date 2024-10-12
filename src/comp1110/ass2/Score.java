package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score {

    private int score;

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
        for (char[] chars : board) {
            if (chars[col] == '.') {
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
        for (char[] chars : board) {
            if (!isWindow(chars[col])) {
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

    /**
     * Updates the score based on the game board.
     * <p>
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
    private void doAddPoints(char[][] gameBoard, HashMap<String, List<Integer>> completedMap) {
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
                    case 7 -> completedMap.get("completedRows").add(1);
                    case 5 -> completedMap.get("completedRows").add(3);
                    case 3 -> completedMap.get("completedRows").add(5);
                    default -> {
                    } // If i is not 7, 5, or 3, do nothing
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

//          Public Methods
//===================================
    /**
     * Constructs a new Score with an initial score of 0.
     * @author Jalal
     */
    public Score() {
        this.score = 0;
    }

    /**
     * Updates the score counter by reading the gameBoard and checking if rows and coloums are completed
     * @param gameBoard
     * @param completedMap
     */
    public void addPoints(char[][] gameBoard, HashMap<String, List<Integer>> completedMap){
        doAddPoints(gameBoard,completedMap);
    }

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

}
