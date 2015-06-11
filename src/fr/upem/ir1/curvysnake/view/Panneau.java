package fr.upem.ir1.curvysnake.view;

import java.awt.Color;

import javax.swing.JPanel;

import com.sun.prism.Graphics;

public class Panneau extends JPanel {
	private  int posX;
	private  int posY;
	private  int weight;
	private  int height;
	private  Color color;
	
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void paintComponent(Graphics body) {
		
		System.out.println("je suis passer par paint");
		((java.awt.Graphics) body).setColor(Color.BLUE);
		((java.awt.Graphics) body).fillOval(posX,posY,height,weight);
		
	}

}
