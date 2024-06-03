package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Row;

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
	}

	@Override
	public void undo(Card card) {

	}
}
