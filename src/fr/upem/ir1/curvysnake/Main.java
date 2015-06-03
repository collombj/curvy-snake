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

package fr.upem.ir1.curvysnake;

import fr.upem.ir1.curvysnake.controller.Bonus;
import fr.upem.ir1.curvysnake.controller.Snake;
import fr.upem.ir1.curvysnake.exception.CollisionException;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // Set environnement
        Snake.setGameSize(new Rectangle(0, 0, 500, 500));

        // Initialize the Snake
        Snake s = new Snake(new Point(250, 250), new Point(1, 0));

        // Display the first position
        System.out.println(s);
        try {
            s.addBonus(Bonus.size(10));

            while(true) {
                s.move();   // Exception origin
                s.decrement(); // Decrement Bonus time

                System.out.println(s);

               // s.changeDirection(Snake.MoveTo.RIGHT);  // Direction change
            }
        } catch(CollisionException e) {
            System.out.print("Collision: ");
            System.out.println(s);
            e.printStackTrace();

        } catch(Exception e) { // Other Exception
            e.printStackTrace();
        }

    }
}
