package server.model.card.special;

import server.controller.game.MatchMenuController;
import server.model.card.Card;
import server.model.card.unit.Unit;
import server.model.game.Game;
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
		Row row = Game.getCurrentGame().getRow(rowNumber);
		if (row.getCards().size() == 0) {
			throw new Exception("Row is empty");
		}
		Unit unit = (Unit) MatchMenuController.askSpace(row, true);
		unit.pull();
		unit.updateSpace(Game.getCurrentGame().getCurrentHand());
		this.updateSpace(row);
	}

	@Override
	public Card clone() {
		return new Decoy();
	}

}