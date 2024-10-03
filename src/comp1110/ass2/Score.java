package comp1110.ass2;

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
    public void addPoints(char[][] gameBoard) {
        int rows = gameBoard.length;
        int cols = gameBoard[0].length;

        // Check each row
        for (int i = 0; i < rows; i++) {
            if (isCompleteRow(gameBoard, i)) {
                if (isAllWindowsRow(gameBoard, i)) {
                    score += 2;  // All windows
                } else {
                    score += 1;  // Not all windows, but complete
                }
            }
        }

        // Check each column
        for (int j = 0; j < cols; j++) {
            if (isCompleteColumn(gameBoard, j)) {
                if (isAllWindowsColumn(gameBoard, j)) {
                    score += 4;  // All windows
                } else {
                    score += 2;  // Not all windows, but complete
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

    /**
     * checks row 1,3,5 and col 1,3
     * return 1 if row is completed, 0 is row else -1
     * @param board
     * @return
     */
    private HashMap<String, List<Integer>> isCoA(char[][] board){
        HashMap<String, List<Integer>> res = new HashMap<>();
        res.put("completedRows", new ArrayList<>());
        res.put("completedCols", new ArrayList<>());
        res.put("completedRowsWindows", new ArrayList<>());
        res.put("completedColsWindows", new ArrayList<>());

        int[] rows = {1, 3, 5};
        int[] cols = {1, 3};

        for (int i = 0; i < rows.length; i++) {
            if (isCompleteRow(board, rows[i])) {
                if (isAllWindowsRow(board,rows[i])){
                    res.get("completedRowsWindows").add(rows[i]);
                }
                res.get("completedRows").add(rows[i]);  // Add completed row index
            }
        }
        for (int i = 0; i < cols.length; i++) {
            if (isCompleteColumn(board, cols[i])) {
                if (isCompleteColumn(board,cols[i])){
                    res.get("completedColsWindows").add(cols[i]);
                }
                res.get("completedCols").add(cols[i]);  // Add completed column index
            }
        }
        return res;
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

    /**
     * Checks for completed rows and columns of interest (CoA).
     *
     * @param board The game board represented as a 2D character array.
     * @return A list of integers indicating completed rows and columns.
     * @author Jalal
     */
    public HashMap<String, List<Integer>> getCoA(char[][] board) {
        return isCoA(board);
    }
}
