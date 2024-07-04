package server.view.user;

import message.Command;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;
import server.model.user.User;
import server.view.Appview;
import server.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class InfoMenu implements Menuable {
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
		Appview.setStage(stage);
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
		username.setText(User.getLoggedInUser().getUsername());
		nickname.setText(User.getLoggedInUser().getNickname());
		maxScore.setText(String.valueOf(User.getLoggedInUser().getMaxScore()));
		rank.setText(String.valueOf(User.getLoggedInUser().getRank()));
		playedMatches.setText(String.valueOf(User.getLoggedInUser().getNumberOfPlayedMatches()));
		wins.setText(String.valueOf(User.getLoggedInUser().getNumberOfWins()));
		draws.setText(String.valueOf(User.getLoggedInUser().getNumberOfDraws()));
		losses.setText(String.valueOf(User.getLoggedInUser().getNumberOfLosses()));
	}

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(command.getCommand())) != null) result = showGameHistory(matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null) result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command.getCommand()) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showGameHistory(Matcher matcher) {
		int numberOfGames = (matcher.group("numberOfGames") != null ? Integer.parseInt(matcher.group("numberOfGames")) : 5);
		return UserMenusController.showGameHistory(numberOfGames);
	}

	private Result showCurrentMenu() {
		return new Result("User Info Menu", true);
	}

	@FXML
	private Result exit() {
		return UserMenusController.exit();
	}

}
