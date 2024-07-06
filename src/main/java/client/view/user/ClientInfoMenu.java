package client.view.user;

import client.controller.ClientUserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;
import client.view.ClientAppview;
import client.view.Menuable;
import message.UserMenusCommands;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class ClientInfoMenu extends Application implements Menuable {
	public Label username;
	public Label nickname;
	public Label maxScore;
	public Label rank;
	public Label playedMatches;
	public Label wins;
	public Label draws;
	public Label losses;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/InfoMenu.fxml");
		if (url == null){
			System.out.println("Couldn't find file: FXML/InfoMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		username.setText(ClientUserMenusController.getUsername());
		nickname.setText(ClientUserMenusController.getNickname());
		maxScore.setText(ClientUserMenusController.getMaxScore());
		rank.setText(ClientUserMenusController.getRank());
		playedMatches.setText(ClientUserMenusController.getNumberOfPlayedMatches());
		wins.setText(ClientUserMenusController.getNumberOfWins());
		draws.setText(ClientUserMenusController.getNumberOfDraws());
		losses.setText(ClientUserMenusController.getNumberOfLosses());
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(input)) != null) result = showGameHistory(matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(input) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showGameHistory(Matcher matcher) {
		int numberOfGames = (matcher.group("numberOfGames") != null ? Integer.parseInt(matcher.group("numberOfGames")) : 5);
		return ClientUserMenusController.showGameHistory(numberOfGames);
	}

	private Result showCurrentMenu() {
		return new Result("User Info Menu", true);
	}

	@FXML
	private Result exit() {
		return ClientUserMenusController.exit();
	}

}
