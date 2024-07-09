package view.game.prematch;

import controller.game.PreMatchMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;
import view.game.GameMenusCommands;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class MatchFinderMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	public ListView suggestUsersList;
	public TextField usernameField;

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
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
		Image cursorImage = new Image(getClass().getResourceAsStream("/images/icons/cursor.png"));
		Cursor cursor = new ImageCursor(cursorImage);
		scene.setCursor(cursor);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		ArrayList<String> users = PreMatchMenusController.getUsernames();
		suggestUsersList.getItems().addAll(users);
		usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
			ArrayList<String> suggestedUsers = PreMatchMenusController.getUsernames();
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
		Result result = PreMatchMenusController.createGame(opponent);
		AlertMaker.makeAlert("Game created", result);
	}

	public void quickMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Quick Match", new Result("coming soon!!!", true));
	}

	public void tournamentMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Tournament Match", new Result("coming soon!!!", true));
	}

	public void back(MouseEvent mouseEvent) {
		PreMatchMenusController.exit();
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.CREATE_GAME.getMatcher(input)) != null) {
			result = createGame(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result createGame(Matcher matcher) {
		String opponent = matcher.group("opponent");
		Result result = PreMatchMenusController.createGame(opponent);
		return null;
	}
}
