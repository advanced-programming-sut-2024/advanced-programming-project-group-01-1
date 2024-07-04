package view.game.prematch;

import com.google.gson.Gson;
import controller.game.PreMatchMenusController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Result;
import view.Appview;
import view.Menuable;
import view.game.GameMenusCommands;
import view.model.LargeCards;

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

	public ScrollPane collectionCardPane;
	public ScrollPane inDeckCardPane;


	@Override
	public void start(Stage stage) {
		/*VBox vbox = new VBox(10); // Create a VBox with spacing 10
		// Add buttons to the VBox
		for (int i = 1; i <= 20; i++) {
			vbox.getChildren().add(new Button("Button " + i));
		}
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(vbox); // Set the VBox as the content of the ScrollPane
		scrollPane.setFitToWidth(true); // Optional: make the ScrollPane fit the width of its content
		Scene scene = new Scene(scrollPane, 200, 200);
		stage.setTitle("ScrollPane Example");
		stage.setScene(scene);
		stage.show();*/
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
		updateCollectionCardPane();
		updateInDeckCardPane();
	}

	public void updateCollectionCardPane() {
		Result result = PreMatchMenusController.showCardsForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<LargeCards> largeCards = new ArrayList<>();
		if (!result.getMessage().isEmpty()){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				LargeCards largeCard = new LargeCards(cardName, count);
				largeCard.setOnMouseClicked(this::addToDeck);
				largeCards.add(largeCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (LargeCards largeCard : largeCards) {
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
		LargeCards largeCard = (LargeCards) mouseEvent.getSource();
		Result result = PreMatchMenusController.addToDeck(largeCard.getName(), 1);
		updateCollectionCardPane();
		updateInDeckCardPane();
	}

	public void updateInDeckCardPane() {
		Result result = PreMatchMenusController.showDeckForGraphic();
		String[] cards = result.getMessage().split("\n");
		ArrayList<LargeCards> largeCards = new ArrayList<>();
		if (!result.getMessage().equals("")){
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				LargeCards largeCard = new LargeCards(cardName, count);
				largeCard.setOnMouseClicked(this::removeFromDeck);
				largeCards.add(largeCard);
			}
		}
		GridPane gridPane = new GridPane();
		int row = 0;
		int column = 0;
		for (LargeCards largeCard : largeCards) {
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

	public void removeFromDeck(MouseEvent mouseEvent) {
		LargeCards largeCard = (LargeCards) mouseEvent.getSource();
		GridPane gridPane = (GridPane) inDeckCardPane.getContent();
		int idx = 0;
		for (int i = 0; i < gridPane.getChildren().size(); i++) {
			if (gridPane.getChildren().get(i).equals(largeCard)) {
				break;
			}
			idx += ((LargeCards) gridPane.getChildren().get(i)).getCount();
		}
		Result result = PreMatchMenusController.deleteFromDeck(idx, 1);
		System.out.println(result);
		updateCollectionCardPane();
		updateInDeckCardPane();
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
