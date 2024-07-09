package server.model;

import server.model.card.Card;
import server.model.game.space.Space;
import message.SelectionHandler;
import server.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class Asker implements Runnable {

	private static final HashMap<User, ArrayList<Asker>> askers = new HashMap<>();
	private static final HashMap<User, Asker> runnings = new HashMap<>();
	private final User player;
	private final Space space;
	private final boolean onlyUnit;
	private final boolean ignoreHero;
	private ArrayList<Card> cards;
	private final boolean isRandom;
	private final SelectionHandler selectionHandler;
	private final boolean isOptional;
	private final int ptr;
	private final boolean isFast;

	public Asker(User player, Space space, boolean onlyUnit, boolean ignoreHero, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr) {
		this.player = player;
		this.space = space;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		this.isRandom = isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = isOptional;
		this.ptr = ptr;
		this.isFast = false;
		this.add();
		this.run();
	}

	public Asker(User player, Space space, boolean onlyUnit, boolean ignoreHero, boolean isRandom, SelectionHandler selectionHandler, boolean isOptional, int ptr, boolean isFast) {
		this.player = player;
		this.space = space;
		this.onlyUnit = onlyUnit;
		this.ignoreHero = ignoreHero;
		this.isRandom = isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = isOptional;
		this.ptr = ptr;
		this.isFast = isFast;
		boolean added = false;
		this.add();
		this.run();
	}

	private Asker(Asker asker, SelectionHandler selectionHandler) {
		this.player = asker.player;
		this.space = asker.space;
		this.onlyUnit = asker.onlyUnit;
		this.ignoreHero = asker.ignoreHero;
		this.isRandom = asker.isRandom;
		this.selectionHandler = selectionHandler;
		this.isOptional = asker.isOptional;
		this.ptr = asker.ptr;
		this.isFast = asker.isFast;
	}

	public static boolean isAsking(User player) {
		return runnings.get(player) != null;
	}

	public static Asker getRunning(User player) {
		return runnings.get(player);
	}

	public static boolean select(User player, int index) {
		if (getRunning(player) == null) return false;
		return getRunning(player).selectCard(index);
	}

	public Space getSpace() {
		return space;
	}

	public ArrayList<Card> getCards() {
		return cards = space.getCards(onlyUnit, ignoreHero);
	}

	public boolean isRandom() {
		return isRandom;
	}

	public boolean isOptional() {
		return isOptional;
	}

	public int getPtr() {
		return ptr;
	}

	private void add() {
		if (!askers.containsKey(player)) {
			ArrayList<Asker> playerAskers = new ArrayList<>();
			playerAskers.add(this);
			askers.put(player, playerAskers);
		} else {
			boolean added = false;
			ArrayList<Asker> playerAskers = askers.get(player);
			if (isFast) {
				for (int i = 0; i < playerAskers.size(); i++) {
					if (!playerAskers.get(i).isFast) {
						playerAskers.add(i, this);
						added = true;
						break;
					}
				}
			}
			if (!added) playerAskers.add(this);
		}
	}

	private void remove() {
		if (!askers.containsKey(player)) return;
		askers.get(player).remove(this);
	}

	private boolean selectCard(int index) {
		if (index < -1 || index >= cards.size()) return false;
		if (index == -1 && !isOptional) return false;
		selectionHandler.handle(index);
		return true;
	}

	@Override
	public void run() {
		if (getRunning(player) != null || askers.get(player).indexOf(this) != 0) {
			if (isRandom && isFast) {
				cards = space.getCards(onlyUnit, ignoreHero);
				int randomIndex = (int) (Math.random() * cards.size());
				selectionHandler.handle(randomIndex);
				this.remove();
			}
			return;
		}
		this.remove();
		cards = space.getCards(onlyUnit, ignoreHero);
		if (cards.isEmpty()) {
			if (!askers.get(player).isEmpty()) askers.get(player).get(0).run();
		} else if (isRandom) {
			int randomIndex = (int) (Math.random() * cards.size());
			selectionHandler.handle(randomIndex);
			if (!askers.get(player).isEmpty()) askers.get(player).get(0).run();
		} else {
			final SelectionHandler selectionHandler = this.selectionHandler;
			runnings.put(player, new Asker(this, index -> {
				selectionHandler.handle(index);
				runnings.remove(player);
				if (!askers.get(player).isEmpty()) askers.get(player).get(0).run();
			}));
		}
	}

}
