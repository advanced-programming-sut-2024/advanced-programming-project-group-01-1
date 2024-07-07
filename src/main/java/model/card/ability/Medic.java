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
		MatchMenuController.askCards(discardPile, Game.getCurrentGame().isMedicRandom(), index -> {
			Unit unit = (Unit) discardPile.get(index);
			Game.getCurrentGame().getCurrentDiscardPile().getCards().remove(unit);
			Game.getCurrentGame().putRevived(unit, false);
		});
	}
}
