package client.view.game.prematch;

import client.controller.game.ClientPreMatchMenusController;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;
import client.view.model.Request;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ClientMatchFinderMenu extends Application implements Menuable {

	public ListView suggestUsersList;
	public TextField usernameField;
	public Button sendRequest;
	public Button updateRequests;
	public ScrollPane requests;
	public Button stopWaiting;
	public Button quickMatch;
	public Button tournamentMatch;
	public Button back;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/MatchFinderMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/MatchFinderMenu.fxml");
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
		ArrayList<String> users = ClientPreMatchMenusController.getOtherUsernames();
		suggestUsersList.getItems().addAll(users);
		usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
			ArrayList<String> suggestedUsers = ClientPreMatchMenusController.getOtherUsernames();
			suggestUsersList.getItems().clear();
			for (String user : suggestedUsers) {
				if (user.startsWith(newValue)) {
					suggestUsersList.getItems().add(user);
				}
			}
		});
	}

	private void update(Node node, boolean disable) {
		node.setDisable(disable);
		node.setOpacity(disable ? 0 : 1);
	}

	public void update() {
		boolean isWaiting = ClientPreMatchMenusController.isWaiting().isSuccessful();
		update(suggestUsersList, isWaiting);
		update(usernameField, isWaiting);
		update(sendRequest, isWaiting);
		update(updateRequests, isWaiting);
		update(requests, isWaiting);
		requests.setOpacity(0);
		update(stopWaiting, !isWaiting);
		update(quickMatch, isWaiting);
		update(tournamentMatch, isWaiting);
		update(back, isWaiting);
	}

	public void sendRequest(MouseEvent mouseEvent) {
		String opponent = (String) suggestUsersList.getSelectionModel().getSelectedItem();
		if (opponent == null) {
			AlertMaker.makeAlert("request", new Result("No user selected", false));
			return;
		}
		Result result = ClientPreMatchMenusController.requestMatch(opponent, this);
		AlertMaker.makeAlert("GAME REQUEST", result);
		update();
	}

	public void stopWaiting(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.stopWait();
		update();
	}

	public void updateRequests(MouseEvent mouseEvent) {
		if (updateRequests.getText().equals("show requests")) {
			showRequests();
			if (updateRequests.getText().equals("show requests"))
				AlertMaker.makeAlert("Show Requests", new Result("No match requests", false));
		} else {
			requests.setOpacity(0);
			updateRequests.setText("show requests");
		}
	}

	public void showRequests() {
		Result result = ClientPreMatchMenusController.getMatchRequests();
		if (result.getMessage().isEmpty()) {
			requests.setOpacity(0);
			updateRequests.setText("show requests");
			return;
		}
		String[] requesters = result.getMessage().split("\n");
		VBox vBox = new VBox();
		for (String requester : requesters) {
			vBox.getChildren().add(new Request(requester, this));
		}
		requests.setContent(vBox);
		requests.setOpacity(0.7);
		requests.setMinHeight(Math.min(vBox.getPrefHeight() + 30, 200));
		updateRequests.setText("hide requests");
	}

	public void quickMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Quick Match", new Result("coming soon!!!", true));
	}

	public void tournamentMatch(MouseEvent mouseEvent) {
		AlertMaker.makeAlert("Tournament Match", new Result("coming soon!!!", true));
	}

	public void back(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.exit();
	}


	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if (ClientPreMatchMenusController.isWaiting().isSuccessful()) {
			if (GameMenusCommands.STOP_WAIT.getMatcher(input) != null)
				result = ClientPreMatchMenusController.stopWait();
			else if (GameMenusCommands.CHECK_REQUEST.getMatcher(input) != null)
				result = ClientPreMatchMenusController.checkRequest();
			else result = new Result("Invalid command", false);
			return result;
		}
		if ((matcher = GameMenusCommands.SEND_MATCH_REQUEST.getMatcher(input)) != null)
			result = sendMatchRequest(matcher);
		else if (GameMenusCommands.GET_MATCH_REQUESTS.getMatcher(input) != null)
			result = ClientPreMatchMenusController.getMatchRequests();
		else if ((matcher = GameMenusCommands.HANDLE_MATCH_REQUEST.getMatcher(input)) != null)
			result = handleMatchRequest(matcher);
		else if (GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(input) != null)
			result = ClientPreMatchMenusController.exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result sendMatchRequest(Matcher matcher) {
		String opponentUsername = matcher.group("opponent");
		return ClientPreMatchMenusController.requestMatch(opponentUsername, null);
	}

	private Result handleMatchRequest(Matcher matcher) {
		boolean accept = matcher.group("handle").equals("accept");
		String senderUsername = matcher.group("sender");
		return ClientPreMatchMenusController.handleMatchRequest(senderUsername, accept);
	}
}
