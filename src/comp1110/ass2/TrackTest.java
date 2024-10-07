package comp1110.ass2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrackTest {
    Dices diceOne;
    Tile tileOne;
    Score scoreOne;
    GameState gameStateOne;
    Dices diceTwo;


    @BeforeEach
    public void setup() {

//      Create game Instance for Player 1
        diceOne = new Dices();
        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        tileOne = new Tile(diceOne);
        scoreOne = new Score();
        gameStateOne = new GameState(diceOne, tileOne, scoreOne);
        diceTwo = new Dices();
//
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
    //tests Track methods
    @Test
    public void updateRedTrackTest() {
        assertEquals(0, gameStateOne.redTrack.getTrack());
        gameStateOne.redTrack.addTrack();
        gameStateOne.redTrack.addTrack();
        gameStateOne.redTrack.addTrack();
        //test abilities and bonuses are updated correctly
        assertEquals(1, gameStateOne.redTrack.getAbility());
        assertEquals(4, gameStateOne.redTrack.getBonus());
        assertEquals(0, gameStateOne.redTrack.getAbility()); //ensures useAbility reduces ability count
        assertEquals(3, gameStateOne.redTrack.getBonus()); //ensures useBonus reduces bonus count
        //removed useBonus and ability so these tests will now fail
        assertEquals(0, gameStateOne.redTrack.getAbility()); //tests ability cant be negative
    }
    @Test
    //tets blue updates according to specifications
    public void updateBlueTrackTest() {
        assertEquals(0, gameStateOne.blueTrack.getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.blueTrack.addTrack();
        }
        assertEquals(3, gameStateOne.blueTrack.getAbility());
        assertEquals(2, gameStateOne.blueTrack.getBonus());
    }
    @Test
    //tests green updates according to specifications
    public void updateGreenTrackTest() {
        assertEquals(0, gameStateOne.greenTrack.getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.greenTrack.addTrack();
        }
        assertEquals(1, gameStateOne.greenTrack.getAbility());
        assertEquals(2, gameStateOne.greenTrack.getBonus());
    }
    @Test
    //tests yellow updates according to specifications
    public void updateYellowTrackTest() {
        assertEquals(0, gameStateOne.yellowTrack.getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.yellowTrack.addTrack();
        }
        assertEquals(2, gameStateOne.yellowTrack.getAbility());
        assertEquals(2, gameStateOne.yellowTrack.getBonus());
    }
    @Test
    //tests purple updates according to specifications
    public void updatePurpleTrackTest() {
        assertEquals(0, gameStateOne.purpleTrack.getTrack());//tests track starts at zero
        for (int i = 0; i < 6; i++) {
            gameStateOne.purpleTrack.addTrack();
        }
        assertEquals(2, gameStateOne.purpleTrack.getAbility());
        assertEquals(2, gameStateOne.purpleTrack.getBonus());
    }



}
