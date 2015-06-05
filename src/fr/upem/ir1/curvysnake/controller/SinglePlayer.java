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

package fr.upem.ir1.curvysnake.controller;

import fr.upem.ir1.curvysnake.controller.core.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.core.Snake;
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;

import java.awt.*;

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

        Snake player1 = new Snake(new Point((int) gameSize.getCenterX(), (int) gameSize.getCenterY()), 0);

        // Display the first position
        System.out.println(player1);
        try {
            while(true) {
                player1.move();   // Exception origin
                System.out.println(player1);

                // player1.changeDirection(Snake.MoveTo.RIGHT);  // Direction change

                // Pop a new bonus with 25% of luck
                if((int)(Math.random()*4) == 2) {
                    bonusListInGame.randomPosition();
                    System.out.println("Bonus Pop");
                }

                Snake.decrementAll(); // Decrement Bonus time
            }
        } catch(CollisionException e) {
            System.out.print("Collision: ");
            System.out.println(player1);
            e.printStackTrace();

        } catch(Exception e) { // Other Exception
            e.printStackTrace();
        }
    }
}
