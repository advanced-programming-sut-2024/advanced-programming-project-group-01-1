package view.sign.register;

import controller.sign.RegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;
import view.sign.login.LoginMenu;

import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class PickQuestionMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	public ChoiceBox choiceBox;
	public TextField answerField;
	public TextField confirmAnswerField;


	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
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
	public void initialize(){
		String[] questions = RegisterMenusController.getQuestions();
		for (int i = 0; i < questions.length; i++){
			choiceBox.getItems().add(questions[i]);
		}
	}

	public void pickQuestion(MouseEvent mouseEvent){
		String question = (String) choiceBox.getValue();
		String answer = answerField.getText();
		String answerConfirm = confirmAnswerField.getText();
		int questionNumber = -1;
		for (int i = 0; i < RegisterMenusController.getQuestions().length; i++){
			if (RegisterMenusController.getQuestions()[i].equals(question)){
				questionNumber = i;
				break;
			}
		}
		Result result = RegisterMenusController.pickQuestion(questionNumber, answer, answerConfirm);
		AlertMaker.makeAlert("Pick Question", result);
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = RegisterMenusCommands.PICK_QUESTION.getMatcher(input)) != null) {
			result = pickQuestion(matcher);
		} else if ((matcher = RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input)) != null) {
			result = showCurrentMenu();
		} else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) {
			result = exit(matcher);
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}


	private Result pickQuestion(Matcher matcher) {
		int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
		String answer = matcher.group("answer");
		String answerConfirm = matcher.group("answerConfirm");
		Result result = RegisterMenusController.pickQuestion(questionNumber, answer, answerConfirm);
		if (result.isSuccessful()) {
			Appview.setMenu(new LoginMenu());
		}
		return result;
	}

	private Result showCurrentMenu() {
		return new Result("Pick Question Menu", true);
	}

	private Result exit(Matcher matcher) {
		return RegisterMenusController.exit();
	}

}
