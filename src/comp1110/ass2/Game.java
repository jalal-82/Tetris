package comp1110.ass2;

import comp1110.ass2.gui.*;

public class Game {

    private void updateGUIState(int currentPlayer, GameBoard gameboard, GameGUI gui) {
        System.out.println("currPlayer in updateGUI "+currentPlayer);
        char[][] board = gameboard.getGameBoard();
        for (int y = 0; y < board.length; y++) { // @Eileen: y is gameboard row
            for (int x = 0; x < board[y].length; x++) { // @Eileen: x is gameboard column
                char c = board[y][x];
                boolean isWindow = false; // @Eileen: default is not a window
                if (c != '.') {
                    //to extract colour value from c, decrement if it is representing a window
                    if (c == 'S' || c == 'C' || c == 'Q' || c == 'H' || c == 'Z') { // @Eileen: This is identifying windows on tile
                        c--;
                        isWindow = true; // @Eileen: This is a window
                    }
                    String color = String.valueOf(c);
                    // adjust y-coordinate for GUI
                    int guiY = board.length - 1 - y;
                    gui.setFacadeSquare(currentPlayer, x, guiY, color, isWindow);
                }
            }
        }
    }


    public void getUpdateGUIState(int currentPlayer, GameBoard gameboard,GameGUI gui){
        updateGUIState(currentPlayer,gameboard,gui);
    }



}
