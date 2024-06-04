package view;

public class Appview {
	private static Menuable menu;


	public static Menuable getMenu() {
		return menu;
	}

	public static void setMenu(Menuable menu) {
		Appview.menu = menu;
	}

	public static String getMenuName() {
		return menu.getClass().getSimpleName();
	}
}
