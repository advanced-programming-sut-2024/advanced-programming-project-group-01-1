package server.model.leader;

import server.model.game.Game;

import java.io.Serializable;
import java.lang.reflect.Method;

public class PassiveLeader extends Leader implements Serializable {

	String setterName;

	public PassiveLeader(String name, String description, String setterName) {
		super(name, description, false);
		this.setterName = setterName;
	}

	@Override
	public void act() {
		try {
			Method method = Game.class.getDeclaredMethod(setterName);
			method.invoke(Game.getCurrentGame());
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.act();
	}

	@Override
	public Leader clone() {
		return new PassiveLeader(name, getDescription(), setterName);
	}
}