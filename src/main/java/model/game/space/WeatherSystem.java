package model.game.space;

import model.card.Card;

public class WeatherSystem extends Space {

	boolean[] isRowDebuffed;

	public WeatherSystem() {
		isRowDebuffed = new boolean[6];
	}

}
