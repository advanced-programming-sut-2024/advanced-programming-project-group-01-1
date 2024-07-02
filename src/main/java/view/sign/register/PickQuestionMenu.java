package view.sign.register;

import controller.sign.RegisterMenusController;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;
import view.sign.login.LoginMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class PickQuestionMenu implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	@Override
	public void start(Stage stage) {
		// TODO:
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
