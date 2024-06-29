module w {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	opens model to javafx.fxml, com.google.gson, com.fasterxml.jackson.databind;
	opens model.user to com.fasterxml.jackson.databind, com.google.gson;
	requires com.google.gson;
}