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

/**
 * @author collombj
 * @project EelZen
 * @package fr.upem.ir1.curvysnake.controller
 * @date 05/06/2015
 */
public class SinglePlayer {
	/**
	 * Create a gameSize
	 */
    private static Rectangle gameSize = new Rectangle(0, 0, 500, 500);
    /**
     * Create a bonus list fo the game
     */
    private static BonusListInGame bonusListInGame = new BonusListInGame();

    /**
     * Play the game in multiplayer mode
     */
    public static void run() {
        // Set environnement
        Snake.setGameSize(gameSize);
        // Set Bonus List
        Snake.setBonusListInGame(bonusListInGame);


        Player player1 = new Player(new Snake(new Point((int) gameSize.getCenterX(), (int) gameSize.getCenterY()), 0)
                                           , Color.GREEN);

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

            List<RectangularShape> add = new ArrayList<>();
            List<RectangularShape> erase = new ArrayList<>();

            while(true) {
                Event event = context.pollOrWaitEvent(1);
                time++;
                if(event != null) { // no event
                    Action action = event.getAction();
                    if(action == Action.KEY_PRESSED) {

                        KeyboardKey key = event.getKey();

                        try {
                            // Player
                            if(key == KeyboardKey.RIGHT) {
                                player1.getPlayer().changeDirection(MoveTo.RIGHT, flag);
                            }
                            if(key == KeyboardKey.LEFT) {
                                player1.getPlayer().changeDirection(MoveTo.LEFT, flag);
                            }
                        } catch(IllegalAccessException e) {
                            e.printStackTrace();
                            context.exit(-1);
                            return;
                        }
                        flag = true;
                    } else if(action == Action.KEY_RELEASED) {
                        flag = false;
                    }
                }

                if(time >= 25) {
                    try {
                        if(player1.isAlive())
                            player1.getPlayer().move(add, erase);
                    } catch(CollisionException e) {
                        player1.kill();

                        try {
                            Thread.sleep(2000);
                        } catch(InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        context.exit(0);
                        return;
                    } catch(Exception e) {
                        e.printStackTrace();
                        context.exit(-1);
                        return;
                    }

                    time = 0;
                }

                erase.forEach(Draw::undraw);
                erase.clear();

                Draw.draw(player1.getPlayer().getQueue(), player1.getColor());

                add.forEach(rectangularShape -> Draw.draw(rectangularShape, player1.getColor()));
                add.clear();

                Draw.drawBonus(bonusListInGame.random());


                Snake.decrementAll();
            }
        });

		/*
         * Interface Graphique fin
		 */

    }
}
