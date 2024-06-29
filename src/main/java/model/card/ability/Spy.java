package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Row;

import java.util.ArrayList;
import java.util.Random;

public enum Spy implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = (Unit) card;
		Row enemyRow = Game.getCurrentGame().getEnemy(unit.getSpace());
		unit.getSpace().getCards().remove(unit);
		enemyRow.getCards().add(unit);
		unit.setHornCount(enemyRow.getHornCount());
		unit.setBoostCount(enemyRow.getBoostCount());
		unit.setDebuff(enemyRow.isDebuffed());
		pullFromDeck();
		pullFromDeck();
	}

	private void pullFromDeck() {
		ArrayList<Card> deckCards = Game.getCurrentGame().getCurrentDeck().getCards();
		if (deckCards.isEmpty()) return;
		Card unit = deckCards.get((new Random()).nextInt(deckCards.size()));
		deckCards.remove(unit);
		Game.getCurrentGame().getCurrentHand().getCards().add(unit);
	}
}
