package comp1110.ass2;

import java.util.*;

public class Score {

    private int score;
    private boolean coaIndicator = false;

    //variables to store which rows/columns have already been checked
    private final Set<Integer> scoredRows = new HashSet<>();
    private final Set<Integer> scoredColumns = new HashSet<>();

    /**
     * Constructs a new Score with an initial score of 0.
     * @author Jalal
     */
    public Score() {
        this.score = 0;
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
            if (isWindowInverted(board[row][j])) {
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
            if (isWindowInverted(chars[col])) {
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
    private boolean isWindowInverted(char tile) {
        return tile != 'S' && tile != 'C' && tile != 'Q' && tile != 'H' && tile != 'Z';
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

        completedMap.putIfAbsent("completedRows", new ArrayList<>());
        completedMap.putIfAbsent("completedCols", new ArrayList<>());

        // Check each row
        for (int i = 0; i < rows; i++) {
            //checks points haven't already been added for a specific row before adding them
            if (isCompleteRow(gameBoard, i) && !scoredRows.contains(i)) {
                // Add points based on whether the row is all windows

                if (isAllWindowsRow(gameBoard, i)) {
                    score += 2;
                } else {
                    score += 1;
                }
                scoredRows.add(i);

                switch (i) {
                    case 7 -> {
                        if (!completedMap.get("completedRows").contains(1)) {
                            completedMap.get("completedRows").add(1);
                            coaIndicator = true;
                        }
                    }
                    case 5 -> {
                        if (!completedMap.get("completedRows").contains(3)) {
                            completedMap.get("completedRows").add(3);
                            coaIndicator = true;
                        }
                    }
                    case 3 -> {
                        if (!completedMap.get("completedRows").contains(5)) {
                            completedMap.get("completedRows").add(5);
                            coaIndicator = true;
                        }
                    }
                    default -> {
                    } // If I will be not 7, 5, or 3, do nothing
                }
            }
        }

        // Check each column
        for (int j = 0; j < cols; j++) {
            //checks if a column is complete and points haven't been added for it yet
            if (isCompleteColumn(gameBoard, j) && !scoredColumns.contains(j)) {
                // Add points based on whether the column is all windows
                if (isAllWindowsColumn(gameBoard, j)) {
                    score += 4;
                } else {
                    score += 2;
                }
                scoredColumns.add(j);

                if ((j == 1 || j == 3) && !completedMap.get("completedCols").contains(j)) {
                    completedMap.get("completedCols").add(j);
                    //sets coaIndicator to trigger ability selection
                    coaIndicator = true;
                }
            }
        }
    }

//          Public Methods
//===================================

    /**
     * Updates the score counter by reading the gameBoard and checking if rows and coloums are completed
     * @param gameBoard: Gameboard
     * @param completedMap: completedMap
     */
    public void addPoints(char[][] gameBoard, HashMap<String, List<Integer>> completedMap){
        doAddPoints(gameBoard,completedMap);
    }

    /**
     * returns the current coaIndicator value before resetting it to false
     * @author: Hunter
     */
    public boolean isCoaIndicator() {
        boolean returnValue = coaIndicator;
        coaIndicator = false;
        return returnValue;
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
