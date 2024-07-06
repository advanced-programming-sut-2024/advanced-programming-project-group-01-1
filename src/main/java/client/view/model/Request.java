package client.view.model;

import client.controller.game.ClientPreMatchMenusController;
import client.view.ClientAppview;
import client.view.game.prematch.ClientMatchFinderMenu;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Request extends HBox {
	private static final String textCSS = Request.class.getResource("/CSS/textstyle.css").toExternalForm();
	private static final String buttonCSS = Request.class.getResource("/CSS/buttonstyle.css").toExternalForm();

	Button reject;
	Label username;
	Button accept;
	ClientMatchFinderMenu menu;

	private static Button getButton(String text) {
		Button button = new Button(text);
		button.getStylesheets().add(buttonCSS);
		button.getStylesheets().add(textCSS);
		button.setId("ipad-dark-grey-small");
		button.setStyle("-fx-font-family: 'Mason Serif Regular'; -fx-font-size: 18");
		return button;
	}

	public Request(String username, ClientMatchFinderMenu menu) {
		this.reject = getButton("reject");
		this.reject.setOnMouseClicked(this::reject);
		this.username = new Label(username);
		this.username.setPrefWidth(96);
		this.username.setPrefHeight(50);
		this.username.setAlignment(javafx.geometry.Pos.CENTER);
		this.username.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		String textCSS = getClass().getResource("/CSS/textstyle.css").toExternalForm();
		this.username.getStylesheets().add(textCSS);
		this.username.setId("normal-text");
		this.accept = getButton("accept");
		this.accept.setOnMouseClicked(this::accept);
		this.getChildren().add(this.reject);
		this.getChildren().add(this.username);
		this.getChildren().add(this.accept);
		this.menu = menu;
	}

	private void accept(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.handleMatchRequest(username.getText(), true);
		if (ClientAppview.getMenu() instanceof ClientMatchFinderMenu)
			menu.showRequests();
	}

	private void reject(MouseEvent mouseEvent) {
		ClientPreMatchMenusController.handleMatchRequest(username.getText(), false);
		if (ClientAppview.getMenu() instanceof ClientMatchFinderMenu)
			menu.showRequests();
	}
}
