package server.model.card.ability;

import server.model.card.Card;
import server.model.card.special.spell.InstantSpell;
import server.model.card.unit.Melee;
import server.model.card.unit.Ranged;
import server.model.card.unit.Siege;
import server.model.card.unit.Unit;
import server.model.game.Game;

import java.util.ArrayList;

public enum Scorch implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		if (card instanceof InstantSpell) killRow(((InstantSpell) card).getRowNumber());
		else if (card.getName().equals("Clan Dimun Pirate")) killRow(-1);
		else if (card instanceof Melee) killRow(2);
		else if (card instanceof Ranged) killRow(1);
		else if (card instanceof Siege) killRow(0);
	}

	public void killRow(int row) { // row = -1 for killing the maximum of table
		ArrayList<Unit> units = new ArrayList<>();
		if (row != -1)
			for (Card cardInRow : Game.getCurrentGame().getRow(5 - row).getCards()) units.add((Unit) cardInRow);
		else {
			for (int i = 0; i < 6; i++)
				for (Card cardInRow : Game.getCurrentGame().getRow(i).getCards()) units.add((Unit) cardInRow);
		}
		int maxPower = -1, sumOfPower = 0;
		for (Unit unit : units) {
			if (!unit.isHero()) {
				maxPower = Math.max(maxPower, unit.getPower());
				sumOfPower += unit.getPower();
			}
		}
		if (sumOfPower < 10 && row != -1) return;
		ArrayList<Unit> toBeKilled = new ArrayList<>();
		for (Unit unit : units) if (unit.getPower() == maxPower)
			toBeKilled.add(unit);
		for (Unit unit : toBeKilled) unit.pull();
	}

}
