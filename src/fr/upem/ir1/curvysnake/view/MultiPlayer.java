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

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.zen5.Application;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.MoveTo;
import fr.upem.ir1.curvysnake.controller.Snake;
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;

/**
 * @author collombj
 * @project EelZen
 * @package fr.upem.ir1.curvysnake.controller
 * @date 05/06/2015
 */
public class MultiPlayer {
	private static Rectangle gameSize = new Rectangle(0, 0, 500, 500);
	private static BonusListInGame bonusListInGame = new BonusListInGame();

	public static void run() {
		// Set environnement
		Snake.setGameSize(gameSize);
		// Set Bonus List
		Snake.setBonusListInGame(bonusListInGame);

		Player player1 = new Player(new Snake(new Point(
				(int) gameSize.getCenterX(), (int) gameSize.getCenterY()), 0),
				Color.GREEN);
		Player player2 = new Player(new Snake(new Point(
				(int) gameSize.getCenterX(), (int) gameSize.getCenterY() + 50),
				0), Color.RED);

		List<RectangularShape> add = new ArrayList<>();
        List<RectangularShape> erase = new ArrayList<>();
        List<RectangularShape> add2 = new ArrayList<>();
        List<RectangularShape> erase2 = new ArrayList<>();


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

				gameSize.height = (int) height;
				gameSize.width = (int) width;
				context.renderFrame(graphics -> {
					graphics.setColor(Color.WHITE);
					graphics.fill(new Rectangle2D.Float(0, 0, width, height));

				});
				boolean flag = false;
				int time = 0;
				while (true) {
					Event event = context.pollOrWaitEvent(20);
					time++;
					if (event != null) { // no event
					Action action = event.getAction();
					if (action == Action.KEY_PRESSED) {

						KeyboardKey key = event.getKey();
						

						if (key == KeyboardKey.RIGHT) {
							try {
								player1.getPlayer().changeDirection(
										MoveTo.RIGHT, flag);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (key == KeyboardKey.LEFT) {
							try {
								player1.getPlayer().changeDirection(
										MoveTo.LEFT, flag);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (key == KeyboardKey.D) {
							try {
								player2.getPlayer().changeDirection(
										MoveTo.RIGHT, flag);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (key == KeyboardKey.Q) {
							try {
								player2.getPlayer().changeDirection(
										MoveTo.LEFT, flag);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (key == KeyboardKey.P) {
							context.exit(0);
							return;
						}

						flag = true;

					} else if (action == Action.KEY_RELEASED) {
						flag = false;
					}
				}

				try {
					List<RectangularShape> lstOut = null;
					List<RectangularShape> lstOut2 = null;
					if (time >= 5) {
						player1.getPlayer().move(add,erase);
						player2.getPlayer().move(add2,erase2);
						time = 0;
					}
					if (add != null) {
						for (RectangularShape l : add) {
							Draw.draw(l, player1.getColor());
						}
					}
					add.clear();

					if (erase != null) {
						for (RectangularShape l : erase) {
							Draw.undraw(l);
						}
					}
					Draw.draw(player1.getPlayer().getQueue(),
							player1.getColor());

					if (add2 != null) {
						for (RectangularShape l2 : add2) {
							
							Draw.draw(l2, player2.getColor());
						}
						add2.clear();
					}

					if (erase2 != null) {
						for (RectangularShape l2 : erase2) {
							Draw.undraw(l2);
						}
					}
					Draw.draw(player2.getPlayer().getQueue(),
							player2.getColor());
					

					Draw.drawBonus(bonusListInGame.random());

					Snake.decrementAll();

				/*} catch (CollisionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					context.exit(0);*/
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		/*
		 * Interface Graphique fin
		 */

	}

}
