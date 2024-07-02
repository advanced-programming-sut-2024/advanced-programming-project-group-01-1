package view.sign.register;

import controller.sign.RegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;
import view.sign.login.LoginMenu;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class RegisterMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the RegisterMenu
	 */

	public void createStage(){
		launch();
	}

	public TextField usernameField;
	public TextField passwordField;
	public TextField confirmPasswordField;
	public TextField nicknameField;
	public TextField emailField;


	@Override
	public void start(Stage stage) {
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
		stage.setScene(scene);
		stage.show();
	}

	public void register(KeyEvent keyEvent) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String passwordConfirm = confirmPasswordField.getText();
		String nickname = nicknameField.getText();
		String email = emailField.getText();
		Result result = RegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		AlertMaker.makeAlert("register", result);
	}

	public void back(KeyEvent keyEvent) {
		RegisterMenusController.exit();
	}

	/*
	* Terminal version of the RegisterMenu
	*/

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(input)) != null) {
			result = register(matcher);
		} else if ((matcher = RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
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
		return null;
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Matcher matcher) {
		return RegisterMenusController.exit();
	}


}
