
package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
				Dices dices = new Dices();
				Tile tile = new Tile(dices);
				Score score = new Score();

				gameStates.add(new GameState(dices, tile, score));
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
		if (currentState.isTilePlacementValid(currentState.getGameBoard(), p.getY(),p.getX())){
			currentState.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
			gui.setMessage(p.getTileName()+" Placement valid");

//			after tile is placed, update score
			currentState.updateScore(currentState);
			System.out.println(currentState.getScore());
//			use setScore to apply the new score
			gui.setScore(0,currentState.getScore());

//			check if CoA is completed
			HashMap<String, List<Integer>> completedIndices = currentState.getCoA();
			System.out.println("completed indices "+completedIndices);
			if (completedIndices != null){
				for (int i = 0;i<completedIndices.get("completedRows").size();i++){
					gui.setRowCoA(0, i,true);
				}

				for (int i = 0;i<completedIndices.get("completedCols").size();i++){
					gui.setColumnCoA(0,i,true);
				}
			}

		} else {
			gui.setMessage(p.getTileName()+" Placement invalid");
		}

		// @Eileen: replace placeTile method with placeTileWithRotationWindows method
		// @Eileen: for more functionalities
		//update bonuses from windows?
		updateGUIState();


	});

//
	gui.setOnDiceSelectionChanged((i) -> {
		List<Integer> selectedDice = gui.getSelectedDice();
		gui.setMessage("dice selection: " + selectedDice);

//		if red ability unlocked
//		reroll selected dices to their options
//

	    });
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

		//	this works but need to implement conditions to when reroll dice can be used
		if (s.equals("Reroll")) {
			currentState.rerollDice(currentState);
			gui.setAvailableDice(List.of(currentState.getDice()));
			System.out.println("Reroll Dices clicked");
		}

		});
//

//		need to think of a process that after confirming tile, it moves to next players tab
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
					gui.setFacadeSquare(0, x, guiY, color, isWindow);
				}
			}
		}
}
}



