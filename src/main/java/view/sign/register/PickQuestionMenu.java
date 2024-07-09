package view.sign.register;

import controller.sign.RegisterMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Result;
import view.AlertMaker;
import view.Appview;
import view.Menuable;
import view.sign.login.LoginMenu;

import java.net.URL;
import java.util.regex.Matcher;

public class PickQuestionMenu extends Application implements Menuable {

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
		Image cursorImage = new Image(getClass().getResourceAsStream("/images/icons/cursor.png"));
		ImageView imageView = new ImageView(cursorImage);
		imageView.setFitWidth(25);
		imageView.setFitHeight(25);
		SnapshotParameters parameters = new SnapshotParameters();
		parameters.setFill(Color.TRANSPARENT);
		cursorImage = imageView.snapshot(parameters, null);

		Cursor cursor = new ImageCursor(cursorImage);
		scene.setCursor(cursor);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		String[] questions = RegisterMenusController.getQuestions();
		for (int i = 0; i < questions.length; i++) {
			choiceBox.getItems().add(questions[i]);
		}
	}

	public void pickQuestion(MouseEvent mouseEvent) {
		String question = (String) choiceBox.getValue();
		String answer = answerField.getText();
		String answerConfirm = confirmAnswerField.getText();
		int questionNumber = -1;
		for (int i = 0; i < RegisterMenusController.getQuestions().length; i++) {
			if (RegisterMenusController.getQuestions()[i].equals(question)) {
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
		if ((matcher = RegisterMenusCommands.PICK_QUESTION.getMatcher(input)) != null) result = pickQuestion(matcher);
		else if (RegisterMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if ((matcher = RegisterMenusCommands.EXIT.getMatcher(input)) != null) result = exit(matcher);
		else result = new Result("Invalid command", false);
		System.out.println(result);
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
