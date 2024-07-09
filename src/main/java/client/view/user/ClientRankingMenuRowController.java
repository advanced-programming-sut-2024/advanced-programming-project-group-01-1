package client.view.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class ClientRankingMenuRowController {

	@FXML
	private Label rank, username, rating;

	@FXML
	private ImageView onlineStatus;

	private ColorAdjust grayScaleEffect = new ColorAdjust(), colouredEffect = new ColorAdjust();

	@FXML
	public void initialize() {
		grayScaleEffect.setSaturation(-1);
		colouredEffect.setSaturation(0);
	}

	public void setRank(int rank) {
		this.rank.setText(String.valueOf(rank));
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public void setRating(int rating) {
		this.rating.setText(String.valueOf(rating));
	}

	public void setOnline(boolean isOnline) {
		if (isOnline) {
			onlineStatus.setEffect(colouredEffect);
		} else {
			onlineStatus.setEffect(grayScaleEffect);
		}
	}

}