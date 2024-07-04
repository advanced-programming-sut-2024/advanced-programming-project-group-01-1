package server.model.card;

import server.model.card.ability.Ability;
import server.model.game.Game;
import server.model.game.space.Space;

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

	public void setSpace(Space space) {
		this.space = space;
	}

	public void updateSpace(Space space) {
		this.space.getCards().remove(this);
		this.space = space;
		this.space.getCards().add(this);
	}

	@Override
	public abstract Card clone();

	public void put(int rowNumber) throws Exception {

	}

	public void pull() {
		if (this.ability != null) this.ability.undo(this);
		this.updateSpace(Game.getCurrentGame().getCurrentDiscardPile());
	}

	@Override
	public String toString() {
		String card = this.name + "\nType: " + this.getClass().getSimpleName();
		card += "\nAbility: " + (ability == null ? "None" : this.ability.getClass().getSimpleName());
		return card;
	}

	public String toSuperString() {
		return super.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
}
