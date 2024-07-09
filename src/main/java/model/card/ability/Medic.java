package model.card.ability;

import controller.game.MatchMenuController;
import model.Asker;
import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Space;

import java.util.ArrayList;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		final Space discardPile = Game.getCurrentGame().getCurrentDiscardPile();
		if (discardPile.getCards(true, true).isEmpty()) return;
		new Asker(discardPile, true, true, Game.getCurrentGame().isMedicRandom(), index -> {
			Unit unit = (Unit) discardPile.getCards(true, true).get(index);
			Game.getCurrentGame().putRevived(unit, false);
			Game.getCurrentGame().changeTurn();
		}, false, 0);
		Game.getCurrentGame().changeTurn();
	}

	@Override
	public String getDescription(Card card) {
		return "Revives a unit.";
	}

}
