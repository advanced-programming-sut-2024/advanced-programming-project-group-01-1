package client.view.game;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import client.view.Constants;
import client.view.model.LargeCard;

import java.util.ArrayList;

public class SelectPanel {

    Pane root, selectPanelPane;
    ArrayList<LargeCard> cards = new ArrayList<>();
    int ptr;
    SelectionHandler selectionHandler;

    public SelectPanel(Pane root, String[] cards, int ptr, SelectionHandler selectionHandler) {
        this.root = root;
        this.selectionHandler = selectionHandler;
        this.ptr = ptr;
        selectPanelPane = new Pane();
        selectPanelPane.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
        selectPanelPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        for (int i = 0; i < cards.length; i += 2) {
            LargeCard card = new LargeCard(cards[i], cards[i + 1]);
            this.cards.add(card);
        }
        root.getChildren().add(selectPanelPane);
        updatePanel();
    }

    private void updatePanel() {
        selectPanelPane.getChildren().clear();
		cards.get(ptr).setLayoutX(Constants.SCREEN_WIDTH.getValue() / 2 - Constants.LARGE_CARD_WIDTH.getValue() / 2);
		cards.get(ptr).setLayoutY(50);
		cards.get(ptr).setStyle("-fx-opacity: 1");
		cards.get(ptr).setOnMouseClicked(this::selectCard);
		selectPanelPane.getChildren().add(cards.get(ptr));
        Label label = new Label(cards.get(ptr).getDescription());
		label.setLayoutX(cards.get(ptr).getLayoutX());
		label.setLayoutY(Constants.SCREEN_HEIGHT.getValue() - 200);
		label.setPrefWidth(Constants.LARGE_CARD_WIDTH.getValue());
		label.setPrefHeight(150);
		label.setWrapText(true);
		label.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-background-color: rgba(0, 0, 0, 0.9)");
		label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		label.setAlignment(javafx.geometry.Pos.CENTER);
		selectPanelPane.getChildren().add(label);
		for (int i = 0; i < cards.size(); i++) {
            if (i < ptr - 1 && i > ptr + 1) {
                continue;
            } else if (i == ptr - 1) {
                cards.get(i).setLayoutX(cards.get(ptr).getLayoutX() - Constants.LARGE_CARD_WIDTH.getValue() - 100);
                cards.get(i).setLayoutY(cards.get(ptr).getLayoutY());
                cards.get(i).setStyle("-fx-opacity: 0.8");
                cards.get(i).setOnMouseClicked(this::previousCard);
                selectPanelPane.getChildren().add(cards.get(i));
            } else if (i == ptr + 1) {
                cards.get(i).setLayoutX(cards.get(ptr).getLayoutX() + Constants.LARGE_CARD_WIDTH.getValue() + 100);
                cards.get(i).setLayoutY(cards.get(ptr).getLayoutY());
                cards.get(i).setStyle("-fx-opacity: 0.8");
                cards.get(i).setOnMouseClicked(this::nextCard);
                selectPanelPane.getChildren().add(cards.get(i));
            }
        }
    }

    private void selectCard(MouseEvent mouseEvent) {
        root.getChildren().remove(selectPanelPane);
        selectionHandler.handle(ptr);
    }

    private void previousCard(MouseEvent mouseEvent) {
        ptr--;
        updatePanel();
    }

    private void nextCard(MouseEvent mouseEvent) {
        ptr++;
        updatePanel();
    }
}
