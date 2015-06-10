package fr.upem.ir1.curvysnake.view;

import java.awt.Color;


import java.awt.Point;

import fr.upem.ir1.curvysnake.controller.Snake;

public class Player {
	
	private final Snake player;
	private final Color color;

	private int score;
	
	public Player(Snake player, Color color) {
		super();
		this.player = player;
		this.color = color;
		this.score=0;
	}
	
	private void increaseScore(){
		this.score++;
	}

	public Snake getPlayer() {
		return player;
	}

	public Color getColor() {
		return color;
	}


	public int getScore() {
		return score;
	}
	
	
	
}
