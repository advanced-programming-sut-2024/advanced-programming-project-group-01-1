package client.view;

import client.controller.ClientMainMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.MainMenuCommands;
import message.Result;

import java.net.URL;

import static client.controller.ClientMainMenuController.*;

public class ClientMainMenu extends Application implements Menuable {


	@Override
	public void createStage() {
		launch();
	}

	public Label username;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/MainMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/MainMenu.fxml");
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
		username.setText(ClientMainMenuController.getLoggedInUsername());
	}

	public void newGame(MouseEvent mouseEvent) {
		Result result = goToMatchFinderMenu();
	}

	public void profile(MouseEvent mouseEvent) {
		Result result = goToProfileMenu();
	}

	public void leaderboard(MouseEvent mouseEvent) {
		Result result = new Result("coming soon!!!", true);
		AlertMaker.makeAlert("leaderboard", result);
		//TODO: implement leaderboard
	}

	public void tv(MouseEvent mouseEvent) {
		Result result = new Result("coming soon!!!", true);
		AlertMaker.makeAlert("GoGo TV", result);
		//TODO: implement GoGo TV
	}

	public void logout(MouseEvent mouseEvent) {
		Result result = ClientMainMenuController.logout();
		AlertMaker.makeAlert("Logout", result);
	}

	@Override
	public Result run(String input) {
		Result result;
		if (MainMenuCommands.ENTER_GAME_MENU.getMatcher(input) != null) result = goToMatchFinderMenu();
		else if (MainMenuCommands.ENTER_PROFILE_MENU.getMatcher(input) != null) result = goToProfileMenu();
		else if (MainMenuCommands.LOGOUT.getMatcher(input) != null) result = ClientMainMenuController.logout();
		else if (MainMenuCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) result = showCurrentMenu();
		else if (MainMenuCommands.GET_LOGGED_IN_USERNAME.getMatcher(input) != null) result = new Result(getLoggedInUsername(), true);
		else if (MainMenuCommands.SHOW_FRIENDS.getMatcher(input) != null) result = showFriends();
		else if (MainMenuCommands.SHOW_RECEIVED_FRIEND_REQUESTS.getMatcher(input) != null) result = showReceivedFriendRequests();
		else if (MainMenuCommands.SHOW_SENT_FRIEND_REQUESTS.getMatcher(input) != null) result = showSentFriendRequests();
		else if (MainMenuCommands.ACCEPT_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = MainMenuCommands.ACCEPT_FRIEND_REQUEST.getMatcher(input).group("username");
			result = acceptFriendRequest(username);
		} else if (MainMenuCommands.DECLINE_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = MainMenuCommands.DECLINE_FRIEND_REQUEST.getMatcher(input).group("username");
			result = declineFriendRequest(username);
		} else if (MainMenuCommands.REMOVE_FRIEND.getMatcher(input) != null) {
			String username = MainMenuCommands.REMOVE_FRIEND.getMatcher(input).group("username");
			result = removeFriend(username);
		} else if (MainMenuCommands.SEND_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = MainMenuCommands.SEND_FRIEND_REQUEST.getMatcher(input).group("username");
			result = sendFriendRequest(username);
		} else if (MainMenuCommands.UNSEND_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = MainMenuCommands.UNSEND_FRIEND_REQUEST.getMatcher(input).group("username");
			result = unsendFriendRequest(username);
		}
		else result = new Result("Invalid command", false);
		return result;
	}

}
