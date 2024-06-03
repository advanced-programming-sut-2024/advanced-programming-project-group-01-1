package model.card.ability;

import model.card.Card;
import model.card.special.spell.Buffer.Buffer;
import model.card.special.spell.Spell;
import model.card.unit.Unit;
import model.game.space.Row;
import model.game.space.Space;

public enum Mardroeme implements Ability {
	INSTANCE;

	@Override
	public void act(Card card, Space space) throws Exception {
		Row row = (Row) space;
		if (card instanceof Spell) row.setBuffer((Buffer) card);
		row.setMardroeme(true);
		for(Card cardInRow : row.getCards()){
			Ability cardAbility = cardInRow.getAbility();
			if(cardAbility instanceof Berserker) ((Berserker) cardAbility).act(cardInRow, space);
		}
	}

	@Override
	public void undo(Card card, Space space) {

	}
}
