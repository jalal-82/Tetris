package comp1110.ass2;

import comp1110.ass2.gui.*;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class GameTemplate extends Application {

    GameGUI gui;

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GameGUI();
        Scene scene = new Scene(gui, GameGUI.WINDOW_WIDTH, GameGUI.WINDOW_HEIGHT);

	// This is where you should set up callbacks (or at least one
	// callback, for the start-of-game event).

	// The following are only provided as examples; you should
	// replace them with your own.
	
	gui.setOnStartGame((np, isAI) -> {
		gui.setMessage("start new game with " + np + " players");
		gui.setAvailableTiles(List.of("R2", "R3", "R4", "R5", "B2", "B3", "B4L", "B4R", "B5"));
		gui.setAvailableDice(List.of("Red", "White", "Blue", "Red", "Yellow"));
		gui.setAvailableActions(List.of("Reroll", "Give up"));
	    });

	gui.setOnTilePlaced((p) -> {
		gui.setMessage("tile placed: " + p);
		if (p.getTileName().equals("R5"))
		    gui.endGame(new int[4]);
	    });

	gui.setOnDiceSelectionChanged((i) -> {
		gui.setMessage("dice selection: " + gui.getSelectedDice());
	    });

	gui.setOnTrackSelectionChanged((i) -> {
		gui.setMessage("track selection: " + gui.getSelectedTracks());
	    });

	gui.setOnGameAction((s) -> {
		gui.setMessage("action: " + s);
		if (s.equals("Give up"))
		    gui.setAvailableActions(List.of("Reroll"));
	    });

	gui.setOnConfirm((s) -> {
		gui.setMessage("confirm: " + s);
	    });

	gui.setOnPass((s) -> {
		gui.setMessage("pass: " + s);
	    });

	// Start the application:
        stage.setScene(scene);
        stage.setTitle("Copenhagen Roll & Write");
        stage.show();
    }

}
