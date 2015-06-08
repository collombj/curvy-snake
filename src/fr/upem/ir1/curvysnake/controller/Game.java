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

import fr.upem.ir1.curvysnake.controller.exception.BonusException;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Game {
    private static Rectangle gameSize = null;
    private static BonusListInGame bonus = null;

    public static Rectangle getGameSize() {
        return gameSize;
    }

    public static void setGameSize(Rectangle gameSize) {
        Game.gameSize = gameSize;
    }

    public static BonusListInGame getBonus() {
        return bonus;
    }

    public static void setBonus(BonusListInGame bonus) {
        Game.bonus = bonus;
    }

    public static boolean positionIsFree(RectangularShape position, Snake snake) {
        if(bonus.intersects(position))
            return false;

        if(snake.intersects(position))
            return false;

        return true;
    }

    public static List<Entry<Shape, Bonus>> getBonus(Shape position) throws BonusException {
        if(bonus == null)
            return null;

        List<Entry<Shape, Bonus>> result = new LinkedList<>();
        Entry<Shape, Bonus> entry = null;
        Iterator<Entry<Shape, Bonus>> it = bonus.iterator();
        while(it.hasNext()) {
            entry = it.next();

            // Intersects
            if(entry.getKey().intersects(position.getBounds().x, position.getBounds().y, position.getBounds().width,
                                         position.getBounds().height)) {
                result.add(entry);
                it.remove();
            }
        }

        return result;
    }

}
