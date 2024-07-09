package model.card.special;

import model.Asker;
import model.card.Card;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Row;

public class Decoy extends Special {

	public Decoy() {
		super("Decoy", null);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber < 0 || rowNumber > 2) {
			throw new Exception("Invalid row number");
		}
		final Row row = Game.getCurrentGame().getRow(rowNumber);
		if (row.getCards().size() == 0) {
			throw new Exception("Row is empty");
		}
		Decoy decoy = this;
		new Asker(row, true, true, false, index -> {
			Unit unit = (Unit) row.getCards(true, true).get(index);
			unit.pull();
			unit.updateSpace(Game.getCurrentGame().getCurrentHand());
			decoy.updateSpace(row);
			Game.getCurrentGame().changeTurn();
		}, false, 0);
		Game.getCurrentGame().changeTurn();
	}

	@Override
	public Card clone() {
		return new Decoy();
	}

	@Override
	public String getDescription() {
		return "Return a card on the battlefield to your hand.";
	}

}
