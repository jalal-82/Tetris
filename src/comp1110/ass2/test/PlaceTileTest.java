package comp1110.ass2.test;

import comp1110.ass2.Dices;
import comp1110.ass2.GameState;
import comp1110.ass2.Score;
import comp1110.ass2.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/*
This Test class should be the main submission, However, it is just stuck on run for some
reason even though it is the same thing as D2C except for variable naming changes. Therefore, we have left it here.
 */
public class PlaceTileTest {
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    GameState gameStateOne;

    Dices dicesTwo;
    Tile tileTwo;
    Score scoreTwo;
    GameState gameStateTwo;

    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        gameStateOne = new GameState(diceOne, tileOne, scoreOne);

//      Create Game Instance for Player 2
        dicesTwo = new Dices();
        tileTwo = new Tile(dicesTwo);
        scoreTwo = new Score();
        gameStateTwo = new GameState(dicesTwo, tileTwo, scoreTwo);

        //      Apply Tiles to Player 1's Gameboard
        if (tileOne.getAllTiles().containsKey("B3")) {
            tileOne.updateSelectedTile("B3");
            boolean[] windows = {true, false, true};
            gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);
        }
        if (tileOne.getAllTiles().containsKey("G4L")) {
            tileOne.updateSelectedTile("G4L");
            boolean[] windows = {true, false, true, true};
            gameStateOne.placeTileWithRotationWindows( 0, 3, 0, windows);
        }
    }

//    @Test
//    public void selectR3(){
//        boolean res = tileOne.isValidSelection("R3");
//        assertEquals(res,true);
//    }

//    @Test
//    public void selectR4(){
//        boolean res = tileOne.isValidSelection("R4");
//        assertEquals(res,true);
//    }
//
//    @Test
//    public void selectB3(){
//        boolean res = tileOne.isValidSelection("B3");
//        assertEquals(res,false);
//    }

    @Test
    public void isTileY3PlacementValid1(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.updateSelectedTile("Y3");
            tileOne.rotateTile( 0);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,0);
            assertFalse(isValid);
        }
    }

    @Test
    public void isTileY3PlacementValid2(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.updateSelectedTile("Y3");
            tileOne.rotateTile( 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,0);
            assertEquals(isValid,true);
        }
    }

    @Test
    public void isTileY3PlacementValid3(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.updateSelectedTile("Y3");
            tileOne.rotateTile( 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),2,0);
            assertEquals(isValid,false);
        }
    }

    @Test
    public void isTileY3PlacementValid4(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.updateSelectedTile("Y3");
            tileOne.rotateTile( 1);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),2,0);
            assertEquals(isValid,true);
        }
    }

    @Test
    public void isTileY3PlacementValid5(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.updateSelectedTile("Y3");
            tileOne.rotateTile( 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),1,1);
            assertEquals(isValid,false);
        }
    }

}
