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

import fr.upem.ir1.curvysnake.controller.BonusListInGame;
import fr.upem.ir1.curvysnake.controller.Game;
import fr.upem.ir1.curvysnake.controller.Snake;

import java.awt.*;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        /*if(args.length == 1 && (args[0].equals("multiplayer") || args[0].equals("--multiplayer")))
            MultiPlayer.run();
        else
            SinglePlayer.run();*/

        Rectangle gameSize = new Rectangle(0, 0, 500, 500);
        BonusListInGame bonusList = new BonusListInGame();
        LinkedList<Snake> snakeList = new LinkedList<>();

        Game.setGameSize(gameSize);
        Game.setBonus(bonusList);
        Game.setSnakes(snakeList);

        Snake s1 = new Snake(new Point(200, 200), 0);
        snakeList.add(s1);
        s1.move(Game.snakesCollision(), Game.wallCollision(), Game::getBonus);
        System.out.println(s1);
    }
}
