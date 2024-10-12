package comp1110.ass2.test;

import comp1110.ass2.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoatOfArmsTest {
    Score scoreOne;
    GameState gameStateOne;
    GameBoard gameBoardOne;

    @BeforeEach
    public void setup() {
//        Create game Instance for one player
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
    }

    @Test
    public void testRow(){
        for (int i = 0; i < 15; i++) {
            gameStateOne.updateSelectedTile("B2");
            boolean[] windows = {false, false};
            int row = (i / 5) * 2;
            int col = i % 5;
            gameBoardOne.placeTileWithRotationWindows(row, col, 0, windows);
        }
        HashMap<String, List<Integer>> completedMap = new HashMap<>();
        scoreOne.addPoints(gameBoardOne.getGameBoard(), completedMap);

        List<Integer> res = new ArrayList<>();
        res.add(5);
        res.add(3);
        res.add(1);

        List<Integer> res2 = completedMap.get("completedRows");

        assertEquals(res,res2);
        System.out.println(completedMap);
    }

    @Test
    public void testColoms(){
        int[] rows = {0, 3, 5, 7}; // Rows that repeat
        int[] columns = {1, 3};    // Columns that repeat

        for (int col : columns) {
            for (int i = 0; i < rows.length; i++) {
                if (i == 0) { // First two rows get P3, others get P2
                    gameStateOne.updateSelectedTile("P3");
                    gameBoardOne.placeTileWithRotationWindows(rows[i], col, 0, new boolean[] {false, false, false});
                } else {
                    gameStateOne.updateSelectedTile("P2");
                    gameBoardOne.placeTileWithRotationWindows(rows[i], col, 0, new boolean[] {false, false});
                }

            }
        }

        HashMap<String, List<Integer>> completedMap = new HashMap<>();
        scoreOne.addPoints(gameBoardOne.getGameBoard(),completedMap);

        List<Integer> res = new ArrayList<>();
        res.add(1);
        res.add(3);

        List<Integer> res2 = completedMap.get("completedCols");

        assertEquals(res,res2);
    }

}
