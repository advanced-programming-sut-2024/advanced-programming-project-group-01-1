package server.model.leader;

import server.model.Client;
import server.model.card.Card;
import server.model.card.unit.Agile;
import server.model.game.Game;

import java.util.ArrayList;

public class AgileOptimizer extends Leader {

	private ArrayList<Agile> agiles = null;

	public AgileOptimizer() {
		super("Francesca Findabair Hope of the Aen Seidhe",
		"Move agile units to whichever row maximizes their strength (don't move unit already in optimal row",true);
	}

	private int putAll(Client client, int mask) {
		int powerSum = 0;
		try {
			for (int i = 0; i < agiles.size(); i++) {
				if ((mask & (1 << i)) == 0) agiles.get(i).put(client, 2);
				else agiles.get(i).put(client, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Agile agile : agiles)
			powerSum += agile.getPower(client);
		return powerSum;
	}

	private void pullAll(Client client) {
		for (Agile agile : agiles)
			agile.pull(client);
	}

	@Override
	public void act(Client client) {
		agiles = new ArrayList<>();
		for (Card card : client.getIdentity().getCurrentGame().getRow(2).getCards()) {
			if (card instanceof Agile) {
				agiles.add((Agile) card);
				card.pull(client);
			}
		}
		for (Card card : client.getIdentity().getCurrentGame().getRow(1).getCards()) {
			if (card instanceof Agile) {
				agiles.add((Agile) card);
				card.pull(client);
			}
		}
		int maxMask = 0, maxPowerSum = 0;
		for (int mask = 0; mask < (1 << agiles.size()); mask++) {
			int maskPowerSum = putAll(client, mask);
			if (maskPowerSum > maxPowerSum) {
				maxMask = mask;
				maxPowerSum = maskPowerSum;
			}
			pullAll(client);
		}
		putAll(client, maxMask);
		super.act(client);
	}

	@Override
	public Leader clone() {
		return new AgileOptimizer();
	}
}
