package model.card.unit.melee.all;

import model.card.ability.Hero;
import model.card.ability.Spy;
import model.card.faction.*;
import model.card.unit.melee.Melee;

public class AllHeroSpyMelee extends Melee implements Hero, Spy, Monsters, Nilfgaard, NorthernRealms, Scoiatael, Skellige {

	public AllHeroSpyMelee(String name, int basePower) {
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
