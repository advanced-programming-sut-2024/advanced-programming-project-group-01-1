package client.view.game.prematch;

import client.controller.game.ClientPreMatchMenusController;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;

import java.net.URL;
import java.util.ArrayList;

public class ClientQuickMatchMenu extends Application implements Menuable {

	public Pane root;
	public ListView<String> matches;
	public Button cancel;

	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/QuickMatchMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: QuickMatchMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		ArrayList<String> matchMakers = ClientPreMatchMenusController.getQuickMatchList();
		matches.getItems().addAll(matchMakers);
	}

	public void start(MouseEvent mouseEvent) {
		String opponent = matches.getSelectionModel().getSelectedItem();
		if (opponent != null)
			AlertMaker.makeAlert("START", ClientPreMatchMenusController.startQuickMatch(opponent));
	}

	public void createNewMatch(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.createNewQuickMatch();
		if (result.isSuccessful()) {
			for (Node child : root.getChildren()) {
				if (child instanceof ImageView) continue;
				child.setDisable(true);
				child.setOpacity(0);
			}
			cancel.setDisable(false);
			cancel.setOpacity(1);
		}
	}

	public void cancel(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.cancelQuickMatch();
		System.out.println(result);
		if (result.isSuccessful()) {
			for (Node child : root.getChildren()) {
				child.setDisable(false);
				child.setOpacity(1);
			}
			cancel.setDisable(true);
			cancel.setOpacity(0);
		}
	}

	public void back(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.backToMatchFinder();
	}

	@Override
	public Result run(String input) {
		return null;
	}
}
