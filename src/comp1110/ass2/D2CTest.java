package comp1110.ass2;

public class D2CTest {

    Player P1 = new Player();
    Player P2 = new Player();

    String[] D1 = {"Blue","BLue","Red","Blue","White"};
    String[] D2 = {"Green","Green","Green","Green","White"};

    Score S1 = new Score();
    Score S2 = new Score();

    Abilities A1 = new Abilities();
    Abilities A2 = new Abilities();

    Bonus B1 = new Bonus("Red",2);
    Bonus B2 = new Bonus("Blue",1);

    GameState G1 = new GameState(P1,D1,S1,A1,B1);
    GameState G2 = new GameState(P2,D2,S2,A2,B2);

    /**
     * Test your code for
     * checking if the current player can select the red size 3 tile
     * (should be allowed), the red size 4 tile (should be allowed) and
     * the blue size 3 tile (should not be allowed).
     * Assert equal stuff
     *
     * ToDo
     * Create Dices class that initiates 5 random colours to a string[]
     * Think of way on how to generate the tiles based on the dices
     * Zhening
     *
     * ToDo
     * Create a method in Dice which validates tile can be selected and create test cases
     *
     * Assuming Tile placement is given for this task
     * gameState.board -> [[0,0,0,0,0],[0,0,0,0,0],[],[],[]]
     *
     *
     * ToDo
     * Write backend code to validate the tile placement in the gameState class
     * Tile = "R2"
     * confusing bit -Jalal
     *
     */


}
