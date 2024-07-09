package server.model.card.ability;

import server.model.card.Card;
import server.model.card.special.spell.Buffer;
import server.model.card.special.spell.Spell;
import server.model.card.unit.Unit;
import server.model.game.space.Row;

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
		row.setMardroeme(false);
	}

	@Override
	public String getDescription(Card card) {
		return "Transforms all Berserkers into Vildkaarl.";
	}

}
