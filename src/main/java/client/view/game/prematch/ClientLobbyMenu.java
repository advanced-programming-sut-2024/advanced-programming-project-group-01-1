package client.view.game.prematch;

import client.controller.game.ClientPreMatchMenusController;
import client.view.AlertMaker;
import client.view.ClientAppview;
import client.view.Constants;
import client.view.Menuable;
import client.view.game.SelectPanel;
import client.view.model.PreviewCard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.util.Duration;
import message.GameMenusCommands;
import message.Result;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;


public class ClientLobbyMenu extends Application implements Menuable {

	@Override
	public void createStage() {
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
	public Button ready;
	private Rectangle waiting;
	private Timeline circulation;

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
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
		ready.setDisable(!ClientPreMatchMenusController.isDeckValid().isSuccessful());
	}

	private void updateStats() {
		Result result = ClientPreMatchMenusController.showInfo();
		String[] parts = result.getMessage().split("\n");
		countField.setText(parts[5].substring(25));
		specialField.setText(parts[6].substring(33));
		unitField.setText(parts[7].substring(30));
		heroField.setText(parts[8].substring(30));
		strengthField.setText(parts[9].substring(21));
	}

	private void updateCardsPane(Result result, ScrollPane cardPane, boolean add) {
		String[] cards = result.getMessage().split("\n");
		ArrayList<PreviewCard> previewCards = new ArrayList<>();
		if (!result.getMessage().isEmpty()) {
			for (String card : cards) {
				String[] cardInfo = card.split(":");
				String cardName = cardInfo[0];
				int count = Integer.parseInt(cardInfo[1]);
				PreviewCard previewCard = new PreviewCard(cardName, count);
				if (add) previewCard.setOnMouseClicked(this::addToDeck);
				else previewCard.setOnMouseClicked(this::removeFromDeck);
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
		cardPane.setContent(gridPane);
	}

	public void updateCollectionCardPane() {
		Result result = ClientPreMatchMenusController.showCardsForGraphic();
		updateCardsPane(result, collectionCardPane, true);
	}

	public void updateInDeckCardPane() {
		Result result = ClientPreMatchMenusController.showDeckForGraphic();
		updateCardsPane(result, inDeckCardPane, false);
	}

	public void addToDeck(MouseEvent mouseEvent) {
		PreviewCard largeCard = (PreviewCard) mouseEvent.getSource();
		Result result = ClientPreMatchMenusController.addToDeck(largeCard.getName(), 1);
		updateScreen();
	}

	private void updateLeader() {
		Result result = ClientPreMatchMenusController.showNowLeaderToGraphics();
		String leaderName = result.getMessage();
		System.out.println(leaderName);
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/largecards/" + leaderName + ".jpg")));
		leaderField.setFill(imagePattern);
	}

	private void updateFaction() {
		Result result = ClientPreMatchMenusController.showNowFactionToGraphics();
		String factionName = result.getMessage();
		System.out.println(factionName);
		ImagePattern imagePattern = new ImagePattern(new Image(getClass().getResourceAsStream("/images/icons/" + "deck_shield_" + factionName + ".png")));
		factionIconField.setFill(imagePattern);
		factionNameField.setText(factionName);
	}

	private void updateButtons() {
		boolean preferFirst = ClientPreMatchMenusController.getPreference().getMessage().equals("First");
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
		Result result = ClientPreMatchMenusController.deleteFromDeck(idx, 1);
		System.out.println(result);
		updateScreen();
	}

	public void changeLeader(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.showLeaders();
		String[] leaders = result.getMessage().split("\n");
		for (int i = 0; i < leaders.length; i += 2) {
			leaders[i] = leaders[i].substring(8);

		}
		String currentLeader = ClientPreMatchMenusController.showNowLeaderToGraphics().getMessage();
		int ptr = -1;
		for (int i = 0; i < leaders.length; i += 2) {
			if (leaders[i].equals(currentLeader)) {
				ptr = i / 2;
				break;
			}
		}
		new SelectPanel(root, leaders, ptr, this::selectLeaderByGraphic);
	}

	public void selectLeaderByGraphic(int idx) {
		Result result = ClientPreMatchMenusController.selectLeader(idx);
		updateScreen();
	}

	public void changeFaction(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.showFactions();
		String[] parts = result.getMessage().split("\n");
		String[] factions = new String[parts.length - 2];
		for (int i = 2; i < parts.length; i += 2) {
			factions[i - 2] = parts[i].substring(9);
			factions[i - 1] = parts[i + 1];

		}
		String currentFaction = ClientPreMatchMenusController.showNowFactionToGraphics().getMessage();
		int ptr = -1;
		for (int i = 0; i < factions.length; i += 2) {
			if (factions[i].equals(currentFaction)) {
				ptr = i / 2;
				break;
			}
		}
		new SelectPanel(root, factions, ptr, this::selectFactionByGraphic);
	}

	public void selectFactionByGraphic(int idx) {
		Result result = ClientPreMatchMenusController.showFactions();
		String[] parts = result.getMessage().split("\n");
		String[] factions = new String[parts.length - 2];
		for (int i = 2; i < parts.length; i += 2) {
			factions[i - 2] = parts[i].substring(9);
			factions[i - 1] = parts[i + 1];

		}
		Result result1 = ClientPreMatchMenusController.selectFaction(factions[idx * 2]);
		updateScreen();
	}

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

		//saveDeckLabel.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");

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


		saveDeckPane.getChildren().addAll(saveDeckLabel, saveDeckByNameTextField, saveDeckByNameButton, saveDeckByAddressButton);
		root.getChildren().add(saveDeckPane);
	}

	public void saveDeckByName(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.saveDeckByName(saveDeckByNameTextField.getText());
		AlertMaker.makeAlert("Save deck", result);
		root.getChildren().remove(saveDeckPane);
		updateScreen();
	}

	public void saveDeckByAddress(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save deck");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showOpenDialog(ClientAppview.getStage());
		if (file == null) {
			return;
		}
		String address = file.getAbsolutePath();
		Result result = ClientPreMatchMenusController.saveDeckByAddress(address);
		AlertMaker.makeAlert("Save deck", result);
		root.getChildren().remove(saveDeckPane);
		updateScreen();
	}

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

		loadDeckPane.getChildren().addAll(loadDeckLabel, loadDeckByNameTextField, loadDeckByNameButton, loadDeckByAddressButton);
		root.getChildren().add(loadDeckPane);
	}

	public void loadDeckByName(MouseEvent mouseEvent) {
		Result result = ClientPreMatchMenusController.loadDeckByName(loadDeckByNameTextField.getText());
		AlertMaker.makeAlert("Load deck", result);
		root.getChildren().remove(loadDeckPane);
		updateScreen();
	}

	public void loadDeckByAddress(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load deck");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON file", "*.json"));
		File file = fileChooser.showOpenDialog(ClientAppview.getStage());
		if (file == null) {
			return;
		}
		String address = file.getAbsolutePath();
		Result result = ClientPreMatchMenusController.loadDeckByAddress(address);
		AlertMaker.makeAlert("Load deck", result);
		root.getChildren().remove(loadDeckPane);
		updateScreen();
	}

	public void setFirst(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.preferFirst();
		updateButtons();
	}

	public void setSecond(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.preferSecond();
		updateButtons();
	}

	public void ready(MouseEvent mouseEvent) {
		if (ready.getText().equals("ready")) {
			if (ClientPreMatchMenusController.getReady().isSuccessful()) {
				for (Node child : root.getChildren())
					child.setDisable(true);
				waiting = new Rectangle(100, 100);
				waiting.setFill(new ImagePattern(new Image(ClientLobbyMenu.class.getResourceAsStream("/images/load.png"))));
				circulation = new Timeline(new KeyFrame(Duration.millis(30), actionEvent -> {
					waiting.setRotate(waiting.getRotate() + 10);
				}));
				circulation.setCycleCount(-1);
				circulation.play();
				root.getChildren().add(waiting);
				waiting.setX(Constants.SCREEN_WIDTH.getValue() / 2 - 50);
				waiting.setY(Constants.SCREEN_HEIGHT.getValue() / 2 - 50);
				factionIconField.setOpacity(0.5);
				leaderField.setOpacity(0.5);
				ready.setDisable(false);
				ready.setText("Back to Lobby");
			}
		} else {
			if (ClientPreMatchMenusController.cancelReady().isSuccessful()) {
				for (Node child : root.getChildren())
					child.setDisable(false);
				root.getChildren().remove(waiting);
				circulation.stop();
				factionIconField.setOpacity(1);
				leaderField.setOpacity(1);
				ready.setText("ready");
			}
		}
	}

	@Override
	public Result run(String input) {
		Matcher matcher;
		Result result;
		if (GameMenusCommands.SHOW_FACTIONS.getMatcher(input) != null)
			result = ClientPreMatchMenusController.showFactions();
		else if ((matcher = GameMenusCommands.SELECT_FACTION.getMatcher(input)) != null)
			result = selectFaction(matcher);
		else if (GameMenusCommands.SHOW_CARDS.getMatcher(input) != null)
			result = ClientPreMatchMenusController.showCards();
		else if (GameMenusCommands.SHOW_DECK.getMatcher(input) != null)
			result = ClientPreMatchMenusController.showDeck();
		else if (GameMenusCommands.SHOW_INFORMATION_CURRENT_USER.getMatcher(input) != null)
			result = ClientPreMatchMenusController.showInfo();
		else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_FILE_ADDRESS.getMatcher(input)) != null)
			result = saveDeckWithAddress(matcher);
		else if ((matcher = GameMenusCommands.SAVE_DECK_WITH_NAME.getMatcher(input)) != null)
			result = saveDeckWithName(matcher);
		else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_FILE_ADDRESS.getMatcher(input)) != null)
			result = loadDeckWithAddress(matcher);
		else if ((matcher = GameMenusCommands.LOAD_DECK_WITH_NAME.getMatcher(input)) != null)
			result = loadDeckWithName(matcher);
		else if (GameMenusCommands.SHOW_LEADERS.getMatcher(input) != null)
			result = ClientPreMatchMenusController.showLeaders();
		else if ((matcher = GameMenusCommands.SELECT_LEADER.getMatcher(input)) != null) result = selectLeader(matcher);
		else if ((matcher = GameMenusCommands.ADD_TO_DECK.getMatcher(input)) != null) result = addToDeck(matcher);
		else if ((matcher = GameMenusCommands.REMOVE_FROM_DECK.getMatcher(input)) != null)
			result = deleteFromDeck(matcher);
		else if (GameMenusCommands.PASS_ROUND.getMatcher(input) != null)
			result = ClientPreMatchMenusController.changeTurn();
		else if (GameMenusCommands.START_GAME.getMatcher(input) != null)
			result = ClientPreMatchMenusController.startGame();
		else if (GameMenusCommands.PREFER_FIRST.getMatcher(input) != null)
			result = ClientPreMatchMenusController.preferFirst();
		else if (GameMenusCommands.PREFER_SECOND.getMatcher(input) != null)
			result = ClientPreMatchMenusController.preferSecond();
		else if (GameMenusCommands.EXIT_MATCH_FINDER.getMatcher(input) != null)
			result = ClientPreMatchMenusController.exit();
		else result = new Result("Invalid command", false);
		Platform.runLater(() -> {updateScreen();});
		return result;
	}

	private Result loadDeckWithName(Matcher matcher) {
		String name = matcher.group("name");
		return ClientPreMatchMenusController.loadDeckByName(name);
	}

	private Result loadDeckWithAddress(Matcher matcher) {
		String address = matcher.group("fileAddress");
		return ClientPreMatchMenusController.loadDeckByAddress(address);
	}

	private Result selectFaction(Matcher matcher) {
		String faction = matcher.group("faction");
		return ClientPreMatchMenusController.selectFaction(faction);
	}

	private Result saveDeckWithAddress(Matcher matcher) {
		String address = matcher.group("fileAddress");
		return ClientPreMatchMenusController.saveDeckByAddress(address);
	}

	private Result saveDeckWithName(Matcher matcher) {
		String name = matcher.group("name");
		return ClientPreMatchMenusController.saveDeckByName(name);
	}

	private Result selectLeader(Matcher matcher) {
		int leaderNumber = Integer.parseInt(matcher.group("leaderNumber"));
		return ClientPreMatchMenusController.selectLeader(leaderNumber);
	}

	private Result addToDeck(Matcher matcher) {
		String cardName = matcher.group("cardName");
		int count = Integer.parseInt(matcher.group("count"));
		return ClientPreMatchMenusController.addToDeck(cardName, count);
	}

	private Result deleteFromDeck(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		int count = Integer.parseInt(matcher.group("count"));
		return ClientPreMatchMenusController.deleteFromDeck(cardNumber, count);
	}

}
