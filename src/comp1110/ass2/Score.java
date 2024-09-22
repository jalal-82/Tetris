package comp1110.ass2;

public class Score {

    private int score;

    /**
     * Constructs a new Score with an initial score of 0.
     */
    public Score() {
        this.score = 0;
    }

    /**
     * based on gameBoard, it will update score
     *
     * Completing a row of their building with windows in all squares of
     * the row: 2 points.
     * Completing a row of their building, but not with windows in all
     * squares of the row: 1 point.
     * Completing a column of their building with windows in all squares of
     * the column: 4 points.
     * Completing a column of their building, but not with windows in all
     * squares of the column: 2 points.
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

    // Helper function to check if a row is complete (i.e., no '.')
    private boolean isCompleteRow(char[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            if (board[row][j] == '.') {
                return false;  // Found an empty space
            }
        }
        return true;
    }

    // Helper function to check if a row contains only windows
    private boolean isAllWindowsRow(char[][] board, int row) {
        for (int j = 0; j < board[row].length; j++) {
            if (!isWindow(board[row][j])) {
                return false;  // Found a non-window tile
            }
        }
        return true;
    }

    // Helper function to check if a column is complete (i.e., no '.')
    private boolean isCompleteColumn(char[][] board, int col) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == '.') {
                return false;  // Found an empty space
            }
        }
        return true;
    }

    // Helper function to check if a column contains only windows
    private boolean isAllWindowsColumn(char[][] board, int col) {
        for (int i = 0; i < board.length; i++) {
            if (!isWindow(board[i][col])) {
                return false;  // Found a non-window tile
            }
        }
        return true;
    }

    // Helper function to check if a tile is a window
    private boolean isWindow(char tile) {
        return tile == 'S' || tile == 'C' || tile == 'Q' || tile == 'H' || tile == 'Z';
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }
}