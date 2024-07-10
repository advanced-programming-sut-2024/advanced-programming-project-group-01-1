package client.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class TestMenu extends Application {

    @Override
    public void start(Stage stage) {
        URL url = getClass().getResource("/FXML/ChatBoxPanel.fxml");
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
        System.out.println("TestMenu started");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
