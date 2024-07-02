package view;

import javafx.stage.Stage;

public class Appview {

	private static Stage stage = null;
	private static Menuable menu;


	public static Menuable getMenu() {
		return menu;
	}

	public static void setMenu(Menuable menu) {
		Appview.menu = menu;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		Appview.stage = stage;
	}

	public static void runMenu() {
		if (stage == null) {
			menu.createStage();
		}
		else{
			menu.start(stage);
		}
	}

	public static String getMenuName() {
		return menu.getClass().getSimpleName();
	}
}
