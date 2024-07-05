package server.view.game.prematch;

import message.GameMenusCommands;
import server.controller.game.PreMatchMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.Result;
import server.model.Client;
import server.view.Menuable;
import server.view.model.LargeCard;
import server.view.model.PreviewCard;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class LobbyMenu implements Menuable {



	@FXML
	public void initialize() {
		updateScreen();
	}

	public void updateScreen() {
		updateCollectionCardPane();
		updateInDeckCardPane();
		updateLeader();
		updateFaction();
	}

	public void updateCollectionCardPane() {
		Result result = PreMatchMenusController.showCardsForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<PreviewCard> previewCards = new ArrayList<>();
		if (!result.getMessage().isEmpty()){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				PreviewCard previewCard = new PreviewCard(cardName, count);
				previewCard.setOnMouseClicked(this::addToDeck);
				previewCards.add(previewCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (PreviewCard largeCard : previewCards) {
			gridPane.add(largeCard, column, row);
			column++;
			if (column == 3) {
				column = 0;
				row++;
			}
		}
		//hide the scroll bar
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		collectionCardPane.setContent(gridPane);
	}

	public void updateInDeckCardPane() {
		Result result = PreMatchMenusController.showDeckForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<PreviewCard> previewCards = new ArrayList<>();
		if (!result.getMessage().equals("")){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				PreviewCard previewCard = new PreviewCard(cardName, count);
				previewCard.setOnMouseClicked(this::removeFromDeck);
				previewCards.add(previewCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (PreviewCard largeCard : previewCards) {
			gridPane.add(largeCard, column, row);
			column++;
			if (column == 3) {
				column = 0;
				row++;
			}
		}
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		inDeckCardPane.setContent(gridPane);
	}

	private void updateLeader() {
		Result result = PreMatchMenusController.showNowLeaderToGraphics();
		String leaderName = result.getMessage();
		System.out.println(leaderName);
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + leaderName + ".jpg")));
		leaderField.setFill(imagePattern);
	}

	private void updateFaction() {
		Result result = PreMatchMenusController.showNowFactionToGraphics();
		String factionName = result.getMessage();
		System.out.println(factionName);
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/icons/" + "deck_shield_" + factionName + ".png")));
		factionIconField.setFill(imagePattern);
		factionNameField.setText(factionName);
	}

	public ArrayList<LargeCard> leaders;
	int ptrLeader;

	public void updateLeaderPane() {
		changeLeaderPane.getChildren().clear();
		leaders.get(ptrLeader).setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - Constants.LARGE_CARD_WIDTH.getValue() / 2);
		leaders.get(ptrLeader).setLayoutY(50);
		leaders.get(ptrLeader).setStyle("-fx-opacity: 1");
		leaders.get(ptrLeader).setOnMouseClicked(this::selectLeader);
		changeLeaderPane.getChildren().add(leaders.get(ptrLeader));
		Label label = new Label(leaders.get(ptrLeader).getDescription());
		label.setLayoutX(leaders.get(ptrLeader).getLayoutX());
		label.setLayoutY(Constants.SCREEN_HEIGHT.getValue() - 200);
		label.setPrefWidth(Constants.LARGE_CARD_WIDTH.getValue());
		label.setPrefHeight(150);
		label.setWrapText(true);
		label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-background-color: rgba(0, 0, 0, 0.9)");
		label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		label.setAlignment(javafx.geometry.Pos.CENTER);
		changeLeaderPane.getChildren().add(label);
		for (int i = 0; i < leaders.size(); i++) {
			if (i < ptrLeader -1 && i > ptrLeader + 1) {
				continue;
			} else if (i == ptrLeader -1) {
				leaders.get(i).setLayoutX(leaders.get(ptrLeader).getLayoutX() - Constants.LARGE_CARD_WIDTH.getValue() - 100);
				leaders.get(i).setLayoutY(leaders.get(ptrLeader).getLayoutY());
				leaders.get(i).setStyle("-fx-opacity: 0.8");
				leaders.get(i).setOnMouseClicked(this::previousLeader);
				changeLeaderPane.getChildren().add(leaders.get(i));
			} else if (i == ptrLeader + 1) {
				leaders.get(i).setLayoutX(leaders.get(ptrLeader).getLayoutX() + Constants.LARGE_CARD_WIDTH.getValue() + 100);
				leaders.get(i).setLayoutY(leaders.get(ptrLeader).getLayoutY());
				leaders.get(i).setStyle("-fx-opacity: 0.8");
				leaders.get(i).setOnMouseClicked(this::nextLeader);
				changeLeaderPane.getChildren().add(leaders.get(i));
			}
		}
	}

	ArrayList<LargeCard> factions;
	int ptrFaction;



	public void updateFactionPane() {
		changeFactionPane.getChildren().clear();
		factions.get(ptrFaction).setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - Constants.LARGE_CARD_WIDTH.getValue() / 2);
		factions.get(ptrFaction).setLayoutY(50);
		factions.get(ptrFaction).setStyle("-fx-opacity: 1");
		factions.get(ptrFaction).setOnMouseClicked(this::selectFaction);
		changeFactionPane.getChildren().add(factions.get(ptrFaction));
		Label label = new Label(factions.get(ptrFaction).getDescription());
		label.setLayoutX(factions.get(ptrFaction).getLayoutX());
		label.setLayoutY(Constants.SCREEN_HEIGHT.getValue() - 200);
		label.setPrefWidth(Constants.LARGE_CARD_WIDTH.getValue());
		label.setPrefHeight(150);
		label.setWrapText(true);
		label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-background-color: rgba(0, 0, 0, 0.9)");
		label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		label.setAlignment(javafx.geometry.Pos.CENTER);
		changeFactionPane.getChildren().add(label);
		for (int i = 0; i < factions.size(); i++) {
			if (i < ptrFaction -1 && i > ptrFaction + 1) {
				continue;
			} else if (i == ptrFaction -1) {
				factions.get(i).setLayoutX(factions.get(ptrFaction).getLayoutX() - Constants.LARGE_CARD_WIDTH.getValue() - 100);
				factions.get(i).setLayoutY(factions.get(ptrFaction).getLayoutY());
				factions.get(i).setStyle("-fx-opacity: 0.8");
				factions.get(i).setOnMouseClicked(this::previousFaction);
				changeFactionPane.getChildren().add(factions.get(i));
			} else if (i == ptrFaction + 1) {
				factions.get(i).setLayoutX(factions.get(ptrFaction).getLayoutX() + Constants.LARGE_CARD_WIDTH.getValue() + 100);
				factions.get(i).setLayoutY(factions.get(ptrFaction).getLayoutY());
				factions.get(i).setStyle("-fx-opacity: 0.8");
				factions.get(i).setOnMouseClicked(this::nextFaction);
				changeFactionPane.getChildren().add(factions.get(i));
			}
		}
	}


	@Override
	public Result run(Client client, String input) {
		Matcher matcher;
		Result result;
		if (GameMenusCommands.SHOW_FACTIONS.getMatcher(input) != null){
			result = PreMatchMenusController.showFactions(client);
		} else if ((matcher = GameMenusCommands.SELECT_FACTION.getMatcher(input)) != null){
			result = selectFaction(client, matcher);
		} else if (GameMenusCommands.SHOW_CARDS.getMatcher(input) != null){
			result = PreMatchMenusController.showCards(client);
		} else if (GameMenusCommands.SHOW_DECK.getMatcher(input) != null){
			result = PreMatchMenusController.showDeck(client);
		} else if (GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getMatcher(input) != null) {
			result = PreMatchMenusController.showInfo(client);
		} else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_FILE_ADDRESS.getMatcher(input)) != null) {
			result = saveDeckWithAddress(climatcher);
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

	private Result loadDeckWithName(Client client, Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.loadDeckByName(client, name);
	}

	private Result loadDeckWithAddress(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(client, address);
	}

	private Result selectFaction(Client client, Matcher matcher) {
		String faction = matcher.group("faction");
		return PreMatchMenusController.selectFaction(client, faction);
	}

	private Result saveDeckWithAddress(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.saveDeckByAddress(client, address);
	}

	private Result saveDeckWithName(Client client, Matcher matcher) {
		String name = matcher.group("name");
		return PreMatchMenusController.saveDeckByName(client, name);
	}

	private Result loadDeck(Client client, Matcher matcher) {
		String address = matcher.group("fileAddress");
		return PreMatchMenusController.loadDeckByAddress(client, address);
	}

	private Result selectLeader(Client client, Matcher matcher) {
		int leaderNumber = Integer.parseInt(matcher.group("leaderNumber"));
		return PreMatchMenusController.selectLeader(client, leaderNumber);
	}

	private Result addToDeck(Client client, Matcher matcher) {
		String cardName = matcher.group("cardName");
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.addToDeck(client, cardName, count);
	}

	private Result deleteFromDeck(Client client, Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int count = Integer.parseInt(matcher.group("count"));
		return PreMatchMenusController.deleteFromDeck(client, cardNumber, count);
	}

}
