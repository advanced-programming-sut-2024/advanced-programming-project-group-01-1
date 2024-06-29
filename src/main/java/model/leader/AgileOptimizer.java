package model.leader;

import model.card.Card;
import model.card.unit.Agile;
import model.game.Game;

import java.util.ArrayList;

public class AgileOptimizer extends Leader {

	private ArrayList<Agile> agiles = null;

	public AgileOptimizer() {
		super("Francesca Findabair Hope of the Aen Seidhe", true);
	}

	private int putAll(int mask) {
		int powerSum = 0;
		try {
			for (int i = 0; i < agiles.size(); i++) {
				if ((mask & (1 << i)) == 0) agiles.get(i).put(2);
				else agiles.get(i).put(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Agile agile : agiles)
			powerSum += agile.getPower();
		return powerSum;
	}

	private void pullAll() {
		try {
			for (Agile agile : agiles)
				agile.pull();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void act() {
		try {
			agiles = new ArrayList<>();
			for (Card card : Game.getCurrentGame().getRow(2).getCards()) {
				if (card instanceof Agile) {
					agiles.add((Agile) card);
					card.pull();
				}
			}
			for (Card card : Game.getCurrentGame().getRow(1).getCards()) {
				if (card instanceof Agile) {
					agiles.add((Agile) card);
					card.pull();
				}
			}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		disable();
	}

	@Override
	public Leader clone() {
		return new AgileOptimizer();
	}
}
