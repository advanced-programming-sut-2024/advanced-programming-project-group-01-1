package model.card.ability;

import model.card.Card;
import model.card.special.spell.Buffer;
import model.card.special.spell.Spell;
import model.card.unit.Unit;
import model.game.space.Row;

import java.util.ArrayList;

public enum Mardroeme implements Ability {
	INSTANCE;

	@Override
	public void act(Card card) {
		Row row = (Row) card.getSpace();
		if (card instanceof Spell) row.setBuffer((Buffer) card);
		row.setMardroeme(true);
		ArrayList<Unit> berserkerUnits = new ArrayList<>();
		for (Card cardInRow : row.getCards()) {
			Ability cardAbility = cardInRow.getAbility();
			if (cardAbility instanceof Berserker) berserkerUnits.add((Unit) cardInRow);
		}
		for (Unit berserkerUnit : berserkerUnits) Berserker.INSTANCE.act(berserkerUnit);
	}

	@Override
	public void undo(Card card) {
		Row row = (Row) card.getSpace();
		if (card instanceof Spell) row.setBuffer(null);
		row.setMardroeme(false);
	}

	@Override
	public String getDescription(Card card) {
		return "Transforms all Berserkers into Vildkaarl.";
	}

}
