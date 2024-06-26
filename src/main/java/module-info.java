module w {
	opens model to com.google.gson, com.fasterxml.jackson.databind;
	opens model.user to com.fasterxml.jackson.databind, com.google.gson;
	requires com.google.gson;
}