/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 COLLOMB-GRISET
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package fr.upem.ir1.curvysnake.view;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.MoveTo;
import fr.upem.ir1.curvysnake.controller.Snake;
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.awt.Dimension; 
import javax.swing.JFrame;

/**
 * @author collombj
 * @project EelZen
 * @package fr.upem.ir1.curvysnake.controller
 * @date 05/06/2015
 */
public class SinglePlayer extends JFrame {

	private Draw pan = new Draw();
	/**
	 * Create a gameSize
	 */
	private static Rectangle gameSize = new Rectangle(0, 0, 500, 500);
	/**
	 * Create a bonus list fo the game
	 */
	private static BonusListInGame bonusListInGame = new BonusListInGame();


	public SinglePlayer() {
		this.setTitle("CurviSnake");
		this.setSize((int) gameSize.getWidth(), (int) gameSize.getWidth());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(pan);
		this.setVisible(true);
		go();

	}


	public void go() {
		// Set environnement
		Snake.setGameSize(gameSize);
		// Set Bonus List
		Snake.setBonusListInGame(bonusListInGame);

		Player player1 = new Player(new Snake(new Point(
				(int) gameSize.getCenterX(), (int) gameSize.getCenterY()), 0),
				Color.GREEN);

		/*
		 * Interface Graphique initialisation
		 */

		boolean flag = false;
		int time = 0;
		List<RectangularShape> add = new ArrayList<>();
		List<RectangularShape> erase = new ArrayList<>();
		

		while (true) {
	
			
	
			
				try {
					if (player1.isAlive())
						player1.getPlayer().move(add, erase);
				} catch (CollisionException e) {
					player1.kill();
					e.printStackTrace();

					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

					return;
				} catch (Exception e) {
					e.printStackTrace();

					return;
				}

				time = 0;
			

			if(add.size()==0){
				System.out.println("zero");
			}
			System.out.println("avant le for");
			System.out.println(add.size());
			for (RectangularShape rectangularShape : add) {
				
				pan.setPosX((int) rectangularShape.getX());
				pan.setPosY((int) rectangularShape.getY());
				pan.setW((int) rectangularShape.getWidth());
				pan.setH((int) rectangularShape.getHeight());;
				pan.repaint();
			}
			System.out.println("après le for");
		
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			

			add.clear();
            erase.clear();
            
            //erase.forEach(Draw::draw);

			//erase.forEach(Draw::undraw);
			erase.clear();


			//Draw.drawBonus(bonusListInGame.random());

			Snake.decrementAll();
		

		}
	}
}
