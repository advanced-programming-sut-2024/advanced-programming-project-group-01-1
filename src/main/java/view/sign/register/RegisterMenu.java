package view.sign.register;

import controller.sign.RegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class RegisterMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the RegisterMenu
	 */

	public void createStage() {
		launch();
	}

	public TextField usernameField;
	public TextField passwordField;
	public TextField confirmPasswordField;
	public TextField nicknameField;
	public TextField emailField;


	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/RegisterMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/RegisterMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		Image cursorImage = new Image(getClass().getResourceAsStream("/images/icons/cursor.png"));
		Cursor cursor = new ImageCursor(cursorImage);
		scene.setCursor(cursor);
		stage.setScene(scene);
		stage.show();
	}

	public void register(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String passwordConfirm = confirmPasswordField.getText();
		String nickname = nicknameField.getText();
		String email = emailField.getText();
		System.err.println(username + " " + password + " " + passwordConfirm + " " + nickname + " " + email);
		Result result = RegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		AlertMaker.makeAlert("register", result);
	}

	public void back(MouseEvent mouseEvent) {
		RegisterMenusController.exit();
	}

	/*
	 * Terminal version of the RegisterMenu
	 */

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(input)) != null) result = register(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		System.out.println(result);
	}

	private Result register(Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		String passwordConfirm = matcher.group("passwordConfirm");
		String nickname = matcher.group("nickname");
		String email = matcher.group("email");
		Result result = RegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		if (result.isSuccessful()) {
			Appview.setMenu(new PickQuestionMenu());
		}
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Matcher matcher) {
		return RegisterMenusController.exit();
	}


}
