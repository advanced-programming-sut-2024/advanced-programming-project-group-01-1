package client.controller.game;

import client.main.TCPClient;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.ClientMainMenu;
import client.view.game.ClientMatchMenu;
import client.view.game.ClientTournamentMenu;
import client.view.game.prematch.ClientLobbyMenu;
import client.view.game.prematch.ClientMatchFinderMenu;
import client.view.game.prematch.ClientQuickMatchMenu;
import javafx.application.Platform;
import message.GameMenusCommands;
import message.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ClientPreMatchMenusController {

	public static Result requestMatch(String opponentUsername, ClientMatchFinderMenu menu) {
		String command = GameMenusCommands.SEND_MATCH_REQUEST.getPattern();
		command = command.replace("(?<opponent>\\S+)", opponentUsername);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) {
			if (result.getMessage().equals("Go to Lobby")) {
				Platform.runLater(() -> ClientAppview.setMenu(new ClientLobbyMenu()));
				return result;
			}
			new Thread(() -> {
				while (true) {
					try {
						if (!isWaiting().isSuccessful()) break;
						Result check = checkRequest();
						if (check.isSuccessful()) {
							if (check.getMessage().equals("You are accepted"))
								Platform.runLater(() -> ClientAppview.setMenu(new ClientLobbyMenu()));
							else if (menu != null) {
								Platform.runLater(() -> {
									AlertMaker.makeAlert("Request", new Result("Your are rejected", false));
									menu.stopWaiting(null);
								});
							}
							break;
						}
						Thread.sleep(234);
					} catch (Exception e) {
						break;
					}
				}
			}).start();
		}
		return result;
	}

	public static Result enterTournament() {
		String command = GameMenusCommands.ENTER_TOURNAMENT.getPattern();
		Result result = TCPClient.send(command);
		System.out.println(result.getMessage());
		if (result != null && result.isSuccessful()) {
			if (result.getMessage().equals("Go to Tournament")) {
				System.out.println("Go to Tournament");
				Platform.runLater(() -> ClientAppview.setMenu(new ClientTournamentMenu()));
				return result;
			}
			new Thread(() -> {
				while (true) {
					try {
						if (!isWaiting().isSuccessful()) break;
						Result check = checkTournament();
						if (check.isSuccessful() && check.getMessage().equals("Tournament starting")) {
							Platform.runLater(() -> ClientAppview.setMenu(new ClientTournamentMenu()));
							break;
						}
						Thread.sleep(234);
					} catch (Exception e) {
						break;
					}
				}
			}).start();
		}
		return result;
	}

	public static Result checkTournament() {
		String command = GameMenusCommands.CHECK_TOURNAMENT.getPattern();
		return TCPClient.send(command);
	}

	public static Result getMatchRequests() {
		String command = GameMenusCommands.GET_MATCH_REQUESTS.getPattern();
		return TCPClient.send(command);
	}

	public static Result handleMatchRequest(String senderUsername, boolean accept) {
		String command = GameMenusCommands.HANDLE_MATCH_REQUEST.getPattern();
		command = command.replace("(?<handle>(accept|reject))", (accept ? "accept" : "reject"));
		command = command.replace("(?<sender>\\S+)", senderUsername);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful() && accept) ClientAppview.setMenu(new ClientLobbyMenu());
		return result;
	}

	public static Result checkRequest() {
		String command = GameMenusCommands.CHECK_REQUEST.getPattern();
		return TCPClient.send(command);
	}

	public static Result stopWait() {
		String command = GameMenusCommands.STOP_WAIT.getPattern();
		return TCPClient.send(command);
	}

	public static Result isWaiting() {
		String command = GameMenusCommands.IS_WAITING.getPattern();
		return TCPClient.send(command);
	}

	public static Result showFactions() {
		String command = GameMenusCommands.SHOW_FACTIONS.getPattern();
		return TCPClient.send(command);
	}

	public static Result selectFaction(String factionName) {
		String command = GameMenusCommands.SELECT_FACTION.getPattern();
		command = command.replace("(?<faction>.+)", factionName);
		return TCPClient.send(command);
	}

	public static Result showCards() {
		String command = GameMenusCommands.SHOW_CARDS.getPattern();
		return TCPClient.send(command);
	}

	public static Result showDeck() {
		String command = GameMenusCommands.SHOW_DECK.getPattern();
		return TCPClient.send(command);
	}

	public static Result showInfo() {
		String command = GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getPattern();
		return TCPClient.send(command);
	}

	public static Result saveDeckByAddress(String address) {
		String command = GameMenusCommands.SAVE_DECK.getPattern();
		Result result = TCPClient.send(command);
		try {
			File deckFile = new File(address);
			FileOutputStream fileOutputStream = new FileOutputStream(deckFile);
			fileOutputStream.write(result.getMessage().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			return new Result("Error Saving Deck", false);
		}
		return new Result("Deck Saved Successfully", true);
	}

	public static Result saveDeckByName(String name) {
		String address = ClientPreMatchMenusController.class.getResource("/resources/decks/") + name + ".txt";
		return saveDeckByAddress(address);
	}

	public static Result loadDeckByAddress(String address) {
		String command = GameMenusCommands.LOAD_DECK.getPattern();
		try {
			File deckFile = new File(address);
			FileInputStream fileInputStream = new FileInputStream(deckFile);
			byte[] bytes = new byte[(int) deckFile.length()];
			fileInputStream.read(bytes);
			String deckFson = new String(bytes);
			command = command.replace("(?<deckFson>\\S+)", deckFson);
		} catch (Exception e) {
			return new Result("Error Loading Deck", false);
		}
		return TCPClient.send(command);
	}

	public static Result loadDeckByName(String name) {
		String address = ClientPreMatchMenusController.class.getResource("/resources/decks/") + name + ".txt";
		return loadDeckByAddress(address);
	}

	public static Result showLeaders() {
		String command = GameMenusCommands.SHOW_LEADERS.getPattern();
		return TCPClient.send(command);
	}

	public static Result selectLeader(int leaderNumber) {
		String command = GameMenusCommands.SELECT_LEADER.getPattern();
		command = command.replace("(?<leaderNumber>\\d+)", String.valueOf(leaderNumber));
		return TCPClient.send(command);
	}

	public static Result addToDeck(String cardName, int count) {
		String command = GameMenusCommands.ADD_TO_DECK.getPattern();
		command = command.replace("(?<cardName>.+)", cardName);
		command = command.replace("(?<count>\\d+)", String.valueOf(count));
		return TCPClient.send(command);
	}

	public static Result deleteFromDeck(int cardNumber, int count) {
		String command = GameMenusCommands.REMOVE_FROM_DECK.getPattern();
		command = command.replace("(?<cardNumber>\\d+)", String.valueOf(cardNumber));
		command = command.replace("(?<count>\\d+)", String.valueOf(count));
		return TCPClient.send(command);
	}

	public static Result changeTurn() {
		String command = GameMenusCommands.CHANGE_TURN.getPattern();
		return TCPClient.send(command);
	}

	public static Result startGame() {
		String command = GameMenusCommands.START_GAME.getPattern();
		return TCPClient.send(command);
	}

	public static Result exit() {
		String command = GameMenusCommands.EXIT_MATCH_FINDER.getPattern();
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientMainMenu());
		return result;
	}

	public static ArrayList<String> getOtherUsernames() {
		String command = GameMenusCommands.SHOW_PLAYERS_INFO.getPattern();
		Result result = TCPClient.send(command);
		String[] usernames = result.getMessage().split("\n");
		ArrayList<String> usernamesList = new ArrayList<>();
		Collections.addAll(usernamesList, usernames);
		return usernamesList;
	}

	public static Result showCardsForGraphic() {
		String command = GameMenusCommands.SHOW_CARDS_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showDeckForGraphic() {
		String command = GameMenusCommands.SHOW_DECK_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showNowLeaderToGraphics() {
		String command = GameMenusCommands.SHOW_LEADER_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result showNowFactionToGraphics() {
		String command = GameMenusCommands.SHOW_FACTION_GRAPHIC.getPattern();
		return TCPClient.send(command);
	}

	public static Result isDeckValid() {
		String command = GameMenusCommands.IS_DECK_VALID.getPattern();
		return TCPClient.send(command);
	}

	public static Result getReady() {
		String command = GameMenusCommands.READY.getPattern();
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) {
			if (result.getMessage().equals("game started")) {
				Platform.runLater(() -> ClientAppview.setMenu(new ClientMatchMenu()));
				return result;
			}
			new Thread(() -> {
				while (true) {
					if (!isWaiting().isSuccessful()) break;
					try {
						Result check = checkOpponentReady();
						if (check.isSuccessful()) Platform.runLater(() -> ClientAppview.setMenu(new ClientMatchMenu()));
						Thread.sleep(234);
					} catch (Exception e) {
						break;
					}
				}
			}).start();
		}
		return result;
	}

	public static Result cancelReady() {
		String command = GameMenusCommands.CANCEL_READY.getPattern();
		return TCPClient.send(command);
	}

	public static Result checkOpponentReady() {
		String command = GameMenusCommands.CHECK_OPPONENT_READY.getPattern();
		return TCPClient.send(command);
	}

	public static Result preferFirst() {
		String command = GameMenusCommands.PREFER_FIRST.getPattern();
		return TCPClient.send(command);
	}

	public static Result preferSecond() {
		String command = GameMenusCommands.PREFER_SECOND.getPattern();
		return TCPClient.send(command);
	}

	public static Result getPreference() {
		String command = GameMenusCommands.GET_PREFERRED_TURN.getPattern();
		return TCPClient.send(command);
	}


	public static Result goToQuickMatchMenu() {
		String command = GameMenusCommands.GO_QUICK_MATCH.getPattern();
		Result result = TCPClient.send(command);
		if (result.isSuccessful()) ClientAppview.setMenu(new ClientQuickMatchMenu());
		return result;
	}

	public static ArrayList<String> getQuickMatchList() {
		String command = GameMenusCommands.QUICK_MATCH_LIST.getPattern();
		Result result = TCPClient.send(command);
		String[] usernames = result.getMessage().split("\n");
		ArrayList<String> list = new ArrayList<>();
		Collections.addAll(list, usernames);
		return list;
	}

	public static Result startQuickMatch(String opponent) {
		String command = GameMenusCommands.START_QUICK_MATCH.getPattern();
		command = command.replace("(?<opponent>\\S+)", opponent);
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientLobbyMenu());
		return result;
	}

	public static Result createNewQuickMatch() {
		String command = GameMenusCommands.NEW_QUICK_MATCH.getPattern();
		Result result =  TCPClient.send(command);
		if (result != null && result.isSuccessful()) {
			new Thread(() -> {
				while (true) {
					try {
						if (!isWaiting().isSuccessful()) break;
						Result check = checkMatchReady();
						if (check.isSuccessful()) {
							Platform.runLater(() -> ClientAppview.setMenu(new ClientLobbyMenu()));
							break;
						}
						Thread.sleep(345);
					} catch (Exception e) {
						break;
					}
				}
			}).start();
		}
		return result;
	}

	public static Result checkMatchReady() {
		String command = GameMenusCommands.CHECK_MATCH_READY.getPattern();
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientLobbyMenu());
		return result;
	}

	public static Result cancelQuickMatch() {
		String command = GameMenusCommands.CANCEL_QUICK_MATCH.getPattern();
		return TCPClient.send(command);
	}

	public static Result backToMatchFinder() {
		String command = GameMenusCommands.BACK.getPattern();
		Result result = TCPClient.send(command);
		if (result != null && result.isSuccessful()) ClientAppview.setMenu(new ClientMatchFinderMenu());
		return result;
	}
}
