package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
This Test class should be the main submission, However, it is just stuck on run for some
reason even though it is the same thing as D2C except for variable naming changes. Therefore, we have left it here.
 */
public class PlaceTileTest {
    Score scoreOne;
    GameBoard gameBoardOne;
    GameState gameStateOne;

    Score scoreTwo;
    GameBoard gameBoardTwo;
    GameState gameStateTwo;

    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "B", "W"});

//      Create Game Instance for Player 2
        scoreTwo = new Score();
        gameStateTwo = new GameState(scoreTwo);
        gameBoardTwo = new GameBoard(gameStateTwo);

        //Apply Tiles to Player 1's Gameboard
        gameStateOne.updateSelectedTile("B3");
        boolean[] windows = {true, false, true};
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, windows);

        gameStateOne.updateSelectedTile("G4L");
        boolean[] windowsTwo = {true, false, true, true};
        gameBoardOne.placeTileWithRotationWindows( 0, 3, 0, windowsTwo);

    }

    @Test
    public void isTileY3PlacementValid1(){
        gameStateOne.updateSelectedTile("Y3");
        gameStateOne.rotateTile(0);
        boolean isValid = gameBoardOne.isTilePlacementValid(1,0);
        assertFalse(isValid);
    }

    @Test
    public void isTileY3PlacementValid2(){
        gameStateOne.updateSelectedTile("Y3");
        gameStateOne.rotateTile(3);
        boolean isValid = gameBoardOne.isTilePlacementValid(1,0);
        assertTrue(isValid);
    }

    @Test
    public void isTileY3PlacementValid3(){
        gameStateOne.updateSelectedTile("Y3");
        gameStateOne.rotateTile(3);
        boolean isValid = gameBoardOne.isTilePlacementValid(2,0);
        assertFalse(isValid);
    }

    @Test
    public void isTileY3PlacementValid4(){
        gameStateOne.updateSelectedTile("Y3");
        gameStateOne.rotateTile(1);
        boolean isValid = gameBoardOne.isTilePlacementValid(2,0);
        assertTrue(isValid);
    }

    @Test
    public void isTileY3PlacementValid5(){
        gameStateOne.updateSelectedTile("Y3");
        gameStateOne.rotateTile(3);
        boolean isValid = gameBoardOne.isTilePlacementValid(1,1);
        assertFalse(isValid);
    }

}
