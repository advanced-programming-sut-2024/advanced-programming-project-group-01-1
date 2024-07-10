package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.view.Constants;
import client.view.model.Chat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import message.Result;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ChatPanel {

    private Pane root, chatBox;
    private String username;
    Thread chatThread;


    public ListView chatBoxField;
    public TextArea messageField;

    public ChatPanel(Pane root, String username) {
        this.root = root;
        this.username = username;
        chatBox = new Pane();
        chatBox.setPrefSize(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
        chatBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        Rectangle backButton = new Rectangle(Constants.SCREEN_WIDTH.getValue(), Constants.SCREEN_HEIGHT.getValue());
        backButton.setStyle("-fx-fill: transparent;");
        backButton.setOnMouseClicked(event -> {
            root.getChildren().remove(chatBox);
            chatThread.interrupt();
        });
        chatBox.getChildren().add(backButton);

        chatBoxField = new ListView();
        chatBoxField.setPrefSize(600, 500);
        chatBoxField.setLayoutX((Constants.SCREEN_WIDTH.getValue() - chatBoxField.getPrefWidth()) / 2);
        chatBoxField.setLayoutY(50);
        chatBoxField.getStylesheets().add(getClass().getResource("/CSS/listviewstyle.css").toExternalForm());
        chatBoxField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            String[] parts = newValue.toString().split("\n--------------------\n");
            String replyMessage = parts[parts.length - 1];
            String[] replyParts = replyMessage.split("\n");
            StringBuilder reply = new StringBuilder();
            for (int i = 0; i < replyParts.length - 1; i++) {
                reply.append(replyParts[i]).append("\n");
            }
            messageField.setText("reply to:\n" + newValue + "\n--------------------\n");
        });
        chatBox.getChildren().add(chatBoxField);

        messageField = new TextArea();
        messageField.setPrefSize(600, 100);
        messageField.setLayoutX((Constants.SCREEN_WIDTH.getValue() - messageField.getPrefWidth()) / 2);
        messageField.setLayoutY(600);
        messageField.getStylesheets().add(getClass().getResource("/CSS/textareastyle.css").toExternalForm());
        messageField.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    sendMessage();
                    break;
            }
        });
        chatBox.getChildren().add(messageField);

        root.getChildren().add(chatBox);

        chatThread = new Thread(() -> {
            try {
                while (true) {
                    String[] message = ClientMatchMenuController.getChats().getMessage().split("\n####################\n");
                    int sz = chatBoxField.getItems().size();
                    for (int i = sz; i < message.length; i++) {
                        chatBoxField.getItems().add(message[i]);
                    }
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                return;
            }
        });
        chatThread.setDaemon(true);
        chatThread.start();
    }

    public void sendMessage() {
        System.out.println("Sending message");
        String message = messageField.getText();
        message.replaceAll("\\\\n", "\n");
        if (message.isBlank()) return;
        String replyMessage = "";
        if (message.startsWith("reply to:")) {
            String[] parts = message.split("\n--------------------\n");
            replyMessage = parts[0].substring(10);
            message = parts[1];

        }
        Result result = ClientMatchMenuController.sendMessage(new Chat(message, replyMessage, username).toString());
        messageField.clear();
    }

}
