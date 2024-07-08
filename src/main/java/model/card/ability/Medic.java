package model.card.ability;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;

import java.util.ArrayList;

public enum Medic implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		ArrayList<Card> discardPile = Game.getCurrentGame().getCurrentDiscardPile().getCards(true, true);
		if (discardPile.isEmpty()) return;
		MatchMenuController.askCards(discardPile, Game.getCurrentGame().isMedicRandom(), index -> {
			Unit unit = (Unit) discardPile.get(index);
			Game.getCurrentGame().getCurrentDiscardPile().getCards().remove(unit);
			Game.getCurrentGame().putRevived(unit, false);
			Game.getCurrentGame().changeTurn();
		}, false, 0);
		Game.getCurrentGame().changeTurn();
	}
}
