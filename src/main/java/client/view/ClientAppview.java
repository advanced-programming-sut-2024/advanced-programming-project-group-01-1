package client.view;

import javafx.application.Platform;
import javafx.stage.Stage;

public class ClientAppview {

	private static Stage stage = null;
	private static Menuable menu;


	public static Menuable getMenu() {
		return menu;
	}

	public static void setMenu(Menuable menu) {
		ClientAppview.menu = menu;
		runMenu();
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		ClientAppview.stage = stage;
	}

	private static void runMenu() {
		if (stage == null) menu.createStage();
		else Platform.runLater(() -> menu.start(stage));
	}

	public static String getMenuName() {
		return menu.getClass().getSimpleName();
	}
}
