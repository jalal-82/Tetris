//package comp1110.ass2.test;
//
//import comp1110.ass2.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
///*
//This Test class should be the main submission, However, it is just stuck on run for some
//reason even though it is the same thing as D2C except for variable naming changes. Therefore, we have left it here.
// */
//public class PlaceTileTest {
//    Dices diceOne;
//    Tile tileOne;
//    Score scoreOne;
//    GameBoard gameBoardOne;
//
//    Dices dicesTwo;
//    Tile tileTwo;
//    Score scoreTwo;
//    GameBoard gameBoardTwo;
//
//    @BeforeEach
//    public void setup() {
//
////      Create game Instance for Player 1
//        diceOne = new Dices();
//        diceOne.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
//        tileOne = new Tile(diceOne);
//        scoreOne = new Score();
//        gameBoardOne = new GameBoard(diceOne, tileOne);
//
////      Create Game Instance for Player 2
//        dicesTwo = new Dices();
//        tileTwo = new Tile(dicesTwo);
//        scoreTwo = new Score();
//        gameBoardTwo = new GameBoard(dicesTwo, tileTwo);
//
//        //      Apply Tiles to Player 1's Gameboard
//        if (tileOne.getAllTiles().containsKey("B3")) {
//            tileOne.updateSelectedTile("B3");
//            boolean[] windows = {true, false, true};
//            gameBoardOne.placeTileWithRotationWindows(0, 0, 1, windows);
//        }
//        if (tileOne.getAllTiles().containsKey("G4L")) {
//            tileOne.updateSelectedTile("G4L");
//            boolean[] windows = {true, false, true, true};
//            gameBoardOne.placeTileWithRotationWindows( 0, 3, 0, windows);
//        }
//    }
//
//    @Test
//    public void isTileY3PlacementValid1(){
//        if (tileOne.getAllTiles().containsKey("Y3")) {
//            tileOne.updateSelectedTile("Y3");
//            tileOne.rotateTile( 0);
//            boolean isValid = gameBoardOne.getIsTilePlacementValid(1,0);
//            assertFalse(isValid);
//        }
//    }
//
//    @Test
//    public void isTileY3PlacementValid2(){
//        if (tileOne.getAllTiles().containsKey("Y3")) {
//            tileOne.updateSelectedTile("Y3");
//            tileOne.rotateTile( 3);
//            boolean isValid = gameBoardOne.getIsTilePlacementValid(1,0);
//            assertEquals(isValid,true);
//        }
//    }
//
//    @Test
//    public void isTileY3PlacementValid3(){
//        if (tileOne.getAllTiles().containsKey("Y3")) {
//            tileOne.updateSelectedTile("Y3");
//            tileOne.rotateTile( 3);
//            boolean isValid = gameBoardOne.getIsTilePlacementValid(2,0);
//            assertEquals(isValid,false);
//        }
//    }
//
//    @Test
//    public void isTileY3PlacementValid4(){
//        if (tileOne.getAllTiles().containsKey("Y3")) {
//            tileOne.updateSelectedTile("Y3");
//            tileOne.rotateTile( 1);
//            boolean isValid = gameBoardOne.getIsTilePlacementValid(2,0);
//            assertEquals(isValid,true);
//        }
//    }
//
//    @Test
//    public void isTileY3PlacementValid5(){
//        if (tileOne.getAllTiles().containsKey("Y3")) {
//            tileOne.updateSelectedTile("Y3");
//            tileOne.rotateTile( 3);
//            boolean isValid = gameBoardOne.getIsTilePlacementValid(1,1);
//            assertEquals(isValid,false);
//        }
//    }
//
//}
