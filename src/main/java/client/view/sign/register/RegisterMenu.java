package client.view.sign.register;

import client.controller.sign.ClientRegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.RegisterMenusCommands;
import message.Result;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;

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
		ClientAppview.setStage(stage);
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

	public void register(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String passwordConfirm = confirmPasswordField.getText();
		String nickname = nicknameField.getText();
		String email = emailField.getText();
		System.err.println(username + " " + password + " " + passwordConfirm + " " + nickname + " " + email);
		Result result = ClientRegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		AlertMaker.makeAlert("register", result);
	}

	public void back(MouseEvent mouseEvent) {
		ClientRegisterMenusController.exit();
	}

	/*
	 * Terminal version of the RegisterMenu
	 */

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.REGISTER.getMatcher(input)) != null) result = register(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result register(Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		String passwordConfirm = matcher.group("passwordConfirm");
		String nickname = matcher.group("nickname");
		String email = matcher.group("email");
		Result result = ClientRegisterMenusController.register(username, password, passwordConfirm, nickname, email);
		if (result.isSuccessful()) {
			ClientAppview.setMenu(new PickQuestionMenu());
		}
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Register Menu", true);
	}

	private Result exit(Matcher matcher) {
		return ClientRegisterMenusController.exit();
	}


}