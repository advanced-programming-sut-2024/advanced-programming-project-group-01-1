package model.card.ability;

import model.card.Card;
import model.card.unit.Unit;
import model.game.CardMover;
import model.game.Game;
import model.game.space.Row;

import java.util.ArrayList;
import java.util.Random;

public enum Spy implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Unit unit = (Unit) card;
		Row enemyRow = Game.getCurrentGame().getEnemy((Row) unit.getSpace());
		unit.getSpace().getCards().remove(unit);
		enemyRow.getCards().add(unit);
		unit.setHornCount(enemyRow.getHornCount());
		unit.setBoostCount(enemyRow.getBoostCount());
		unit.setDebuff(enemyRow.isDebuffed());
		new CardMover(Game.CURRENT_DECK, Game.CURRENT_HAND, true, 2, false, false).move();
	}

}
