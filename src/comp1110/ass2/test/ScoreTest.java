package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    GameState gameStateOne;

    @BeforeEach
    public void setup() {
//        Create game Instance for one player
        diceOne = new Dices();
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        gameStateOne = new GameState(diceOne, tileOne, scoreOne);
    }

    @Test
    public void CompleteRowNoWindows(){
//       This test case simulates applying a tile "P5" to fill a row without any windows.
//       It checks if the scoring is correctly computed for a completed row with no windows.
//       The expected score is 1 point for completing the row.
        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windows = {false, false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(0, 0, 0, windows);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(1, res);
    }

    @Test
    public void CompleteRowWithWindows(){
//       This test case simulates applying a tile "P5" to fill a row, but with all tiles having windows.
//       It checks if the score is correctly doubled when windows are present in all positions of a completed row.
//       The expected score is 2 points for completing the row with windows.
        diceOne.applyPresetDiceD2CP1("P","P","P","P","P");
        tileOne.updateSelectedTile("P5");
        boolean[] windows = {true, true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(0, 0, 0, windows);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(2, res);
    }

    @Test
    public void CompleteColoumWithWindows(){
//       This test case simulates filling a column with tiles of different colors, all having windows.
//       It tests if the score is correctly calculated for completing the column with windows.
//       The expected score is 4 points for completing the column with windows.
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);

        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(2, 0, 1, windowsTwo);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "P", "P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {true, true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(4, 0, 1, windowsThree);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(4, res);
    }

    @Test
    public void CompleteColoumWithNoWindows(){
//       This test case simulates filling a column with tiles of different colors, without any windows.
//       It checks if the score is correctly calculated when no windows are present in the completed column.
//       The expected score is 2 points for completing the column without windows.
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);

        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(2, 0, 1, windowsTwo);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "P", "P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {false, false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(4, 0, 1, windowsThree);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(2, res);
    }

    @Test
    public void CompleteColoumAndRow(){
//       This test case simulates completing both a row and a column with tiles that have no windows.
//       It checks if the scoring system works when both row and column are completed without windows.
//       The expected score is 3 points (2 for the column and 1 for the row).
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);

        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(2, 0, 1, windowsTwo);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "P", "P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {false, false, false, false, false};
        gameStateOne.placeTileWithRotationWindows(4, 0, 1, windowsThree);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "R", "P");
        tileOne.updateSelectedTile("P3");
        boolean[] windowsFour = {false, false, false};
        gameStateOne.placeTileWithRotationWindows(0, 2, 1, windowsFour);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(3, res);
    }

    @Test
    public void CompleteColoumAndRowWithWindows(){
//       This test case simulates completing both a row and a column with tiles that have windows.
//       It checks if the scoring system correctly handles the doubled score when both the row and column have windows.
//       The expected score is 6 points (4 for the column and 2 for the row).
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windows = {true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(0, 0, 1, windows);

        diceOne.applyPresetDiceD2CP1("R", "R", "R", "R", "P");
        tileOne.updateSelectedTile("R4");
        boolean[] windowsTwo = {true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(2, 0, 1, windowsTwo);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "P", "P");
        tileOne.updateSelectedTile("P5");
        boolean[] windowsThree = {true, true, true, true, true};
        gameStateOne.placeTileWithRotationWindows(4, 0, 1, windowsThree);

        diceOne.applyPresetDiceD2CP1("P", "P", "P", "R", "P");
        tileOne.updateSelectedTile("P3");
        boolean[] windowsFour = {true, true, true};
        gameStateOne.placeTileWithRotationWindows(0, 2, 1, windowsFour);

        scoreOne.addPoints(gameStateOne.getGameBoard());
        int res = scoreOne.getScore();
        assertEquals(6, res);
    }

}
