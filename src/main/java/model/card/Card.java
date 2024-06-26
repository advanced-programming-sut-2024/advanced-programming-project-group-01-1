package model.card;

import model.card.ability.Ability;
import model.game.space.Space;

public abstract class Card implements Cloneable {
	protected final String name;
	protected final Ability ability;
	protected Space space = null;

	public Card(String name, Ability ability) {
		this.name = name;
		this.ability = ability;
	}

	public String getName() {
		return name;
	}

	public Ability getAbility() {
		return ability;
	}

	@Override
	public abstract Card clone();

	public void put(int rowNumber) throws Exception {

	}

	public void pull() throws Exception {

	}

	public Space getSpace() {
		return space;
	}

}
