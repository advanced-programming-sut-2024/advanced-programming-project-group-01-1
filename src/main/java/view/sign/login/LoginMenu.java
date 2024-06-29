package view.sign.login;

import controller.sign.LoginMenusController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Constants;
import view.Menuable;
import view.sign.register.RegisterMenu;

import java.util.regex.Matcher;

public class LoginMenu extends Application implements Menuable {

	public static void createStage(){
		launch();
	}

	/**
	 * JavaFX version of the LoginMenu
	 */

	@Override
	public void start(Stage stage) {
		stage.setFullScreen(true);
		Pane root = new Pane();
		for (int i = 0; i < 1; i++) {
			Button button = new Button("Button " + i);
			button.setPrefHeight(Constants.STAGE_HEIGHT.getValue());
			button.setPrefWidth(Constants.STAGE_WIDTH.getValue());
			root.getChildren().add(button);
		}
		Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
		stage.setScene(scene);
		stage.show();
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
