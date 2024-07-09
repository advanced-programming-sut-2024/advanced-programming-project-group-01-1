package view.sign.login;

import controller.sign.LoginMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class SetPasswordMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	public TextField passwordField;

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/SetPasswordMenu.fxml");
		if (url == null){
			System.out.println("Couldn't find file: FXML/SetPasswordMenu.fxml");
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


	public void setPassword(MouseEvent mouseEvent) {
		String password = passwordField.getText();
		Result result = LoginMenusController.setPassword(password);
		AlertMaker.makeAlert("set password", result);
	}


	/*
	 * Terminal version of the LobbyMenu
	 */

	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(input)) != null) {
			result = setPassword(matcher);
		} else if ((matcher = LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result showCurrentMenu() {
		return new Result("Set Password Menu", true);
	}

	private Result setPassword(Matcher matcher) {
		String password = matcher.group("password");
		Result result = LoginMenusController.setPassword(password);
		if (result.isSuccessful()) {
			Appview.setMenu(new LoginMenu());
		}
		return result;
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}

}
