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

    @BeforeEach
    public void setup() {
        dice = new Dices();
        dice.applyPresetDiceD2CP1("R", "R", "R", "W", "P");
        tile = new Tile(dice);
        List<Integer> setOfDice =  Arrays.asList(0,0,0,3,4);
        dice.setSelectedDice(setOfDice);
    }

    // This test case evaluate the applyWindows() method under Tile class.
    // It checks the value of variable selectedTile after calling the
    // method.
    // It succeeds because the method correctly updates the selectedTile.
    @Test
    public void applyWindowsTest() {
        tile.updateSelectedTile("R3");
        tile.applyWindows(new boolean[] {true,true,false});
        char[][] result = new char[][] {{' ', 'R'}, {'S', 'S'}};
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
