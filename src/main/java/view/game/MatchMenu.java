package view.game;

import controller.game.MatchMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Result;
import view.Appview;
import view.Menuable;
import view.model.SmallCard;
import view.model.SmallUnit;

import java.net.URL;
import java.util.ArrayList;
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

	public Pane root;
	public Pane handPane;
	public Pane rowPane0;
	public Pane rowPane1;
	public Pane rowPane2;
	public Pane rowPane3;
	public Pane rowPane4;
	public Pane rowPane5;
	public Pane rowBuffer0;
	public Pane rowBuffer1;
	public Pane rowBuffer2;
	public Pane rowBuffer3;
	public Pane rowBuffer4;
	public Pane rowBuffer5;
	public Label rowPowerLabel0;
	public Label rowPowerLabel1;
	public Label rowPowerLabel2;
	public Label rowPowerLabel3;
	public Label rowPowerLabel4;
	public Label rowPowerLabel5;
	public Label myPower;
	public Label opponentPower;
	public Pane myLeaderPane;
	public Pane opponentLeaderPane;
	public Pane weatherPane;
	public Pane myPilePane;
	public Pane opponentPilePane;
	public Pane myDeckPane;
	public Pane opponentDeckPane;
	public HBox myLife, opponentLife;
	public Label myHandSize, opponentHandSize;
	public ImageView meAhead, opponentAhead;
	public ImageView myFactionField, opponentFactionField;
	public Label myUsernameField, opponentUsernameField;
	public Label myFactionNameField, opponentFactionNameField;

	public Pane[] rowPanes;
	public Pane[] rowBufferPanes;
	public Label[] rowPowerLabels;
	public Pane selectedCard;
	public ArrayList<Pane> selectedPanes = new ArrayList<>();

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

	@FXML
	public void initialize() {
		rowPanes = new Pane[]{rowPane0, rowPane1, rowPane2, rowPane3, rowPane4, rowPane5};
		rowBufferPanes = new Pane[]{rowBuffer0, rowBuffer1, rowBuffer2, rowBuffer3, rowBuffer4, rowBuffer5};
		rowPowerLabels = new Label[]{rowPowerLabel0, rowPowerLabel1, rowPowerLabel2, rowPowerLabel3, rowPowerLabel4, rowPowerLabel5};
		for (Pane pane : rowPanes) {
			pane.setOnMouseClicked(this::showSpace);
		}
		for (Pane pane : rowBufferPanes) {
			pane.setOnMouseClicked(this::showSpace);
		}
		weatherPane.setOnMouseClicked(this::showSpace);
		myPilePane.setOnMouseClicked(this::showSpace);
		opponentPilePane.setOnMouseClicked(this::showSpace);
		//myLeaderPane.setOnMouseClicked(this::showSpace);
		//opponentLeaderPane.setOnMouseClicked(this::showSpace);
		updateScreen();
	}

	public void updateScreen() {
		updateHand();
		updateRows();
		updateWeather();
		updateDiscardPiles();
		updateDecks();
		//updateLeader();
		updateInfo();
	}

	public void updateSpace(Pane space, String[] cardsInfo, EventHandler<MouseEvent> eventHandler) {
		System.out.println("cardsInfo: " + cardsInfo.length);
		space.getChildren().clear();
		for (int i = 0; i < cardsInfo.length; i++) {
			System.out.println("Card " + i + ": " + cardsInfo[i]);
			SmallCard smallCard;
			String[] cardInfo = cardsInfo[i].split("\n");
			String cardName = cardInfo[0];
			String cardDescription = "KTKM";
			String type = cardInfo[1].substring(6);
			String Ability = cardInfo[2].substring(9);
			if (type.equals("Melee") || type.equals("Ranged") || type.equals("Siege") || type.equals("Agile")) {
				int power = Integer.parseInt(cardInfo[3].substring(7));
				String currentPower = cardInfo[4].substring(15);
				boolean hero = (cardInfo.length == 7);
				String uniqueCode = cardInfo[cardInfo.length - 1].substring(13);
				smallCard = SmallUnit.getInstance(cardName, cardDescription, type, Ability, power, currentPower, hero, uniqueCode);

			} else {
				String uniqueCode = cardInfo[3].substring(13);
				smallCard = SmallCard.getInstance(cardName, cardDescription, type, Ability, uniqueCode);
			}
			if (space.getPrefWidth() >= cardsInfo.length * smallCard.getPrefWidth()) {
				double tmp = (space.getPrefWidth() - cardsInfo.length * smallCard.getPrefWidth()) / 2 + smallCard.getPrefWidth() * i;
				smallCard.setLayoutX(tmp);
			} else {
				double tmp = i == cardsInfo.length - 1 ? space.getPrefWidth() - smallCard.getPrefWidth() : (space.getPrefWidth() - smallCard.getPrefWidth()) / (cardsInfo.length - 1) * i;
				smallCard.setLayoutX(tmp);
			}
			smallCard.setLayoutY(0);
			smallCard.setOnMouseClicked(eventHandler);
			space.getChildren().add(smallCard);
		}
	}

	public void updateHand() {
		Result result = MatchMenuController.showHandForGraphic();
		if (result.getMessage().isEmpty()){
			updateSpace(handPane, new String[]{}, null);
			return;
		}
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		updateSpace(handPane, cardsInfo, this::selectCard);
	}

	public void updateRows() {
		for (int i = 0; i < 6; i++) {
			Result result = MatchMenuController.showRowForGraphic(i);
			if (result.getMessage().isEmpty()){
				updateSpace(rowPanes[i], new String[]{}, null);
				updateSpace(rowBufferPanes[i], new String[]{}, null);
				continue;
			}
			System.out.println("Row " + i + ": " + result.getMessage());
			String[] cardsInfo = result.getMessage().split("\n------------------\n");
			if (cardsInfo.length >= 1 && cardsInfo[0].startsWith("Buffer: ")) {
				String cardName = cardsInfo[0].substring(8);
				updateSpace(rowBufferPanes[i], new String[]{cardsInfo[0]}, null);
				String[] newCardsInfo = new String[cardsInfo.length - 1];
				System.arraycopy(cardsInfo, 1, newCardsInfo, 0, cardsInfo.length - 1);
				updateSpace(rowPanes[i], newCardsInfo, null);
			} else {
				updateSpace(rowBufferPanes[i], new String[]{}, null);
				updateSpace(rowPanes[i], cardsInfo, null);
			}
		}
	}

	public void updateWeather() {
		Result result = MatchMenuController.showWeatherSystemForGraphic();
		if (result.getMessage().isEmpty()){
			updateSpace(weatherPane, new String[]{}, null);
			return;
		}
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		updateSpace(weatherPane, cardsInfo, null);
	}

	public void updateDiscardPiles() {
		Result result = MatchMenuController.showDiscardPilesForGraphic();
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		int idx = 0;
		for (int i = 0; i < cardsInfo.length; i++) {
			System.out.println("Card " + i + ": " + cardsInfo[i]);
			if (cardsInfo[i].startsWith("Current Discard Pile:\n")) {
				cardsInfo[i] = cardsInfo[i].substring(22);
			}
			if (cardsInfo[i].startsWith("Opponent Discard Pile:\n")) {
				cardsInfo[i] = cardsInfo[i].substring(23);
				idx = i;
				break;
			}
		}
		if (cardsInfo[0].isEmpty()) {
			updateSpace(myPilePane, new String[]{}, null);
		} else {
			String[] myCardsInfo = new String[idx-1];
			System.arraycopy(cardsInfo, 0, myCardsInfo, 0, idx-1);
			updateSpace(myPilePane, myCardsInfo, null);

		}
		if (cardsInfo[idx].isEmpty()) {
			updateSpace(opponentPilePane, new String[]{}, null);
		} else {
			String[] opponentCardsInfo = new String[cardsInfo.length - idx];
			System.arraycopy(cardsInfo, idx, opponentCardsInfo, 0, cardsInfo.length - idx);
			updateSpace(opponentPilePane, opponentCardsInfo, null);
		}
	}

	public void updateDecks() {
		Result result = MatchMenuController.remainingInDecksForGraphic();
		String[] cardsInfo = result.getMessage().split("\n");
		result = MatchMenuController.showFactionsForGraphic();
		String[] factionsInfo = result.getMessage().split("\n");
		String[] myDeckInfo = new String[Integer.parseInt(cardsInfo[0])];
		for (int i = 0; i < myDeckInfo.length; i++) {
			myDeckInfo[i] = factionsInfo[0].substring(9) + '\n' + "type: faction\nAbility: none\n" + "unique code: my" + i;
		}
		String[] opponentDeckInfo = new String[Integer.parseInt(cardsInfo[1])];
		for (int i = 0; i < opponentDeckInfo.length; i++) {
			opponentDeckInfo[i] = factionsInfo[2].substring(9) + '\n' + "type: faction\nAbility: none\n" + "unique code: opponent" + i;
		}
		updateSpace(myDeckPane, myDeckInfo, null);
		updateSpace(opponentDeckPane, opponentDeckInfo, null);
	}

	public void updateInfo() {
		for (int i = 0; i < 6; i++) {
			int power = 0;
			for (Node node: rowPanes[i].getChildren()){
				if (node instanceof SmallUnit) {
					SmallUnit unit = (SmallUnit) node;
					power += unit.getCurrentPower();
				}
			}
			rowPowerLabels[i].setText(String.valueOf(power));
			rowPowerLabels[i].setStyle("-fx-font-size: 20");
		}
		int power = 0;
		for (int i = 0; i < 3; i++) {
			power += Integer.parseInt(rowPowerLabels[i].getText());
		}
		myPower.setText(String.valueOf(power));
		myPower.setStyle("-fx-font-size: 20");
		power = 0;
		for (int i = 3; i < 6; i++) {
			power += Integer.parseInt(rowPowerLabels[i].getText());
		}
		opponentPower.setText(String.valueOf(power));
		opponentPower.setStyle("-fx-font-size: 20");
		String life = MatchMenuController.showPlayersLives().getMessage();
		String[] lifeInfo = life.split("\\s");
		int myLifeCount = Integer.parseInt(lifeInfo[1]);
		int opponentLifeCount = Integer.parseInt(lifeInfo[3]);
		int count = 0;
		for (Node node: myLife.getChildren()) {
			((ImageView) node).setImage(new Image(this.getClass().getResource("/images/icons/icon_gem_" +
					(count < myLifeCount ? "on" : "off") + ".png").toString()));
			count++;
		}
		count = 0;
		for (Node node: opponentLife.getChildren()) {
			((ImageView) node).setImage(new Image(this.getClass().getResource("/images/icons/icon_gem_" +
					(count < opponentLifeCount ? "on" : "off") + ".png").toString()));
			count++;
		}
		String handSize = MatchMenuController.showHandSize().getMessage();
		String[] handSizeInfo = handSize.split("\\s");
		myHandSize.setText(handSizeInfo[0]);
		opponentHandSize.setText(handSizeInfo[2]);
		meAhead.setVisible(Integer.parseInt(myPower.getText()) > Integer.parseInt(opponentPower.getText()));
		opponentAhead.setVisible(Integer.parseInt(myPower.getText()) < Integer.parseInt(opponentPower.getText()));
		String factions = MatchMenuController.showFactionsForGraphic().getMessage();
		String[] factionsInfo = factions.split("\n");
		myFactionNameField.setText(factionsInfo[0].substring(9));
		opponentFactionNameField.setText(factionsInfo[2].substring(9));
		myFactionField.setImage(new Image(this.getClass().getResource("/images/icons/deck_shield_" + factionsInfo[0].substring(9) + ".png").toString()));
		opponentFactionField.setImage(new Image(this.getClass().getResource("/images/icons/deck_shield_" + factionsInfo[2].substring(9) + ".png").toString()));
	}

	public void selectCard(MouseEvent event) {
		clearSelectedCard();
		SmallCard card = (SmallCard) event.getSource();
		selectedCard = card;
		selectedCard.setLayoutY(-10);
		selectedPanes = new ArrayList<>();
		ArrayList<Pane> panes = new ArrayList<>();
		if (card instanceof SmallUnit) {
			SmallUnit unit = (SmallUnit) card;
			if (unit.getType().equals("Melee") || unit.getType().equals("Agile")) {
				panes.add(rowPanes[2]);
			}
			if (unit.getType().equals("Ranged") || unit.getType().equals("Agile")) {
				panes.add(rowPanes[1]);
			}
			if (unit.getType().equals("Siege")) {
				panes.add(rowPanes[0]);
			}
		}
		else if (card.getType().equals("Weather")) {
			panes.add(weatherPane);
		}
		else if (card.getType().equals("Buffer")) {
			for (int i = 0; i < 3; i++) {
				panes.add(rowBufferPanes[i]);
			}
		} else if (card.getType().equals("Decoy")) {
			for (int i = 0; i < 3; i++) {
				if (rowPanes[i].getChildren().size() > 0) {
					panes.add(rowPanes[i]);
				}
			}
		}
		else {
			panes.add(myPilePane);
		}
		for (Pane pane : panes) {
			pane.setOnMouseClicked(this::placeCard);
			//make the background transparent green
			pane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
			selectedPanes.add(pane);
		}
	}

	public void clearSelectedCard() {
		if (selectedCard != null) {
			selectedCard.setLayoutY(0);
			selectedCard = null;
			for (Pane pane : selectedPanes) {
				pane.setOnMouseClicked(this::showSpace);
				pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
			}
			selectedPanes = new ArrayList<>();
		}
	}

	public void placeCard(MouseEvent event) {
		SmallCard card = (SmallCard) selectedCard;
		int idx = handPane.getChildren().indexOf(card);
		Pane pane = (Pane) event.getSource();
		int row = -1;
		for (int i = 0; i < 3; i++) {
			if (rowPanes[i] == pane || rowBufferPanes[i] == pane) {
				row = i;
				break;
			}
		}
		clearSelectedCard();
		MatchMenuController.placeCard(idx, row);
		updateScreen();
	}

	public void showSpace(MouseEvent mouseEvent) {
		clearSelectedCard();
		Pane pane = (Pane) mouseEvent.getSource();
		String[] cardsInfo = new String[pane.getChildren().size()*2];
		if (cardsInfo.length == 0) {
			return;
		}
		for (int i = 0; i < pane.getChildren().size(); i++) {
			SmallCard card = (SmallCard) pane.getChildren().get(i);
			cardsInfo[i*2] = card.getName();
			cardsInfo[i*2+1] = card.getDescription();
		}
		SelectPanel selectPanel = new SelectPanel(root, cardsInfo, 0, null, true);
	}

	public void passTurn(MouseEvent mouseEvent) {
		MatchMenuController.passTurn();
		updateScreen();
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
