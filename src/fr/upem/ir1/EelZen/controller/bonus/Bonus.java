package fr.upem.ir1.EelZen.controller.bonus;

import javax.naming.TimeLimitExceededException;

import fr.upem.ir1.EelZen.controller.Circle;
import fr.upem.ir1.EelZen.controller.Snake;

public abstract class Bonus {

	private int time = 5;

	public Bonus() {
		this(5);
	}

	public Bonus(int t) {
		this.time = t;
	}

	public abstract void action(Circle b);

	public abstract void action(Snake s);

	public void decrement() throws TimeLimitExceededException {
		this.time--;

		if (this.time <= 0) {
			throw new TimeLimitExceededException();
		}

	}

	public int getTime() {
		return time;
	}

}