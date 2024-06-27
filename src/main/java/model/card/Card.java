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

	public String getDisplayName() {
		if (Character.isDigit(name.charAt(name.length() - 1))) return name.substring(0, name.length() - 1);
		return name;
	}

	public Ability getAbility() {
		return ability;
	}

	public Space getSpace() {
		return space;
	}

	@Override
	public abstract Card clone();

	public void put(int rowNumber) throws Exception {

	}

	public void pull() throws Exception {

	}


}
