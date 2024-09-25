package comp1110.ass2.D2D;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class u7480662_Test {

    Player playerOne;
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    Abilities abilitiesOne;
    Bonus bonusOne;
    GameState gameStateOne;

    @BeforeEach
    public void setup() {
//      Create game Instance for Player 1
        playerOne = new Player();
        diceOne = new Dices();
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        abilitiesOne = new Abilities();
        bonusOne = new Bonus("Red", 2);
        gameStateOne = new GameState(playerOne, diceOne, tileOne, scoreOne, abilitiesOne, bonusOne);
    }

    @Test
    public void CompleteRowNoWindows(){
//       Apply tile P5 with no windowsto fill a row
        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windows = {false,false,false,false,false};
        gameStateOne.placeTileWithRotationWindows(0,0,0,windows);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(1,res);
    }

    @Test
    public void CompleteRowWithWindows(){
//       Apply tile P5 with windows to fill a row
        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windows = {true,true,true,true,true};
        gameStateOne.placeTileWithRotationWindows(0,0,0,windows);


        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(2,res);
    }

    @Test
    public void CompleteColoumWithWindows(){
//       Fill a coloum of windows of different colours
        diceOne.applyPresetDiceD2CP1("R","R","R","R","P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {true,true,true,true};
        gameStateOne.placeTileWithRotationWindows(0,0,1,windows);

        diceOne.applyPresetDiceD2CP1("R","R","R","R","P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {true,true,true,true};
        gameStateOne.placeTileWithRotationWindows(0,2,1,windowsTwo);

        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {true,true,true,true,true};
        gameStateOne.placeTileWithRotationWindows(0,4,1,windowsThree);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(4,res);
    }

    @Test
    public void CompleteColoumWithNoWindows(){
//       Fill a coloum of windows of different colours
        diceOne.applyPresetDiceD2CP1("R","R","R","R","P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {false,false,false,false};
        gameStateOne.placeTileWithRotationWindows(0,0,1,windows);

        diceOne.applyPresetDiceD2CP1("R","R","R","R","P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {false,false,false,false};
        gameStateOne.placeTileWithRotationWindows(0,2,1,windowsTwo);

        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {false,false,false,false,false};
        gameStateOne.placeTileWithRotationWindows(0,4,1,windowsThree);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(2,res);
    }

}
