package comp1110.ass2;

import java.util.ArrayList;
import java.util.List;

public class Dices {
    private final List<String> availableDie = new ArrayList<>();
    // very rough implementation of the class -Hunter
    public Dices(String c1, String c2, String c3, String c4, String c5) {
        this.availableDie.add(c1);
        this.availableDie.add(c2);
        this.availableDie.add(c3);
        this.availableDie.add(c4);
        this.availableDie.add(c5);
    }

    public List<String> getAvailableDie(){
        return availableDie;
    }
}
