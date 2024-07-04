package server.view.user;

import message.Command;
import message.UserMenusCommands;
import server.controller.UserMenusController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;
import server.model.user.User;
import server.view.AlertMaker;
import server.view.Appview;
import server.view.Menuable;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;

public class ProfileMenu implements Menuable {
	public TextField usernameField;
	public TextField nicknameField;
	public TextField emailField;
	public PasswordField oldPasswordField;
	public PasswordField newPasswordField;
	public PasswordField confirmNewPasswordField;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/ProfileMenu.fxml");
		if (url == null){
			System.out.println("Couldn't find file: FXML/ProfileMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		Platform.runLater(root::requestFocus);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		usernameField.setText(User.getLoggedInUser().getUsername());
		nicknameField.setText(User.getLoggedInUser().getNickname());
		emailField.setText(User.getLoggedInUser().getEmail());
	}

	@Override
	public Result run(Command command) {
		Matcher matcher;
		Result result;
		if ((matcher = UserMenusCommands.CHANGE_USERNAME.getMatcher(command.getCommand())) != null) result = changeUsername(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_PASSWORD.getMatcher(command.getCommand())) != null)
			result = changePassword(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_NICKNAME.getMatcher(command.getCommand())) != null)
			result = changeNickname(matcher);
		else if ((matcher = UserMenusCommands.CHANGE_EMAIL.getMatcher(command.getCommand())) != null) result = changeEmail(matcher);
		else if (UserMenusCommands.ENTER_USER_INFO.getMatcher(command.getCommand()) != null) result = enterUserInfo();
		else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(command.getCommand()) != null) result = showCurrentMenu();
		else if (UserMenusCommands.EXIT.getMatcher(command.getCommand()) != null) result = exit();
		else result = new Result("Invalid command", false);
		return result;
	}

	private Result changeUsername(Matcher matcher) {
		String username = matcher.group("username");
		return UserMenusController.changeUsername(username);
	}

	private Result changePassword(Matcher matcher) {
		String newPassword = matcher.group("newPassword");
		String oldPassword = matcher.group("oldPassword");
		return UserMenusController.changePassword(newPassword, oldPassword);
	}

	private Result changeNickname(Matcher matcher) {
		String nickname = matcher.group("nickname");
		return UserMenusController.changeNickname(nickname);
	}

	private Result changeEmail(Matcher matcher) {
		String email = matcher.group("email");
		return UserMenusController.changeEmail(email);
	}

	@FXML
	private Result enterUserInfo() {
		return UserMenusController.goToInfoMenu();
	}

	private Result showCurrentMenu() {
		return new Result("Profile Menu", true);
	}

	public void saveChanges(MouseEvent mouseEvent) {
		String username = usernameField.getText();
		String nickname = nicknameField.getText();
		String email = emailField.getText();
		String oldPassword = oldPasswordField.getText();
		String newPassword = newPasswordField.getText();
		String confirmNewPassword = confirmNewPasswordField.getText();
		Result result = UserMenusController.saveChanges(username, nickname, email, oldPassword, newPassword, confirmNewPassword);
		AlertMaker.makeAlert("update profile", result);
	}

	@FXML
	private Result exit() {
		return UserMenusController.exit();
	}

}