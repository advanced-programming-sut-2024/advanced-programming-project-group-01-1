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
	opens model.user to com.fasterxml.jackson.databind, com.google.gson;
	opens model.card to com.fasterxml.jackson.databind, com.google.gson;
	opens model.card.unit to com.fasterxml.jackson.databind, com.google.gson;
	opens model.card.ability to com.fasterxml.jackson.databind, com.google.gson;
	opens model.card.special to com.fasterxml.jackson.databind, com.google.gson;
	opens model.card.special.spell to com.fasterxml.jackson.databind, com.google.gson;
	opens model.game to com.fasterxml.jackson.databind, com.google.gson;
	opens model.game.space to com.fasterxml.jackson.databind, com.google.gson;
	opens model.leader to com.fasterxml.jackson.databind, com.google.gson;
	requires com.google.gson;
}