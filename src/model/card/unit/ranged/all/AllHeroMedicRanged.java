package model.card.unit.ranged.all;

import model.card.ability.Hero;
import model.card.ability.Medic;
import model.card.faction.*;
import model.card.unit.ranged.Ranged;

public class AllHeroMedicRanged extends Ranged implements Hero, Medic, Monsters, Nilfgaard, Scoiatael, NorthernRealms, Skellige {
	public AllHeroMedicRanged(String name, int basePower) {
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
