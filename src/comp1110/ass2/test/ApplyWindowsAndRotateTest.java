package comp1110.ass2.test;

import comp1110.ass2.Dices;
import comp1110.ass2.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ApplyWindowsAndRotateTest {

    Dices dice;
    Tile tile;

    /**
     * This method sets up the dice and tile objects before each test case.
     * It configures a preset dice set for use and prepares a selected set of dice indices.
     */
    @BeforeEach
    public void setup() {
        dice = new Dices();
        dice.applyPresetDiceD2CP1("R", "R", "R", "W", "P");
        tile = new Tile();
        List<Integer> setOfDice = Arrays.asList(0, 0, 0, 3, 4);
        dice.setSelectedDice(setOfDice);
    }

    /**
     * This test case evaluates the `applyWindows()` method under the Tile class.
     * It verifies if the `selectedTile` is correctly updated with the window configurations applied.
     * The test is successful if the resulting tile matches the expected output.
     */
    @Test
    public void applyWindowsTest() {
        tile.updateSelectedTile("R3");
        tile.applyWindows(new boolean[] {true, true, false});
        char[][] result = new char[][] {{' ', 'R'}, {'S', 'S'}};
        assertArrayEquals(result, tile.getSelectedTile());
    }

    /**
     * This test case evaluates the `rotateTile()` method under the Tile class.
     * It verifies if the `selectedTile` is correctly rotated based on the input rotation.
     * The test is successful if the rotated tile matches the expected output.
     */
    @Test
    public void rotateTileTest() {
        tile.updateSelectedTile("B3");
        tile.rotateTile(1);
        assertArrayEquals(new char[][] {{'B', ' '}, {'B', 'B'}}, tile.getSelectedTile());
    }

}
