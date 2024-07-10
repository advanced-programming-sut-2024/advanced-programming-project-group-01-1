package client.controller.game;

import client.main.TCPClient;
import client.view.game.ClientTournamentMenu;
import javafx.application.Platform;
import message.GameMenusCommands;
import message.Result;

public class ClientTournamentMenuController {

	private static Thread bracketThread;

	public static void startUpdatingBracket(ClientTournamentMenu menu) {
		if (bracketThread != null) stopBracketThread();
		bracketThread = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					return;
				}
				Platform.runLater(menu::updateScreen);
			}
		});
		bracketThread.setDaemon(true);
		bracketThread.start();
	}

	public static void stopBracketThread() {
		bracketThread.interrupt();
		bracketThread = null;
	}

	public static Result getTournamentInfo() {
		String command = GameMenusCommands.GET_TOURNAMENT_INFO.getPattern();
		return TCPClient.send(command);
	}


}
