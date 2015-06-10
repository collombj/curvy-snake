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


import java.util.Random;

/**
 * Enum representing a list of Bonus Type. It is offering a List of good and bad Bonus. It is represent by Enum (for
 * the name associated with an instance of Bonus). It offering some special action to manipulate this list.
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 * @see Bonus
 */
public enum BonusAvailable {
    SPEED_INCREASE(Bonus.speed(1)),
    SPEED_DECREASE(Bonus.speed(-1)),
    SIZE_INCREASE(Bonus.size(5)),
    SIZE_DECREASE(Bonus.size(-5)),
    NEXT_HOPE(Bonus.nextHope(50).setDuration(75)),
    WALL_THROUGH(Bonus.wallThrough(true)),
    INVERSE_DIRECTION(Bonus.inverseDirection(true)),
    ERASE_ALL(Bonus.eraseAll(true));

    static private BonusAvailable[] list = {
                                                   SPEED_INCREASE,
                                                   SPEED_DECREASE,
                                                   SIZE_INCREASE,
                                                   SIZE_DECREASE,
                                                   NEXT_HOPE,
                                                   WALL_THROUGH,
                                                   INVERSE_DIRECTION,
                                                   ERASE_ALL
    };


    /**
     * Bonus action represent by the Enum.
     */
    private final Bonus bonus;

    /**
     * Constructor of the Enum value. It associate a Bonus name, to a Bonus Action.
     *
     * @param bonus The Bonus associated to the Name (Enum).
     */
    BonusAvailable(Bonus bonus) {
        this.bonus = bonus;
    }

    /**
     * Method to get a new instance of this type of Bonus.
     *
     * @return The clone of this Bonus instance.
     */
    public Bonus get() {
        return (Bonus) bonus.clone();
    }

    /**
     * Return a random Bonus clone available in the Bonus type list.
     *
     * @return A random Bonus.
     */
    public static Bonus random() {
        Random r = new Random();

        return list[r.nextInt(list.length)].get();
    }
}
