package server.model.card.ability;

import server.model.Client;
import server.model.card.Card;

public interface Ability {
	default void act(Client client, Card card) {}
	default void undo(Client client, Card card) {}
	
}
