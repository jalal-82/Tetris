package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class GameTemplate extends Application {
	GameGUI gui;
	List<GameState> gameStates;
	List<GameBoard> gameBoards;
	GameState currentState;
	GameBoard currentBoard;

	int currentPlayer = 0;
	int controlPlayer = 0;
	int maxPlayers = 0;
	boolean trigger = false;//temp variable for coa logic, set to true to test

		public void start(Stage stage) throws Exception {
			if (stage == null) {
				throw new Exception("Stage is not initialized.");
			}
		gui = new GameGUI();
		gameStates = new ArrayList<>();
		gameBoards = new ArrayList<>();

		Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

		gui.setOnStartGame((np, isAI) -> {
			maxPlayers = np; // Set the number of players

			initializeGameStatesAndBoards(np); // Initialize game states and boards for all players

			setupInitialGameState(); // Set the current player to the first player and update the GUI

			setupGameUI(); // Display message and available actions for the start of the game
		});

		gui.setOnTilePlaced((p) -> {
			// Validate the window configuration and check blue ability
//			if (!handleWindowValidation(p)) return;
			if (!currentBoard.handleWindowValidation(gui,p,currentState)) return;

			// Check if the tile placement is valid on the board
			if (currentBoard.isTilePlacementValid(p.getY(), p.getX())) {
				handleTilePlacement(p); // Handle the tile placement on the board and update the message
				handleScoreAndBonusUpdate(p); // Update the score, bonus, and available dice
				if (trigger) {//temporary trigger variable fro devolping coa logic
					gui.showPopup();
				}
			} else {
				gui.setMessage(p.getTileName() + " Placement invalid");
			}
			// Update the GUI with the latest board and game state
			updateGUIState();
		});

		// Updates the list of available dice
		gui.setOnDiceSelectionChanged((i) -> {
			currentState.updateSelectedDice(gui.getSelectedDice());
			System.out.println(currentState.getSelectedDice());
			System.out.println("Dice selection updated");
		});

		// Updates the selected tile when a tile is selected in the GUI
		gui.setOnTileSelected(tileName -> {
			currentState.updateSelectedDice(gui.getSelectedDice());
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
			// Handle reroll logic
			if (action.equals("Reroll")) {
				currentState.handleRerollAction(gui,currentState,currentPlayer);
//				handleRerollAction();
			}

			// Handle dice change logic
			if (action.contains("Change")) {
				handleDiceChangeAction(action);
			}
		});

		gui.setTrackTwice((s) -> {
			gui.setMessage("Player " + currentPlayer + " select a track to advance twice");
			gui.setControlPlayer(currentPlayer);
		});

		gui.setSingleTile((s) -> {
			System.out.println("single working");
		});

		gui.setOnConfirm((s) -> {
			if (trigger) {
				handleTrackSelectionCOA();
				trigger = false;
				reEstablishControlPlayer();
				gui.setMessage( " player " + (controlPlayer) + " now select Track");
			}
			else if (s.contains(String.valueOf(currentPlayer))){
				updateCurrentPlayer();
				gui.setMessage("Player " + currentPlayer + "'s turn");
				updatePlayerStateForNextTurn();
				setAvailableTilesAndDice();
			}
			else if (currentState.getAvailableDice().isEmpty()){
				gui.setMessage("No dice available for track selection, player " + currentPlayer + " confirm end of turn");
			} else {
				handleTrackSelection();
			}
		});

		gui.setOnPass((s) -> {
			handlePassTurnMessage(); // Handle message and turn skipping
			updateCurrentPlayer();
			updatePlayerStateForNextTurn();
			//calls update gui to ensure all end of turn logic functions as normal
			updateGUIState();
		});

		// Start the application:
		stage.setScene(scene);
		stage.setTitle("Copenhagen Roll & Write");
		stage.show();
	}


	private void handleTrackSelection() {
		if (gui.getSelectedTracks().size() > 1) {
			gui.setMessage("Too many tracks selected, select only one");
		} else if (gui.getSelectedTracks().isEmpty()) {
			gui.setMessage("No track selected, please select a track");
		} else {
			int selectedTrackNum = gui.getSelectedTracks().get(0);
			TrackType selectedTrackType = getTrackTypeFromInt(selectedTrackNum);
			if (selectedTrackType == null) {
				gui.setMessage("Invalid track selected");
				return;
			}
			if (!currentState.isInAvailableDice(selectedTrackType)) {
				gui.setMessage("This track colour is not available");
			} else {
				gameStates.get(controlPlayer).updateTrack(selectedTrackType);
				gameStates.get(controlPlayer).updateTrackInfo(gui,controlPlayer,selectedTrackType,gameStates);
				handlePlayerControlUpdate();
				if (controlPlayer == currentPlayer)
					gui.setMessage("player " + currentPlayer + " confirm end of turn");
				else
					gui.setMessage("player " + controlPlayer + " select track");
				System.out.println(controlPlayer);
				gui.setControlPlayer(controlPlayer);
				gui.clearTrackSelection();
			}
		}
	}

	private void handleTrackSelectionCOA() {
		if (gui.getSelectedTracks().size() > 1) {
			gui.setMessage("Too many tracks selected, select only one");
		} else if (gui.getSelectedTracks().isEmpty()) {
			gui.setMessage("No track selected, please select a track");
		} else {
			gui.setMessage("select a track to advance twice");
			int selectedTrackNum = gui.getSelectedTracks().get(0);
			TrackType selectedTrackType = getTrackTypeFromInt(selectedTrackNum);
			if (selectedTrackType == null) {
				gui.setMessage("Invalid track selected");
				return;
			}
			gameStates.get(currentPlayer).updateTrack(selectedTrackType);
			gameStates.get(currentPlayer).updateTrack(selectedTrackType);
//			updateTrackInfo(currentPlayer, selectedTrackType);
			gameStates.get(currentPlayer).updateTrackInfo(gui,currentPlayer,selectedTrackType,gameStates);
			handlePlayerControlUpdate();
			gui.clearTrackSelection();

		}
	}

	private TrackType getTrackTypeFromInt(int trackNum) {
		return switch (trackNum) {
			case 0 -> TrackType.RED;
			case 1 -> TrackType.BLUE;
			case 2 -> TrackType.PURPLE;
			case 3 -> TrackType.GREEN;
			case 4 -> TrackType.YELLOW;
			default -> null;
		};
	}

//	Jalal's improvement of readability
//=============================================

	private void handleTilePlacement(Placement p) {
		currentBoard.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
		System.out.println(controlPlayer);
		reEstablishControlPlayer();
		gui.setMessage(p.getTileName() + " placed. player " + (controlPlayer) + " now select Track");
		currentBoard.printBoard(currentBoard.getGameBoard());
	}

	private void handleScoreAndBonusUpdate(Placement p) {
		currentState.updateBonus(p.getTileName());

		HashMap<String, List<Integer>> completedMap = new HashMap<>();
		currentState.updateScore(currentBoard, completedMap);

		gui.setScore(currentPlayer, currentState.getScore());

		gui.setAvailableDice(currentState.getAvailableDice());
		System.out.println(currentState.getAvailableDice());

		currentBoard.updateCoA(gui, currentPlayer, completedMap);
	}

	private void handlePlayerControlUpdate() {
		if (controlPlayer != maxPlayers - 1) {
			controlPlayer = controlPlayer + 1;
			gui.setControlPlayer(controlPlayer);
		} else {
			gui.setControlPlayer(0);
			controlPlayer = 0;
		}
	}

	private void updateGUIState() {
		currentBoard.getUpdateGUIState(currentPlayer, gameBoards.get(currentPlayer), gui);
	}

	// move this as well
	/**
	 * Handles the logic for the dice change action.
	 * Validates selected dice and changes them to the desired color, then updates the GUI.
	 */
	private void handleDiceChangeAction(String action) {
		// Check if the green ability is available
		if (currentState.getGreenTrack().getAbility() == 0) {
			gui.setMessage("This ability is not currently available, keep playing to unlock green ability");
			return;
		}

		String desiredColour = String.valueOf(action.charAt(19));

		// Validate that all selected dice are the same color
		if (!areSelectedDiceSameColour()) {
			gui.setMessage("All selected dice must be the same colour");
			return;
		}

		// Change selected dice to the desired color
		changeSelectedDiceToColour(desiredColour);

		// Update the green track ability
		currentState.getGreenTrack().updateAbility();
	}

	// maybe this as well
	/**
	 * Validates that all selected dice are the same color.
	 * Returns true if valid, otherwise false.
	 */
	private boolean areSelectedDiceSameColour() {
		List<String> selectedDice = currentState.getSelectedDice();

		// Check if all selected dice have the same color
		for (String s : selectedDice) {
			if (!Objects.equals(selectedDice.get(0), s)) {
				return false;
			}
		}
		return true;
	}

	// move this to gameState
	/**
	 * Changes the selected dice to the desired color and updates the GameState and GUI.
	 */
	private void changeSelectedDiceToColour(String desiredColour) {
		// Create a list of the currently displayed dice
		List<String> currentDice = new ArrayList<>(currentState.getAvailableDice());
		List<Integer> selectedDiceIndices = gui.getSelectedDice();

		// Change the color of each selected dice
		for (int index : selectedDiceIndices) {
			currentDice.set(index, desiredColour);
		}

		// Update the rolled dice in the GameState and GUI
		currentState.setRolledDice(currentDice);
		gui.setAvailableDice(currentDice);
	}

	/**
	 * Handles the message when a player passes their turn.
	 * Displays the message that the player has skipped their turn and updates the GUI.
	 */
	private void handlePassTurnMessage() {
		gui.setMessage("Player " + currentPlayer + " skips their turn");
//		reset current player if it is the last player
		int tmpCurrentPlayer = currentPlayer+1;
		if (currentPlayer==maxPlayers-1){
			tmpCurrentPlayer = 0;
		}

		// Update message for next player's turn
		gui.setMessage("Now it's player " + tmpCurrentPlayer + "'s turn");
	}

	/**
	 * Updates the current player for the next turn.
	 * Handles player rotation and sets the control to the new player.
	 */
	private void updateCurrentPlayer() {
		// Move to the next player, reset to player 0 if it's the last player
		if (currentPlayer == maxPlayers - 1) {
			currentPlayer = 0;
		} else {
			currentPlayer++;
		}

		// Set control to the new player in the GUI
		gui.setControlPlayer(currentPlayer);
	}

	private void reEstablishControlPlayer() {
		if (currentPlayer == maxPlayers - 1) {
			controlPlayer = 0;
			gui.setControlPlayer(0);
		} else {
			controlPlayer = currentPlayer + 1;
			gui.setControlPlayer(currentPlayer + 1);
		}
	}

	/**
	 * Updates the current player's state, including re rolling dice and assigning new tiles.
	 */
	private void updatePlayerStateForNextTurn() {
		// Get the current board and game state for the next player
		currentBoard = gameBoards.get(currentPlayer);
		currentState = gameStates.get(currentPlayer);

		// Update the dice and tiles for the new player's turn
		currentState.updateDiceAndTiles(gui);
	}

	/**
	 * Initializes the game states and boards for the given number of players.
	 * Sets up tracks and creates GameState and GameBoard objects for each player.
	 */
	private void initializeGameStatesAndBoards(int np) {
		for (int i = 0; i < np; i++) {
			// Create a new score and game state for each player
			Score score = new Score();
			GameState gameState = new GameState(score);

			// Add the game state and game board for each player
			gameStates.add(gameState);
			gameBoards.add(new GameBoard(gameState));

			// Initialize track information for each player
			for (TrackType trackType : TrackType.values()) {
//				updateTrackInfo(i, trackType);
				gameStates.get(i).updateTrackInfo(gui,i,trackType,gameStates);
			}
		}
	}

	/**
	 * Sets the current state and board to the first player's state and board.
	 */
	private void setupInitialGameState() {
		// Set the current state and board to player 0
		currentState = gameStates.get(0);
		currentBoard = gameBoards.get(0);
	}

	/**
	 * Sets up the initial GUI elements, such as available tiles, dice, and actions.
	 * Also sets the control to the first player.
	 */
	private void setupGameUI() {
		// Display a message for starting the game
		gui.setMessage("Start new game with " + maxPlayers + " players");

		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(event -> {
			// After the delay, update the message
			gui.setMessage("Player 0's turn to place tile");

			// Set the available tiles and dice in the GUI
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));

			// Set available actions in the GUI
			gui.setAvailableActions(List.of("Reroll", "Change selected to Red", "Change selected to Blue",
					"Change selected to Purple", "Change selected to Green", "Change selected to Yellow"));

			// Set the control to the first player
			gui.setControlPlayer(0);
		});

		// Start the delay
		pause.play();
	}


	private void setAvailableTilesAndDice(){
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		gui.setAvailableDice(List.of(currentState.getDice()));
	}



}
