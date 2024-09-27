
package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class GameTemplate extends Application {

    GameGUI gui;
	List<GameState> gameStates;
	GameState currentState;//variable for easy reference to the currentState
	public void start(Stage stage) throws Exception {
		gui = new GameGUI();
		gameStates = new ArrayList<>();


		Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

	// This is where you should set up callbacks (or at least one
	// callback, for the start-of-game event).

		gui.setOnStartGame((np, isAI) -> {
			// Initialize game states for np players
			for (int i = 0; i < np; i++) {
				System.out.println(i);
				Player player = new Player();
				Dices dices = new Dices();
				System.out.println(Arrays.deepToString(dices.getAllDice()));
				Tile tile = new Tile(dices);
				System.out.println(Arrays.deepToString(tile.getGeneratedTiles()));
				System.out.println();
				Score score = new Score();

				// Create a new GameState for each player and add to the list
				gameStates.add(new GameState(player, dices, tile, score));
			}
			currentState = gameStates.get(0);//sets the currentState to Player one
			gui.setMessage("Start new game with " + np + " players");
//			System.out.println(currentState);
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));
			gui.setAvailableActions(List.of("Reroll", "Give up"));

		});



	//places the tile on the board in our backend logic then updates the gui
	gui.setOnTilePlaced((p) -> {
		gameStates.get(0).placeTile(p.getY(), p.getX()); // @Eileen: here no windows updated
		//update bonuses from windows?
		updateGUIState();
	});
//
//	gui.setOnDiceSelectionChanged((i) -> {
//		gui.setMessage("dice selection: " + gui.getSelectedDice());
//	    });
//
//	gui.setOnTrackSelectionChanged((i) -> {
//		gui.setMessage("track selection: " + gui.getSelectedTracks());
//	    });
	// updates the selected tile when a tile is selected in the gui
	gui.setOnTileSelected(tileName -> {
		currentState.updateSelectedTile(tileName);
	});
	gui.setOnGameAction((s) -> {
		gui.setMessage("action: " + s);
		if (s.equals("Give up")) {
			gui.setAvailableActions(List.of("Reroll"));
		}
		});
//
//	gui.setOnConfirm((s) -> {
//		gui.setMessage("confirm: " + s);
//	    });
//
//	gui.setOnPass((s) -> {
//		gui.setMessage("pass: " + s);
//	    });

	// Start the application:
        stage.setScene(scene);
        stage.setTitle("Copenhagen Roll & Write");
        stage.show();


    }
	//method to update the gui
	//so far it just updates the gameboard
	//ie sets a square wherever it one should be
	private void updateGUIState() {
		char[][] board = gameStates.get(0).getGameBoard();
		for (int y = 0; y < board.length; y++) { // @Eileen: y is gameboard row
			for (int x = 0; x < board[y].length; x++) { // @Eileen: x is gameboard column
				char c = board[y][x];
				boolean isWindow = false;
				if (c != '.') {
					//to extract colour value from c, decrement if it is representing a window
					if (c == 'S' || c == 'C' || c == 'Q' || c == 'H' || c == 'Z') {// @Eileen: This is identifying windows on tile
						c--;
						isWindow = true; // @Eileen: This is a window
						System.out.println("Test windows");
					}
					String color = String.valueOf(c);
					// adjust y-coordinate for GUI
					int guiY = board.length - 1 - y;
					System.out.println("Test update");
					gui.setFacadeSquare(0, x, guiY, color, !isWindow);
				}
			}
		}
}
}



