package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.view.ClientAppview;
import client.view.Menuable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.GameMenusCommands;
import message.Result;

import java.net.URL;
import java.util.regex.Matcher;


public class ClientMatchMenu extends Application implements Menuable {


	@Override
	public void createStage() {
		launch();
	}

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
		URL url = getClass().getResource("/FXML/MatchMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: FXML/MatchMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.VETO_CARD.getMatcher(input)) != null) {
			result = vetoCard(matcher);
		} else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(input)) != null) {
			result = showHand(matcher);
		} else if ((matcher = GameMenusCommands.REMAINING_CARDS_TO_PLAY.getMatcher(input)) != null) {
			result = ClientMatchMenuController.remainingInDeck();
		} else if ((matcher = GameMenusCommands.OUT_OF_PLAY_CARDS.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showDiscordPiles();
		} else if ((matcher = GameMenusCommands.CARDS_IN_ROW.getMatcher(input)) != null) {
			result = showRow(matcher);
		} else if ((matcher = GameMenusCommands.SPELLS_IN_PLAY.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showWeatherSystem();
		} else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(input)) != null) {
			result = placeCard(matcher);
		} else if ((matcher = GameMenusCommands.SHOW_COMMANDER.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showLeader();
		} else if ((matcher = GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(input)) != null) {
			result = ClientMatchMenuController.useLeaderAbility();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showPlayersInfo();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showPlayersLives();
		} else if ((matcher = GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showHandSize();
		} else if ((matcher = GameMenusCommands.SHOW_TURN_INFO.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showTurnInfo();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE.getMatcher(input)) != null) {
			result = ClientMatchMenuController.showTotalPower();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getMatcher(input)) != null) {
			result = showTotalScoreOfRow(matcher);
		} else if ((matcher = GameMenusCommands.PASS_ROUND.getMatcher(input)) != null) {
			result = ClientMatchMenuController.passTurn();
		} else {
			result = new Result("Invalid command", false);
		}
		return result;
	}

	private Result vetoCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return ClientMatchMenuController.vetoCard(cardNumber);
	}

	private Result showHand(Matcher matcher) {
		boolean option = matcher.group("option") != null;
		if (option) {
			int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
			return ClientMatchMenuController.showHand(cardNumber);
		} else {
			return ClientMatchMenuController.showHand(-1);
		}
	}

	private Result showRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return ClientMatchMenuController.showRow(rowNumber);
	}

	private Result placeCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int rowNumber = matcher.group("rowNumber") != null ? Integer.parseInt(matcher.group("rowNumber")) : -1;
		return ClientMatchMenuController.placeCard(cardNumber, rowNumber);
	}

	private Result showTotalScoreOfRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return ClientMatchMenuController.showRowPower(rowNumber);
	}
}
