package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class hunterTest {
    @Test
    void testIsValidSelection() {
        Dices D1 = new Dices();
        D1.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
        Tile T1 = new Tile(D1);
        Assertions.assertTrue(T1.isValidSelection( "R4"));
}


}
