package model.game.space;

import model.card.Card;

public class WeatherSystem extends Space {

	boolean[] isRowDebuffed;

	public WeatherSystem() {
		isRowDebuffed = new boolean[6];
	}

	public void reset() {
		for(Card card : cards) {
			try {
				card.pull();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < 6; i++) isRowDebuffed[i] = false;
	}

}
