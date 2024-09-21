
package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.BiConsumer;

public class GameTemplate extends Application {

    GameGUI gui;
	GameState G1;

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GameGUI();
		Player P1 = new Player();
		Dices D1 = new Dices();
		D1.applyPresetDiceD2CP1("R", "R", "R", "B", "W");
		Tile T1 = new Tile(D1);
		Score S1 = new Score();
		Abilities A1 = new Abilities();
		Bonus B1 = new Bonus("Red", 2);
		G1 = new GameState(P1, D1, T1, S1, A1, B1);
        Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

	// This is where you should set up callbacks (or at least one
	// callback, for the start-of-game event).

	gui.setOnStartGame((np, isAI) -> {
		gui.setMessage("start new game with " + np + " players");
		gui.setAvailableTiles(List.of(T1.generateTiles(D1)));
		gui.setAvailableDice(List.of(D1.getAllDice()));
		gui.setAvailableActions(List.of("Reroll", "Give up"));
	    });

	gui.setOnTilePlaced((p) -> {
		G1.placeTile( p.getY(), p.getX());
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
//
//	gui.setOnGameAction((s) -> {
//		gui.setMessage("action: " + s);
//		if (s.equals("Give up")) {
//			gui.setAvailableActions(List.of("Reroll"));
//		}
//		});
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
		char[][] board = G1.getGameBoard();
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
}}



