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
	GameState currentState;
	GameBoard currentBoard;

	int currentPlayer = 0;
	int controlPlayer = 0;
	int maxPlayers = 0;
	boolean coaTrigger = true;//temp variable for coa logic, set to true to test
	boolean trackTwiceTrigger = false; //trigger used to determine the player should be updating their track by two
	boolean coaUsedTrigger = false;//trigger used to see if this round is the result of the coa ability


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

		// Places the tile on the board in our backend logic then updates the GUI
		gui.setOnTilePlaced((p) -> {
			System.out.println("player names are "+gui.getPlayerNames());

			// Validate the window configuration and check blue ability
			if (!handleWindowValidation(p)) return;

			// Check if the tile placement is valid on the board
			if (currentBoard.isTilePlacementValid(p.getY(), p.getX())) {
				//first see if the single tile is being used outside the coa case, this allows the player to still place another tile
				if (p.getTileName().equals("I1X") && !coaUsedTrigger) {
					currentState.purpleTrack.updateAbility();
					updateTrackInfo(currentPlayer, TrackType.PURPLE);
					handleSingleTilePlacement(p);
					gui.cycleBackToCurrent(maxPlayers);
					gui.setMessage("Single tile placed, continue with turn");
					if (currentState.purpleTrack.getTrack() == 0)
						gui.setAvailableTiles(List.of(currentState.getTiles())); // updates the tiles to not include single tile
					return;
				}
				handleTilePlacement(p); // Handle the tile placement on the board and update the message
				handleScoreAndBonusUpdate(p); // Update the score, bonus, and available dice
				//check if player just unlocked a coa, if so, supply them with options
				if (coaTrigger) {
					coaUsedTrigger = true;
					gui.showPopup();
					coaTrigger = false;
				}
				if (currentState.getAvailableDice().isEmpty())
					startNextTurn();
			} else {
				gui.setMessage(p.getTileName() + " Placement invalid");
			}
			// Update the GUI with the latest board and game state
			updateGUIState();
		});

		// Updates the list of available dice
		gui.setOnDiceSelectionChanged((i) -> {
			currentState.updateSelectedDice(gui.getSelectedDice());
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
				handleRerollAction();
			}

			// Handle dice change logic
			if (action.contains("Change")) {
				handleDiceChangeAction(action);
			}
		});
		//sets the logic for if a player chooses to advance their track twice
		gui.setTrackTwice((s) -> {
			gui.cycleBackToCurrent(maxPlayers);
			//the trigger will influence the confirm button's behaviour once the tracks have been selected
			trackTwiceTrigger = true;
			gui.setMessage("Player " + currentPlayer + " select a track to advance twice");
			gui.setControlPlayer(currentPlayer);
		});
//sets the logic for if the player chooses to place a single tile
		gui.setSingleTile((s) -> {
			gui.cycleBackToCurrent(maxPlayers);
			handleSingleTileCOA();
		});
		gui.setOnConfirm((s) -> {
//if the template trigger is active, it will handle coa track selection
			if (trackTwiceTrigger) {
				System.out.println("on confirm non tile placement");
				handleTrackSelectionCOA();
				trackTwiceTrigger = false;
				reEstablishControlPlayer();
//				gui.setMessage( " player " + (controlPlayer) + " now select Track");
				gui.setMessage((gui.getPlayerNames().get(controlPlayer)) + " now select Track");
			}
			//if not, and it is the current player,it confirms end of turn
			else if (s.contains(String.valueOf(currentPlayer))){
				startNextTurn();
			}
			//failing that it handles track selection
			else {
				coaUsedTrigger = false;
				handleTrackSelection();
			}
		});

		gui.setOnPass((s) -> {
			handlePassTurnMessage(); // Handle message and turn skipping
			if (currentPlayer == maxPlayers - 1) {
				controlPlayer = 0;
				gui.setControlPlayer(0);
			} else {
				controlPlayer = currentPlayer + 1;
				gui.setControlPlayer(currentPlayer + 1);
			}
//			gui.setMessage("player " + currentPlayer + " turn passed. Player " + (controlPlayer) + " now select Track");
			gui.setMessage(gui.getPlayerNames().get(currentPlayer) + " turn passed. " + (gui.getPlayerNames().get(controlPlayer)) + " now select Track");
			//calls update gui to ensure all end of turn logic functions as normal
			updateGUIState();

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
		String colour;
		Track trackToUpdate;
		GameState gameStateToUpdate = gameStates.get(player);
		if (trackType == null)
			return;
		switch (trackType) {
			case RED -> {
				colour = "Red";
				trackToUpdate = gameStateToUpdate.getRedTrack();
			}
			case BLUE -> {
				colour = "Blue";
				trackToUpdate = gameStateToUpdate.getBlueTrack();
			}
			case PURPLE -> {
				colour = "Purple";
				trackToUpdate = gameStateToUpdate.getPurpleTrack();
			}
			case GREEN -> {
				colour = "Green";
				trackToUpdate = gameStateToUpdate.getGreenTrack();
			}
			case YELLOW -> {
				colour = "Yellow";
				trackToUpdate = gameStateToUpdate.getYellowTrack();
			}
			default -> throw new IllegalArgumentException("Unknown track type");
		}

		gui.setTrackInfo(player, colour, trackToUpdate.getTrack(), trackToUpdate.getBonus(), trackToUpdate.getAbility(),
				trackToUpdate.getNextBonus(), trackToUpdate.getNextAbility());
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
				updateTrackInfo(controlPlayer, selectedTrackType);
				handlePlayerControlUpdate();
				if (controlPlayer == currentPlayer)
//					gui.setMessage("player " + currentPlayer + " confirm end of turn");
					gui.setMessage(gui.getPlayerNames().get(currentPlayer) + " confirm end of turn");
				else
//					gui.setMessage("player " + controlPlayer + " select track");
					gui.setMessage(gui.getPlayerNames().get(controlPlayer) + " select track");
				gui.setControlPlayer(controlPlayer);
				gui.clearTrackSelection();
				System.out.println("track Done");
			}
		}
	}

	/**
	 * this method sets the window up for the player to place a single tile
	 */
	private void handleSingleTileCOA() {
		gui.setControlPlayer(currentPlayer);
		List<String> single = new ArrayList<>();
		single.add("I1X");
		gui.setAvailableTiles(single);
		gui.setMessage(gui.getPlayerNames().get(currentPlayer) + " place your single tile");
	}

	/**
	 * adds two points to the players selected track when using coa ability
	 */
	private void handleTrackSelectionCOA() {
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
			gameStates.get(currentPlayer).updateTrack(selectedTrackType);
			gameStates.get(currentPlayer).updateTrack(selectedTrackType);
			updateTrackInfo(currentPlayer, selectedTrackType);
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

	private TrackType getTrackTypeFromTileName(String tileName) {
		return switch (tileName.charAt(0)){
			case 'R' -> TrackType.RED;
			case 'B' -> TrackType.BLUE;
			case 'P' -> TrackType.PURPLE;
			case 'G' -> TrackType.GREEN;
			case 'Y' -> TrackType.YELLOW;
			default -> null;
		};

	}

//	Jalal's improvement of readability
//=============================================
	private boolean handleWindowValidation(Placement p) {
		if (allWindows(p.getWindows())) {
			if (currentState.getBlueTrack().getAbility() > 0) {
				currentState.getBlueTrack().updateAbility();
				updateTrackInfo(currentPlayer, TrackType.BLUE);
			} else {
				gui.setMessage("No blue ability available, choose a different window configuration");
				return false;
			}
		}
		return true;
	}

	private void handleTilePlacement(Placement p) {
		currentBoard.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
		reEstablishControlPlayer();
//		gui.setMessage(p.getTileName() + " placed. player " + (controlPlayer) + " now select Track");
		gui.setMessage(p.getTileName() + " placed. " + (gui.getPlayerNames().get(controlPlayer)) + " now select Track");
		gui.setSelectDiceMode(true);

	}
	private void handleSingleTilePlacement(Placement p) {
		currentBoard.placeTileWithRotationWindows(p.getY(), p.getX(), p.getRotation(), p.getWindows());
		updateGUIState();
	}
	private void handleScoreAndBonusUpdate(Placement p) {
		currentState.updateBonus(p.getTileName());
		if (!p.getTileName().contains("I"))
			updateTrackInfo(currentPlayer, getTrackTypeFromTileName(p.getTileName()));
		updateTrackInfo(currentPlayer, getTrackTypeFromTileName(p.getTileName()));
		System.out.println(currentPlayer + "current players score = " + currentState.getScore());
		HashMap<String, List<Integer>> completedMap = new HashMap<>();
		currentState.updateScore(currentBoard, completedMap);

		gui.setScore(currentPlayer, currentState.getScore());
		if (!p.getTileName().contains("1"))
			gui.setAvailableDice(currentState.getAvailableDice());
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

	/**
	 * Handles the logic for the reroll action.
	 * Checks if the red ability is available, reroll dices, and updates the GUI.
	 */
	private void handleRerollAction() {
		// Check if the red ability is available
		if (currentState.getRedTrack().getAbility() == 0) {
			gui.setMessage("Missing red ability, can't reroll");
		} else {
			// Perform the reroll
//			gui.setMessage("Player " + currentPlayer + " rerolled");
			gui.setMessage(gui.getPlayerNames().get(currentPlayer) + " rerolled");
			currentState.rerollDice();

			// Update the available tiles and dice in the GUI
			gui.setAvailableTiles(List.of(currentState.getTiles()));
			gui.setAvailableDice(List.of(currentState.getDice()));

			// Update the red track ability
			currentState.getRedTrack().updateAbility();
			updateTrackInfo(currentPlayer, TrackType.RED);
		}
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
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		// Update the green track ability
		currentState.getGreenTrack().updateAbility();
		updateTrackInfo(currentPlayer, TrackType.GREEN);
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
		String[] currentS = currentDice.toArray(new String[currentDice.size() - 1]);
		// Update the rolled dice in the GameState and GUI
		currentState.setRolledDice(currentDice);
		currentState.setAvailableDice(currentS);//this ensures that if the ability is used again it knows what was previously changed
		gui.setAvailableDice(currentDice);
	}

	/**
	 * Handles the message when a player passes their turn.
	 * Displays the message that the player has skipped their turn and updates the GUI.
	 */
	private void handlePassTurnMessage() {
		// Display message for current player skipping their turn
//		gui.setMessage("Player " + currentPlayer + " skips their turn");
		gui.setMessage(gui.getPlayerNames().get(currentPlayer) + " skips their turn");

		// Update message for next player's turn
//		gui.setMessage("Now it's player " + currentPlayer + "'s turn");
		gui.setMessage("Now it's " + gui.getPlayerNames().get(currentPlayer) + "'s turn");
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
	 * implements logic to start the next turn
	 */
	private void startNextTurn() {
		updateCurrentPlayer();
		gui.setMessage("Player " + currentPlayer + "'s turn");
		updatePlayerStateForNextTurn();
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		gui.setAvailableDice(List.of(currentState.getDice()));
		// unlock pass button
		gui.setSelectDiceMode(false);
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
				updateTrackInfo(i, trackType);
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

		// Set the available tiles and dice in the GUI
		gui.setAvailableTiles(List.of(currentState.getTiles()));
		gui.setAvailableDice(List.of(currentState.getDice()));

		// Set available actions in the GUI
		gui.setAvailableActions(List.of("Reroll", "Change selected to Red", "Change selected to Blue",
				"Change selected to Purple", "Change selected to Green", "Change selected to Yellow"));

		// Set the control to the first player
		gui.setControlPlayer(0);
	}






}
