package model.card.ability;

import model.card.Card;
import model.card.special.spell.Spell;
import model.card.unit.Melee;
import model.card.unit.Ranged;
import model.card.unit.Siege;
import model.card.unit.Unit;
import model.game.Game;

import java.util.ArrayList;

public enum Scorch implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		if (card instanceof Spell || ((Unit) card).getFaction().equals("skellige")) killRow(-1);
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
		for (Unit unit : units)
			if (unit.getPower() == maxPower) {
				try {
					unit.pull();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Game.getCurrentGame().getCurrentDiscordPile().add(unit);
			}
	}

}
