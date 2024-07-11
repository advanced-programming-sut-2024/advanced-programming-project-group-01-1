package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.controller.game.ClientTVMenuController;
import client.controller.game.ClientTournamentMenuController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;
import server.controller.game.TournamentMenuController;

import java.io.IOException;
import java.net.URL;

public class ClientTournamentMenu extends Application implements Menuable {

	@FXML
	private Pane basePane;

	private final Pane[] matches = new Pane[14];
	private final Label[] seedLabels = new Label[28], seedScores = new Label[30];

	private int counter = 0;

	@FXML
	public void initialize() {
		for (int i = 5; i < 5 + 14; i++) {
			Pane match = (Pane) basePane.getChildren().get(i);
			matches[i - 5] = match;
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
		System.out.println(info);
		if (lines[0].equals("Placements:")) {
			showEndScreen(lines);
			return;
		}
		for (int i = 0; i < 28; i++) {
			String[] parts = lines[i].equals(" ") ? new String[2] : lines[i].split(" ");
			seedLabels[i].setText(parts[0]);
			seedScores[i].setText(parts[1] == null || parts[1].equals("-1") ? "" : parts[1]);
		}
		for (int i = 0; i < 28; i += 2) {
			if (seedScores[i].getText().equals("2") && !seedScores[i + 1].getText().equals("2")) {
				seedScores[i].setStyle("-fx-background-color: lightgreen;");
			} else if (seedScores[i + 1].getText().equals("2") && !seedScores[i].getText().equals("2")) {
				seedScores[i + 1].setStyle("-fx-background-color: lightgreen;");
			} else if (!seedScores[i].getText().equals("") && !seedScores[i + 1].getText().equals("")) {
				matches[i / 2].setStyle("-fx-background-color: lightgreen");
				final int idx = i;
				matches[i / 2].setOnMouseClicked(event -> {
					System.out.println(ClientTournamentMenuController.getUsername());
					ClientTVMenuController.spectate(seedLabels[idx].getText(), seedLabels[idx + 1].getText());
				});
			}
		}
		for (int i = 0; i < 28; i++) {
			if (seedScores[i].getText().equals("") || seedScores[i].getText().equals("2")) {
				matches[i / 2].setStyle("-fx-background-color: black");
				matches[i / 2].setOnMouseClicked(mouseEvent -> {});
			}
		}
	}

	private void showEndScreen(String[] lines) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/TournamentEndingScreen.fxml"));
		Pane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		((Pane) ClientAppview.getStage().getScene().getRoot()).getChildren().add(pane);
		String username = ClientTournamentMenuController.getUsername();
		System.out.println(username);
		for (int i = 0; i < 4; i++) {
			HBox hBox = (HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(i);
			Label label = new Label(lines[i + 1]);
			label.setStyle("-fx-font-size: 20; " + "-fx-font-family: 'Mason Serif Regular';"
					+ (i == 0 ? "-fx-text-fill: gold" : i == 1 ? "-fx-text-fill: silver" :
					i == 2 ? "-fx-text-fill: #8B4513" : "-fx-text-fill: white"));
			if (username.equals(lines[i + 1])) {
				hBox.setStyle("-fx-background-color: rgba(57, 227, 61, 0.5)");
			}
			((VBox) hBox.getChildren().get(1)).getChildren().add(label);
		}
		for (int i = 4; i < 8; i += 2) {
			HBox hBox = (HBox) ((VBox) pane.getChildren().get(0)).getChildren().get(4 + (i - 4) / 2);
			Label label1 = new Label(lines[i + 1]);
			label1.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-family: 'Mason Serif Regular'");
			Label label2 = new Label(lines[i + 2]);
			label2.setStyle("-fx-font-size: 20; -fx-text-fill: white; -fx-font-family: 'Mason Serif Regular'");
			if (username.equals(lines[i + 1]) || username.equals(lines[i + 2])) {
				hBox.setStyle("-fx-background-color: rgba(57, 227, 61, 0.5)");
			}
			((VBox) hBox.getChildren().get(1)).getChildren().add(label1);
			((VBox) hBox.getChildren().get(1)).getChildren().add(label2);
		}
		ClientTournamentMenuController.stopBracketThread();
	}

	public Result exit() {
		return ClientTournamentMenuController.exit();
	}

}
