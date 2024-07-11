package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.view.ClientAppview;
import client.view.model.SmallCard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import message.Result;

import java.net.URL;

public class ClientStreamMenu extends ClientMatchMenu {


    @Override
    public void createStage() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        ClientAppview.setStage(stage);
        URL url = getClass().getResource("/FXML/StreamMenu.fxml");
        if (url == null) {
            System.out.println("Couldn't find file: StreamMenu.fxml");
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
        initializePanes();
        initializeOnlineStatus();
        lastMove = "null\n" + (ClientMatchMenuController.getNumberOfMoves() - 1) + "\n";
        updater =  new Thread(() -> {
            try {
                while (true) {
                    int number = getLastMoverNumber() + 1;
                    Result result = ClientMatchMenuController.getMove(number);
                    if (result.getMessage() != null) {
                        lastMove = result.getMessage();
                        String description = getLastMoveDescription();
                        boolean isOpponent = getLastMover().equals("opponent");
                        if (description.startsWith("reaction")) {
                            if(isOpponent)
                               Platform.runLater(() -> reactionForOpponent(description.substring(9)));
                        }
                        else if (isOpponent) Platform.runLater(() -> opponentPut(description));
                        else Platform.runLater(() -> myPut(description));
                    }
                    Thread.sleep(345);
                }
            } catch (Exception e) {
                return;
            }
        });
        updater.setDaemon(true);
        updater.start();
        updateScreen();
    }

    private String getLastMover() {
        return lastMove.substring(0, lastMove.indexOf('\n'));
    }

    private int getLastMoverNumber() {
        String description = lastMove.substring(getLastMover().length() + 1);
        return Integer.parseInt(description.substring(0, description.indexOf('\n')));
    }

    private String getLastMoveDescription() {
        String description = lastMove.substring(lastMove.indexOf('\n') + 1);
        description = description.substring(description.indexOf('\n') + 1);
        return description;
    }

    @Override
    public void updateHand() {
        updateSpace(handPane, new String[]{}, null);
    }

    @Override
    public void showLeader(MouseEvent mouseEvent) {
        clearSelectedCard();
        Pane pane = (Pane) mouseEvent.getSource();
        SmallCard card = (SmallCard) pane.getChildren().get(0);
        String[] cardsInfo = new String[2];
        cardsInfo[0] = card.getName();
        cardsInfo[1] = card.getDescription();
        SelectPanel selectPanel = new SelectPanel(root, cardsInfo, 0, null, true);
    }

    public void myPut(String myMove) {
        if (myMove.equals("pass")){
            updateScreen();
            return;
        }
        String[] cardsInfo = myMove.split("\n");
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
        card.setLayoutY(1366-card.getHeight());
        unclickablePane.getChildren().add(card);
        Pane dest;
        if (row != -1) {
            if (card.getType().equals("Buffer")) {
                dest = rowBufferPanes[row];
            } else {
                dest = rowPanes[row];
            }
        } else if (card.getType().equals("Weather")) {
            dest = weatherPane;
        } else {
            dest = opponentPilePane;
        }
        putAnimation(card, dest.getLayoutX() + (dest.getPrefWidth() - card.getPrefWidth()) / 2, dest.getLayoutY(), false, -1, row);
    }

    public void back(MouseEvent mouseEvent) {
        ClientMatchMenuController.back();
    }
}
