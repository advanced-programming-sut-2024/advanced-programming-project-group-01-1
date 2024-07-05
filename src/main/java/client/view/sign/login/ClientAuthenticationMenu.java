package client.view.sign.login;

import client.controller.sign.ClientLoginMenusController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.LoginMenusCommands;
import message.Result;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class ClientAuthenticationMenu extends Application implements Menuable {

	public TextField authenticationCodeField;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/AuthenticationMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/AuthenticationMenu.fxml");
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

	@FXML
	public void initialize() {
		ClientLoginMenusController.sendEmail();
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if (LoginMenusCommands.SEND_EMAIL.getMatcher(input) != null) result = ClientLoginMenusController.sendEmail();
		else if ((matcher = LoginMenusCommands.CHECK_CODE.getMatcher(input)) != null) result = checkCode(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = new Result("Authentication Menu", true);
		else if (LoginMenusCommands.EXIT.getMatcher(input) != null) result = ClientLoginMenusController.exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result checkCode(Matcher matcher) {
		String code = matcher.group("code");
		return ClientLoginMenusController.checkCode(code);
	}

	public void sendAgain(MouseEvent mouseEvent) {
		ClientLoginMenusController.sendEmail();
	}

	public void checkCode(MouseEvent mouseEvent) {
		ClientLoginMenusController.checkCode(authenticationCodeField.getText());
	}

	public void exit(MouseEvent mouseEvent) {
		ClientLoginMenusController.exit();
	}
}
