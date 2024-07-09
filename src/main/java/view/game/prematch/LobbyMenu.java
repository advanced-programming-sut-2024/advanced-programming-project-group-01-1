package view.game.prematch;

import controller.game.PreMatchMenusController;
import javafx.application.Application;
import javafx.application.Platform;
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
import model.Result;
import model.game.Game;
import model.user.User;
import view.AlertMaker;
import view.Appview;
import view.Constants;
import view.Menuable;
import view.game.GameMenusCommands;
import view.game.SelectPanel;
import view.model.LargeCard;
import view.model.PreviewCard;

import java.io.File;
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
	public Rectangle factionIconField;
	public Label factionNameField;
	public Label countField;
	public Label strengthField;
	public Label unitField;
	public Label specialField;
	public Label heroField;
	public Button firstButton;
	public Button secondButton;

	@Override
	public void start(Stage stage) {

		Appview.setStage(stage);
		URL url = getClass().getResource("/FXML/LobbyMenu.fxml");
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
		updateFaction();
		updateStats();
		updateButtons();
	}

	private void updateStats() {
		Result result = PreMatchMenusController.showInfo();
		String[] parts = result.getMessage().split("\n");
		countField.setText(parts[5].substring(25));
		specialField.setText(parts[6].substring(33));
		unitField.setText(parts[7].substring(30));
		heroField.setText(parts[8].substring(30));
		strengthField.setText(parts[9].substring(21));
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
		gridPane.setVgap(20);
		gridPane.setHgap(20);
		collectionCardPane.setContent(gridPane);
	}

	public void addToDeck(MouseEvent mouseEvent) {
		PreviewCard largeCard = (PreviewCard) mouseEvent.getSource();
		Result result = PreMatchMenusController.addToDeck(largeCard.getName(), 1);
		updateScreen();
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
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + leaderName + ".jpg")));
		leaderField.setFill(imagePattern);
	}

	private void updateFaction() {
		Result result = PreMatchMenusController.showNowFactionToGraphics();
		String factionName = result.getMessage();
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/icons/" + "deck_shield_" + factionName + ".png")));
		factionIconField.setFill(imagePattern);
		factionNameField.setText(factionName);
	}

	public void updateButtons() {
		boolean preferFirst = User.getLoggedInUser().getDeck().doesPreferFirst();
		firstButton.setDisable(preferFirst);
		secondButton.setDisable(!preferFirst);
	}

	public void removeFromDeck(MouseEvent mouseEvent) {
		PreviewCard largeCard = (PreviewCard) mouseEvent.getSource();
		GridPane gridPane = (GridPane) inDeckCardPane.getContent();
		int idx = 0;
		for (int i = 0; i < gridPane.getChildren().size(); i++) {
			if (gridPane.getChildren().get(i).equals(largeCard)) {
				break;
			}
			idx += ((PreviewCard) gridPane.getChildren().get(i)).getCount();
		}
		Result result = PreMatchMenusController.deleteFromDeck(idx, 1);
		System.out.println(result);
		updateScreen();
	}

	/*
	 * change leader panel
	 */

	public void changeLeader(MouseEvent mouseEvent) {
		Result result = PreMatchMenusController.showLeaders();
		String[] leaders = result.getMessage().split("\n");
		for (int i = 0; i < leaders.length; i += 2) {
			leaders[i] = leaders[i].substring(8);

		}
		String currentLeader = PreMatchMenusController.showNowLeaderToGraphics().getMessage();
		int ptr = -1;
		for (int i = 0; i < leaders.length; i += 2) {
			if (leaders[i].equals(currentLeader)) {
				ptr = i/2;
				break;
			}
		}
		new SelectPanel(root, leaders, ptr, this::selectLeaderByGraphic, true);
	}

	public void selectLeaderByGraphic(int idx) {
		Result result = PreMatchMenusController.selectLeader(idx);
		updateScreen();
	}

	/*
	 * change faction panel
	 */


	public void changeFaction(MouseEvent mouseEvent) {
		Result result = PreMatchMenusController.showFactions();
		String[] parts = result.getMessage().split("\n");
		String[] factions = new String[parts.length-2];
		for (int i = 2; i < parts.length; i += 2) {
			factions[i-2] = parts[i].substring(9);
			factions[i-1] = parts[i+1];

		}
		String currentFaction = PreMatchMenusController.showNowFactionToGraphics().getMessage();
		int ptr = -1;
		for (int i = 0; i < factions.length; i += 2) {
			if (factions[i].equals(currentFaction)) {
				ptr = i/2;
				break;
			}
		}
		new SelectPanel(root, factions, ptr, this::selectFactionByGraphic, true);
	}

	public void selectFactionByGraphic(int idx) {
		Result result = PreMatchMenusController.showFactions();
		String[] parts = result.getMessage().split("\n");
		String[] factions = new String[parts.length-2];
		for (int i = 2; i < parts.length; i += 2) {
			factions[i-2] = parts[i].substring(9);
			factions[i-1] = parts[i+1];

		}
		Result result1 = PreMatchMenusController.selectFaction(factions[idx*2]);
		updateScreen();
	}

	/*
	 * save deck panel
	 */

	public Pane saveDeckPane;
	public Label saveDeckLabel;
	public TextField saveDeckByNameTextField;
	public Button saveDeckByNameButton;
	public Button saveDeckByAddressButton;

	public void saveDeck(MouseEvent mouseEvent) {

		saveDeckPane = new Pane();
		saveDeckPane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
		saveDeckPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");

		saveDeckLabel = new Label("Save deck");
		saveDeckLabel.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 100);
		saveDeckLabel.setLayoutY(Constants.SCREEN_HEIGHT.getValue() / 2 - 100);
		saveDeckLabel.setPrefWidth(200);
		saveDeckLabel.setPrefHeight(50);
		saveDeckLabel.setAlignment(javafx.geometry.Pos.CENTER);
		saveDeckLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

		String textCSS = getClass().getResource("/CSS/textstyle.css").toExternalForm();
		saveDeckLabel.getStylesheets().add(textCSS);
		saveDeckLabel.setId("normal-text");

		saveDeckByNameTextField = new TextField();
		saveDeckByNameTextField.setAlignment(javafx.geometry.Pos.CENTER);
		String textFieldCSS = getClass().getResource("/CSS/textfieldstyle.css").toExternalForm();
		saveDeckByNameTextField.getStylesheets().add(textFieldCSS);
		System.err.println(saveDeckByNameTextField.getPrefWidth());
		saveDeckByNameTextField.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 150);
		saveDeckByNameTextField.setLayoutY(saveDeckLabel.getLayoutY() + 70);
		saveDeckByNameTextField.setPromptText("Enter deck name");

		saveDeckByNameButton = new Button("Save by name");
		String buttonCSS = getClass().getResource("/CSS/buttonstyle.css").toExternalForm();
		saveDeckByNameButton.getStylesheets().add(buttonCSS);
		saveDeckByNameButton.getStylesheets().add(textCSS);
		saveDeckByNameButton.setId("ipad-dark-grey");
		saveDeckByNameButton.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");
		saveDeckByNameButton.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 75);
		saveDeckByNameButton.setLayoutY(saveDeckByNameTextField.getLayoutY() + 70);
		saveDeckByNameButton.setOnMouseClicked(this::saveDeckByName);


		saveDeckByAddressButton = new Button("choose file");
		saveDeckByAddressButton.getStylesheets().add(buttonCSS);
		saveDeckByAddressButton.getStylesheets().add(textCSS);
		saveDeckByAddressButton.setId("ipad-dark-grey");
		saveDeckByAddressButton.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");
		saveDeckByAddressButton.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 75);
		saveDeckByAddressButton.setLayoutY(saveDeckByNameButton.getLayoutY() + 70);
		saveDeckByAddressButton.setOnMouseClicked(this::saveDeckByAddress);


		saveDeckPane.getChildren().addAll(saveDeckLabel, saveDeckByNameTextField,  saveDeckByNameButton, saveDeckByAddressButton);
		root.getChildren().add(saveDeckPane);
	}

	public void saveDeckByName(MouseEvent mouseEvent) {
		Result result = PreMatchMenusController.saveDeckByName(saveDeckByNameTextField.getText());
		AlertMaker.makeAlert("Save deck", result);
		root.getChildren().remove(saveDeckPane);
		updateScreen();
	}

	public void saveDeckByAddress(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save deck");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showOpenDialog(Appview.getStage());
		if (file == null) {
			return;
		}
		String address = file.getAbsolutePath();
		Result result = PreMatchMenusController.saveDeckByAddress(address);
		AlertMaker.makeAlert("Save deck", result);
		root.getChildren().remove(saveDeckPane);
		updateScreen();
	}

	/*
	 * load deck panel
	 */

	public Pane loadDeckPane;
	public Label loadDeckLabel;
	public TextField loadDeckByNameTextField;
	public Button loadDeckByNameButton;
	public Button loadDeckByAddressButton;

	public void loadDeck(MouseEvent mouseEvent) {
		loadDeckPane = new Pane();
		loadDeckPane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
		loadDeckPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");

		loadDeckLabel = new Label("Load deck");
		loadDeckLabel.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 100);
		loadDeckLabel.setLayoutY(Constants.SCREEN_HEIGHT.getValue() / 2 - 100);
		loadDeckLabel.setPrefWidth(200);
		loadDeckLabel.setPrefHeight(50);
		loadDeckLabel.setAlignment(javafx.geometry.Pos.CENTER);
		loadDeckLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

		String textCSS = getClass().getResource("/CSS/textstyle.css").toExternalForm();
		loadDeckLabel.getStylesheets().add(textCSS);
		loadDeckLabel.setId("normal-text");

		loadDeckByNameTextField = new TextField();
		loadDeckByNameTextField.setAlignment(javafx.geometry.Pos.CENTER);
		String textFieldCSS = getClass().getResource("/CSS/textfieldstyle.css").toExternalForm();
		loadDeckByNameTextField.getStylesheets().add(textFieldCSS);
		System.err.println(loadDeckByNameTextField.getPrefWidth());
		loadDeckByNameTextField.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 150);
		loadDeckByNameTextField.setLayoutY(loadDeckLabel.getLayoutY() + 70);
		loadDeckByNameTextField.setPromptText("Enter deck name");

		loadDeckByNameButton = new Button("Load by name");
		String buttonCSS = getClass().getResource("/CSS/buttonstyle.css").toExternalForm();
		loadDeckByNameButton.getStylesheets().add(buttonCSS);
		loadDeckByNameButton.getStylesheets().add(textCSS);
		loadDeckByNameButton.setId("ipad-dark-grey");
		loadDeckByNameButton.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");
		loadDeckByNameButton.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 75);
		loadDeckByNameButton.setLayoutY(loadDeckByNameTextField.getLayoutY() + 70);
		loadDeckByNameButton.setOnMouseClicked(this::loadDeckByName);

		loadDeckByAddressButton = new Button("choose file");
		loadDeckByAddressButton.getStylesheets().add(buttonCSS);
		loadDeckByAddressButton.getStylesheets().add(textCSS);
		loadDeckByAddressButton.setId("ipad-dark-grey");
		loadDeckByAddressButton.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");
		loadDeckByAddressButton.setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - 75);
		loadDeckByAddressButton.setLayoutY(loadDeckByNameButton.getLayoutY() + 70);
		loadDeckByAddressButton.setOnMouseClicked(this::loadDeckByAddress);

		loadDeckPane.getChildren().addAll(loadDeckLabel, loadDeckByNameTextField,  loadDeckByNameButton, loadDeckByAddressButton);
		root.getChildren().add(loadDeckPane);
	}

	public void loadDeckByName(MouseEvent mouseEvent) {
		Result result = PreMatchMenusController.loadDeckByName(loadDeckByNameTextField.getText());
		AlertMaker.makeAlert("Load deck", result);
		root.getChildren().remove(loadDeckPane);
		updateScreen();
	}

	public void loadDeckByAddress(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load deck");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showOpenDialog(Appview.getStage());
		if (file == null) {
			return;
		}
		String address = file.getAbsolutePath();
		Result result = PreMatchMenusController.loadDeckByAddress(address);
		AlertMaker.makeAlert("Load deck", result);
		root.getChildren().remove(loadDeckPane);
		updateScreen();
	}

	public void ready(MouseEvent mouseEvent) {
		Result result = PreMatchMenusController.changeTurn();
		AlertMaker.makeAlert("ready", result);
		updateScreen();
	}

	public void setFirst(MouseEvent mouseEvent) {
		PreMatchMenusController.setPreferFirst(true);
		updateButtons();
	}

	public void setSecond(MouseEvent mouseEvent) {
		PreMatchMenusController.setPreferFirst(false);
		updateButtons();
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
		} else if (GameMenusCommands.PREFER_FIRST.getMatcher(input) != null) {
			result = preferFirst();
		} else if (GameMenusCommands.PREFER_SECOND.getMatcher(input) != null) {
			result = preferSecond();
		} else {
			result = new Result("Invalid command", false);
		}
		if (result != null) {
			System.out.println(result);
		}
		Platform.runLater(() -> {updateScreen();});
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

	private Result preferFirst() {
		return PreMatchMenusController.setPreferFirst(true);
	}

	private Result preferSecond() {
		return PreMatchMenusController.setPreferFirst(false);
	}

}
