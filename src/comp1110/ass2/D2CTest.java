package comp1110.ass2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class D2CTest {
    Player P1;
    Dices D1;
    Tile T1;
    Score S1;
    Abilities A1;
    Bonus B1;
    GameState G1;

    Player P2;
    Dices D2;
    Tile T2;
    Score S2;
    Abilities A2;
    Bonus B2;
    GameState G2;


    @BeforeEach
    public void setup(){

//      Create game Instance for Player 1
        P1 = new Player();
        D1 = new Dices();
        D1.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        T1 = new Tile(D1);
        S1 = new Score();
        A1 = new Abilities();
        B1 = new Bonus("Red", 2);
        G1 = new GameState(P1, D1, T1, S1, A1, B1);

//      Create Game Instance for Player 2
        P2 = new Player();
        D2 = new Dices();
        T2 = new Tile(D1);
        S2 = new Score();
        A2 = new Abilities();
        B2 = new Bonus("Blue", 2);
        G2 = new GameState(P2, D2, T2, S2, A2, B2);

//      Apply Tiles to Player 1's Gameboard

//      B3 (rotataion = 1 (90 degrees))
        if (T1.getAllTiles().containsKey("B3") && !T1.getAllTiles().get("B3").isEmpty()) {
            T1.rotateTile("B3", 1);
            G1.placeTile("B3", 0, 0);
        }

//      G4L (No rotation)
        if (T1.getAllTiles().containsKey("G4L") && !T1.getAllTiles().get("G4L").isEmpty()) {
            G1.placeTile("G4L", 0, 3);
        }
    }
    @Test
    public void selectR3(){
        boolean res = T1.isValidSelection("R3");
        assertEquals(res,true);
    }

    @Test
    public void selectR4(){
        boolean res = T1.isValidSelection("R4");
        assertEquals(res,true);
    }

    @Test
    public void selectB3(){
        boolean res = T1.isValidSelection("B3");
        assertEquals(res,false);
    }

    @Test
    public void isTileY3PlacementValid1(){
        if (T1.getAllTiles().containsKey("Y3") && !T1.getAllTiles().get("Y3").isEmpty()) {
            T1.rotateTile("Y3", 0);
            boolean isValid = G1.isTilePlacementValid(G1.getGameBoard(),"Y3",0,0);
            assertEquals(isValid,false);
        }
    }

    @Test
    public void isTileY3PlacementValid2(){
        if (T1.getAllTiles().containsKey("Y3") && !T1.getAllTiles().get("Y3").isEmpty()) {
            T1.rotateTile("Y3", 3);
            boolean isValid1 = G1.isTilePlacementValid(G1.getGameBoard(),"Y3",0,1);
            assertEquals(isValid1,true);
        }
    }

    @Test
    public void isTileY3PlacementValid3(){
        if (T1.getAllTiles().containsKey("Y3") && !T1.getAllTiles().get("Y3").isEmpty()) {
            T1.rotateTile("Y3", 3);
            boolean isValid1 = G1.isTilePlacementValid(G1.getGameBoard(),"Y3",0,2);
            assertEquals(isValid1,false);
        }
    }

    @Test
    public void isTileY3PlacementValid4(){
        if (T1.getAllTiles().containsKey("Y3") && !T1.getAllTiles().get("Y3").isEmpty()) {
            T1.rotateTile("Y3", 1);
            boolean isValid1 = G1.isTilePlacementValid(G1.getGameBoard(),"Y3",0,2);
            assertEquals(isValid1,true);
        }
    }

    @Test
    public void isTileY3PlacementValid5(){
        if (T1.getAllTiles().containsKey("Y3") && !T1.getAllTiles().get("Y3").isEmpty()) {
            T1.rotateTile("Y3", 3);
            boolean isValid1 = G1.isTilePlacementValid(G1.getGameBoard(),"Y3",1,1);
            assertEquals(isValid1,false);
        }
    }
}
