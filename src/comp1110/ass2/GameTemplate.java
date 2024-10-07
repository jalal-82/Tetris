
package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTemplate extends Application {

    GameGUI gui;
	List<GameState> gameStates;
	GameState currentState;//variable for easy reference to the currentState

	int currentPlayer = 0;
	int controlPlayer = 0;
	int maxPlayers = 0;


	public void start(Stage stage) throws Exception {
		gui = new GameGUI();
		gameStates = new ArrayList<>();

		Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);


		gui.setOnStartGame((np, isAI) -> {
			maxPlayers = np;
			// Initialize game states for np players

			for (int i=0;i<np;i++){
				Dices dices = new Dices();
				Tile tile = new Tile(dices);
				Score score = new Score();
				gameStates.add(new GameState(dices, tile, score));
				for (int j = 0; j < 5; j++) {
					updateTrackInfo(i, j);
				}
			}
			currentState = gameStates.get(0);
			gui.setMessage("Start new game with " + np + " players");
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));
			gui.setAvailableActions(List.of("Reroll", "Give up"));

			gui.setControlPlayer(0);
		});

	//places the tile on the board in our backend logic then updates the gui
	gui.setOnTilePlaced((p) -> {
//check if bonus was used and adjust accordingly
		//currentState = gameStates.get(currentPlayer);
		if (currentState.isTilePlacementValid(currentState.getGameBoard(), p.getY(),p.getX())){
			currentState.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
			gui.setMessage(p.getTileName()+" placed. Other players should now select Track");
			//updates bonus if bonus was used
			currentState.updateBonus(currentState.getSelectedDice(), p.getTileName());
//			after tile is placed, update score
			HashMap<String, List<Integer>> completedMap = new HashMap<>();
			currentState.updateScore(currentState,completedMap);

//			use setScore to apply the new score
			gui.setScore(currentPlayer,currentState.getScore());
			//resets the available dice to be all that the player hasn't used
			gui.setAvailableDice(currentState.getAvailableDice());
			System.out.println(currentState.getAvailableDice());
//			check if Coat of Arms is completed
			boolean isCompleted = false; // this boolean will be used by hunter's abilities/track
			if (!completedMap.isEmpty()){
				List<Integer> rows = completedMap.get("completedRows");
				for (int j=0;j<rows.size();j++){
					gui.setRowCoA(currentPlayer,rows.get(j),true);
					isCompleted = true;
				}

				List<Integer> cols = completedMap.get("completedCols");
				for (int j=0;j<cols.size();j++){
					gui.setColumnCoA(currentPlayer,cols.get(j),true);
					isCompleted = true;
				}
			}
			System.out.println("Board after tile for player "+currentPlayer);
			currentState.printBoard(currentState.getGameBoard());
			//update control player to represent player to select track
            if (currentPlayer != maxPlayers - 1) {
                gui.setControlPlayer(currentPlayer + 1);
				controlPlayer = currentPlayer + 1;
            } else {
                gui.setControlPlayer(0);
				controlPlayer = 0;
            }

        } else {
			gui.setMessage(p.getTileName()+" Placement invalid");
		}
		// @Eileen: replace placeTile method with placeTileWithRotationWindows method
		// @Eileen: for more functionalities
		//update bonuses from windows?
		updateGUIState();

	});




//updates the list of available dice
	gui.setOnDiceSelectionChanged((i) -> {
		currentState.updateSelectedDice(gui.getSelectedDice());

		gui.setMessage("dice selection: " + currentState.getSelectedDice());
		System.out.println(currentState.getSelectedDice());


//		if red ability unlocked
//		roll selected dices to their options
//
	    });

	// updates the selected tile when a tile is selected in the gui
	gui.setOnTileSelected(tileName -> {
//checks if the selection is valid given the selected die. only allows valid selections
		if (currentState.isValidTileSelection(currentState.getSelectedDice(), tileName)) {
			gui.setMessage("valid tile");
			currentState.updateSelectedTile(tileName);

		} else {
			gui.setMessage("invalid selection, ensure you have selected the required die");
			gui.clearTileSelection();
		}


	});

	gui.setOnGameAction((s) -> {
		gui.setMessage("action: " + s);

		//	this works but need to implement conditions to when reroll dice can be used
		if (s.equals("Reroll")) {
			currentState.rerollDice();
			gui.setAvailableDice(List.of(currentState.getDice()));
			gui.setMessage("Reroll Dices clicked");
		}

		});
//

//		need to think of a process that after confirming tile, it moves to next players tab
	gui.setOnConfirm((s) -> {
		//checks the control player, if it's the currentPlayer then it starts next turn, else it confirms track selection
		if (s.contains(String.valueOf(currentPlayer))) {
			currentPlayer++;

			if (currentPlayer > maxPlayers - 1) {
				currentPlayer = 0;
			}
			gui.setMessage("Player " + String.valueOf(currentPlayer) + "'s turn");
			currentState = gameStates.get(currentPlayer);
			currentState.rerollDice();
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));

			gui.setControlPlayer(currentPlayer);
		} else if (gui.getSelectedTracks().size() > 1){
			gui.setMessage("too many tracks selected, select only one");
		} else if (!currentState.isInAvailableDice(gui.getSelectedTracks().get(0))) {
			System.out.println(gui.getSelectedTracks());
			System.out.println(currentState.getAvailableDice());
			gui.setMessage("this track colour is not available");
		}else {
			gameStates.get(controlPlayer).updateTrack(gui.getSelectedTracks().get(0));
			updateTrackInfo(controlPlayer, gui.getSelectedTracks().get(0));
			controlPlayer = (controlPlayer == maxPlayers - 1) ? 0 : controlPlayer + 1;
			gui.setControlPlayer(controlPlayer);
		}
		//also need case for if available die is null to skip track selection
	    });

	// Start the application:
        stage.setScene(scene);
        stage.setTitle("Copenhagen Roll & Write");
        stage.show();

    }


	private void updateTrackInfo(int player, int track) {
		String colour = "";
		Track trackToUpdate = null;
		GameState gameStateToUpdate = gameStates.get(player);
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
	//method to update the gui
	//so far it just updates the gameboard
	//ie sets a square wherever one should be
	private void updateGUIState() {
		System.out.println("currPlayer in updateGUI "+currentPlayer);
		char[][] board = gameStates.get(currentPlayer).getGameBoard();
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
}



