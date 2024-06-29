package model.leader;

import model.game.Game;

import java.io.Serializable;
import java.lang.reflect.Method;

public class PassiveLeader extends Leader implements Serializable {

	String setterName;

	public PassiveLeader(String name, String setterName) {
		super(name, false);
		this.setterName = setterName;
	}

	@Override
	public void act() {
		try {
			Method method = Game.class.getDeclaredMethod(setterName, Boolean.TYPE);
			method.invoke(Game.getCurrentGame(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Leader clone() {
		return new PassiveLeader(name, setterName);
	}
}
