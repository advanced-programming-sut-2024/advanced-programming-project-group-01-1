package client.view.game.prematch;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ClientTVMenuRowController {

    public Label usernamesField;
    public Label roundField;
    public VBox scoresField;

    public void setUsernamesField(String usernames) {
        usernamesField.setText(usernames);
    }

    public void setRoundField(String round) {
        roundField.setText(round);
    }

    public void setScoresField(String[] scores) {
        scoresField.getChildren().clear();
        for (String score : scores) {
            Label scoreLabel = new Label(score);
            scoresField.getChildren().add(scoreLabel);
        }
    }
}
