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

    @BeforeEach
    public void setup() {
        dice = new Dices();
        dice.applyPresetDiceD2CP1("R", "R", "R", "W", "P");
        tile = new Tile(dice);
        List<Integer> setOfDice =  Arrays.asList(0,0,0,3,4);
        dice.setSelectedDice(setOfDice);
    }

    // This test case evaluate the generateTiles() method under Tile class.
    // It succeeds because the method correctly generate tiles based on
    // the given dice.
    @Test
    public void generateTilesTest() {
        String[] current = tile.generateTiles(dice);
        System.out.println(Arrays.toString(current));
        String[] result = {"R2","R3","P2","R4"};
        assertArrayEquals(result, current);
    }

    // This test case evaluate the generateTiles() method again,
    // but on a different given dice.
    @Test
    public void generateTilesTest2() {
        dice.applyPresetDiceD2CP1("B", "B", "Y", "G", "Y");
        String[] current = tile.generateTiles(dice);
        String[] result = {"B2","Y2"};
        assertArrayEquals(result, current);
    }

    @Test
    public void generateTilesTest3() {
        dice.applyPresetDiceD2CP1("G", "G", "G", "G", "Y");
        String[] current = tile.generateTiles(dice);
        String[] result = {"G4R","G2","G3","G4L"};
        assertArrayEquals(result, current);
    }

    // This test case evaluate the isValidSelection() method under Tile class.
    // Given dice: ("R", "R", "R", "W", "P").
    // It succeeds because the method correctly identify the given tile is vaild.
    @Test
    public void isValidSelectionTest() {
        assertTrue(tile.isValidSelection(dice.getSelectedDice(),"R3"));
    }
    @Test
    public void isValidSelectionTest2() {
        assertTrue(tile.isValidSelection(dice.getSelectedDice(),"R4"));
    }

    // This test case evaluate the isValidSelection() method under Tile class.
    // Given dice: ("R", "R", "R", "W", "P").
    // It succeeds because the method correctly identifies the given tile is invaild.
    @Test
    public void isValidSelectionTest3() {
        assertFalse(tile.isValidSelection(dice.getSelectedDice(),"Y2"));
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
}
