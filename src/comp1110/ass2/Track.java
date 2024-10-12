package comp1110.ass2;

import java.util.List;
import java.util.Objects;

enum TrackType {
    RED, BLUE, PURPLE, GREEN, YELLOW
}
public class Track {
    private final TrackType trackType;
    private int ability = 0;
    private int bonus = 0;
    private int trackPosition = 0;
    private int nextAbility;
    private int nextBonus;
    private final Score score;

    public Track(TrackType trackType, Score score) {
        this.trackType = trackType;
        this.score = score;
        initializeTrack();
    }

    private void initializeTrack() {
        switch (trackType) {
            case BLUE:
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
                break;
            case RED:
                this.setBonus(2);
                nextAbility = 2;
                nextBonus = 1;
                break;
            case PURPLE:
                nextAbility = 3;
                nextBonus = 1;
                break;
            case GREEN:
                nextAbility = 4;
                nextBonus = 1;
                break;
            case YELLOW:
                nextAbility = 3;
                nextBonus = 1;
                break;
            default:
                System.out.println(trackType);
                throw new IllegalArgumentException("Unknown track type");
        }
    }

    /**
     * Called when a tile is placed.
     * Calculates the bonus points used to place that tile.
     *
     * @param selectedDice Should be from the dice class.
     * @param tileName     The tile placed.
     */
    public void updateBonus(List<String> selectedDice, String tileName) {
        int tileSize = Integer.parseInt(tileName.replaceAll("[A-Z]", ""));
        int diceOfTileColour = 0;
        for (String s : selectedDice) {
            if (tileName.startsWith(s) || Objects.equals(s, "W"))
                diceOfTileColour++;
        }
        this.bonus = this.bonus - (tileSize - diceOfTileColour);
    }

    /**
     * Updates the track based on its type and current position.
     */
    public void update() {
        switch (trackType) {
            case BLUE:
                updateBlueTrack();
                break;
            case RED:
                updateRedTrack();
                break;
            case PURPLE:
                updatePurpleTrack();
                break;
            case GREEN:
                updateGreenTrack();
                break;
        }
    }

    private void updateBlueTrack() {
        switch (trackPosition) {
            case 1:
                addBonus();
                nextAbility = 1;
                nextBonus = 3;
                break;
            case 2:
                addAbility();
                nextAbility = 2;
                nextBonus = 3;
                break;
            case 3:
                nextAbility = 1;
                nextBonus = 2;
                break;
            case 4:
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
                break;
            case 5:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 6:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    private void updateRedTrack() {
        switch (trackPosition) {
            case 1:
                addBonus();
                nextBonus = 2;
                nextAbility = 1;
                break;
            case 2:
                addAbility();
                nextBonus = 1;
                nextAbility = 2;
                break;
            case 3:
                addBonus();
                nextBonus = 0;
                nextAbility = 1;
                break;
            case 4:
            case 5:
                addAbility();
                nextAbility = 1;
                break;
            case 6:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    private void updatePurpleTrack() {
        switch (trackPosition) {
            case 1:
                addBonus();
                nextAbility = 2;
                nextBonus = 3;
                break;
            case 2:
                nextAbility = 1;
                nextBonus = 2;
                break;
            case 3:
                addAbility();
                nextAbility = 2;
                nextBonus = 1;
                break;
            case 4:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 5:
                addAbility();
                nextAbility = 2;
                break;
            case 6:
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    private void updateGreenTrack() {
        switch (trackPosition) {
            case 1:
                addBonus();
                nextBonus = 4;
                nextAbility = 3;
                break;
            case 2:
                nextBonus = 3;
                nextAbility = 2;
                break;
            case 3:
                nextBonus = 2;
                nextAbility = 1;
                break;
            case 4:
                addAbility();
                nextBonus = 1;
                nextAbility = 3;
                break;
            case 5:
                addBonus();
                nextBonus = 0;
                nextAbility = 2;
                break;
            case 6:
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                break;
            case 8:
                addAbility();
                nextAbility = 0;
                break;
            case 9:
                updateScore();
                break;
        }
    }

    private void updateYellowTrack(){
        switch (trackPosition){
            case 1:
                addBonus();
                nextAbility = 2;
                nextBonus = 3;
                break;
            case 2:
                nextAbility = 1;
                nextBonus = 2;
                break;
            case 3:
                addAbility();
                nextAbility = 3;
                nextBonus = 1;
                break;
            case 4:
                addBonus();
                nextAbility = 1;
                nextBonus = 0;
                break;
            case 5:
                nextAbility = 1;
                break;
            case 6:
                addAbility();
                nextAbility = 1;
                break;
            case 7:
                addAbility();
                nextAbility = 0;
                break;
            case 8:
                break;
            case 9:
                updateScore();
                break;
        }
    }

    // Getters and Setters
    public int getTrack() {
        return this.trackPosition;
    }

    public int getAbility() {
        return ability;
    }

    public void updateAbility() {
        if (ability > 0)
            ability--;
    }

    public int getBonus() {
        return bonus;
    }

    public void addTrack() {
        this.trackPosition++;
        update();
    }

    public void setBonus(int bonusCount) {
        this.bonus = bonusCount;
    }

    public void addAbility() {
        this.ability++;
    }

    public void addBonus() {
        this.bonus++;
    }

    public void updateScore() {
        score.addTwoToScore();
    }

    public int getNextAbility() {
        return nextAbility;
    }

    public int getNextBonus() {
        return nextBonus;
    }

    public TrackType getTrackType() {
        return trackType;
    }
}


