package fr.upem.ir1.curvysnake.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Draw extends JPanel {
	private int posX = -50;
	private int posY = -50;
	private int w;
	private int h;


	public void paintComponent(Graphics g) {
		System.out.println("print");
		g.setColor(Color.red);
		g.fillOval(posX, posY,w,h);
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

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}


	
}