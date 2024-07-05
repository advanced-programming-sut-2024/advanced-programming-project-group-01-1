package client.view.game.prematch;

import client.controller.game.ClientPreMatchMenusController;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;
import server.controller.game.PreMatchMenusController;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ClientMatchFinderMenu extends Application implements Menuable {


	@Override
	public void createStage() {
		launch();
	}

	public ListView suggestUsersList;
	public TextField usernameField;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/MatchFinderMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/MatchFinderMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		ArrayList<String> users = ClientPreMatchMenusController.getUsernames();
		suggestUsersList.getItems().addAll(users);
		usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
			ArrayList<String> suggestedUsers = ClientPreMatchMenusController.getUsernames();
			suggestUsersList.getItems().clear();
			for (String user : suggestedUsers) {
				if (user.startsWith(newValue)) {
					suggestUsersList.getItems().add(user);
				}
			}
		});
	}

	public void startGame(MouseEvent mouseEvent) {
		String opponent = (String) suggestUsersList.getSelectionModel().getSelectedItem();
		Result result = ClientPreMatchMenusController.createGame(opponent);
		AlertMaker.makeAlert("GAME", result);
	}

	public void quickMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Quick Match", new Result("coming soon!!!", true));
	}

	public void tournamentMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Tournament Match", new Result("coming soon!!!", true));
	}

	public void back(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.exit();
	}


	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.CREATE_GAME.getMatcher(input)) != null) result = createGame(matcher);
		else if (GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(input) != null) result = PreMatchMenusController.exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result createGame(Matcher matcher) {
		String opponent = matcher.group("opponent");
		return ClientPreMatchMenusController.createGame(opponent);
	}
}
