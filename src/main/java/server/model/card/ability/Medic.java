package server.model.card.ability;

import server.controller.game.MatchMenuController;
import server.model.card.Card;
import server.model.card.unit.*;
import server.model.game.Game;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = (Unit) MatchMenuController.askSpace(Game.getCurrentGame().getCurrentDiscardPile(), Game.getCurrentGame().isMedicRandom(), true);
		if (unit == null || unit.isHero()) return;
		Game.getCurrentGame().getCurrentDiscardPile().getCards().remove(unit);
		Game.getCurrentGame().putRevived(unit, false);
	}
}
