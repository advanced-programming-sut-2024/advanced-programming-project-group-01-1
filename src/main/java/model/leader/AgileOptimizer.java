package model.leader;

import model.card.Card;
import model.card.unit.Agile;
import model.card.unit.Melee;
import model.card.unit.Ranged;
import model.game.Game;

import java.util.ArrayList;

public class AgileOptimizer extends Leader {

	private ArrayList<Agile> agiles = null;

	public AgileOptimizer() {
		super("Francesca Findabair Hope of the Aen Seidhe",
		"Move agile units to whichever row maximizes their strength (don't move unit already in optimal row",true);
	}

	private int putAll(int mask) {
		try {
			for (int i = 0; i < agiles.size(); i++) {
				if ((mask & (1 << i)) == 0) agiles.get(i).put(2);
				else agiles.get(i).put(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Game.getCurrentGame().getCurrentPower();
	}

	private void pullAll() {
		for (Agile agile : agiles)
			agile.pull();
	}

	@Override
	public void act() {
		agiles = new ArrayList<>();
		for (Card card : Game.getCurrentGame().getRow(2).getCards()) {
			if (card instanceof Agile && !(card instanceof Melee)) agiles.add((Agile) card);
		}
		for (Card card : Game.getCurrentGame().getRow(1).getCards()) {
			if (card instanceof Agile && !(card instanceof Ranged)) agiles.add((Agile) card);
		}
		pullAll();
		int maxMask = 0, maxPowerSum = 0;
		for (int mask = 0; mask < (1 << agiles.size()); mask++) {
			int maskPowerSum = putAll(mask);
			if (maskPowerSum > maxPowerSum) {
				maxMask = mask;
				maxPowerSum = maskPowerSum;
			}
			pullAll();
		}
		putAll(maxMask);
		super.act();
	}

	@Override
	public Leader clone() {
		return new AgileOptimizer();
	}
}
