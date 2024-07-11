package server.model.card.special;

import server.model.Asker;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;
import server.model.game.Move;
import server.model.game.space.Row;

public class Decoy extends Special {

	public Decoy() {
		super("Decoy", null);
	}

	@Override
	public void put(int rowNumber) throws Exception {
		if (rowNumber < 0 || rowNumber > 2) {
			throw new Exception("Invalid row number");
		}
		final Row row = this.game.getRow(rowNumber);
		if (row.getCards().size() == 0) {
			throw new Exception("Row is empty");
		}
		Decoy decoy = this;
		new Asker(game.getCurrent(), row, true, true, false, index -> {
			Unit unit = (Unit) row.getCards(true, true).get(index);
			unit.pull();
			unit.updateSpace(this.game.getCurrentHand());
			decoy.updateSpace(row);
			game.getMoves().add(new Move(game.getCurrent(), rowNumber + "\n" + this +
					"\nunique code: " + this.toSuperString()));
			this.game.changeTurn();
		}, false, 0);
		this.game.changeTurn();
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
