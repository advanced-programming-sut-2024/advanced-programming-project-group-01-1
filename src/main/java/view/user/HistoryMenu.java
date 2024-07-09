package view.user;

import controller.UserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class HistoryMenu extends Application implements Menuable {

	@FXML
	private TextField numberOfGames;

	@FXML
	private ListView<HBox> gamesList;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/HistoryMenu.fxml");
		if (url == null){
			System.out.println("Couldn't find file: FXML/HistoryMenu.fxml");
			return;
		}
		Pane root = null;
		try {root = FXMLLoader.load(url);
		} catch (IOException e){throw new RuntimeException(e);
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

	@Override
	public void run(String input) {
		Result result = null;
		Matcher matcher;
		if ((matcher = UserMenusCommands.GAME_HISTORY.getMatcher(input)) != null) {
			String numberOfGames = matcher.group("numberOfGames");
			result = gameHistory(numberOfGames != null ? Integer.parseInt(matcher.group("numberOfGames")) : 0);
		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) {
			result = new Result("History Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(input) != null) {
			result = exit();
		} else {
			result = new Result("Invalid command", false);
		}
		System.out.println(result.getMessage());
	}

	private Result gameHistory(int number) {
		if (number == 0) number = 10000000;
		Result result = UserMenusController.showGameHistory(number);
		String histories = result.getMessage();
		String[] history = histories.split("\n\n");
		gamesList.getItems().clear();
		for (String gameDetails : history)
			gamesList.getItems().add(rowCreator(gameDetails));
		return new Result("Showing game history", true);
	}

	public void gameHistory(MouseEvent mouseEvent) {
		try {
			gameHistory(Integer.parseInt(numberOfGames.getText()));
		} catch (NumberFormatException e) {
			System.out.println("Invalid number of games");
		}
	}

	private HBox rowCreator(String gameDetails) {
		String[] gameDetail = gameDetails.split("\n");
		String opponent = gameDetail[0].split(": ")[1];
		String date = gameDetail[1].split(": ")[1];
		String gameResult = gameDetail[6].split(": ")[1];
		int myRoundScore = Integer.parseInt(gameDetail[4].split(": ")[1]);
		int opponentRoundScore = Integer.parseInt(gameDetail[5].split(": ")[1]);
		ArrayList<Integer> myScores = new ArrayList<>();
		ArrayList<Integer> opponentScores = new ArrayList<>();
		String[] myScoresString = gameDetail[2].split(": ")[1].substring(1).split("(, |])");
		String[] opponentScoresString = gameDetail[3].split(": ")[1].substring(1).split("(, |])");
		for (String score : myScoresString) {
			myScores.add(Integer.parseInt(score));
		}
		for (String score : opponentScoresString) {
			opponentScores.add(Integer.parseInt(score));
		}
		HBox game = new HBox();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/HistoryMenuRow.fxml"));
		try {
			game = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HistoryMenuRowController controller = loader.getController();
		controller.setOpponent(opponent);
		controller.setDate(date);
		controller.setResult(gameResult);
		controller.setRoundScore(myRoundScore, opponentRoundScore);
		controller.setRoundScores(myScores, opponentScores);
		return game;
	}

	private Result exit() {
		return UserMenusController.exit();
	}

	public void exit(MouseEvent mouseEvent) {
		exit();
	}
}
