package fr.upem.ir1.EelZen.controller;

import java.util.LinkedList;

public class Movement {

	private LinkedList<Body> move;
	private final int defaultRadius = 5;
	private boolean isIncreased;

	public Movement(Position init) {

		this.move = new LinkedList<Body>();
		this.isIncreased = false;

		move.add(new Body(init, this.defaultRadius));
	}

	public boolean isCrossed() {
		// TODO
		return false;
	}

	public Body move(Position p) {

		this.move.add(this.move.getLast().Duplicate().addMove(p));
		this.isIncreased = !this.isIncreased;
		if(!this.isIncreased){
			return this.move.pop();
		}
		
		return null;

	}
}
