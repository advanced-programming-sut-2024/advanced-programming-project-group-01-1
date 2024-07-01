package model.card;

import model.card.ability.Ability;
import model.game.space.Space;

import java.io.Serializable;

public abstract class Card implements Cloneable, Serializable {
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
		String displayName = name;
		if (Character.isDigit(name.charAt(name.length() - 1))) displayName = name.substring(0, name.length() - 1);
		return displayName.replace(';', ':');
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

	public void pull() {

	}

	@Override
	public String toString() {
		String card = this.name + "\nType: " + this.getClass().getSimpleName();
		card += "\nAbility: " + (ability == null ? "None" : this.ability.getClass().getSimpleName());
		return card;
	}
}
