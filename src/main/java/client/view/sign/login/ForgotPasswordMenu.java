package client.view.sign.login;

import client.controller.sign.ClientLoginMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.LoginMenusCommands;
import message.Result;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

public class ForgotPasswordMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage() {
		launch();
	}

	public Label question;
	public TextField answerField;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/ForgotPasswordMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/ForgotPasswordMenu.fxml");
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
		question.setText(ClientLoginMenusController.getQuestion());
	}

	public void answerQuestion(MouseEvent mouseEvent) {
		String answer = answerField.getText();
		Result result = ClientLoginMenusController.answerQuestion(answer);
		AlertMaker.makeAlert("Forgot Password", result);
	}

	public void back(MouseEvent mouseEvent) {
		ClientLoginMenusController.exit();
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = LoginMenusCommands.ANSWER_QUESTION.getMatcher(input)) != null) result = answerQuestion(matcher);
		else if (LoginMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = LoginMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Forgot Password Menu", true);
	}

	private Result answerQuestion(Matcher matcher) {
		String answer = matcher.group("answer");
		return ClientLoginMenusController.answerQuestion(answer);
	}

	private Result exit(Matcher matcher) {
		return ClientLoginMenusController.exit();
	}

}
