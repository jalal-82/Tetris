package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoatOfArmsTest {
    Score scoreOne;
    GameState gameStateOne;
    GameBoard gameBoardOne;

    /**
     * Setup method executed before each test case.
     * Initializes the game state, game board, and score for a single player.
     */
    @BeforeEach
    public void setup() {
        // Create a game instance for one player
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
    }

    /**
     * Test for evaluating row completion logic.
     * Places tiles on the board to fill up specific rows and checks if the score
     * is correctly calculated for completed rows. The test verifies if the correct
     * rows are marked as completed.
     */
    @Test
    public void testRow() {
        for (int i = 0; i < 15; i++) {
            gameStateOne.updateSelectedTile("B2");
            boolean[] windows = {false, false};
            int row = (i / 5) * 2; // Calculate row placement based on index
            int col = i % 5; // Calculate column placement based on index
            gameBoardOne.placeTileWithRotationWindows(row, col, 0, windows);
        }

        HashMap<String, List<Integer>> completedRowsMap = new HashMap<>();
        scoreOne.addPoints(gameBoardOne.getGameBoard(), completedRowsMap);

        List<Integer> expectedCompletedRows = new ArrayList<>();
        expectedCompletedRows.add(5); // Expected completed rows: 5, 3, 1
        expectedCompletedRows.add(3);
        expectedCompletedRows.add(1);

        List<Integer> actualCompletedRows = completedRowsMap.get("completedRows");

        assertEquals(expectedCompletedRows, actualCompletedRows);
    }

    /**
     * Test for evaluating column completion logic.
     * Places tiles in specific columns, filling them up across multiple rows,
     * and checks if the score is correctly calculated for completed columns.
     * The test verifies if the correct columns are marked as completed.
     */
    @Test
    public void testColumns() {
        int[] rows = {0, 3, 5, 7}; // Rows where tiles are placed (repeated)
        int[] columns = {1, 3};    // Columns where tiles are placed (repeated)

        for (int col : columns) {
            for (int i = 0; i < rows.length; i++) {
                if (i == 0) { // For the first row, place P3 tiles
                    gameStateOne.updateSelectedTile("P3");
                    gameBoardOne.placeTileWithRotationWindows(rows[i], col, 0, new boolean[] {false, false, false});
                } else { // For the other rows, place P2 tiles
                    gameStateOne.updateSelectedTile("P2");
                    gameBoardOne.placeTileWithRotationWindows(rows[i], col, 0, new boolean[] {false, false});
                }
            }
        }

        HashMap<String, List<Integer>> completedColumnsMap = new HashMap<>();
        scoreOne.addPoints(gameBoardOne.getGameBoard(), completedColumnsMap);

        List<Integer> expectedCompletedColumns = new ArrayList<>();
        expectedCompletedColumns.add(1); // Expected completed columns: 1, 3
        expectedCompletedColumns.add(3);

        List<Integer> actualCompletedColumns = completedColumnsMap.get("completedCols");

        assertEquals(expectedCompletedColumns, actualCompletedColumns);
    }

}
