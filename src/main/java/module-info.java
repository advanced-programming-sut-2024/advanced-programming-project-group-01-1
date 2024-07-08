module w {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	//opens views to javafx.fxml;
	opens view to javafx.fxml, javafx.graphics;
	opens view.game to javafx.fxml, javafx.graphics;
	opens view.game.prematch to javafx.fxml, javafx.graphics;
	opens view.sign.login to javafx.fxml, javafx.graphics;
	opens view.sign.register to javafx.fxml, javafx.graphics;
	opens view.user to javafx.fxml, javafx.graphics;

	// open models to gson and jackson
	opens model to com.google.gson, com.fasterxml.jackson.databind;
	opens model.user to com.google.gson, com.fasterxml.jackson.databind;
	opens model.card to com.google.gson, com.fasterxml.jackson.databind;
	opens model.card.unit to com.google.gson, com.fasterxml.jackson.databind;
	opens model.card.ability to com.google.gson, com.fasterxml.jackson.databind;
	opens model.card.special to com.google.gson, com.fasterxml.jackson.databind;
	opens model.card.special.spell to com.google.gson, com.fasterxml.jackson.databind;
	opens model.game to com.google.gson, com.fasterxml.jackson.databind;
	opens model.game.space to com.google.gson, com.fasterxml.jackson.databind;
	opens model.leader to com.google.gson, com.fasterxml.jackson.databind;



	exports model;
	exports model.user;
	exports model.card;
	exports model.card.unit;
	exports model.card.ability;
	exports model.card.special;
	exports model.card.special.spell;
	exports model.game;
	exports model.game.space;
	exports model.leader;
	exports view.game;

	exports main;
	//opens other packages to gson and jackson
	//opens java.util to com.google.gson, com.fasterxml.jackson.databind;

	requires com.google.gson;
}