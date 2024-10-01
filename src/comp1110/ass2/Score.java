package comp1110.ass2;

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
