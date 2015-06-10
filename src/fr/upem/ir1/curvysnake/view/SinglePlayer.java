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

import fr.umlv.zen5.*;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.MoveTo;
import fr.upem.ir1.curvysnake.controller.Snake;
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.LinkedList;
import java.util.List;

/**
 * @author collombj
 * @project EelZen
 * @package fr.upem.ir1.curvysnake.controller
 * @date 05/06/2015
 */
public class SinglePlayer {
	private static Rectangle gameSize = new Rectangle(0, 0, 500, 500);
	private static BonusListInGame bonusListInGame = new BonusListInGame();

	public static void run() {
		// Set environnement
		Snake.setGameSize(gameSize);
		// Set Bonus List
		Snake.setBonusListInGame(bonusListInGame);

		
		Player player1= new Player(new Snake(new Point((int)gameSize.getCenterX(),(int)gameSize.getCenterY()),0),Color.WHITE);
		
		LinkedList<RectangularShape> lstIn = new LinkedList<>();
		
		/*
		 * Interface Graphique initialisation
		 */

		Application.run(Color.WHITE, context -> {
			Draw.context = context;
			// get the size of the screen
				ScreenInfo screenInfo = context.getScreenInfo();
				float width = screenInfo.getWidth();
				float height = screenInfo.getHeight();
				System.out.println("size of the screen (" + width + " x "
						+ height + ")");
				
				gameSize.height=(int) height;
				gameSize.width=(int) width;
				context.renderFrame(graphics -> {
					graphics.setColor(Color.WHITE);
					graphics.fill(new Rectangle2D.Float(0, 0, width, height));
					
				});
			boolean flag = false;
			int time=0;
				while (true) {
					Event event = context.pollOrWaitEvent(1);
					time++;
					if (event != null) { // no event
						Action action = event.getAction();
						if(action==Action.KEY_PRESSED){
							
						
							KeyboardKey key=event.getKey();
							System.out.println(key);

							if(key==KeyboardKey.RIGHT){
								try {
									player1.getPlayer().changeDirection(MoveTo.RIGHT, flag);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if(key==KeyboardKey.LEFT){
								try {
									player1.getPlayer().changeDirection(MoveTo.LEFT, flag);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							if(key==KeyboardKey.Q){
								context.exit(0);
			                    return;
							}

							flag = true;
							
						} else if(action==Action.KEY_RELEASED) {
							flag = false;
						}
					}
				
				try {
					List<RectangularShape> lstOut=null;

					if(time>=25){
						lstOut=player1.getPlayer().move(lstIn);
						time=0;
					}

					if(lstIn != null) {
						for(RectangularShape l : lstIn) {
							Draw.draw(l);
						}
						lstIn.clear();
					}

					if(lstOut != null) {
						for(RectangularShape l : lstOut) {
							Draw.undraw(l);
						}
					}
					
					Draw.draw(player1.getPlayer().getQueue());
					
					
					Draw.drawBonus(bonusListInGame.random());
					
					Snake.decrementAll();
				

					
				} catch (CollisionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					context.exit(0);
				} catch(Exception e){
					e.printStackTrace();
				}

			}
		});

		/*
		 * Interface Graphique fin
		 */

	}
}
