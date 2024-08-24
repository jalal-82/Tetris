package comp1110.ass2;

public class Tile {

    private final int width;
    private final int height;
    private final boolean hasWindow;


    public Tile(int width, int height, boolean hasWindow) {
        this.width = width;
        this.height = height;
        this.hasWindow = hasWindow;
    }

    /**
     *
     * @param tileShape
     * @param gameState
     * @return Boolean True if Valid else False
     */
    public boolean isValid(int[][] tileShape, int[][] gameState){
//      implement the rule to validate a placement
        return false;
    }

    /**
     *
     * @param tileSHape
     * @param gameState
     * @return
     * @author
     */
    public int[][] placeTile(int[][] tileSHape, int[][] gameState){
        return gameState;
    }

}


