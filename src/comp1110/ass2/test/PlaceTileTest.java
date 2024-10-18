package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class verifies the logic of placing tiles on the game board
 * and checks if tile placements are valid in various scenarios.
 */
public class PlaceTileTest {
    Score scoreOne;
    GameBoard gameBoardOne;
    GameState gameStateOne;

    Score scoreTwo;
    GameBoard gameBoardTwo;
    GameState gameStateTwo;

    /**
     * Setup method executed before each test case.
     * Initializes game instances for two players and sets up the game boards for testing.
     * Applies some initial tile placements for Player 1.
     */
    @BeforeEach
    public void setup() {
        // Create game instance for Player 1
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "B", "W"}); // Set available dice for Player 1

        // Create game instance for Player 2
        scoreTwo = new Score();
        gameStateTwo = new GameState(scoreTwo);
        gameBoardTwo = new GameBoard(gameStateTwo);

        // Apply initial tiles to Player 1's game board
        gameStateOne.updateSelectedTile("B3");
        boolean[] windowsForFirstTile = {true, false, true};
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, windowsForFirstTile);

        gameStateOne.updateSelectedTile("G4L");
        boolean[] windowsForSecondTile = {true, false, true, true};
        gameBoardOne.placeTileWithRotationWindows(0, 3, 0, windowsForSecondTile);
    }

    /**
     * Tests the placement validity of tile "Y3" in a specific scenario.
     * Verifies that placement is invalid when rotated 0 times and placed at (1,0).
     */
    @Test
    public void testTileY3PlacementInvalidAt_1_0_NoRotation() {
        gameStateOne.updateSelectedTile("Y3");

        boolean isValidPlacement = gameBoardOne.isTilePlacementValid(1, 0, 0);
        assertFalse(isValidPlacement);
    }

    /**
     * Tests the placement validity of tile "Y3" in a specific scenario.
     * Verifies that placement is valid when rotated 3 times and placed at (1,0).
     */
    @Test
    public void testTileY3PlacementValidAt_1_0_Rotate3Times() {
        gameStateOne.updateSelectedTile("Y3"); // Select tile "Y3
        boolean isValidPlacement = gameBoardOne.isTilePlacementValid(1, 0, 3);
        assertTrue(isValidPlacement);
    }

    /**
     * Tests the placement validity of tile "Y3" in a specific scenario.
     * Verifies that placement is invalid when rotated 3 times and placed at (2,0).
     */
    @Test
    public void testTileY3PlacementInvalidAt_2_0_Rotate3Times() {
        gameStateOne.updateSelectedTile("Y3");
        boolean isValidPlacement = gameBoardOne.isTilePlacementValid(2, 0, 3);
        assertFalse(isValidPlacement);
    }

    /**
     * Tests the placement validity of tile "Y3" in a specific scenario.
     * Verifies that placement is valid when rotated 1 time and placed at (2,0).
     */
    @Test
    public void testTileY3PlacementValidAt_2_0_Rotate1Time() {
        gameStateOne.updateSelectedTile("Y3");
        boolean isValidPlacement = gameBoardOne.isTilePlacementValid(2, 0, 1);
        assertTrue(isValidPlacement);
    }

    /**
     * Tests the placement validity of tile "Y3" in a specific scenario.
     * Verifies that placement is invalid when rotated 3 times and placed at (1,1).
     */
    @Test
    public void testTileY3PlacementInvalidAt_1_1_Rotate3Times() {
        gameStateOne.updateSelectedTile("Y3");
        boolean isValidPlacement = gameBoardOne.isTilePlacementValid(1, 1, 3);
        assertFalse(isValidPlacement);
    }
}
