package client.controller.game;

import client.main.TCPClient;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.game.ClientTournamentMenu;
import client.view.game.prematch.ClientLobbyMenu;
import client.view.game.prematch.ClientMatchFinderMenu;
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
		if (bracketThread == null) return;
		bracketThread.interrupt();
		bracketThread = null;
	}

	public static Result getTournamentInfo() {
		String command = GameMenusCommands.GET_TOURNAMENT_INFO.getPattern();
		return TCPClient.send(command);
	}

	public static Result setPlayerReady() {
		String command = GameMenusCommands.READY.getPattern();
		Result result = TCPClient.send(command);
		new Thread(() -> {
			while (true) {
				Result check = TCPClient.send(GameMenusCommands.CHECK_REQUEST.getPattern());
				System.out.println(check);
				if (check.getMessage().equals("Go to Lobby")) {
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			System.out.println("Anjam meri dege?");
			stopBracketThread();
			Platform.runLater(() -> ClientAppview.setMenu(new ClientLobbyMenu()));
		}).start();
		return result;
	}

	public static Result exit() {
		String command = GameMenusCommands.EXIT_MATCH_FINDER.getPattern();
		stopBracketThread();
		ClientAppview.setMenu(new ClientMatchFinderMenu());
		return TCPClient.send(command);
	}

	public static Result endBracket() {
		stopBracketThread();
		String command = GameMenusCommands.END_TOURNAMENT.getPattern();
		Result result = TCPClient.send(command);
		if (!result.isSuccessful()) {
			System.out.println("Couldn't end tournament");
		}
		ClientAppview.setMenu(new ClientMainMenu());
		return result;
	}

	public static String getUsername() {
		String command = GameMenusCommands.GET_USERNAMES.getPattern();
		return TCPClient.send(command).getMessage();
	}
}
