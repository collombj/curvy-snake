package fr.upem.ir1.EelZen.controller;

import java.util.HashMap;

import fr.upem.ir1.EelZen.controller.bonus.Bonus;

public class BonusListInGame {

	private HashMap<Circle, Bonus> bonusPosition = new HashMap<>();

	public BonusListInGame() {
	}

	/**
	 * Add an Bonus in Hashmap
	 * 
	 * @param p
	 * @param b
	 */
	public void add(Position p, Bonus b) {
		this.bonusPosition.put(new Circle(p, 5), b);
	}

	/**
	 *Return a bonus and delete it in the bonus list
	 * 
	 * @param p Position of the bonus
	 * @return the bonus at the position
	 */
	public Bonus getBonus(Position p) {
		//TODO
		Circle c = new Circle(p, 5);
		Bonus b = this.bonusPosition.get(c);
		this.bonusPosition.remove(c);
		return b;

	}
	
	public boolean isBonus(){
		//TODO
		return true;
	}
	
	

}
