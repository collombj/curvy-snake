package fr.upem.ir1.EelZen.controller;

public class Body extends Position {

	private int radius;

	public Body(int x, int y, int radius) {
		super(x, y);
		this.radius = radius;
	}

	public Body(Position p, int radius) {
		this(p.getX(), p.getY(), radius);
	}
	public Body(Body b){
		this(b.getX(), b.getY(), b.getRadius());

	}
	
	public Body Duplicate(){
		
		return new Body(this);		
	}
	
	public Body addMove(Position p){
		
		this.setX(this.getX()+p.getX());
		this.setY(this.getX()+p.getX());
		return this;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public boolean isInside(Position p) {
		// TODO

		return true;
	}

}
