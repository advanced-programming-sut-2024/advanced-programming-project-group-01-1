package model.card.unit.melee.nilfgaard;

import model.card.ability.Hero;
import model.card.ability.Medic;
import model.card.faction.Nilfgaard;
import model.card.unit.melee.Melee;

public class NilfgaardHeroMedicMelee extends Melee implements Nilfgaard, Medic, Hero {

	public NilfgaardHeroMedicMelee(String name, int basePower) {
		//TODO: implement the constructor
		super(name, basePower);
	}

	@Override
	public void put(int row) throws Exception {
		//TODO: implement the method
	}

	@Override
	public void pull() throws Exception {
		//TODO: implement the method
	}
}
