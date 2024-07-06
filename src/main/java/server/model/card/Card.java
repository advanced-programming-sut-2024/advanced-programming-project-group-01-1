package server.model.card;

import server.model.Client;
import server.model.card.ability.Ability;
import server.model.card.special.Decoy;
import server.model.card.special.spell.Buffer;
import server.model.card.special.spell.InstantSpell;
import server.model.card.special.spell.Weather;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.space.Space;

import java.io.Serializable;

public abstract class Card implements Cloneable, Serializable, Comparable<Card> {
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

	public void put(Client client, int rowNumber) throws Exception {

	}

	public void pull(Client client) {
		if (this.ability != null) this.ability.undo(client, this);
		this.updateSpace(client.getIdentity().getCurrentGame().getCurrentDiscardPile());
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

	@Override
	public int compareTo(Card card) {
		int thisPriority;
		if (this instanceof Decoy) {
			thisPriority = 1000;
		} else if (this instanceof InstantSpell) {
			thisPriority = 999;
		} else if (this instanceof Buffer) {
			thisPriority = 998;
		} else if (this instanceof Weather) {
			thisPriority = 997;
		} else {
			Unit unit = (Unit) this;
			thisPriority = unit.getBasePower();
		}
		int cardPriority;
		if (card instanceof Decoy) {
			cardPriority = 1000;
		} else if (card instanceof InstantSpell) {
			cardPriority = 999;
		} else if (card instanceof Buffer) {
			cardPriority = 998;
		} else if (card instanceof Weather) {
			cardPriority = 997;
		} else {
			Unit unit = (Unit) card;
			cardPriority = unit.getBasePower();
		}
		if (cardPriority == thisPriority){
			return this.getName().compareTo(card.getName());
		}
		return cardPriority - thisPriority;
	}
}
