package client.controller.game;

import client.main.TCPClient;
import message.GameMenusCommands;
import message.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ClientPreMatchMenusController {

	public static Result createGame(String opponentUsername) {
	return null;
	}

	public static Result showFactions() {
		String command = GameMenusCommands.SHOW_FACTIONS.getPattern();
		return TCPClient.send(command);
	}

	public static Result selectFaction(String factionName) {
		String command = GameMenusCommands.SELECT_FACTION.getPattern();
		command = command.replace("(?<faction>\\S+)", factionName);
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
			command.replace("(?<deckFson>\\S+)", deckFson);
		} catch (Exception e) {
			e.printStackTrace();
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
		command = command.replace("(?<cardName>\\S+)", cardName);
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
		return TCPClient.send(command);
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
}
