package view.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HistoryMenuRowController {

	@FXML
	private Label opponent, date, result, roundScore;

	@FXML
	private VBox roundScores;

	public void setOpponent(String opponent) {
		this.opponent.setText(opponent);
	}

	public void setDate(String date) {
		this.date.setText(date);
	}

	public void setResult(String result) {
		this.result.setText(result);
		if (result.equals("Win")) this.result.setStyle("-fx-text-fill: lightgreen");
		else if (result.equals("Lose")) this.result.setStyle("-fx-text-fill: pink");
		else this.result.setStyle("-fx-text-fill: black");
	}

	public void setRoundScore(int myRoundScore, int opponentRoundScore) {
		this.roundScore.setText(myRoundScore + "-" + opponentRoundScore);
	}

	public void setRoundScores(ArrayList<Integer> myScores, ArrayList<Integer> opponentScores) {
		for (int i = 0; i < myScores.size(); i++) {
			Label roundScore = new Label(myScores.get(i).toString() + "-" + opponentScores.get(i).toString());
			roundScores.getChildren().add(roundScore);
		}
	}



}
