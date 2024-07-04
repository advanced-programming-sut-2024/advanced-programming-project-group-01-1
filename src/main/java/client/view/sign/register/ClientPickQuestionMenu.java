package client.view.sign.register;

import client.controller.sign.ClientRegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.RegisterMenusCommands;
import message.Result;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;
import client.view.sign.login.ClientLoginMenu;

import java.net.URL;
import java.util.regex.Matcher;

public class ClientPickQuestionMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage() {
		launch();
	}

	public ChoiceBox choiceBox;
	public TextField answerField;
	public TextField confirmAnswerField;


	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/PickQuestionMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/pickQuestion.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		String[] questions = ClientRegisterMenusController.getQuestions();
		for (int i = 0; i < questions.length; i++) {
			choiceBox.getItems().add(questions[i]);
		}
	}

	public void pickQuestion(MouseEvent mouseEvent) {
		String question = (String) choiceBox.getValue();
		String answer = answerField.getText();
		String answerConfirm = confirmAnswerField.getText();
		int questionNumber = -1;
		for (int i = 0; i < ClientRegisterMenusController.getQuestions().length; i++) {
			if (ClientRegisterMenusController.getQuestions()[i].equals(question)) {
				questionNumber = i;
				break;
			}
		}
		Result result = ClientRegisterMenusController.pickQuestion(questionNumber, answer, answerConfirm);
		AlertMaker.makeAlert("Pick Question", result);
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.PICK_QUESTION.getMatcher(input)) != null) result = pickQuestion(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		return result;
	}


	private Result pickQuestion(Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
		String answerConfirm = matcher.group("answerConfirm");
		Result result = ClientRegisterMenusController.pickQuestion(questionNumber, answer, answerConfirm);
		if (result.isSuccessful()) {
			ClientAppview.setMenu(new ClientLoginMenu());
		}
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Pick Question Menu", true);
	}

	private Result exit(Matcher matcher) {
		return ClientRegisterMenusController.exit();
	}

}
