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
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.Snake;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author collombj
 * @project EelZen
 * @package fr.upem.ir1.curvysnake.controller
 * @date 05/06/2015
 */
public class SinglePlayer {
    private static Rectangle gameSize = new Rectangle(0, 0, 500, 500);
    private static BonusListInGame bonusListInGame = new BonusListInGame();
    private static ApplicationContext context;
    
    public static void run() {
        // Set environnement
        Snake.setGameSize(gameSize);
        // Set Bonus List
        Snake.setBonusListInGame(bonusListInGame);

        Snake player1 = new Snake(new Point((int) gameSize.getCenterX(), (int) gameSize.getCenterY()), 0);

        
        /*
         * Interface Graphique initialisation
         */
        Application.run(Color.ORANGE, context -> {

            // get the size of the screen
            ScreenInfo screenInfo = context.getScreenInfo();
            float width = screenInfo.getWidth();
            float height = screenInfo.getHeight();
            System.out.println("size of the screen (" + width + " x " + height + ")");

            context.renderFrame(graphics -> {
                graphics.setColor(Color.ORANGE);
                graphics.fill(new  Rectangle2D.Float(0, 0, width, height));
            });

            Snake snake = new Snake(new Point(40,40), 0);
           while(true){
                Event event = context.pollOrWaitEvent(10);
                if (event == null) {  // no event
                    continue;
                }
                Action action = event.getAction();
                if (action == Action.KEY_PRESSED || action == Action.KEY_RELEASED) {
                    System.out.println("abort abort !");
                    context.exit(0);
                    return;
                }
                System.out.println(event);

                Point2D.Float location = event.getLocation();
                
                
        	}
        });
        
        
        
        
        /*
         * Interface Graphique fin
         */

    }
    
    private void draw(Ellipse2D.Float body){
    	
    	context.renderFrame(graphics ->{
    		graphics.setColor(Color.GREEN);
    		graphics.fill(body);
    	});
    	
    }
}
