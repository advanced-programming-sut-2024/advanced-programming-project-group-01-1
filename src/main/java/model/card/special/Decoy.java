package model.card.special;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.ability.Ability;
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
		Row row = Game.getCurrentGame().getRow(rowNumber);
		if (row.getCards().size() == 0) {
			throw new Exception("Row is empty");
		}
		Unit unit = (Unit) MatchMenuController.askSpace(row, true);
		unit.pull();
		Game.getCurrentGame().getCurrentHand().getCards().add(unit);
		unit.setSpace(Game.getCurrentGame().getCurrentHand());
		this.getSpace().getCards().remove(this);
		row.getCards().add(this);
		this.setSpace(row);
	}

	@Override
	public Card clone() {
		return new Decoy();
	}

}
