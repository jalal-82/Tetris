package comp1110.ass2.test;

import comp1110.ass2.Dices;
import comp1110.ass2.GameBoard;
import comp1110.ass2.GameState;
import comp1110.ass2.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackTest {

    Score scoreOne;
    GameState gameStateOne;
    GameBoard gameBoardOne;
    Dices diceTwo;


    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        scoreOne = new Score();
        gameStateOne = new GameState(scoreOne);
        gameBoardOne = new GameBoard(gameStateOne);
        gameStateOne.setAvailableDice(new String[]{"R", "R", "R", "B", "W"});

        diceTwo = new Dices();
//
    }
    //tests the ability for the setter method to override existing value
    @Test
    public void updateSelectedTest() {
        gameStateOne.updateSelectedTile("R3");
        gameStateOne.updateSelectedTile("R4");
        assertArrayEquals(gameStateOne.getSelectedTile(), gameStateOne.getAllTiles().get("R4").get(0));
    }

    //tests Track methods
    @Test
    public void updateRedTrackTest() {
        assertEquals(0, gameStateOne.getRedTrack().getTrack());
        gameStateOne.getRedTrack().addTrack();
        gameStateOne.getRedTrack().addTrack();
        gameStateOne.getRedTrack().addTrack();
        List<String> diceUsed = new ArrayList<>();
        diceUsed.add("R");
        diceUsed.add("R");
        //test abilities and bonuses are updated correctly
        assertEquals(1, gameStateOne.getRedTrack().getAbility());
        gameStateOne.getRedTrack().updateAbility();
        assertEquals(2, gameStateOne.getRedTrack().getBonus());
        assertEquals(0, gameStateOne.getRedTrack().getAbility());//ensures useAbility reduces ability count
        gameStateOne.getRedTrack().updateBonus(diceUsed, "R3");
        assertEquals(1, gameStateOne.getRedTrack().getBonus()); //ensures useBonus reduces bonus count
        gameStateOne.getRedTrack().updateBonus(diceUsed, "R5");
        assertEquals(-2, gameStateOne.getRedTrack().getBonus()); //ensures useBonus reduces bonus count by the correct amount
        gameStateOne.getRedTrack().updateAbility();
        assertEquals(0, gameStateOne.getRedTrack().getAbility()); //tests ability cant be negative
    }
    @Test
    //Test blue updates according to specifications
    public void updateBlueTrackTest() {
        assertEquals(0, gameStateOne.getBlueTrack().getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.getBlueTrack().addTrack();
        }
        assertEquals(3, gameStateOne.getBlueTrack().getAbility());
        assertEquals(2, gameStateOne.getBlueTrack().getBonus());
    }
    @Test
    //tests green updates according to specifications
    public void updateGreenTrackTest() {
        assertEquals(0, gameStateOne.getGreenTrack().getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.getGreenTrack().addTrack();
        }
        assertEquals(1, gameStateOne.getGreenTrack().getAbility());
        assertEquals(2, gameStateOne.getGreenTrack().getBonus());
    }
    @Test
    //tests yellow updates according to specifications
    public void updateYellowTrackTest() {
        assertEquals(0, gameStateOne.getYellowTrack().getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.getYellowTrack().addTrack();
        }
        assertEquals(0, gameStateOne.getYellowTrack().getAbility());
        assertEquals(0, gameStateOne.getYellowTrack().getBonus());
    }
    @Test
    //tests purple updates according to specifications
    public void updatePurpleTrackTest() {
        assertEquals(0, gameStateOne.getPurpleTrack().getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.getPurpleTrack().addTrack();
        }
        assertEquals(2, gameStateOne.getPurpleTrack().getAbility());
        assertEquals(2, gameStateOne.getPurpleTrack().getBonus());
    }





}
