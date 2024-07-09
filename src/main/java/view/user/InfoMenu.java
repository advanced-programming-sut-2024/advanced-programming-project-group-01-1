package view.user;

import controller.UserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Result;
import model.user.User;
import view.Appview;
import view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class InfoMenu extends Application implements Menuable {
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
		if (url == null) {
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
		Image cursorImage = new Image(getClass().getResourceAsStream("/images/icons/cursor.png"));
		ImageView imageView = new ImageView(cursorImage);
		imageView.setFitWidth(25);
		imageView.setFitHeight(25);
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		cursorImage = imageView.snapshot(parameters, null);

		Cursor cursor = new ImageCursor(cursorImage);
		scene.setCursor(cursor);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		username.setText(User.getLoggedInUser().getUsername());
		nickname.setText(User.getLoggedInUser().getNickname());
		maxScore.setText(String.valueOf(User.getLoggedInUser().getMaxElo()));
		rank.setText(String.valueOf(User.getLoggedInUser().getRank()));
		playedMatches.setText(String.valueOf(User.getLoggedInUser().getNumberOfPlayedMatches()));
		wins.setText(String.valueOf(User.getLoggedInUser().getNumberOfWins()));
		draws.setText(String.valueOf(User.getLoggedInUser().getNumberOfDraws()));
		losses.setText(String.valueOf(User.getLoggedInUser().getNumberOfLosses()));
	}

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(input)) != null) result = showGameHistory(matcher);
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(input) != null) result = exit();
		else if (UserMenusCommands.HISTORY.getMatcher(input) != null) result = goToHistoryMenu();
		else result = new Result("Invalid command", false);
		System.out.println(result);
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

	public Result goToHistoryMenu() {
		return UserMenusController.goToHistoryMenu();
	}

	public void goToHistoryMenu(MouseEvent mouseEvent) {
		goToHistoryMenu();
	}

}
