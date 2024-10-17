package comp1110.ass2.test;

import comp1110.ass2.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateAndSelectTilesTest {

    Dices dice;
    Tile tile;

    /**
     * Sets up the test environment by initializing the Dices and Tile objects.
     * Applies a preset dice configuration and selects a set of dice for testing.
     */
    @BeforeEach
    public void setup() {
        dice = new Dices();
        dice.applyPresetDiceD2CP1("R", "R", "R", "W", "P");
        tile = new Tile();
        List<Integer> selectedDiceIndices = Arrays.asList(0, 0, 0, 3, 4); // Selecting specific dice indices
        dice.setSelectedDice(selectedDiceIndices);
    }

    /**
     * Tests the generateTiles() method with a specific set of dice.
     * Verifies that the tiles generated based on the given dice match the expected output.
     */
    @Test
    public void testGenerateTilesWithBlueAndYellow() {
        dice.applyPresetDiceD2CP1("B", "B", "Y", "G", "Y"); // Set new dice configuration
        String[] generatedTiles = tile.generateTiles(dice, new int[]{0, 0, 0, 0, 0});
        String[] expectedTiles = {"B2", "Y2"}; // Expected tile generation result
        assertArrayEquals(expectedTiles, generatedTiles);
    }

    /**
     * Tests the generateTiles() method with a different set of dice.
     * Verifies that the tiles generated based on the given dice match the expected output.
     */
    @Test
    public void testGenerateTilesWithGreenAndYellow() {
        dice.applyPresetDiceD2CP1("G", "G", "G", "G", "Y"); // Set new dice configuration
        String[] generatedTiles = tile.generateTiles(dice, new int[]{0, 0, 0, 0, 0});
        String[] expectedTiles = {"G4R", "G2", "G3", "G4L"}; // Expected tile generation result
        assertArrayEquals(expectedTiles, generatedTiles);
    }

    /**
     * Tests the isValidSelection() method with a valid tile selection.
     * Verifies that the selection of tile "R3" is valid based on the given dice.
     */
    @Test
    public void testValidSelectionR3() {
        assertTrue(tile.isValidSelection(dice.getSelectedDice(), "R3"));
    }

    /**
     * Tests the isValidSelection() method with another valid tile selection.
     * Verifies that the selection of tile "R4" is valid based on the given dice.
     */
    @Test
    public void testValidSelectionR4() {
        assertTrue(tile.isValidSelection(dice.getSelectedDice(), "R4"));
    }

    /**
     * Tests the isValidSelection() method with an invalid tile selection.
     * Verifies that the selection of tile "Y2" is invalid based on the given dice.
     */
    @Test
    public void testInvalidSelectionY2() {
        assertFalse(tile.isValidSelection(dice.getSelectedDice(), "Y2"));
    }

    /**
     * Tests the updateSelectedTile() method to ensure the tile updates correctly.
     * Verifies that the selected tile key and selected tile grid are properly updated after calling the method.
     */
    @Test
    public void testUpdateSelectedTileToB2() {
        tile.updateSelectedTile("B2"); // Update to tile "B2"
        assertAll(
                () -> assertEquals("B2", tile.getSelectedTileKey()), // Check if the selected tile key is updated correctly
                () -> assertArrayEquals(new char[][] { {'B'}, {'B'} }, tile.getSelectedTile()) // Verify the selected tile grid
        );
    }
}
