package client.view.game.prematch;

import client.controller.game.ClientTVMenuController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;

import java.io.IOException;
import java.net.URL;

public class ClientTVMenu extends Application implements Menuable {

    @Override
    public void createStage() {
        launch();
    }

    public ListView gamesField;

    @Override
    public void start(Stage stage) {
        ClientAppview.setStage(stage);
        URL url = getClass().getResource("/FXML/TVMenu.fxml");
        if (url == null) {
            System.out.println("Couldn't find file: TVMenu.fxml");
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
        refresh();
    }

    public void refresh(MouseEvent mouseEvent) {
        refresh();
    }

    public void spectate(MouseEvent mouseEvent) {
        HBox row = (HBox) gamesField.getSelectionModel().getSelectedItem();
        String[] usernames = ((Label)row.getChildren().getFirst()).getText().split("-");
        Result result = ClientTVMenuController.spectate(usernames[0], usernames[1]);
    }

    public void back(MouseEvent mouseEvent) {
        Result result = ClientTVMenuController.back();
    }

    private void refresh() {
        String[] games = ClientTVMenuController.getGames().getMessage().split("\n---------------------\n");
        gamesField.getItems().clear();
        for (int i = 0; i < games.length; i++) {
            String[] gameData = games[i].split("\n");
            String username1 = gameData[0];
            String username2 = gameData[1];
            String round = gameData[2];
            String[] scores = new String[gameData.length - 3];
            System.arraycopy(gameData, 3, scores, 0, scores.length);
            HBox row = createRow(username1, username2, round, scores);
            gamesField.getItems().add(row);
        }
    }

    private HBox createRow(String username1, String username2, String round, String[] scores) {
        HBox hbox;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/TVMenuRow.fxml"));
        try {
            hbox = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientTVMenuRowController controller = loader.getController();
        controller.setUsernamesField(username1 + " - " + username2);
        controller.setRoundField(round);
        controller.setScoresField(scores);
        return hbox;
    }


    @Override
    public Result run(String command) {
        return null;
    }
}
