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

import fr.upem.ir1.curvysnake.exception.CollisionException;

import javax.naming.TimeLimitExceededException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class consist to manage a snack movement and body. The class managed
 * also the user steering action.
 * <p>
 * <p>
 * The class Store 2 Informations :
 * <ol>
 * <li>The body management</li>
 * <li>The direction management</li>
 * </ol>
 * </p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET Valentin
 * @see Movement
 * @see Position
 */
public class Snake {

    /**
     * Enum to list the two possibilities of action :
     * <ul>
     * <li>Go to the <code>LEFT</code></li>
     * <li>Go to the <code>RIGHT</code></li>
     * </ul>
     */
    public enum MoveTo {
        LEFT, RIGHT
    }

    /**
     * List of direction offer by the movement
     */
    private static final List<Position> directionList = Arrays.asList(
                                                                             new Position(-1, -1), new Position(-1, 0), new Position(-1, 1),
                                                                             new Position(0, 1), new Position(1, 1), new Position(1, 0),
                                                                             new Position(1, -1), new Position(0, -1));

    /**
     * Represent the ID/position in the List of the actual direction of the
     * Snake.
     */
    private int actualPosition;

    /**
     * List of element of the Snake body.
     */
    private Movement movement;

    /**
     * Speed of the snake
     */
    private final static int speed = 5;

    /**
     * Bonus list active on snake
     */
    private final LinkedList<Bonus> bonusList = new LinkedList<>();

    /**
     * Constructor of the class. Initialize the initial position and direction.
     *
     * @param init      The initial position of the Snake.
     * @param direction The initial direction of the Snake.
     */
    public Snake(Position init, Position direction, Position limitMin, Position limitMax) {
        this.actualPosition = directionList.indexOf(direction);

        if(this.actualPosition == -1)
            this.actualPosition = 5;

        this.movement = new Movement(init, limitMin, limitMax);

    }

    /**
     * Move the Snake
     *
     * @return The last deleted position, or null if no position was deleted.
     *
     * @see Movement
     */
    public List<Circle> move() throws CollisionException {
        List<Circle> result = null;

        int speedBonus = speed;
        int sizeBonus = 0;
        int nextHope = 0;

        boolean wallThrough = false;
        boolean inverseDirection = false;

        for(Bonus bonus : this.bonusList) {
            try {
                speedBonus += bonus.speed();
                sizeBonus += bonus.size();
                nextHope += bonus.nextHope();

                wallThrough = wallThrough || bonus.wallThrough();
                inverseDirection = inverseDirection || bonus.inverseDirection();
            } catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // NextHope available only for the first move of this move action
        this.movement.move(directionList.get(this.actualPosition), sizeBonus, nextHope, wallThrough);

        for(int i = 1 ; i < speedBonus ; i++) {
            this.movement.move(directionList.get(this.actualPosition), sizeBonus, wallThrough);
        }


        return result;
    }

    /**
     * Method the List of Movement. This method return a read only instance of
     * the <code>List<Circle></code>
     *
     * @return Instance in read only of the Circle position List.
     */
    public List<Circle> getMove() {
        return this.movement.getMove();
    }

    /**
     * Action of the user to change (<code>LEFT</code> or <code>RIGHT</code>)
     * the direction (step by step only).
     *
     * @param m The movement realize by the user
     */
    public void changeDirection(MoveTo m) {
        if(m == MoveTo.LEFT) {
            this.actualPosition--;

            if(this.actualPosition < 0)
                this.actualPosition = directionList.size() - 1;
        } else if(m == MoveTo.RIGHT) {
            this.actualPosition++;

            if(this.actualPosition >= directionList.size())
                this.actualPosition = 0;
        }
    }

    /**
     * Add a new bonus in the action bonus list of the snake.
     *
     * <p>The <code>Bonus</code> can be empty (null).</p>
     *
     * @param b The new bonus for the snake
     */
    public void addBonus(Bonus b) {
        if(b != null)
            this.bonusList.add(b);
    }

    /**
     * Method to get the head element of the Snake.
     *
     * @return The Circle showing the Snake head.
     */
    public Circle getHead() {
        return this.movement.getHead();
    }

    /**
     * Decrement bonus time and delete if time is exceeded
     */
    public void decrement() {
        for(Bonus bonus : this.bonusList) {
            try {
                bonus.decrement();
            } catch(TimeLimitExceededException e) {
                this.bonusList.remove(bonus);
            }
        }
    }

    @Override
    public String toString() {
        return "Snake{actualPosition=" + directionList.get(actualPosition)
                       + ", movement=" + movement + '}';
    }
}
