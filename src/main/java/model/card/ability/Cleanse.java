package model.card.ability;

import model.card.Card;
import model.game.Game;

public enum Cleanse implements Ability {

	INSTANCE;

	@Override
	public void act(Card card) {
		try {
			Game.getCurrentGame().getCurrentWeatherSystem().clear(Game.getCurrentGame().getCurrentDiscardPile());
			Game.getCurrentGame().getOpponentWeatherSystem().clear(Game.getCurrentGame().getOpponentDiscardPile());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
