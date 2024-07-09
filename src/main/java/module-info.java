module w {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	//opens views to javafx.fxml;
	opens client.view to javafx.fxml, javafx.graphics;
	opens client.view.game to javafx.fxml, javafx.graphics;
	opens client.view.game.prematch to javafx.fxml, javafx.graphics;
	opens client.view.sign.login to javafx.fxml, javafx.graphics;
	opens client.view.sign.register to javafx.fxml, javafx.graphics;
	opens client.view.user to javafx.fxml, javafx.graphics;

	// open models to gson and jackson
	opens server.model to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.user to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.card to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.card.unit to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.card.ability to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.card.special to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.card.special.spell to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.game to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.game.space to com.google.gson, com.fasterxml.jackson.databind;
	opens server.model.leader to com.google.gson, com.fasterxml.jackson.databind;



	exports server.model;
	exports server.model.user;
	exports server.model.card;
	exports server.model.card.unit;
	exports server.model.card.ability;
	exports server.model.card.special;
	exports server.model.card.special.spell;
	exports server.model.game;
	exports server.model.game.space;
	exports server.model.leader;

	exports server.main;
	exports server.view;
	exports message;
	opens message to com.google.gson, javafx.fxml, javafx.graphics;

	//opens other packages to gson and jackson
	//opens java.util to com.google.gson, com.fasterxml.jackson.databind;
	requires com.google.gson;
	requires jakarta.mail;
	requires jdk.httpserver;
}