package comp1110.ass2.D2D;

import comp1110.ass2.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class u6681480_Test {

    Dices dice;
    Tile tile;

    @BeforeEach
    public void setup() {
        dice = new Dices();
        dice.applyPresetDiceD2CP1("R", "R", "R", "W", "P");
        tile = new Tile(dice);
    }

    // This test case evaluate the generateTiles() method under Tile class.
    // It succeeds because the method correctly generate tiles based on
    // the given dice.
    @Test
    public void generateTilesTest() {
        String[] current = tile.generateTiles(dice);
        String[] result = {"R2","R3","R4","P2"};
        assertArrayEquals(result, current);
    }

    // This test case evaluate the generateTiles() method again,
    // but on a different given dice.
    @Test
    public void generateTilesTest2() {
        dice.applyPresetDiceD2CP1("B", "B", "Y", "G", "Y");
        String[] current = tile.generateTiles(dice);
        String[] result = {"B2","Y2","B2","G2"};
        assertArrayEquals(result, current);
    }

    // This test case evaluate the isValidSelection() method under Tile class.
    // Given dice: ("R", "R", "R", "W", "P").
    // It succeeds because the method correctly identify the given tile
    // is vaild.
    @Test
    public void isValidSelectionTest() {
        assertTrue(tile.isValidSelection("R3"));
    }

    // This test case evaluate the isValidSelection() method under Tile class.
    // Given dice: ("R", "R", "R", "W", "P").
    // It succeeds because the method correctly identifies the given tile
    // is invaild.
    @Test
    public void isValidSelectionTest2() {
        assertFalse(tile.isValidSelection("Y2"));
    }

    // This test case evaluate the updateSelectedTile() method under Tile class.
    // It checks the value of variable selectedTileKey and selectedTile after
    // calling the method.
    // It succeeds because the method correctly updates the tile B2.
    @Test
    public void updateSelectedTileTest() {
        tile.updateSelectedTile("B2");
        assertAll(
            () -> assertEquals("B2", tile.getSelectedTileKey()),
            () -> assertArrayEquals(new char[][] { {'B'}, {'B'} }, tile.getSelectedTile())
        );
    }

    // This test case evaluate the applyWindows() method under Tile class.
    // It checks the value of variable selectedTile after calling the
    // method.
    // It succeeds because the method correctly updates the selectedTile.
    @Test
    public void applyWindowsTest() {
        tile.updateSelectedTile("R3");
        tile.applyWindows(new boolean[] {true,true,false});
        char[][] result = new char[][] {{' ', 'S'}, {'S', 'R'}};
        assertArrayEquals(result, tile.getSelectedTile());
    }

    // This test case evaluate the rotateTile() method under Tile class.
    // It checks the value of variable selectedTile after calling the
    // method.
    // It succeeds because the method correctly rotates the selectedTile.
    @Test
    public void rotateTileTest() {
        tile.updateSelectedTile("B3");
        tile.rotateTile(1);
        assertArrayEquals(new char[][] { {'B', ' '}, {'B', 'B'} }, tile.getSelectedTile());
    }
}
