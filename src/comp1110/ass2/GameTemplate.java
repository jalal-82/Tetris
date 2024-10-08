
package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class GameTemplate extends Application {
	Game game;
    GameGUI gui;
	List<GameState> gameStates;
	GameState currentState;//variable for easy reference to the currentState

	int currentPlayer = 0;
	int controlPlayer = 0;
	int maxPlayers = 0;

	public void start(Stage stage) throws Exception {
		game = new Game();
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
			gui.setAvailableActions(List.of("Reroll","Change selected to Red","Change selected to Blue","Change selected to Purple", "Change selected to Green", "Change selected to Yellow"));
			gui.setControlPlayer(0);
		});

	//places the tile on the board in our backend logic then updates the gui
	gui.setOnTilePlaced((p) -> {
		// first check windows are valid and updateBlue ability if necessary
		if (allWindows(p.getWindows()))
			if (currentState.blueTrack.getAbility() > 0)
				currentState.blueTrack.updateAbility();
			else {
				gui.setMessage("no blue ability available, chose different window configuration");
				return;
			}
			//check place if valid
		if (currentState.getIsTilePlacementValid(p.getY(),p.getX())){
			currentState.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
			gui.setMessage(p.getTileName()+" placed. Other players should now select Track");
			//updates bonus if bonus was used
			currentState.getUpdateBonus(p.getTileName());

//			after tile is placed, update score
			HashMap<String, List<Integer>> completedMap = new HashMap<>();
			currentState.updateScore(currentState,completedMap);
			gui.setScore(currentPlayer,currentState.getScore());

			//resets the available dice to be all that the player hasn't used
			gui.setAvailableDice(currentState.getAvailableDice());
			System.out.println(currentState.getAvailableDice());

//			update Coat of Arms
			currentState.updateCoA(gui,currentPlayer,completedMap);

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
//		updateGUIState();
		game.getUpdateGUIState(currentPlayer,gameStates.get(currentPlayer),gui);

	});


//updates the list of available dice
	gui.setOnDiceSelectionChanged((i) -> {
		currentState.updateSelectedDice(gui.getSelectedDice());
		System.out.println(currentState.getSelectedDice());

	    });

	// updates the selected tile when a tile is selected in the gui

	gui.setOnTileSelected(tileName -> {
		//checks if the selection is valid given the selected die. only allows valid selections
		if (currentState.getIsValidTileSelection(tileName)) {
			gui.setMessage("valid tile");
			currentState.updateSelectedTile(tileName);
		} else {
			gui.setMessage("invalid selection, ensure you have selected the required die");
			gui.clearTileSelection();
		}
	});

	gui.setOnGameAction((action) -> {
		//logic for reroll
		if (action.equals("Reroll")) {
			if (currentState.redTrack.getAbility() == 0)
				gui.setMessage("Missing red ability, can't reroll");
			else {
				gui.setMessage("Player " + String.valueOf(currentPlayer) + " rerolled");
				currentState.rerollDice();
				gui.setAvailableTiles(List.of(currentState.getTiles()));
				gui.setAvailableDice(List.of(currentState.getDice()));
				currentState.redTrack.updateAbility();
			}
		}
		//logic for dice change
		if (action.contains("Change")) {
			//return if blueAbility not available
			if (currentState.greenTrack.getAbility() == 0)
				gui.setMessage("This ability is not currently available, keep playing to unlock blue ability");
			else {
				String desiredColour = String.valueOf(action.charAt(19));
				//determine all selected are the same colour
				for (String s : currentState.getSelectedDice())
					if (!(Objects.equals(currentState.getSelectedDice().get(0), s))) {
						gui.setMessage("All selected die must be the same colour");
						return;
					}
				//change all selected to the desired colour
				currentState.greenTrack.updateAbility();//reduce ability count
				//creates a string of the currently displayed dice
				String[] currentDice = currentState.getAvailableDice().toArray(new String[currentState.getAvailableDice().size()]);
				for (int a : gui.getSelectedDice())
					currentDice[a] = desiredColour;
				//checks if there are 5 or less current dice and acts accordingly
				if (currentDice.length == 5) {
					currentState.setRolledDice(List.of(currentDice));
					System.out.println(Arrays.toString(currentDice));
					gui.setAvailableDice(List.of(currentDice));
				} else {
					currentState.setAvailableDice(currentDice);
					gui.setAvailableDice(List.of(currentDice));
				}
			}

		}

	});

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
			System.out.println(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));

			gui.setControlPlayer(currentPlayer);

		} else if (currentState.getAvailableDice().isEmpty())
			gui.setMessage("No dice available for track selection, player " + currentPlayer + " confirm end of turn");
		else
			handleTrackSelection();
		//also need case for if available die is null to skip track selection
	    });

	gui.setOnPass((s) -> {
		gui.setMessage("Player" + currentPlayer + " Skips their turn");
		if (currentPlayer == maxPlayers-1){
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}
		gui.setControlPlayer(currentPlayer);
		gui.setMessage("Now it's player " + currentPlayer + "'s turn");

		// implement reroll and new tiles for next player
		currentState = gameStates.get(currentPlayer);
		currentState.rerollDice();
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		System.out.println(List.of(currentState.getTiles()));
		gui.setAvailableDice(List.of(currentState.getDice()));
	});


	gui.setOnError((s) -> {
		gui.setControlPlayer(currentPlayer);
		currentState = gameStates.get(currentPlayer);
		currentState.rerollDice();
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		System.out.println(List.of(currentState.getTiles()));
		gui.setAvailableDice(List.of(currentState.getDice()));
	});

	// Start the application:
        stage.setScene(scene);
        stage.setTitle("Copenhagen Roll & Write");
        stage.show();
    }

	private boolean allWindows(boolean[] windows) {
		for (boolean value : windows) {
			if (!value) {
				return false;
			}
		}
		return true;
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

	private void handleTrackSelection () {
		if (gui.getSelectedTracks().size() > 1){
			gui.setMessage("too many tracks selected, select only one");
		} else if (!currentState.isInAvailableDice(gui.getSelectedTracks().get(0))) {
			System.out.println(gui.getSelectedTracks());
			System.out.println(currentState.getAvailableDice());
			gui.setMessage("this track colour is not available");
		} else {
			gameStates.get(controlPlayer).getUpdateTrack(gui.getSelectedTracks().get(0));
			updateTrackInfo(controlPlayer, gui.getSelectedTracks().get(0));
			controlPlayer = (controlPlayer == maxPlayers - 1) ? 0 : controlPlayer + 1;
			gui.setControlPlayer(controlPlayer);
		}
	}

}



