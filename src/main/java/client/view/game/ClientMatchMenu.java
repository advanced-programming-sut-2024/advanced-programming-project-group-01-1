package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.view.ClientAppview;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.GameMenusCommands;
import message.Result;
import client.view.Constants;
import client.view.Menuable;
import client.view.animation.CardMoving;
import client.view.model.SmallCard;
import client.view.model.SmallUnit;
import message.SelectionHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;


public class ClientMatchMenu extends Application implements Menuable {

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
	public ImageView weatherEffectRow0;
	public ImageView weatherEffectRow1;
	public ImageView weatherEffectRow2;
	public ImageView weatherEffectRow3;
	public ImageView weatherEffectRow4;
	public ImageView weatherEffectRow5;
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
	public Label myPassedField, opponentPassedField;
	public Label myDeckNumber, opponentDeckNumber;
	public Pane myInfoPane, opponentInfoPane;
	public ImageView opponentOnlineField;
	public Pane reactionPane;
	public ImageView myReactionField, opponentReactionField;
	public Pane[] rowPanes;
	public Pane[] rowBufferPanes;
	public Label[] rowPowerLabels;
	public ImageView[] weatherEffectPanes;
	public Pane selectedCard;
	public ArrayList<Pane> selectedPanes = new ArrayList<>();
	private boolean isCheating = false;
	public Pane unclickablePane = new Pane();
	protected String lastMove;
	Thread updater, onlineStatus, myReaction, opponentReaction;

	ArrayList<CardMoving> animations = new ArrayList<>();

	@Override
	public void start(Stage stage) {
		ClientAppview.setStage(stage);
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
		initializePanes();
		initializeOnlineStatus();
		lastMove = (ClientMatchMenuController.getNumberOfMoves() - 1) + "\n";
		updater =  new Thread(() -> {
				try {
					while (true) {
						int number = Integer.parseInt(lastMove.substring(0, lastMove.indexOf('\n'))) + 1;
						Result result = ClientMatchMenuController.getOpponentMove(number);
						if (result.getMessage() != null) {
							lastMove = result.getMessage();
							String description = lastMove.substring(lastMove.indexOf('\n') + 1);
							if (description.startsWith("reaction"))
								Platform.runLater(() -> reactionForOpponent(description.substring(9)));
							else Platform.runLater(() -> opponentPut(description));
						}
						Thread.sleep(234);
					}
				} catch (Exception e) {
					return;
				}
		});
		updater.setDaemon(true);
		updater.start();
		updateScreen();
	}

	protected void initializePanes() {
		rowPanes = new Pane[]{rowPane0, rowPane1, rowPane2, rowPane3, rowPane4, rowPane5};
		rowBufferPanes = new Pane[]{rowBuffer0, rowBuffer1, rowBuffer2, rowBuffer3, rowBuffer4, rowBuffer5};
		rowPowerLabels = new Label[]{rowPowerLabel0, rowPowerLabel1, rowPowerLabel2, rowPowerLabel3, rowPowerLabel4, rowPowerLabel5};
		weatherEffectPanes = new ImageView[]{weatherEffectRow0, weatherEffectRow1, weatherEffectRow2, weatherEffectRow3, weatherEffectRow4, weatherEffectRow5};
		for (Pane pane : rowPanes) {
			pane.setOnMouseClicked(this::showSpace);
		}
		for (Pane pane : rowBufferPanes) {
			pane.setOnMouseClicked(this::showSpace);
		}
		weatherPane.setOnMouseClicked(this::showSpace);
		myPilePane.setOnMouseClicked(this::showSpace);
		opponentPilePane.setOnMouseClicked(this::showSpace);
		myLeaderPane.setOnMouseClicked(this::showLeader);
		opponentLeaderPane.setOnMouseClicked(this::showSpace);
		ClientAppview.setMenuOnMatchMenu(this);
		for (Node node : reactionPane.getChildren()) {
			node.setOnMouseClicked(this::reaction);
		}
		reactionPane.setVisible(false);
	}

	protected void initializeOnlineStatus() {
		onlineStatus = new Thread(() -> {
			try {
				while (true) {
					Result result = ClientMatchMenuController.isOpponentOnline();
					Platform.runLater(() -> {
						if (result.isSuccessful()) {
							opponentOnlineField.setEffect(null);
						} else {
							if (result.getMessage().equals("Offline")) {
								ColorAdjust colorAdjust = new ColorAdjust();
								colorAdjust.setSaturation(-1);
								opponentOnlineField.setEffect(colorAdjust);
							} else updateScreen();
						}
					});
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				return;
			}
		});
		onlineStatus.setDaemon(true);
		onlineStatus.start();
	}

	public void updateScreen() {
		removeCards();
		updateHand();
		updateRows();
		updateWeather();
		updateDiscardPiles();
		updateDecks();
		updateLeader();
		updateInfo();
		if (ClientMatchMenuController.isAsking().isSuccessful()) {
			String[] cards = ClientMatchMenuController.getAskerCards().getMessage().split("\n");
			int ptr = Integer.parseInt(ClientMatchMenuController.getAskerPtr().getMessage());
			SelectionHandler selectionHandler = index -> {
				ClientMatchMenuController.selectCard(index);
				updateScreen();
			};
			boolean isOptional = ClientMatchMenuController.isAskerOptional().isSuccessful();
			SelectPanel selectPanel = new SelectPanel(root, cards, ptr, selectionHandler, isOptional);
			if (isOptional) {
				selectPanel.getBackButton().setOnMouseClicked(event -> {
					selectPanel.selectCard(-1);
				});
			}
		}
		if (ClientMatchMenuController.isGameOver()) {
			updater.interrupt();
			onlineStatus.interrupt();
			showEndGame();
		}
	}

	public void removeCardsFromSpace(Pane space) {
		ArrayList<Node> nodes = new ArrayList<>(space.getChildren());
		for (Node node : nodes) {
			if (node instanceof SmallCard) {
				node.setLayoutX(node.getParent().getLayoutX() + node.getLayoutX());
				node.setLayoutY(node.getParent().getLayoutY() + node.getLayoutY());
				space.getChildren().remove(node);
			}
		}
	}

	public void removeCards() {
		for (Node node: handPane.getChildren()) {
			node.setOnMouseClicked(null);
			node.setOnMouseEntered(null);
			node.setOnMouseExited(null);
		}
		removeCardsFromSpace(handPane);
		for (Pane pane : rowPanes) {
			removeCardsFromSpace(pane);
		}
		for (Pane pane : rowBufferPanes) {
			removeCardsFromSpace(pane);
		}
		removeCardsFromSpace(weatherPane);
		removeCardsFromSpace(myPilePane);
		removeCardsFromSpace(opponentPilePane);
	}

	public void updateSpace(Pane space, String[] cardsInfo, EventHandler<MouseEvent> clickHandler) {
		space.getChildren().clear();
		for (int i = 0; i < cardsInfo.length; i++) {
			SmallCard smallCard = getSmallCard(cardsInfo[i]);
			double x = 0;
			if (space.getPrefWidth() >= cardsInfo.length * smallCard.getPrefWidth()) {
				x = (space.getPrefWidth() - cardsInfo.length * smallCard.getPrefWidth()) / 2 + smallCard.getPrefWidth() * i;;
			} else {
				x = i == cardsInfo.length - 1 ? space.getPrefWidth() - smallCard.getPrefWidth() : (space.getPrefWidth() - smallCard.getPrefWidth()) / (cardsInfo.length - 1) * i;
			}
			if (smallCard.getType().equals("leader") || smallCard.getType().equals("faction")) {
				smallCard.setLayoutX(x);
				smallCard.setLayoutY(0);
				smallCard.setOnMouseClicked(clickHandler);
				space.getChildren().add(smallCard);
			} else {
				if (smallCard.getLayoutX() == 0 && smallCard.getLayoutY() == 0) {
					if (space == handPane || space == rowPane0 || space == rowPane1 || space == rowPane2 || space == rowBuffer0 || space == rowBuffer1 || space == rowBuffer2) {
						smallCard.setLayoutX(myDeckPane.getLayoutX());
						smallCard.setLayoutY(myDeckPane.getLayoutY());
					} else {
						smallCard.setLayoutX(opponentDeckPane.getLayoutX());
						smallCard.setLayoutY(opponentDeckPane.getLayoutY());
					}
				}
				smallCard.setLayoutX(smallCard.getLayoutX() - space.getLayoutX());
				smallCard.setLayoutY(smallCard.getLayoutY() - space.getLayoutY());
				space.getChildren().add(smallCard);
				smallCard.setOnMouseClicked(clickHandler);
				moveAnimation(smallCard, x, 0);
			}
		}
	}

	public void updateHand() {
		Result result = ClientMatchMenuController.showHandForGraphic();
		if (result.getMessage().isEmpty()){
			updateSpace(handPane, new String[]{}, null);
			return;
		}
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		if (ClientMatchMenuController.isMyTurn()) {
			updateSpace(handPane, cardsInfo, this::selectCard);
			handPane.setOnMouseClicked(null);
		}
		else {
			updateSpace(handPane, cardsInfo, null);
			handPane.setOnMouseClicked(this::showSpace);
		}
		for (Node node: handPane.getChildren()) {
			if (node instanceof SmallUnit) {
				SmallUnit unit = (SmallUnit) node;
				unit.setCurrentPower(String.valueOf(unit.getPower()));
			}
			SmallCard card = (SmallCard) node;
			if (ClientMatchMenuController.isMyTurn()) {
				card.setOnMouseEntered((e) -> {
					SmallCard card1 = (SmallCard) e.getSource();
					card1.setLayoutY(-10);
				});
				card.setOnMouseExited((e) -> {
					SmallCard card1 = (SmallCard) e.getSource();
					card1.setLayoutY(0);
				});
			} else {
				card.setOnMouseEntered(null);
				card.setOnMouseExited(null);
			}
		}
	}

	public void updateRows() {
		for (int i = 0; i < 6; i++) {
			Result result = ClientMatchMenuController.showRowForGraphic(i);
			if (result.getMessage().isEmpty()) {
				updateSpace(rowPanes[i], new String[]{}, null);
				updateSpace(rowBufferPanes[i], new String[]{}, null);
			}
			else {
				String[] cardsInfo = result.getMessage().split("\n------------------\n");
				if (cardsInfo.length >= 1 && cardsInfo[0].startsWith("Buffer: ")) {
					cardsInfo[0] = cardsInfo[0].substring(8);
					updateSpace(rowBufferPanes[i], new String[]{cardsInfo[0]}, null);
					String[] newCardsInfo = new String[cardsInfo.length - 1];
					System.arraycopy(cardsInfo, 1, newCardsInfo, 0, cardsInfo.length - 1);
					updateSpace(rowPanes[i], newCardsInfo, null);
				} else {
					updateSpace(rowBufferPanes[i], new String[]{}, null);
					updateSpace(rowPanes[i], cardsInfo, null);
				}
			}
			if (ClientMatchMenuController.isRowDebuffed(i)){
				rowPanes[i].getChildren().add(weatherEffectPanes[i]);
			}
		}
	}

	public void updateWeather() {
		Result result = ClientMatchMenuController.showWeatherSystemForGraphic();
		if (result.getMessage().isEmpty()){
			updateSpace(weatherPane, new String[]{}, null);
			return;
		}
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		updateSpace(weatherPane, cardsInfo, null);
	}

	public void updateDiscardPiles() {
		Result result = ClientMatchMenuController.showDiscardPilesForGraphic();
		String[] cardsInfo = result.getMessage().split("\n------------------\n");
		int idx = 0;
		for (int i = 0; i < cardsInfo.length; i++) {
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
		Result result = ClientMatchMenuController.remainingInDecksForGraphic();
		String[] cardsInfo = result.getMessage().split("\n");
		result = ClientMatchMenuController.showFactionsForGraphic();
		String[] factionsInfo = result.getMessage().split("\n");
		String[] myDeckInfo = new String[Integer.parseInt(cardsInfo[0])];
		myDeckNumber.setText(cardsInfo[0]);
		String[] userNames = ClientMatchMenuController.getUsernames().getMessage().split("\n");
		for (int i = 0; i < myDeckInfo.length; i++) {
			myDeckInfo[i] = factionsInfo[0].substring(9) + '\n' + "type: faction\nAbility: none\n" + "unique code: " + userNames[0] + "@" + i;
		}
		String[] opponentDeckInfo = new String[Integer.parseInt(cardsInfo[1])];
		opponentDeckNumber.setText(cardsInfo[1]);
		for (int i = 0; i < opponentDeckInfo.length; i++) {
			opponentDeckInfo[i] = factionsInfo[2].substring(9) + '\n' + "type: faction\nAbility: none\n" + "unique code: " + userNames[1] + "@" + i;
		}
		updateSpace(myDeckPane, myDeckInfo, null);
		updateSpace(opponentDeckPane, opponentDeckInfo, null);
	}

	public void updateLeader() {
		String leaders = ClientMatchMenuController.showLeadersForGraphic().getMessage();
		String[] leadersInfo = leaders.split("\n------------------\n");
		for (int i = 0; i < 2; i++){
			String[] leaderInfo = leadersInfo[i].split("\n");
			leadersInfo[i] = leaderInfo[0].substring(8) + '\n' + "type: leader\nAbility: " + leaderInfo[1] + '\n' + "unique code: " + leaderInfo[2];
		}
		updateSpace(myLeaderPane, new String[]{leadersInfo[0]}, null);
		updateSpace(opponentLeaderPane, new String[]{leadersInfo[1]}, null);
		String[] leadersDisables = ClientMatchMenuController.isLeadersDisable().getMessage().split("\n");
		if (Boolean.parseBoolean(leadersDisables[0])) {
			myLeaderPane.getChildren().get(0).setStyle("-fx-opacity: 0.5");
		}
		if (Boolean.parseBoolean(leadersDisables[1])) {
			opponentLeaderPane.getChildren().get(0).setStyle("-fx-opacity: 0.5");
		}
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
		String power = ClientMatchMenuController.getPowers().getMessage().split("\n")[0];
		myPower.setText(power);
		myPower.setStyle("-fx-font-size: 20");
		power = ClientMatchMenuController.getPowers().getMessage().split("\n")[1];
		opponentPower.setText(power);
		opponentPower.setStyle("-fx-font-size: 20");
		String life = ClientMatchMenuController.showPlayersLives().getMessage();
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
		String handSize = ClientMatchMenuController.showHandSize().getMessage();
		String[] handSizeInfo = handSize.split("\\s");
		myHandSize.setText(handSizeInfo[0]);
		opponentHandSize.setText(handSizeInfo[2]);
		meAhead.setVisible(Integer.parseInt(myPower.getText()) > Integer.parseInt(opponentPower.getText()));
		opponentAhead.setVisible(Integer.parseInt(myPower.getText()) < Integer.parseInt(opponentPower.getText()));
		String factions = ClientMatchMenuController.showFactionsForGraphic().getMessage();
		String[] factionsInfo = factions.split("\n");
		myFactionNameField.setText(factionsInfo[0].substring(9));
		opponentFactionNameField.setText(factionsInfo[2].substring(9));
		myFactionField.setImage(new Image(this.getClass().getResource("/images/icons/deck_shield_" + factionsInfo[0].substring(9) + ".png").toString()));
		opponentFactionField.setImage(new Image(this.getClass().getResource("/images/icons/deck_shield_" + factionsInfo[2].substring(9) + ".png").toString()));
		String usernames = ClientMatchMenuController.getUsernames().getMessage();
		String[] usernamesInfo = usernames.split("\n");
		myUsernameField.setText(usernamesInfo[0]);
		opponentUsernameField.setText(usernamesInfo[1]);
		String[] passedInfo = ClientMatchMenuController.passedState().getMessage().split("\n");
		myPassedField.setVisible(Boolean.parseBoolean(passedInfo[0]));
		opponentPassedField.setVisible(Boolean.parseBoolean(passedInfo[1]));
		boolean result = ClientMatchMenuController.isMyTurn();
		if (result) {
			myInfoPane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
			opponentInfoPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		} else {
			myInfoPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
			opponentInfoPane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
		}
	}

	public void showEndGame() {

		SmallCard.clearCache();

		Pane pane = new Pane();
		pane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
		pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");
		pane.setOnMouseClicked(event -> {
			ClientMatchMenuController.endGame();
		});

		ImageView image;
		if (ClientMatchMenuController.isGameWin()) {
			image = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/end_win.png")));
		} else if (ClientMatchMenuController.isGameDraw()) {
			image = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/end_draw.png")));
		} else {
			image = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/end_lose.png")));
		}
		image.setFitWidth(300);
		image.setFitHeight(200);
		image.setLayoutX((Constants.SCREEN_WIDTH.getValue() - 300) / 2);
		image.setLayoutY(0);
		pane.getChildren().add(image);

		Label[][] labels = new Label[3][4];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 4; j++) {
				labels[i][j] = new Label();
				labels[i][j].setPrefSize(300, 100);
				labels[i][j].setLayoutX((Constants.SCREEN_WIDTH.getValue() - 1200) / 2 + 300 * j);
				labels[i][j].setLayoutY(200 + 100 * i);
				labels[i][j].setAlignment(Pos.CENTER);
				labels[i][j].setTextAlignment(TextAlignment.CENTER);
				labels[i][j].getStylesheets().add(this.getClass().getResource("/CSS/textstyle.css").toExternalForm());
				labels[i][j].setId("normal-text");
				if (i == 0) {
					if (j == 0) {
						labels[i][j].setText("username");
					} else {
						labels[i][j].setText("round " + j);
					}
				} else if (j == 0) {
					labels[i][j].setText(ClientMatchMenuController.getUsernames().getMessage().split("\n")[i - 1]);
				} else {
					labels[i][j].setText("-");
				}
				pane.getChildren().add(labels[i][j]);
			}
		}

		String result = ClientMatchMenuController.getScores().getMessage();
		if (!result.isEmpty()) {
			String[] scores = result.split("\n");
			for (int i = 0; i < scores.length; i += 2) {
				int myScore = Integer.parseInt(scores[i]);
				int opponentScore = Integer.parseInt(scores[i + 1]);
				int round = i / 2 + 1;
				labels[1][round].setText(String.valueOf(myScore));
				labels[2][round].setText(String.valueOf(opponentScore));
			}
		}

		Label label = new Label("Click to continue");
		label.setPrefSize(Constants.SCREEN_WIDTH.getValue(), 100);
		label.setLayoutX(0);
		label.setLayoutY(Constants.SCREEN_HEIGHT.getValue() - 100);
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.getStylesheets().add(this.getClass().getResource("/CSS/textstyle.css").toExternalForm());
		label.setId("normal-text");
		pane.getChildren().add(label);

		root.getChildren().add(pane);
	}

	public void selectCard(MouseEvent event) {
		clearSelectedCard();
		SmallCard card = (SmallCard) event.getSource();
		selectedCard = card;
		selectedCard.setOnMouseEntered(null);
		selectedCard.setOnMouseExited(null);
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
				if (rowBufferPanes[i].getChildren().isEmpty()) panes.add(rowBufferPanes[i]);
			}
		} else if (card.getType().equals("Decoy")) {
			for (int i = 0; i < 3; i++) {
				if (rowPanes[i].getChildren().size() > 1 || (rowPanes[i].getChildren().isEmpty() && rowPanes[i].getChildren().get(0) instanceof SmallUnit)) {
					panes.add(rowPanes[i]);
				}
			}
		}
		else {
			panes.add(myPilePane);
		}
		for (Pane pane : panes) {
			pane.setOnMouseClicked(this::placeCard);
			pane.setStyle("-fx-background-color: rgba(0, 255, 0, 0.2);");
			selectedPanes.add(pane);
		}
	}

	public void clearSelectedCard() {
		if (selectedCard != null) {
			selectedCard.setLayoutY(0);
			selectedCard.setOnMouseEntered((e) -> {
				SmallCard card = (SmallCard) e.getSource();
				card.setLayoutY(-10);
			});
			selectedCard.setOnMouseExited((e) -> {
				SmallCard card = (SmallCard) e.getSource();
				card.setLayoutY(0);
			});
			selectedCard = null;
			for (Pane pane : selectedPanes) {
				pane.setOnMouseClicked(this::showSpace);
				pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
			}
			selectedPanes = new ArrayList<>();
		}
	}

	public void placeCard(MouseEvent event) {
		if (!ClientMatchMenuController.isMyTurn()) return;
		SmallCard card = (SmallCard) selectedCard;
		int idx = handPane.getChildren().indexOf(card);
		Pane pane = (Pane) event.getSource();
		int tmp = -1;
		for (int i = 0; i < 3; i++) {
			if (rowPanes[i] == pane || rowBufferPanes[i] == pane) {
				tmp = i;
				break;
			}
		}
		clearSelectedCard();
		int row = tmp;
		card.setLayoutX(handPane.getLayoutX() + card.getLayoutX());
		card.setLayoutY(handPane.getLayoutY() + card.getLayoutY());
		card.setOnMouseEntered(null);
		card.setOnMouseExited(null);
		unclickablePane.getChildren().add(card);
		new CardMoving(card, pane.getLayoutX() + (pane.getPrefWidth() - card.getPrefWidth()) / 2, pane.getLayoutY());
		putAnimation(card, pane.getLayoutX() + (pane.getPrefWidth() - card.getPrefWidth()) / 2, pane.getLayoutY(), true, idx, row);
	}

	public void showSpace(MouseEvent mouseEvent) {
		clearSelectedCard();
		Pane pane = (Pane) mouseEvent.getSource();
		String[] cardsInfo;
		if (pane.getChildren().isEmpty()) {
			return;
		}
		if (pane.getChildren().get(pane.getChildren().size() - 1) instanceof SmallCard) {
			cardsInfo = new String[2 * pane.getChildren().size()];
		} else {
			cardsInfo = new String[2 * pane.getChildren().size() - 2];
		}
		if (cardsInfo.length == 0) {
			return;
		}
		for (int i = 0; i < pane.getChildren().size(); i++) {
			if (pane.getChildren().get(i) instanceof SmallCard) {
				SmallCard card = (SmallCard) pane.getChildren().get(i);
				cardsInfo[i * 2] = card.getName();
				cardsInfo[i * 2 + 1] = card.getDescription();
			}
		}
		SelectPanel selectPanel = new SelectPanel(root, cardsInfo, 0, null, true);
	}

	public void showLeader(MouseEvent mouseEvent) {
		clearSelectedCard();
		Pane pane = (Pane) mouseEvent.getSource();
		SmallCard card = (SmallCard) pane.getChildren().get(0);
		String[] cardsInfo = new String[2];
		cardsInfo[0] = card.getName();
		cardsInfo[1] = card.getDescription();
		SelectPanel selectPanel = new SelectPanel(root, cardsInfo, 0, this::useLeader, true);
	}

	public void useLeader(int idx) {
		Result result = ClientMatchMenuController.useLeaderAbility();
		if (result.isSuccessful()) updateScreen();
	}

	public void passTurn(MouseEvent mouseEvent) {
		clearSelectedCard();
		Result result = ClientMatchMenuController.passTurn();
		if (result.isSuccessful()) updateScreen();
	}

	public SmallCard getSmallCard(String cardsInfo) {
		String[] cardInfo = cardsInfo.split("\n");
		String cardName = cardInfo[0];
		String type = cardInfo[1].substring(6);
		String Ability = cardInfo[2].substring(9);
		String cardDescription;
		if (type.equals("faction")) {
			cardDescription = "KTKM";
		} else if (type.equals("leader")) {
			cardDescription = Ability;
		} else {
			cardDescription = ClientMatchMenuController.getDescription(cardName).getMessage();
		}
		if (type.equals("Melee") || type.equals("Ranged") || type.equals("Siege") || type.equals("Agile")) {
			int power = Integer.parseInt(cardInfo[3].substring(7));
			String currentPower = cardInfo[4].substring(15);
			boolean hero = (cardInfo.length == 7);
			String uniqueCode = cardInfo[cardInfo.length - 1].substring(13);
			return SmallUnit.getInstance(cardName, cardDescription, type, Ability, power, currentPower, hero, uniqueCode);
		} else {
			String uniqueCode = cardInfo[3].substring(13);
			return SmallCard.getInstance(cardName, cardDescription, type, Ability, uniqueCode);
		}
	}

	public void opponentPut(String opponentMove) {
		System.out.println("\n------------------\n" + opponentMove + "\n------------------\n");
		if (opponentMove.equals("pass")){
			updateScreen();
			return;
		}
		String[] cardsInfo = opponentMove.split("\n");
		int row = Integer.parseInt(cardsInfo[0]);

		StringBuilder cardInfo = new StringBuilder();
		if (cardsInfo[1].startsWith("Leader:")){
			cardInfo.append(cardsInfo[1].substring(8)).append("\ntype: leader\nAbility: ").append(cardsInfo[2]).append("\nunique code: ").append(cardsInfo[3]);
		} else {
			for (int i = 1; i < cardsInfo.length; i++) cardInfo.append(cardsInfo[i]).append("\n");
		}
		SmallCard card = getSmallCard(cardInfo.toString());
		if (card.getType().equals("leader")) {
			//TODO: fix this
			updateScreen();
			return;
		}
		card.setLayoutX(0);
		card.setLayoutY(0);
		unclickablePane.getChildren().add(card);
		Pane dest;
		if (row != -1) {
			if (card.getType().equals("Buffer")) {
				dest = rowBufferPanes[5-row];
			} else {
				dest = rowPanes[5-row];
			}
		} else if (card.getType().equals("Weather")) {
			dest = weatherPane;
		} else {
			dest = opponentPilePane;
		}
		putAnimation(card, dest.getLayoutX() + (dest.getPrefWidth() - card.getPrefWidth()) / 2, dest.getLayoutY(), false, -1, row);
	}

	public void putAnimation(SmallCard card, double x, double y, boolean callPlace, int idx, int row) {
		CardMoving cardMoving = new CardMoving(card, x, y);
		if (animations.isEmpty()) {
			unclickablePane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
			root.getChildren().add(unclickablePane);
		}
		animations.add(cardMoving);
		cardMoving.setOnFinished(e -> {
			ImageView abilityIcon = null;
			switch (card.getAbility()){
				case "TightBond":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_bond.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 74 / 90);
					break;
				case "Horn":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_horn.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 74 / 88);
					break;
				case "Medic":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_medic.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 86 / 90);
					break;
				case "MoralBooster":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_morale.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 71 / 90);
					break;
				case "Muster":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_muster.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 73 / 76);
					break;
				case "Spy":
					abilityIcon = new ImageView(new Image(this.getClass().getResourceAsStream("/images/icons/anim_spy.png")));
					abilityIcon.setFitWidth(Constants.SMALL_CARD_WIDTH.getValue());
					abilityIcon.setFitHeight(abilityIcon.getFitWidth() * 67 / 90);
					break;
				default:
					break;
			}
			if (abilityIcon == null) {
				animations.remove(cardMoving);
				if (animations.isEmpty()){
					root.getChildren().remove(unclickablePane);
				}
				if (callPlace) {
					Result result = ClientMatchMenuController.placeCard(idx, row);
				}
				updateScreen();
			}
			else {
				unclickablePane.getChildren().add(abilityIcon);
				abilityIcon.setLayoutX(card.getLayoutX());
				abilityIcon.setLayoutY(card.getLayoutY() + ((card.getPrefHeight() - abilityIcon.getFitWidth()) / 2));
				FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), abilityIcon);
				fadeTransition.setFromValue(1);
				fadeTransition.setToValue(0);
				fadeTransition.setOnFinished(e1 -> {
					animations.remove(cardMoving);
					if (animations.isEmpty()){
						root.getChildren().remove(unclickablePane);
					}
					if (callPlace) {
						Result result = ClientMatchMenuController.placeCard(idx, row);
					}
					updateScreen();
				});
				fadeTransition.play();
			}
		});
		cardMoving.play();
	}

	public void moveAnimation(SmallCard card, double x, double y) {
		CardMoving cardMoving  = new CardMoving(card, x, y);
		if (animations.isEmpty()) {
			unclickablePane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
			root.getChildren().add(unclickablePane);
		}
		animations.add(cardMoving);
		cardMoving.setOnFinished(e -> {
			animations.remove(cardMoving);
			if (animations.isEmpty()) {
				root.getChildren().remove(unclickablePane);
			}
		});
		cardMoving.play();
	}


	public void openChat() {
		new ChatPanel(root, myUsernameField.getText());
	}

	public void reactionPanel(MouseEvent mouseEvent) {
		reactionPane.setVisible(!reactionPane.isVisible());
	}

	public void reaction(MouseEvent mouseEvent) {
		ImageView imageView = (ImageView) mouseEvent.getSource();
		Image image = imageView.getImage();
		String imageUrl = image.getUrl();
		System.out.println(imageUrl);
		String reaction;
        try {
			reaction = Paths.get(new URI(imageUrl)).getFileName().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
		Result result = ClientMatchMenuController.sendReaction(reaction);
		if (myReaction != null) myReaction.interrupt();
		myReaction = new Thread(() -> {
			try {
				myReactionField.setImage(new Image(this.getClass().getResource("/images/icons/" + reaction).toString()));
				Thread.sleep(7000);
				myReactionField.setImage(null);
			} catch (InterruptedException e) {
				return;
			}
		});
		myReaction.setDaemon(true);
		myReaction.start();
		reactionPane.setVisible(false);
    }

	public void reactionForOpponent(String reaction) {
		if (opponentReaction != null) opponentReaction.interrupt();
		opponentReaction = new Thread(() -> {
			try {
				opponentReactionField.setImage(new Image(this.getClass().getResource("/images/icons/" + reaction).toString()));
				Thread.sleep(7000);
				opponentReactionField.setImage(null);
			} catch (InterruptedException e) {
				return;
			}
		});
		opponentReaction.setDaemon(true);
		opponentReaction.start();
	}

        /*
	 * Terminal version of the LobbyMenu
	 */

	@Override
	public Result run(String input) {
		Result result = null;
		Matcher matcher;
		if (isCheating) {
			if (input.equals("exit")) {
				isCheating = false;
				result = new Result("Cheat menu deactivated", true);
			} else if (input.equals("clear weather")) {
				result = ClientMatchMenuController.cheatClearWeather();
			} else if (input.equals("recover crystal")) {
				result = ClientMatchMenuController.cheatHeal();
			} else if (input.equals("move from deck to hand")) {
				result = ClientMatchMenuController.cheatMoveFromDeckToHand();
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_POWER.getMatcher(input)) != null) {
				int power = Integer.parseInt(matcher.group("power"));
				result = ClientMatchMenuController.cheatAddPower(power);
			} else if ((matcher = GameMenusCommands.CHEAT_ADD_CARD.getMatcher(input)) != null) {
				String cardName = matcher.group("cardName");
				result = ClientMatchMenuController.cheatAddCard(cardName);
			} else if ((matcher = GameMenusCommands.CHEAT_DEBUFF_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = ClientMatchMenuController.cheatDebuffRow(rowNumber);
			} else if ((matcher = GameMenusCommands.CHEAT_CLEAR_ROW.getMatcher(input)) != null) {
				int rowNumber = Integer.parseInt(matcher.group("rowNumber"));
				result = ClientMatchMenuController.cheatClearRow(rowNumber);
			} else if (input.equals("show current menu")) {
				result = new Result("cheat menu", true);
			} else {
				result = new Result("Invalid command", false);
			}
		} else {
			if (ClientMatchMenuController.isAsking().isSuccessful()) {
				if ((matcher = GameMenusCommands.SELECT_CARD.getMatcher(input)) != null) {
					result = selectCard(matcher);
				} else {
					result = new Result("Invalid command", false);
				}
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
			} else if ((matcher = GameMenusCommands.SHOW_HAND.getMatcher(input)) != null) {
				result = ClientMatchMenuController.showCurrentHand();
			} else if (GameMenusCommands.CHEAT_MENU.getMatcher(input) != null) {
				isCheating = true;
				result = new Result("Cheat menu activated", true);
			} else {
				result = new Result("Invalid command", false);
			}
		}
		Platform.runLater(this::updateScreen);
		return result;
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

	private Result selectCard(Matcher matcher) {
		int cardNumber = Integer.parseInt(matcher.group("cardNumber"));
		return ClientMatchMenuController.selectCard(cardNumber);
	}
}
