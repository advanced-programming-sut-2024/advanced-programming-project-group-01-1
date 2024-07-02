package view;

import javafx.stage.Stage;

import java.util.Scanner;

public interface Menuable {

	void createStage();

	void start(Stage stage);

	void run(String input);
}
