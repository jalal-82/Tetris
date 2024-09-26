package comp1110.ass2.D2D;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class u8011583_Test {
    Player playerOne;
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    GameState gameStateOne;

    Dices diceTwo;


    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        playerOne = new Player();
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        gameStateOne = new GameState(playerOne, diceOne, tileOne, scoreOne);
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

}
