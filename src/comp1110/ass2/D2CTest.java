package comp1110.ass2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class D2CTest {
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    Abilities abilitiesOne;
    GameState gameStateOne;

    Dices dicesTwo;
    Tile tileTwo;
    Score scoreTwo;
    Abilities abilitiesTwo;
    GameState gameStateTwo;


    @BeforeEach
    public void setup(){

//      Create game Instance for Player 1
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        abilitiesOne = new Abilities();
        gameStateOne = new GameState(diceOne, tileOne, scoreOne, abilitiesOne);

//      Create Game Instance for Player 2
        dicesTwo = new Dices();
        tileTwo = new Tile(dicesTwo);
        scoreTwo = new Score();
        abilitiesTwo = new Abilities();
        gameStateTwo = new GameState(dicesTwo, tileTwo, scoreTwo, abilitiesTwo);

//      Apply Tiles to Player 1's Gameboard
//      B3 (rotataion = 1 (90 degrees))
        tileOne.updateSelectedTile("B3");
        boolean[] windows = {true, false, true};
        gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);


//      G4L (No rotation)
        tileOne.updateSelectedTile("G4L");
        boolean[] windowsTwo = {true, false, true, true};
        gameStateOne.placeTileWithRotationWindows( 3, 0, 0, windowsTwo);

    }

    @Test
    public void selectR3(){
        boolean res = tileOne.isValidSelection("R3");
        assertEquals(res,true);
    }

    @Test
    public void selectR4(){
        boolean res = tileOne.isValidSelection("R4");
        assertEquals(res,true);
    }

    @Test
    public void selectB3(){
        boolean res = tileOne.isValidSelection("B3");
        assertEquals(res,false);
    }

//    For Tile Y3, windows aren't applied as it doesn't affect placement Validation.
    @Test
    public void isTileY3PlacementValid1(){
        tileOne.updateSelectedTile("Y3");
        tileOne.rotateTile( 0);
        boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,0);
        assertEquals(isValid,false);

    }

    @Test
    public void isTileY3PlacementValid2(){
        tileOne.updateSelectedTile("Y3");
        tileOne.rotateTile( 3);
        boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,0);

        assertEquals(isValid,true);
    }

    @Test
    public void isTileY3PlacementValid3(){
        tileOne.updateSelectedTile("Y3");
        tileOne.rotateTile( 3);
        boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),2,0);
        assertEquals(isValid,false);
    }

    @Test
    public void isTileY3PlacementValid4(){
        tileOne.updateSelectedTile("Y3");
        tileOne.rotateTile( 1);
        tileOne.printTile("Y3");
        boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),2,0);

        assertEquals(isValid,true);
    }

    @Test
    public void isTileY3PlacementValid5(){
        tileOne.updateSelectedTile("Y3");
        tileOne.rotateTile( 3);
        boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,1);

        assertEquals(isValid,false);
    }
}
