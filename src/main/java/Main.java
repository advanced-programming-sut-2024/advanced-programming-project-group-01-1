import view.Appview;
import view.Terminal;
import view.sign.login.LoginMenu;

public class Main {
	public static void main(String[] args) {
		Appview.setMenu(new LoginMenu());
		//TODO: run the graphical interface
		Terminal terminal = new Terminal();
		terminal.run();
	}
}