package comp1110.ass2;

import comp1110.ass2.gui.*;

public class Game {

    private void updateGUIState(int currentPlayer, GameState gameboard, GameGUI gui) {
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

    private void handleTrackSelection(GameGUI gui,GameState gameBoard, int controlPlayer, int maxPlayers) {
        if (gui.getSelectedTracks().size() > 1){
            gui.setMessage("too many tracks selected, select only one");
        } else if (!gameBoard.isInAvailableDice(gui.getSelectedTracks().get(0))) {
            System.out.println(gui.getSelectedTracks());
            System.out.println(gameBoard.getAvailableDice());
            gui.setMessage("this track colour is not available");
        }else {
            gameBoard.getUpdateTrack(gui.getSelectedTracks().get(0));
            updateTrackInfo(controlPlayer, gui.getSelectedTracks().get(0),gameBoard,gui);
            controlPlayer = (controlPlayer == maxPlayers - 1) ? 0 : controlPlayer + 1;
            gui.setControlPlayer(controlPlayer);
        }
    }

    private void updateTrackInfo(int player, int track, GameState gameStateToUpdate, GameGUI gui ) {
        String colour = "";
        Track trackToUpdate = null;
        switch (track) {
            case 0:
                colour = "Red";
                trackToUpdate = gameStateToUpdate.redTrack;
                break;
            case 1:
                colour = "Blue";
                trackToUpdate = gameStateToUpdate.blueTrack;
                break;
            case 2:
                colour = "Purple";
                trackToUpdate = gameStateToUpdate.purpleTrack;
                break;
            case 3:
                colour = "Green";
                trackToUpdate = gameStateToUpdate.greenTrack;
                break;
            case 4:
                colour = "Yellow";
                trackToUpdate = gameStateToUpdate.yellowTrack;
                break;
        }

        gui.setTrackInfo(player, colour, trackToUpdate.getTrack(), trackToUpdate.getBonus(), trackToUpdate.getAbility(), trackToUpdate.nextBonus, trackToUpdate.nextAbility);
    }

    public void getUpdateGUIState(int currentPlayer, GameState gameboard,GameGUI gui){
        updateGUIState(currentPlayer,gameboard,gui);
    }

    public void getHandleTrackSelection(GameGUI gui, GameState gameBoard, int controlPlayer, int maxPlayers){
        handleTrackSelection(gui,gameBoard,controlPlayer,maxPlayers);
    }

    public void getUpdateTrackInfo(int player, int track, GameState gameStateToUpdate, GameGUI gui){
        updateTrackInfo(player,track,gameStateToUpdate,gui);
    }

}
