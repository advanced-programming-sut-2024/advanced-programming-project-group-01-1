package server.view.sign.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Command;
import message.LoginMenusCommands;
import server.controller.sign.LoginMenusController;
import message.Result;
import server.view.AlertMaker;
import server.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class SetPasswordMenu extends Application implements Menuable {

	@Override
	public void createStage() {
		launch();
	}

	public TextField passwordField;

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/SetPasswordMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/SetPasswordMenu.fxml");
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


	public void setPassword(MouseEvent mouseEvent) {
		String password = passwordField.getText();
		Result result = LoginMenusController.setPassword(password);
		AlertMaker.makeAlert("set password", result);
	}

	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(command.getCommand())) != null) result = setPassword(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null) result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(command.getCommand())) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
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
