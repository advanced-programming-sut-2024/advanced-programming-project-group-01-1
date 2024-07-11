package server.model.card.ability;

import server.model.Asker;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.Move;
import server.model.game.space.Space;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		final Space discardPile = card.getGame().getCurrentDiscardPile();
		if (discardPile.getCards(true, true).isEmpty()) return;
		new Asker(card.getGame().getCurrent(), discardPile, true, true, card.getGame().isMedicRandom(), index -> {
			Unit unit = (Unit) discardPile.getCards(true, true).get(index);
			card.getGame().putRevived(unit, false);
			card.getGame().changeTurn();
		}, false, 0);
		card.getGame().changeTurn();
	}

	@Override
	public String getDescription(Card card) {
		return "Revives a unit.";
	}

}
