package model.card.ability;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.unit.*;
import model.game.Game;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = MatchMenuController.askSpace(Game.getCurrentGame().getCurrentDiscardPile(), Game.getCurrentGame().isMedicRandom());
		if (unit == null || unit.isHero()) return;
		Game.getCurrentGame().getCurrentDiscardPile().getCards().remove(unit);
		Game.getCurrentGame().putRevived(unit, false);
	}
}
