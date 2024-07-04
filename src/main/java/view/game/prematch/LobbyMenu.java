package view.game.prematch;

import controller.game.PreMatchMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Result;
import model.leader.Leader;
import view.Appview;
import view.Constants;
import view.Menuable;
import view.game.GameMenusCommands;
import view.model.LargeLeader;
import view.model.PreviewCards;

import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static javafx.application.Application.launch;


public class LobbyMenu extends Application implements Menuable {

	/*
	 * JavaFX version of the LobbyMenu
	 */

	@Override
	public void createStage(){
		launch();
	}

	public Pane root;
	public ScrollPane collectionCardPane;
	public ScrollPane inDeckCardPane;
	public Rectangle leaderField;



	public Pane changeFactionPane;



	@Override
	public void start(Stage stage) {

		Appview.setStage(stage);
		URL url = getClass().getResource("/fxml/LobbyMenu.fxml");
		if (url == null) {
			System.out.println("Couldn't find file: LobbyMenu.fxml");
			return;
		}
		Pane root = null;
		try {
			root = FXMLLoader.load(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		updateScreen();
	}

	public void updateScreen() {
		updateCollectionCardPane();
		updateInDeckCardPane();
		updateLeader();
	}

	public void updateCollectionCardPane() {
		Result result = PreMatchMenusController.showCardsForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<PreviewCards> previewCards = new ArrayList<>();
		if (!result.getMessage().isEmpty()){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				PreviewCards previewCard = new PreviewCards(cardName, count);
				previewCard.setOnMouseClicked(this::addToDeck);
				previewCards.add(previewCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (PreviewCards largeCard : previewCards) {
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

	public void addToDeck(MouseEvent mouseEvent) {
		System.out.println("add to deck");
		PreviewCards largeCard = (PreviewCards) mouseEvent.getSource();
		Result result = PreMatchMenusController.addToDeck(largeCard.getName(), 1);
		updateScreen();
	}

	public void updateInDeckCardPane() {
		Result result = PreMatchMenusController.showDeckForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<PreviewCards> previewCards = new ArrayList<>();
		if (!result.getMessage().equals("")){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				PreviewCards previewCard = new PreviewCards(cardName, count);
				previewCard.setOnMouseClicked(this::removeFromDeck);
				previewCards.add(previewCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (PreviewCards largeCard : previewCards) {
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
		inDeckCardPane.setContent(gridPane);
	}

	private void updateLeader() {
		Result result = PreMatchMenusController.showNowLeaderToGraphics();
		String leaderName = result.getMessage();
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + leaderName + ".jpg")));
		leaderField.setFill(imagePattern);
	}

	public void removeFromDeck(MouseEvent mouseEvent) {
		PreviewCards largeCard = (PreviewCards) mouseEvent.getSource();
		GridPane gridPane = (GridPane) inDeckCardPane.getContent();
		int idx = 0;
		for (int i = 0; i < gridPane.getChildren().size(); i++) {
			if (gridPane.getChildren().get(i).equals(largeCard)) {
				break;
			}
			idx += ((PreviewCards) gridPane.getChildren().get(i)).getCount();
		}
		Result result = PreMatchMenusController.deleteFromDeck(idx, 1);
		System.out.println(result);
		updateScreen();
	}

	/*
	 * change leader panel
	 */

	public Pane changeLeaderPane;
	public ArrayList<LargeLeader> leaders;
	int ptr;

	public void changeLeader(MouseEvent mouseEvent) {
		changeLeaderPane = new Pane();
		leaders = new ArrayList<>();
		changeLeaderPane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
		changeLeaderPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
		Result result = PreMatchMenusController.showLeaders();
		String[] parts = result.getMessage().split("\n");
		for (int i = 0; i < parts.length; i += 2) {
			String leaderName = parts[i].substring(8);
			String description = parts[i+1];
			LargeLeader largeCard = new LargeLeader(leaderName, description);
			leaders.add(largeCard);
		}
		String currentLeader = PreMatchMenusController.showNowLeaderToGraphics().getMessage();
		for (int i = 0; i < leaders.size(); i++) {
			if (leaders.get(i).getName().equals(currentLeader)) {
				ptr = i;
				break;
			}
		}
		updateLeaderPane();
		root.getChildren().add(changeLeaderPane);
	}

	public void updateLeaderPane() {
		changeLeaderPane.getChildren().clear();
		leaders.get(ptr).setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - Constants.LARGE_CARD_WIDTH.getValue() / 2);
		leaders.get(ptr).setLayoutY(50);
		leaders.get(ptr).setStyle("-fx-opacity: 1");
		leaders.get(ptr).setOnMouseClicked(this::selectLeader);
		changeLeaderPane.getChildren().add(leaders.get(ptr));
		Label label = new Label(leaders.get(ptr).getDescription());
		label.setLayoutX(leaders.get(ptr).getLayoutX());
		label.setLayoutY(Constants.SCREEN_HEIGHT.getValue() - 200);
		label.setPrefWidth(Constants.LARGE_CARD_WIDTH.getValue());
		label.setPrefHeight(150);
		label.setWrapText(true);
		label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-background-color: rgba(0, 0, 0, 0.9)");
		label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		label.setAlignment(javafx.geometry.Pos.CENTER);
		changeLeaderPane.getChildren().add(label);
		for (int i = 0; i < leaders.size(); i++) {
			if (i < ptr-1 && i > ptr + 1) {
				continue;
			} else if (i == ptr-1) {
				leaders.get(i).setLayoutX(leaders.get(ptr).getLayoutX() - Constants.LARGE_CARD_WIDTH.getValue() - 100);
				leaders.get(i).setLayoutY(leaders.get(ptr).getLayoutY());
				leaders.get(i).setStyle("-fx-opacity: 0.8");
				leaders.get(i).setOnMouseClicked(this::previousLeader);
				changeLeaderPane.getChildren().add(leaders.get(i));
			} else if (i == ptr + 1) {
				leaders.get(i).setLayoutX(leaders.get(ptr).getLayoutX() + Constants.LARGE_CARD_WIDTH.getValue() + 100);
				leaders.get(i).setLayoutY(leaders.get(ptr).getLayoutY());
				leaders.get(i).setStyle("-fx-opacity: 0.8");
				leaders.get(i).setOnMouseClicked(this::nextLeader);
				changeLeaderPane.getChildren().add(leaders.get(i));
			}
		}
	}

	public void selectLeader(MouseEvent mouseEvent) {
		LargeLeader largeCard = (LargeLeader) mouseEvent.getSource();
		Result result = PreMatchMenusController.selectLeader(leaders.indexOf(largeCard));
		System.out.println(result);
		root.getChildren().remove(changeLeaderPane);
		updateScreen();
	}

	public void previousLeader(MouseEvent mouseEvent) {
		ptr--;
		if (ptr < 0) {
			ptr = leaders.size() - 1;
		}
		updateLeaderPane();
	}

	public void nextLeader(MouseEvent mouseEvent) {
		ptr++;
		if (ptr == leaders.size()) {
			ptr = 0;
		}
		updateLeaderPane();
	}

	/*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public void run(String input) {
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
		if (result != null) {
			System.out.println(result);
		}
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
