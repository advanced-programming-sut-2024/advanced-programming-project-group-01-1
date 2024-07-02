package view.sign.login;

import controller.sign.LoginMenusController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Constants;
import view.Menuable;
import view.sign.register.RegisterMenu;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static view.AlertMaker.makeAlert;

public class LoginMenu extends Application implements Menuable {

    public TextField passwordField;
	public TextField usernameField;
	public CheckBox rememberMe;

	public static void createStage(){
		launch();
	}

	/**
	 * JavaFX version of the LoginMenu
	 */

	@Override
	public void start(Stage stage) {
		URL url = getClass().getResource("/FXML/loginMenu.fxml");
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

	public void login(KeyEvent keyEvent) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		boolean stayLoggedIn = rememberMe.isSelected();
		Result result = LoginMenusController.login(username, password, stayLoggedIn);
		makeAlert("Login", result);
	}

	public void register(KeyEvent keyEvent) {
		LoginMenusController.goToRegisterMenu();
	}

	public void forgotPassword(KeyEvent keyEvent) {
		String username = usernameField.getText();
		Result result = LoginMenusController.forgotPassword(username);
		makeAlert("Forgot Password", result);
	}

	/**
	 * Terminal version of the LoginMenu
	 */
	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.LOGIN.getMatcher(input)) != null) {
			result = login(matcher);
		} else if ((matcher = LoginMenusCommands.FORGOT_PASSWORD.getMatcher(input)) != null) {
			result = forgotPassword(matcher);
		} else if ((matcher = LoginMenusCommands.ENTER_REGISTER_MENU.getMatcher(input)) != null) {
			result = goToRegisterMenu(matcher);
		} else if ((matcher = LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		}
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
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
