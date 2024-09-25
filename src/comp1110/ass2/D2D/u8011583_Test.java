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
    Abilities abilitiesOne;
    Bonus bonusOne;
    GameState gameStateOne;

    Player playerTwo;
    Dices diceTwo;
    Tile tileTwo;
    Score scoreTwo;
    Abilities abilitiesTwo;
    Bonus bonusTwo;
    GameState gameStateTwo;


    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        playerOne = new Player();
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        abilitiesOne = new Abilities();
        bonusOne = new Bonus("Red", 2);
        gameStateOne = new GameState(playerOne, diceOne, tileOne, scoreOne, abilitiesOne, bonusOne);

//      Create Game Instance for Player 2
        playerTwo = new Player();
        diceTwo = new Dices();
        tileTwo = new Tile(diceTwo);
        scoreTwo = new Score();
        abilitiesTwo = new Abilities();
        bonusTwo = new Bonus("Blue", 2);
        gameStateTwo = new GameState(playerTwo, diceTwo, tileTwo, scoreTwo, abilitiesTwo, bonusTwo);

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
    public void generateTilesTest() {

    }
}
