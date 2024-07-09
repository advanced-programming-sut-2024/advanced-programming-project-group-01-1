package client.view.user;

import client.controller.ClientUserMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message.Result;
import client.view.ClientAppview;
import client.view.Menuable;
import message.UserMenusCommands;

import java.io.IOException;
import java.net.URL;

public class ClientRankingMenu extends Application implements Menuable {

	@FXML
	private VBox rankingRows;

	@FXML
	private Button previousPageButton, nextPageButton;

	@FXML
	private Label pageCountDisplay;

	private int page = 1, totalPages;

	@Override
	public void createStage() {
		launch();
	}

	@FXML
	public void initialize() {
		loadPage();
		updateButtons();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/RankingMenu.fxml");
		if (url == null) return;
		Pane root = null;
		try {
			root = javafx.fxml.FXMLLoader.load(url);
		} catch (java.io.IOException e){
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Result run(String input) {
		Result result;
		if (UserMenusCommands.NEXT_PAGE.getMatcher(input) != null) {
			result = nextPage();
		} else if (UserMenusCommands.PREVIOUS_PAGE.getMatcher(input) != null) {
			result = previousPage();
		} else if (UserMenusCommands.SHOW_CURRENT_MENU.getMatcher(input) != null) {
			result = new Result("Ranking Menu", true);
		} else if (UserMenusCommands.EXIT.getMatcher(input) != null) {
			result = exit();
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	@FXML
	private Result exit() {
		return ClientUserMenusController.exit();
	}

	@FXML
	private Result nextPage() {
		if (page == totalPages) return new Result("This is the last page", false);
		page++;
		loadPage();
		updateButtons();
		return new Result("Page changed successfully", true);
	}

	@FXML
	private Result previousPage() {
		if (page == 1) return new Result("This is the first page", false);
		page--;
		loadPage();
		updateButtons();
		return new Result("Page changed successfully", true);
	}

	private void loadPage() {
		rankingRows.getChildren().clear();
		String pageDetails = ClientUserMenusController.getPage(page).getMessage();
		String[] pageDetailsArray = pageDetails.split("\n");
		for (String pageDetail : pageDetailsArray) {
			String[] pageDetailArray = pageDetail.split(" ");
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/RankingMenuRow.fxml"));
			HBox rankingMenuRow = null;
			try {
				rankingMenuRow = loader.load();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			ClientRankingMenuRowController controller = loader.getController();
			controller.setRank(Integer.parseInt(pageDetailArray[0]));
			controller.setUsername(pageDetailArray[1]);
			controller.setRating(Integer.parseInt(pageDetailArray[2]));
			rankingRows.getChildren().add(rankingMenuRow);
		}
	}

	private void updateButtons() {
		totalPages = Integer.parseInt(ClientUserMenusController.getPageCount().getMessage());
		pageCountDisplay.setText(page + "/" + totalPages);
		previousPageButton.setDisable(page == 1);
		nextPageButton.setDisable(page == totalPages);
	}

}