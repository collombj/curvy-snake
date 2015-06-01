package fr.upem.ir1.EelZen.controller;

public class Snack {

	private Position direction;
	private Movement movement;

	public Snack(Position init, Position direction) {

		this.direction = direction;
		this.movement = new Movement(init);

	}

	public Position move() {
		return this.movement.move(direction);

	}

	public void newDirection(Position p) {
		this.direction = p;
	}

}
