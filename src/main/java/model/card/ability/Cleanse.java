package model.card.ability;

import model.card.Card;
import model.game.Game;

public enum Cleanse implements Ability {

	INSTANCE;

	@Override
	public void act(Card card) {
		Game.getCurrentGame().getWeatherSystem().reset();
	}

}
