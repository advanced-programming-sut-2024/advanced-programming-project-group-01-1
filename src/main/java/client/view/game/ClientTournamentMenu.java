package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.controller.game.ClientTournamentMenuController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;

import java.io.IOException;
import java.net.URL;

public class ClientTournamentMenu extends Application implements Menuable {

	@FXML
	private Pane basePane;

	private Label[] seedLabels = new Label[28], seedScores = new Label[30];

	private int counter = 0;

	@FXML
	public void initialize() {
		for (int i = 5; i < 5 + 14; i++) {
			Pane match = (Pane) basePane.getChildren().get(i);
			seedLabels[(i - 5) * 2] = (Label) match.getChildren().get(0);
			seedLabels[(i - 5) * 2 + 1] = (Label) match.getChildren().get(1);
			seedScores[(i - 5) * 2] = (Label) match.getChildren().get(2);
			seedScores[(i - 5) * 2 + 1] = (Label) match.getChildren().get(3);
			if (i == 5 + 14 - 1) {
				seedScores[(i - 5) * 2 + 2] = (Label) match.getChildren().get(4);
				seedScores[(i - 5) * 2 + 3] = (Label) match.getChildren().get(5);
			}
		}
		ClientTournamentMenuController.startUpdatingBracket(this);
	}

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/TournamentMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/TournamentMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Result run(String input) {
		Result result = null;
		if (GameMenusCommands.GET_TOURNAMENT_INFO.getMatcher(input).matches()) {
			result = ClientTournamentMenuController.getTournamentInfo();
		} else if (GameMenusCommands.READY.getMatcher(input).matches()) {
			result = setPlayerReady();
		}
		return result;
	}

	public Result setPlayerReady() {
		System.out.println("Player is ready");
		return ClientTournamentMenuController.setPlayerReady();
	}

	public void updateScreen() {
		Result info = ClientTournamentMenuController.getTournamentInfo();
		if (!info.isSuccessful()) {
			System.out.println("Couldn't get tournament info");
			return;
		}
		String[] lines = info.getMessage().split("\n");
		for (int i = 0; i < 28; i++) {
			String[] parts = lines[i].equals(" ") ? new String[2] : lines[i].split(" ");
			seedLabels[i].setText(parts[0]);
			seedScores[i].setText(parts[1] == null || parts[1].equals("-1") ? "" : parts[1]);
		}
	}

}
