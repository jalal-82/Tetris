package comp1110.ass2;

public class D2CTest {
    public static void main(String[] args) {
        Player P1 = new Player();
        Dices D1 = new Dices();
        D1.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        Tile T1 = new Tile(D1);

        // Test isValidSelection
        System.out.println("\nTesting isValidSelection:");
        System.out.println("Is R3 valid? " + T1.isValidSelection("R3"));
        System.out.println("Is R4 valid? " + T1.isValidSelection("R4"));
        System.out.println("Is B3 valid? " + T1.isValidSelection("B3"));

        Score S1 = new Score();
        Abilities A1 = new Abilities();
        Bonus B1 = new Bonus("Red", 2);

        GameState G1 = new GameState(P1, D1, T1, S1, A1, B1);

        T1.printGeneratedTiles();

        System.out.println("\nApplying tiles to the board:");
        G1.applyTilesD2C();


    }
}
