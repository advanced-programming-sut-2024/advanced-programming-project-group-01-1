package main;

import model.leader.Leader;
import model.leader.SpellLeader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LeaderCreator {

	private static void writeLeader(String faction, Leader leader) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/leaders/" + leader.getName() + ".json"));
		oos.writeObject(faction);
		oos.writeObject(leader);
		oos.close();
	}

	private static void createSpellLeader(String faction, String name, boolean useDeck, int spaceNumber, String... spellNumbers) {
		try {
			SpellLeader spellLeader = new SpellLeader(name, useDeck, spaceNumber, spellNumbers);
			writeLeader(faction, spellLeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

}
