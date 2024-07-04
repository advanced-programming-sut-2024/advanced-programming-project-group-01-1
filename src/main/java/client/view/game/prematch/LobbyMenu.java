package client.view.game.prematch;

import client.controller.game.PreMatchMenusController;
import javafx.stage.Stage;
import message.Result;
import client.view.Menuable;
import message.GameMenusCommands;

import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class LobbyMenu implements Menuable {


	@Override
	public void createStage(){
		launch();
	}

	@Override
	public void start(Stage stage) {
		// TODO:
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.SHOW_FACTIONS.getMatcher(input)) != null){
			result = PreMatchMenusController.showFactions();
		} else if ((matcher = GameMenusCommands.SELECT_FACTION.getMatcher(input)) != null){
			result = selectFaction(matcher);
		} else if ((matcher = GameMenusCommands.SHOW_CARDS.getMatcher(input)) != null){
			result = PreMatchMenusController.showCards();
		} else if ((matcher = GameMenusCommands.SHOW_DECK.getMatcher(input)) != null){
			result = PreMatchMenusController.showDeck();
		} else if ((matcher = GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getMatcher(input)) != null) {
			result = PreMatchMenusController.showInfo();
		} else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_FILE_ADDRESS.getMatcher(input)) != null) {
			result = saveDeckWithAddress(matcher);
		} else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_NAME.getMatcher(input)) != null) {
			result = saveDeckWithName(matcher);
		} else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_FILE_ADDRESS.getMatcher(input)) != null) {
			result = loadDeckWithAddress(matcher);
		} else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_NAME.getMatcher(input)) != null) {
			result = loadDeckWithName(matcher);
		} else if ((matcher = GameMenusCommands.SHOW_LEADERS.getMatcher(input)) != null) {
			result = PreMatchMenusController.showLeaders();
		} else if ((matcher = GameMenusCommands.SELECT_LEADER.getMatcher(input)) != null) {
			result = selectLeader(matcher);
		} else if ((matcher = GameMenusCommands.ADD_TO_DECK.getMatcher(input)) != null) {
			result = addToDeck(matcher);
		} else if ((matcher = GameMenusCommands.REMOVE_FROM_DECK.getMatcher(input)) != null) {
			result = deleteFromDeck(matcher);
		} else if ((matcher = GameMenusCommands.PASS_ROUND.getMatcher(input)) != null) {
			result = PreMatchMenusController.changeTurn();
		} else if ((matcher = GameMenusCommands.START_GAME.getMatcher(input)) != null) {
			result = PreMatchMenusController.startGame();
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result loadDeckWithName(Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.loadDeckByName(name);
	}

	private Result loadDeckWithAddress(Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(address);
	}

	private Result selectFaction(Matcher matcher) {
		String faction = matcher.group("faction");
		return PreMatchMenusController.selectFaction(faction);
	}

	private Result saveDeckWithAddress(Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.saveDeckByAddress(address);
	}

	private Result saveDeckWithName(Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.saveDeckByName(name);
	}

	private Result loadDeck(Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(address);
	}

	private Result selectLeader(Matcher matcher) {
		int leaderNumber = Integer.parseInt(matcher.group("leaderNumber"));
		return PreMatchMenusController.selectLeader(leaderNumber);
	}

	private Result addToDeck(Matcher matcher) {
		String cardName = matcher.group("cardName");
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.addToDeck(cardName, count);
	}

	private Result deleteFromDeck(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.deleteFromDeck(cardNumber, count);
	}

}
