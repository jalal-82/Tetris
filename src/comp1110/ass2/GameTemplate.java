package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;

public class GameTemplate extends Application {
	GameGUI gui;
	List<GameState> gameStates;
	List<GameBoard> gameBoards;
	GameState currentState; // variable for easy reference to the currentState
	GameBoard currentBoard;

	int currentPlayer = 0;
	int controlPlayer = 0;
	int maxPlayers = 0;

	public void start(Stage stage) throws Exception {

		gui = new GameGUI();
		gameStates = new ArrayList<>();
		gameBoards = new ArrayList<>();

		Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

		gui.setOnStartGame((np, isAI) -> {

			maxPlayers = np;
			// Initialize game states and boards for np players
			for (int i = 0; i < np; i++) {
				Score score = new Score();
				GameState gameState = new GameState(score);
				gameStates.add(gameState);
				gameBoards.add(new GameBoard(gameState));
				for (TrackType trackType : TrackType.values()) {
					updateTrackInfo(i, trackType);
				}
			}

			currentState = gameStates.get(0);
			currentBoard = gameBoards.get(0);
			gui.setMessage("Start new game with " + np + " players");
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));
			gui.setAvailableActions(List.of("Reroll", "Change selected to Red", "Change selected to Blue",
					"Change selected to Purple", "Change selected to Green", "Change selected to Yellow"));
			gui.setControlPlayer(0);
		});

		// Places the tile on the board in our backend logic then updates the GUI
		gui.setOnTilePlaced((p) -> {
			// First check if all windows are valid and update blue ability if necessary
			if (allWindows(p.getWindows())) {
				if (currentState.getBlueTrack().getAbility() > 0)
					currentState.getBlueTrack().updateAbility();
				else {
					gui.setMessage("No blue ability available, choose a different window configuration");
					return;
				}
			}

			// Check if placement is valid
			if (currentBoard.isTilePlacementValid(p.getY(), p.getX())) {
				currentBoard.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
				gui.setMessage(p.getTileName() + " placed. Other players should now select Track");

				// Updates bonus if bonus was used
				currentState.updateBonus(p.getTileName());

				// After tile is placed, update score
				HashMap<String, List<Integer>> completedMap = new HashMap<>();
				currentState.updateScore(currentBoard, completedMap);
				gui.setScore(currentPlayer, currentState.getScore());

				// Resets the available dice to be all that the player hasn't used
				gui.setAvailableDice(currentState.getAvailableDice());
				System.out.println(currentState.getAvailableDice());

				// Update Coat of Arms
				currentBoard.updateCoA(gui, currentPlayer, completedMap);

				System.out.println("Board after tile for player " + currentPlayer);
				currentBoard.printBoard(currentBoard.getGameBoard());

				// Update control player to represent player to select track
				if (currentPlayer != maxPlayers - 1) {
					gui.setControlPlayer(currentPlayer + 1);
					controlPlayer = currentPlayer + 1;
				} else {
					gui.setControlPlayer(0);
					controlPlayer = 0;
				}

			} else {
				gui.setMessage(p.getTileName() + " Placement invalid");
			}

			currentBoard.getUpdateGUIState(currentPlayer, gameBoards.get(currentPlayer), gui);

		});

		// Updates the list of available dice
		gui.setOnDiceSelectionChanged((i) -> {
			currentState.updateSelectedDice(gui.getSelectedDice());
			System.out.println(currentState.getSelectedDice());
			System.out.println("Dice selection updated");
		});

		// Updates the selected tile when a tile is selected in the GUI
		gui.setOnTileSelected(tileName -> {
			// Checks if the selection is valid given the selected dice. Only allows valid selections
			if (currentState.isValidTileSelection(tileName)) {
				gui.setMessage("Valid tile selected");
				currentState.updateSelectedTile(tileName);
			} else {
				gui.setMessage("Invalid selection, ensure you have selected the required dice");
				gui.clearTileSelection();
			}
		});

		gui.setOnGameAction((action) -> {
			// Logic for reroll
			if (action.equals("Reroll")) {
				if (currentState.getRedTrack().getAbility() == 0)
					gui.setMessage("Missing red ability, can't reroll");
				else {
					gui.setMessage("Player " + currentPlayer + " rerolled");
					currentState.rerollDice();
					gui.setAvailableTiles(List.of(currentState.getTiles()));
					gui.setAvailableDice(List.of(currentState.getDice()));
					currentState.getRedTrack().updateAbility();
				}
			}
			// Logic for dice change
			if (action.contains("Change")) {
				// Return if green ability not available
				if (currentState.getGreenTrack().getAbility() == 0)
					gui.setMessage("This ability is not currently available, keep playing to unlock green ability");
				else {
					String desiredColour = String.valueOf(action.charAt(19));

					List<String> selectedDice = currentState.getSelectedDice();
					for (String s : selectedDice) {
						if (!Objects.equals(selectedDice.get(0), s)) {
							gui.setMessage("All selected dice must be the same colour");
							return;
						}
					}
					// Change all selected dice to the desired colour
					currentState.getGreenTrack().updateAbility(); // Reduce ability count

					// Create a list of the currently displayed dice
					List<String> currentDice = new ArrayList<>(currentState.getAvailableDice());
					List<Integer> selectedDiceIndices = gui.getSelectedDice();
					for (int index : selectedDiceIndices) {
						currentDice.set(index, desiredColour);
					}
					// Update the dice in the GameState and GUI
					currentState.setRolledDice(currentDice);
					gui.setAvailableDice(currentDice);
				}
			}

		});

		gui.setOnConfirm((s) -> {
			// Into select dice mode
			currentPlayer++;
			if (currentPlayer > maxPlayers - 1)
				currentPlayer = 0;
			currentBoard = gameBoards.get(currentPlayer);
			currentState = gameStates.get(currentPlayer);
			if (s.equals("next")) {
				currentState.updateDiceAndTiles(gui);
				gui.setControlPlayer(currentPlayer);
				return;
			}
			gui.setMessage("Player " + currentPlayer + ", choose a dice");
//			handleTrackSelection();

		});

		gui.setOnPass((s) -> {
			// Move to next player
			gui.setMessage("Player " + currentPlayer + " skips their turn");
			if (currentPlayer == maxPlayers - 1) {
				currentPlayer = 0;
			} else {
				currentPlayer++;
			}

			gui.setControlPlayer(currentPlayer);
			gui.setMessage("Now it's player " + currentPlayer + "'s turn");

			// Implement reroll and new tiles for next player
			currentBoard = gameBoards.get(currentPlayer);
			currentState = gameStates.get(currentPlayer);
			currentState.updateDiceAndTiles(gui);
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

	private void updateTrackInfo(int player, TrackType trackType) {
		String colour = "";
		Track trackToUpdate = null;
		GameState gameStateToUpdate = gameStates.get(player);

		switch (trackType) {
			case RED:
				colour = "Red";
				trackToUpdate = gameStateToUpdate.getRedTrack();
				break;
			case BLUE:
				colour = "Blue";
				trackToUpdate = gameStateToUpdate.getBlueTrack();
				break;
			case PURPLE:
				colour = "Purple";
				trackToUpdate = gameStateToUpdate.getPurpleTrack();
				break;
			case GREEN:
				colour = "Green";
				trackToUpdate = gameStateToUpdate.getGreenTrack();
				break;
			case YELLOW:
				colour = "Yellow";
				trackToUpdate = gameStateToUpdate.getYellowTrack();
				break;
			default:
				throw new IllegalArgumentException("Unknown track type");
		}

		gui.setTrackInfo(player, colour, trackToUpdate.getTrack(), trackToUpdate.getBonus(), trackToUpdate.getAbility(),
				trackToUpdate.getNextBonus(), trackToUpdate.getNextAbility());
	}

//	private void handleTrackSelection() {
//		if (gui.getSelectedTracks().size() > 1) {
//			gui.setMessage("Too many tracks selected, select only one");
//		} else if (gui.getSelectedTracks().isEmpty()) {
//			gui.setMessage("No track selected, please select a track");
//		} else {
//			int selectedTrackNum = gui.getSelectedTracks().get(0);
//			TrackType selectedTrackType = getTrackTypeFromInt(selectedTrackNum);
//			if (selectedTrackType == null) {
//				gui.setMessage("Invalid track selected");
//				return;
//			}
//			if (!currentState.isInAvailableDice(selectedTrackType)) {
//				gui.setMessage("This track colour is not available");
//			} else {
//				gameStates.get(controlPlayer).updateTrack(selectedTrackType);
//				updateTrackInfo(controlPlayer, selectedTrackType);
//				controlPlayer = (controlPlayer == maxPlayers - 1) ? 0 : controlPlayer + 1;
//				gui.setControlPlayer(controlPlayer);
//			}
//		}
//	}

	private TrackType getTrackTypeFromInt(int trackNum) {
		switch (trackNum) {
			case 0:
				return TrackType.RED;
			case 1:
				return TrackType.BLUE;
			case 2:
				return TrackType.PURPLE;
			case 3:
				return TrackType.GREEN;
			case 4:
				return TrackType.YELLOW;
			default:
				return null;
		}
	}
}
