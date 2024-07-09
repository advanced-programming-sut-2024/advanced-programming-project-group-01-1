package client.view.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientRankingMenuRowController {

	@FXML
	private Label rank, username, rating;

	public void setRank(int rank) {
		this.rank.setText(String.valueOf(rank));
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public void setRating(int rating) {
		this.rating.setText(String.valueOf(rating));
	}

}