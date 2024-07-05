package view.game;

import controller.game.MatchMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;

import java.net.URL;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class MatchMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	@Override
	public void start(Stage stage) {
		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/MatchMenu.fxml");
		if (url == null){
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

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
		Matcher matcher;
		Result result;
		if ((matcher = GameMenusCommands.VETO_CARD.getMatcher(input)) != null) {
			result = vetoCard(matcher);
		} else if ((matcher = GameMenusCommands.IN_HAND_DECK.getMatcher(input)) != null) {
			result = showHand(matcher);
		} else if ((matcher = GameMenusCommands.REMAINING_CARDS_TO_PLAY.getMatcher(input)) != null) {
			result = MatchMenuController.remainingInDeck();
		} else if ((matcher = GameMenusCommands.OUT_OF_PLAY_CARDS.getMatcher(input)) != null) {
			result = MatchMenuController.showDiscordPiles();
		} else if ((matcher = GameMenusCommands.CARDS_IN_ROW.getMatcher(input)) != null) {
			result = showRow(matcher);
		} else if ((matcher = GameMenusCommands.SPELLS_IN_PLAY.getMatcher(input)) != null) {
			result = MatchMenuController.showWeatherSystem();
		} else if ((matcher = GameMenusCommands.PLACE_CARD.getMatcher(input)) != null) {
			result = placeCard(matcher);
		} else if ((matcher = GameMenusCommands.SHOW_COMMANDER.getMatcher(input)) != null) {
			result = MatchMenuController.showLeader();
		} else if ((matcher = GameMenusCommands.COMMANDER_POWER_PLAY.getMatcher(input)) != null) {
			result = MatchMenuController.useLeaderAbility();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_INFO.getMatcher(input)) != null) {
			result = MatchMenuController.showPlayersInfo();
		} else if ((matcher = GameMenusCommands.SHOW_PLAYERS_LIVES.getMatcher(input)) != null) {
			result = MatchMenuController.showPlayersLives();
		} else if ((matcher = GameMenusCommands.SHOW_NUMBER_OF_CARDS_IN_HAND.getMatcher(input)) != null) {
			result = MatchMenuController.showHandSize();
		} else if ((matcher = GameMenusCommands.SHOW_TURN_INFO.getMatcher(input)) != null) {
			result = MatchMenuController.showTurnInfo();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE.getMatcher(input)) != null) {
			result = MatchMenuController.showTotalPower();
		} else if ((matcher = GameMenusCommands.SHOW_TOTAL_SCORE_OF_ROW.getMatcher(input)) != null) {
			result = showTotalScoreOfRow(matcher);
		} else if ((matcher = GameMenusCommands.PASS_ROUND.getMatcher(input)) != null) {
			result = MatchMenuController.passTurn();
		} else if ((matcher = GameMenusCommands.SHOW_HAND.getMatcher(input)) != null) {
			result = MatchMenuController.showCurrentHand();
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
	}

	private Result vetoCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return MatchMenuController.vetoCard(cardNumber);
	}

	private Result showHand(Matcher matcher) {
		boolean option = matcher.group("option") != null;
		if (option) {
			int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
			return MatchMenuController.showHand(cardNumber);
		} else {
			return MatchMenuController.showHand(-1);
		}
	}

	private Result showRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRow(rowNumber);
	}

	private Result placeCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int rowNumber = matcher.group("rowNumber") != null ? Integer.parseInt(matcher.group("rowNumber")) : -1;
		return MatchMenuController.placeCard(cardNumber, rowNumber);
	}

	private Result showTotalScoreOfRow(Matcher matcher) {
		int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
		return MatchMenuController.showRowPower(rowNumber);
	}
}
