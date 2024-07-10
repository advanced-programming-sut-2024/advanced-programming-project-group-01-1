package client.view.game;

import client.controller.game.ClientMatchMenuController;
import client.view.Constants;
import client.view.model.Chat;
import javafx.application.Platform;
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
            System.out.println("new reply\n" + newValue);
            String[] parts = newValue.toString().split("\n--------------------\n");
            System.out.println(parts.length);
            String replyMessage = parts[parts.length - 1];
            System.out.println(replyMessage);
            String[] replyParts = replyMessage.split("\n");
            System.out.println(replyParts.length);
            StringBuilder reply = new StringBuilder();
            for (int i = 0; i < replyParts.length - 1; i++) {
                reply.append(replyParts[i]).append("\n");
            }
            messageField.setText("reply to:\n--------------------\n" + reply + "--------------------\n");
            messageField.positionCaret(messageField.getText().length());
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
                    Platform.runLater(() -> {
                        if (message.length == 1){
                            chatBoxField.getItems().clear();
                            chatBoxField.getItems().add(message[0]);
                        }
                        else {
                            int sz = chatBoxField.getItems().size();
                            for (int i = sz; i < message.length; i++) {
                                chatBoxField.getItems().add(message[i]);
                            }
                            chatBoxField.scrollTo(message.length - 1);
                        }
                    });
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
        if (message.isBlank()) return;
        String replyMessage = "";
        System.out.println("message: " + message);
        if (message.startsWith("reply to:")) {
            String[] parts = message.split("--------------------\n");
            replyMessage = parts[1];
            message = parts[2];
        }
        String messageToSend = new Chat(message, replyMessage, username).toString();
        System.out.println("message to send: " + messageToSend);
        Result result = ClientMatchMenuController.sendMessage(messageToSend);
        messageField.clear();
    }

}
