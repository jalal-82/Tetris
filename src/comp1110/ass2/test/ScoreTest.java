package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    Score scoreOne;
    GameState gameStateOne;
    GameBoard gameBoardOne;

    // Setup method to initialize the game state before each test
    @BeforeEach
    public void setup() {
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
    }

    // Test for scoring a complete row with no windows
    @Test
    public void CompleteRowNoWindows() {
        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        boolean[] windows = {false, false, false, false, false};
        gameBoardOne.placeTileWithRotationWindows(0, 0, 0, windows);

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(1, scoreOne.getScore());
    }

    // Test for scoring a complete row with windows
    @Test
    public void CompleteRowWithWindows() {
        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        boolean[] windows = {true, true, true, true, true};
        gameBoardOne.placeTileWithRotationWindows(0, 0, 0, windows);

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(2, scoreOne.getScore());
    }

    // Test for completing a column with windows
    @Test
    public void CompleteColoumWithWindows() {
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, new boolean[]{true, true, true, true});

        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(2, 0, 1, new boolean[]{true, true, true, true});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        gameBoardOne.placeTileWithRotationWindows(4, 0, 1, new boolean[]{true, true, true, true, true});

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(4, scoreOne.getScore());
    }

    // Test for completing a column with no windows
    @Test
    public void CompleteColoumWithNoWindows() {
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, new boolean[]{false, false, false, false});

        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(2, 0, 1, new boolean[]{false, false, false, false});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        gameBoardOne.placeTileWithRotationWindows(4, 0, 1, new boolean[]{false, false, false, false, false});

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(2, scoreOne.getScore());
    }

    // Test for completing both a column and a row with no windows
    @Test
    public void CompleteColoumAndRow() {
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, new boolean[]{false, false, false, false});

        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(2, 0, 1, new boolean[]{false, false, false, false});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        gameBoardOne.placeTileWithRotationWindows(4, 0, 1, new boolean[]{false, false, false, false, false});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P3");
        gameBoardOne.placeTileWithRotationWindows(0, 2, 1, new boolean[]{false, false, false});

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(3, scoreOne.getScore());
    }

    // Test for completing both a column and a row with windows
    @Test
    public void CompleteColoumAndRowWithWindows() {
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(0, 0, 1, new boolean[]{true, true, true, true});

        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "R", "P"});
        gameStateOne.updateSelectedTile("R4");
        gameBoardOne.placeTileWithRotationWindows(2, 0, 1, new boolean[]{true, true, true, true});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P5");
        gameBoardOne.placeTileWithRotationWindows(4, 0, 1, new boolean[]{true, true, true, true, true});

        gameStateOne.setAvailableDice(new String[]{"P","P","P","P","P"});
        gameStateOne.updateSelectedTile("P3");
        gameBoardOne.placeTileWithRotationWindows(0, 2, 1, new boolean[]{true, true, true});

        scoreOne.addPoints(gameBoardOne.getGameBoard(), new HashMap<>());
        assertEquals(6, scoreOne.getScore());
    }
}
