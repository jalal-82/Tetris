package comp1110.ass2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
This Test class should be the main submission, However, it is just stuck on run for some
reason even though it is the same thing as D2C except for variable naming changes. Therefore, we have left it here.
 */
public class D2CTest2 {
    Player playerOne;
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    Abilities abilitiesOne;
    Bonus bonusOne;
    GameState gameStateOne;

    Player playerTwo;
    Dices dicesTwo;
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
        dicesTwo = new Dices();
        tileTwo = new Tile(dicesTwo);
        scoreTwo = new Score();
        abilitiesTwo = new Abilities();
        bonusTwo = new Bonus("Blue", 2);
        gameStateTwo = new GameState(playerTwo, dicesTwo, tileTwo, scoreTwo, abilitiesTwo, bonusTwo);

        //      Apply Tiles to Player 1's Gameboard
        if (tileOne.getAllTiles().containsKey("B3")) {
            boolean[] windows = {true, false, true};
            gameStateOne.placeTileWithRotationWindows("B3", 0, 0, 1, windows);
        }
        if (tileOne.getAllTiles().containsKey("G4L")) {
            boolean[] windows = {true, false, true, true};
            gameStateOne.placeTileWithRotationWindows("G4L", 3, 0, 0, windows);
        }
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

    @Test
    public void isTileY3PlacementValid1(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.rotateTile("Y3", 0);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),"Y3",1,0);
            assertEquals(isValid,false);
        }
    }

    @Test
    public void isTileY3PlacementValid2(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.rotateTile("Y3", 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),"Y3",1,0);
            assertEquals(isValid,true);
        }
    }

    @Test
    public void isTileY3PlacementValid3(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.rotateTile("Y3", 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),"Y3",2,0);
            assertEquals(isValid,false);
        }
    }

    @Test
    public void isTileY3PlacementValid4(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.rotateTile("Y3", 1);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),"Y3",2,0);
            assertEquals(isValid,true);
        }
    }

    @Test
    public void isTileY3PlacementValid5(){
        if (tileOne.getAllTiles().containsKey("Y3")) {
            tileOne.rotateTile("Y3", 3);
            boolean isValid = gameStateOne.isTilePlacementValid(gameStateOne.getGameBoard(),"Y3",1,1);
            assertEquals(isValid,false);
        }
    }

}
