package client.view.sign.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import client.controller.sign.ClientLoginMenusController;
import message.LoginMenusCommands;
import message.Result;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class ClientSetPasswordMenu extends Application implements Menuable {

	@Override
	public void createStage() {
		launch();
	}

	public TextField passwordField;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
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
		Result result = ClientLoginMenusController.setPassword(password);
		AlertMaker.makeAlert("set password", result);
	}

	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.SET_PASSWORD.getMatcher(input)) != null) result = setPassword(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Set Password Menu", true);
	}

	private Result setPassword(Matcher matcher) {
		String password = matcher.group("password");
		Result result = ClientLoginMenusController.setPassword(password);
		if (result.isSuccessful()) {
			ClientAppview.setMenu(new ClientLoginMenu());
		}
		return result;
	}

	private Result exit(Matcher matcher) {
		return ClientLoginMenusController.exit();
	}

}
