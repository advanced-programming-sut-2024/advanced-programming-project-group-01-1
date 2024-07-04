package server.view.sign.login;

import message.Command;
import message.LoginMenusCommands;
import server.controller.sign.LoginMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;
import server.view.Appview;
import server.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static server.view.AlertMaker.makeAlert;

public class LoginMenu extends Application implements Menuable {


	/**
	 * JavaFX version of the LoginMenu
	 */


	public void createStage() {
		launch();
	}

	public TextField passwordField;
	public TextField usernameField;
	public CheckBox rememberMe;

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
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
		Result result = LoginMenusController.login(username, password, stayLoggedIn);
		makeAlert("Login", result);
	}

	public void register(MouseEvent mouseEvent) {
		LoginMenusController.goToRegisterMenu();
	}

	public void forgotPassword(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		Result result = LoginMenusController.forgotPassword(username);
		makeAlert("Forgot Password", result);
	}

	/**
	 * Terminal version of the LoginMenu
	 * @param command
	 */
	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(command.getCommand())) != null) result = login(matcher);
		else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(command.getCommand())) != null)
			result = forgotPassword(matcher);
		else if ((matcher = LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(command.getCommand())) != null)
			result = goToRegisterMenu(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null) result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command.getCommand())) != null) result = exit(matcher);
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
		return LoginMenusController.login(username, password, stayLoggedIn);
	}

	private Result forgotPassword(Matcher matcher) {
		String username = matcher.group("username");
		return LoginMenusController.forgotPassword(username);
	}

	private Result goToRegisterMenu(Matcher matcher) {
		return LoginMenusController.goToRegisterMenu();
	}

	private Result exit(Matcher matcher) {
		return LoginMenusController.exit();
	}


}
