package client.view.user;

import client.controller.ClientUserMenusController;
import client.controller.game.ClientPreMatchMenusController;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import message.Result;
import message.UserMenusCommands;
import client.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ClientSocialMenu extends Application implements Menuable {

	@FXML
	private ListView<String> suggestUsersList = new ListView<>();

	@FXML
	private TextField usernameField;

	@FXML
	private ListView<Label> friendsList;

	@FXML
	private ToggleGroup show;

	@FXML
	private RadioButton showFriends, showReceivedFriendRequests, showSentFriendRequests;

	private String previousFriendList = "";

	@FXML
	private void initialize() {
		ArrayList<String> users = ClientUserMenusController.showPlayersInfo();
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
		ClientUserMenusController.startUpdating(this);
	}


	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/SocialMenu.fxml");
		if (url == null){
			System.out.println("Couldn't find file: FXML/SocialMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result = null;
		if (UserMenusCommands.SHOW_FRIENDS.getMatcher(input) != null) result = ClientUserMenusController.showFriends();
		else if (UserMenusCommands.SHOW_RECEIVED_FRIEND_REQUESTS.getMatcher(input) != null) result = ClientUserMenusController.showReceivedFriendRequests();
		else if (UserMenusCommands.SHOW_SENT_FRIEND_REQUESTS.getMatcher(input) != null) result = ClientUserMenusController.showSentFriendRequests();
		else if (UserMenusCommands.ACCEPT_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = UserMenusCommands.ACCEPT_FRIEND_REQUEST.getMatcher(input).group("username");
			result = ClientUserMenusController.acceptFriendRequest(username);
		} else if (UserMenusCommands.DECLINE_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = UserMenusCommands.DECLINE_FRIEND_REQUEST.getMatcher(input).group("username");
			result = ClientUserMenusController.declineFriendRequest(username);
		} else if (UserMenusCommands.REMOVE_FRIEND.getMatcher(input) != null) {
			String username = UserMenusCommands.REMOVE_FRIEND.getMatcher(input).group("username");
			result = ClientUserMenusController.removeFriend(username);
		} else if (UserMenusCommands.SEND_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = UserMenusCommands.SEND_FRIEND_REQUEST.getMatcher(input).group("username");
			result = ClientUserMenusController.sendFriendRequest(username);
		} else if (UserMenusCommands.UNSEND_FRIEND_REQUEST.getMatcher(input) != null) {
			String username = UserMenusCommands.UNSEND_FRIEND_REQUEST.getMatcher(input).group("username");
			result = ClientUserMenusController.unsendFriendRequest(username);
		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) {
			result = new Result("Social Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(input) != null) {
			result = ClientUserMenusController.exit();
		} else return new Result("Invalid command", false);
		return result;
	}

	@FXML
	private void sendRequest() {
		Result result = ClientUserMenusController.sendFriendRequest(suggestUsersList.getSelectionModel().getSelectedItem());
		AlertMaker.makeAlert("Send Friend Request", result);
	}

	@FXML
	private void updateDisplayPanel() {
		if (((VBox) friendsList.getParent()).getChildren().get(((VBox) friendsList.getParent()).getChildren().size() - 1) instanceof HBox) {
			((VBox) friendsList.getParent()).getChildren().remove(((VBox) friendsList.getParent()).getChildren().size() - 1);
		}
		friendsList.setVisible(true);
		updateFriendsList();
		showButtons();
	}

	public void updateFriendsList() {
		String friends = "";
		if (show.getSelectedToggle() == showFriends) friends = ClientUserMenusController.showFriends().getMessage();
		else if (show.getSelectedToggle() == showReceivedFriendRequests) friends = ClientUserMenusController.showReceivedFriendRequests().getMessage();
		else if (show.getSelectedToggle() == showSentFriendRequests) friends = ClientUserMenusController.showSentFriendRequests().getMessage();
		displayFriendsList(friends);
	}

	private void displayFriendsList(String friends) {
		if (friends.equals(previousFriendList)) return;
		previousFriendList = friends;
		System.out.println(friends);
		String[] friendsArray = friends.split("\n");
		friendsList.getItems().clear();
		for (String friend : friendsArray) {
			Label label = new Label(friend);
			label.setTextFill(Color.WHITE);
			friendsList.getItems().add(label);
		}
	}

	private void setStyleForButtons(Button button) {
		button.setId("ipad-dark-grey");
		button.getStylesheets().add(getClass().getResource("/CSS/buttonstyle.css").toExternalForm());
		button.getStylesheets().add(getClass().getResource("/CSS/textstyle.css").toExternalForm());
		button.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18;");
	}

	private HBox initializeHBox(Button... buttons) {
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		hBox.setAlignment(javafx.geometry.Pos.CENTER);
		for (Button button : buttons) {
			hBox.getChildren().add(button);
		}
		return hBox;
	}

	private void showButtons() {
		if (show.getSelectedToggle() == showFriends) {
			Button removeFriendButton = new Button("Remove Friend");
			removeFriendButton.setOnAction(event -> {
				if (friendsList.getSelectionModel().getSelectedItem() == null) return;
				Result result = ClientUserMenusController.removeFriend(friendsList.getSelectionModel().getSelectedItem().getText());
				AlertMaker.makeAlert("Remove Friend", result);
				updateDisplayPanel();
			});
			setStyleForButtons(removeFriendButton);
			HBox hBox = initializeHBox(removeFriendButton);
			((VBox) friendsList.getParent()).getChildren().add(hBox);
		} else if (show.getSelectedToggle() == showReceivedFriendRequests) {
			Button acceptFriendRequestButton = new Button("Accept");
			acceptFriendRequestButton.setOnAction(event -> {
				if (friendsList.getSelectionModel().getSelectedItem() == null) return;
				Result result = ClientUserMenusController.acceptFriendRequest(friendsList.getSelectionModel().getSelectedItem().getText());
				AlertMaker.makeAlert("Accept Friend Request", result);
				updateDisplayPanel();
			});
			Button declineFriendRequestButton = new Button("Decline");
			declineFriendRequestButton.setOnAction(event -> {
				if (friendsList.getSelectionModel().getSelectedItem() == null) return;
				Result result = ClientUserMenusController.declineFriendRequest(friendsList.getSelectionModel().getSelectedItem().getText());
				AlertMaker.makeAlert("Decline Friend Request", result);
				updateDisplayPanel();
			});
			setStyleForButtons(acceptFriendRequestButton);
			setStyleForButtons(declineFriendRequestButton);
			HBox hBox = initializeHBox(acceptFriendRequestButton, declineFriendRequestButton);
			((VBox) friendsList.getParent()).getChildren().add(hBox);
		} else if (show.getSelectedToggle() == showSentFriendRequests) {
			Button unsendFriendRequestButton = new Button("Unsend");
			unsendFriendRequestButton.setOnAction(event -> {
				if (friendsList.getSelectionModel().getSelectedItem() == null) return;
				Result result = ClientUserMenusController.unsendFriendRequest(friendsList.getSelectionModel().getSelectedItem().getText());
				AlertMaker.makeAlert("Unsend Friend Request", result);
				updateDisplayPanel();
			});
			setStyleForButtons(unsendFriendRequestButton);
			HBox hBox = initializeHBox(unsendFriendRequestButton);
			((VBox) friendsList.getParent()).getChildren().add(hBox);
		}
	}

	public void exit(ActionEvent actionEvent) {
		ClientUserMenusController.exit();
	}
}
