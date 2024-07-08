package model.card.special;

import controller.game.MatchMenuController;
import model.card.Card;
import model.card.ability.Ability;
import model.card.unit.Unit;
import model.game.Game;
import model.game.space.Row;
import view.game.SelectionHandler;

import java.util.ArrayList;

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
		ArrayList<Card> rowCards = row.getCards(true, true);
		Decoy decoy = this;
		MatchMenuController.askCards(rowCards, false, index -> {
			Unit unit = (Unit) rowCards.get(index);
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

}
