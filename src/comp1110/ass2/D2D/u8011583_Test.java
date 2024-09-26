package comp1110.ass2.D2D;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class u8011583_Test {
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    Abilities abilitiesOne;
    GameState gameStateOne;

    Dices diceTwo;


    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        abilitiesOne = new Abilities();
        gameStateOne = new GameState(diceOne, tileOne, scoreOne, abilitiesOne);
        diceTwo = new Dices();
//
    }
    //tests the ability for the setter method to override existing value
    @Test
    public void updateSelectedTest() {
        tileOne.updateSelectedTile("R3");
        tileOne.updateSelectedTile("R4");
        assertArrayEquals(tileOne.getSelectedTile(), tileOne.getAllTiles().get("R4").get(0));

    }
    //tests same method after changing the tile object.
    @Test
    public void updateSelectedTest2() {
        tileOne.updateSelectedTile("R3");
        tileOne = new Tile(diceTwo);
        tileOne.updateSelectedTile("R4");
        assertArrayEquals(tileOne.getSelectedTile(), tileOne.getAllTiles().get("R4").get(0));
    }

    @Test
    public void trackTest() {
        assertEquals(abilitiesOne.getTrack("Red"), 0);
        abilitiesOne.addTrack("Red");
        assertEquals(abilitiesOne.getTrack("Red"), 1);
        for (int i = 0; i < 11; i++) {
            abilitiesOne.addTrack("Red");
        }
        assertEquals(abilitiesOne.getTrack("Red"), 9);
    }

    @Test
    public void hasAbilityTest() {
        assertFalse(abilitiesOne.hasAbility("Blue"));
        for (int i = 0; i < 3; i++) {
            abilitiesOne.addTrack("Blue");

        }
        assertTrue(abilitiesOne.hasAbility("Blue"));
    }
}
