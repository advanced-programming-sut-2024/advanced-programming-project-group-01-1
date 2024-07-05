package server.model.card.unit;

import server.model.Client;
import server.model.card.ability.Ability;

public class Ranged extends Agile {

	public Ranged(String name, Ability ability, int basePower, boolean isHero) {
		super(name, ability, basePower, isHero);
	}

	@Override
	public void put(Client client, int rowNumber) throws Exception {
		if (rowNumber != 1 && rowNumber != 4) throw new Exception("Invalid row number");
		super.put(client, rowNumber);
	}

}