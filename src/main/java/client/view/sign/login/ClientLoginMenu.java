package client.view.sign.login;

import client.controller.sign.ClientLoginMenusController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.LoginMenusCommands;
import message.Result;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static client.view.AlertMaker.makeAlert;

public class ClientLoginMenu extends Application implements Menuable {


	public void createStage() {
		launch();
	}

	public TextField passwordField;
	public TextField usernameField;
	public CheckBox rememberMe;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/LoginMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/loginMenu.fxml");
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

	public void login(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		boolean stayLoggedIn = rememberMe.isSelected();
		Result result = ClientLoginMenusController.login(username, password, stayLoggedIn);
		makeAlert("Login", result);
	}

	public void register(MouseEvent mouseEvent) {
		ClientLoginMenusController.goToRegisterMenu();
	}

	public void forgotPassword(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		Result result = ClientLoginMenusController.forgotPassword(username);
		makeAlert("Forgot Password", result);
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(input)) != null) result = login(matcher);
		else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(input)) != null)
			result = forgotPassword(matcher);
		else if (LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(input) != null) result = goToRegisterMenu();
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if (LoginMenusCommands.EXIT.getMatcher(input) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Login Menu", true);
	}

	private Result login(Matcher matcher) {
		String username = matcher.group("username");
		String password = matcher.group("password");
		boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;
		return ClientLoginMenusController.login(username, password, stayLoggedIn);
	}

	private Result forgotPassword(Matcher matcher) {
		String username = matcher.group("username");
		return ClientLoginMenusController.forgotPassword(username);
	}

	private Result goToRegisterMenu() {
		return ClientLoginMenusController.goToRegisterMenu();
	}

	private Result exit() {
		return ClientLoginMenusController.exit();
	}

}
