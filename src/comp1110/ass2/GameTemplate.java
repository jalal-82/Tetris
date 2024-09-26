
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
			Dices dices = new Dices();
			Tile tile = new Tile(dices);
			System.out.println();
			Score score = new Score();
			Abilities abilities = new Abilities();

			// Create a new GameState for each player and add to the list
			gameStates.add(new GameState(dices, tile, score, abilities));
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
			gui.setTrackInfo(0, "Red",
					currentState.getAbilities("red"),
					currentState.getAbilities("blue"),
					currentState.getAbilities("red"),
					0, 0);
		});

	gui.setOnTilePlaced((p) -> {
		gameStates.get(0).placeTile(p.getY(), p.getX());
		//update bonuses from windows?
		updateGUIState();
	});

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
	private void updateGUIState() {
		char[][] board = gameStates.get(0).getGameBoard();
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				char c = board[y][x];
				if (c != '.') {
					//to extract colour value from c, decrement if it is representing a window
					if (c == 'S' || c == 'C' || c == 'Q' || c == 'H' || c == 'Z')
						c--;
					String color = String.valueOf(c);
					// adjust y-coordinate for GUI
					int guiY = board.length - 1 - y;
					gui.setFacadeSquare(0, x, guiY, color, true);
				}
			}
		}
}
}



