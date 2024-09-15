package comp1110.ass2;

public class D2CTest {



//  test isValidSelection
    public static void main(String[] args) {
        Player P1 = new Player();
        Player P2 = new Player();


        Dices D1 = new Dices();
        D1.applyPresetDiceD2CP1();

        Dices D2 = new Dices();

        Tile T1 = new Tile(D1);
        Tile T2 = new Tile(D2);

        Score S1 = new Score();
        Score S2 = new Score();

//   Abilities and Bonus are irrelevant for D2C, they are just a preqisite for GameState Object
        Abilities A1 = new Abilities();
        Abilities A2 = new Abilities();

        Bonus B1 = new Bonus("Red",2);
        Bonus B2 = new Bonus("Blue",1);


        GameState G1 = new GameState(P1,D1,T1,S1,A1,B1);
        GameState G2 = new GameState(P2,D2,T2,S2,A2,B2);
/**
 *
      WE should generate Tiles here (only the names in String format is what we need)
      T1.generateTiles(); should be called by itself when we did Tile T1 = new Tile(D1);
      this should generate something like ["R2","R3",2 more will be random]

      Then we need to forcefully place B3 and G4l as D2C suggest.
    2 methods)
      we could do is Play 3 rounds on the board right here, make three method like applyPresetDiceD2CP1
        we call them R1,R2,R3 then get their tiles and apply it board
        OR
        we create a method in gameState class called D2CGameBoard or something
        which just puts the tiles by replacing '.' with G,GW,B and BW

        once we have the board, we just apply the R3 with 3 windows and test it
 */

    }


}
