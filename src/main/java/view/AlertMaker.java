package view;

import javafx.scene.control.Alert;
import model.Result;

public class AlertMaker {

    public static void makeAlert(String title, Result result){
        Alert alert;
        if (result.isSuccessful()) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(result.getMessage());
        alert.show();
    }
}
