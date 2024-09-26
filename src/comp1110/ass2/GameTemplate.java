
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

	public void start(Stage stage) throws Exception {
		gui = new GameGUI();
		gameStates = new ArrayList<>();

		// Initialize game states for 2 players
		for (int i = 0; i < 4; i++) {
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

		Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

	// This is where you should set up callbacks (or at least one
	// callback, for the start-of-game event).

		gui.setOnStartGame((np, isAI) -> {
			gui.setMessage("Start new game with " + np + " players");
			GameState currentState = gameStates.get(0);
//			System.out.println(currentState);
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));
			gui.setAvailableActions(List.of("Reroll", "Give up"));

		});




//	gui.setOnTilePlaced((p) -> {
//		gameStates.placeTile(p.getY(), p.getX());
//		//update bonuses from windows?
//		updateGUIState();
//	});
//
//	gui.setOnDiceSelectionChanged((i) -> {
//		gui.setMessage("dice selection: " + gui.getSelectedDice());
//	    });
//
//	gui.setOnTrackSelectionChanged((i) -> {
//		gui.setMessage("track selection: " + gui.getSelectedTracks());
//	    });
//
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
//	private void updateGUIState() {
//		char[][] board = .getGameBoard();
//		for (int y = 0; y < board.length; y++) {
//			for (int x = 0; x < board[y].length; x++) {
//				char c = board[y][x];
//				if (c != '.') {
//					//to extract colour value from c, decrement if it is representing a window
//					if (c == 'S' || c == 'C' || c == 'Q' || c == 'H' || c == 'Z')
//						c--;
//					String color = String.valueOf(c);
//					// adjust y-coordinate for GUI
//					int guiY = board.length - 1 - y;
//					gui.setFacadeSquare(0, x, guiY, color, true);
//				}
//			}
//		}
//}
}



