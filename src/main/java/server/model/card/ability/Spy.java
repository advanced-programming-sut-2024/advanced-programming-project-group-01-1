package server.model.card.ability;

import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.CardMover;
import server.model.game.Game;
import server.model.game.space.Row;

public enum Spy implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = (Unit) card;
		Row enemyRow = card.getGame().getEnemy((Row) unit.getSpace());
		unit.updateSpace(enemyRow);
		unit.setHornCount(enemyRow.getHornCount());
		unit.setBoostCount(enemyRow.getBoostCount());
		unit.setDebuff(enemyRow.isDebuffed());
		new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 2, false, false).move();
	}

	@Override
	public String getDescription(Card card) {
		return "Draws 2 cards from the your deck, but is put in enemy row.";
	}

}
