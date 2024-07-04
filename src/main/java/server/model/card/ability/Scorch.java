package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;
import server.model.card.special.spell.InstantSpell;
import server.model.card.special.spell.Spell;
import server.model.card.unit.Melee;
import server.model.card.unit.Ranged;
import server.model.card.unit.Siege;
import server.model.card.unit.Unit;
import server.model.game.Game;

import java.lang.reflect.Field;
import java.util.ArrayList;

public enum Scorch implements Ability {
	INSTANCE;

	@Override
	public void act(Client client, Card card) {
		if (card instanceof InstantSpell) killRow(client, ((InstantSpell) card).getRowNumber());
		else if (card.getName().equals("Clan Dimun Pirate")) killRow(client, -1);
		else if (card instanceof Melee) killRow(client, 2);
		else if (card instanceof Ranged) killRow(client, 1);
		else if (card instanceof Siege) killRow(client, 0);
	}

	public void killRow(Client client, int row) { // row = -1 for killing the maximum of table
		ArrayList<Unit> units = new ArrayList<>();
		if (row != -1)
			for (Card cardInRow : client.getIdentity().getCurrentGame().getRow(5 - row).getCards()) {
				if (cardInRow instanceof Unit)
					units.add((Unit) cardInRow);
			}
		else {
			for (int i = 0; i < 6; i++)
				for (Card cardInRow : client.getIdentity().getCurrentGame().getRow(i).getCards()) if (cardInRow instanceof Unit)
					units.add((Unit) cardInRow);
		}
		int maxPower = -1, sumOfPower = 0;
		for (Unit unit : units) {
			if (!unit.isHero()) {
				maxPower = Math.max(maxPower, unit.getPower(client));
				sumOfPower += unit.getPower(client);
			}
		}
		if (sumOfPower < 10 && row != -1) return;
		ArrayList<Unit> toBeKilled = new ArrayList<>();
		for (Unit unit : units) if (unit.getPower(client) == maxPower)
			toBeKilled.add(unit);
		for (Unit unit : toBeKilled) unit.pull(client);
	}

}