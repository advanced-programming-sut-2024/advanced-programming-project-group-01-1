package server.model.leader;

import server.model.Client;
import server.model.game.CardMover;

public class MoverLeader extends Leader {

	private final CardMover[] cardMovers;

	public MoverLeader(String name, String description, boolean isManual, CardMover[] cardMovers) {
		super(name, description, isManual);
		this.cardMovers = cardMovers;
	}

	@Override
	public void act(Client client) {
		for (CardMover cardMover : cardMovers)
			cardMover.move(client);
		super.act(client);
	}

	@Override
	public Leader clone() {
		return new MoverLeader(name, getDescription(), isManual, cardMovers);
	}

}
