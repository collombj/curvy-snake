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

package fr.upem.ir1.EelZen.controller;

import fr.upem.ir1.EelZen.Exception.CollisionException;

import java.util.Arrays;
import java.util.List;

/**
 * This class consist to manage a snack movement and body. The class managed also the user steering action.
 *
 * <p>
 *  The class Store 2 Informations :
 *      <ol>
 *          <li>The body management</li>
 *          <li>The direction management</li>
 *      </ol>
 * </p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 *
 * @see Movement
 * @see Position
 */
public class Snake {

    /**
     * Enum to list the two possibilities of action :
     *  <ul>
     *      <li>Go to the <code>LEFT</code></li>
     *      <li>Go to the <code>RIGHT</code></li>
     *  </ul>
     */
    public enum MoveTo {
        LEFT,
        RIGHT
    }

    /**
     * List of direction offer by the movement
     */
    private static final List<Position> directionList = Arrays.asList(
         new Position(-1, -1),
         new Position(-1, 0),
         new Position(-1, 1),
         new Position(0, 1),
         new Position(1, 1),
         new Position(1, 0),
         new Position(1, -1),
         new Position(0, -1)
    );

    /**
     * Represent the ID/position in the List of the actual direction of the Snake.
     */
    private int actualPosition;

    /**
     * List of element of the Snake body.
     */
    private Movement movement;

    /**
     * Constructor of the class. Initialize the initial position and direction.
     *
     * @param init The initial position of the Snake.
     * @param direction The initial direction of the Snake.
     */
    public Snake(Position init, Position direction) {
        this.actualPosition = directionList.indexOf(direction);

        if(this.actualPosition == -1)
            this.actualPosition = 5;

        this.movement = new Movement(init);
    }

    /**
     * Move the Snake
     *
     * @return The last deleted position, or null if no position was deleted.
     *
     * @see Movement
     */
    public Position move() throws CollisionException {
        return this.movement.move(directionList.get(this.actualPosition));
    }

    /**
     * Method the List of Movement. This method return a read only instance of the <code>List<Body></code>
     *
     * @return Instance in read only of the Body position List.
     */
    public List<Body> getMove() {
        return this.movement.getMove();
    }

    /**
     * Action of the user to change (<code>LEFT</code> or <code>RIGHT</code>) the direction (step by step only).
     *
     * @param m The movement realize by the user
     */
    public void changeDirection(MoveTo m) {
        if(m == MoveTo.LEFT) {
            this.actualPosition--;

            if(this.actualPosition < 0)
                this.actualPosition = directionList.size()-1;
        } else if(m == MoveTo.RIGHT) {
            this.actualPosition++;

            if(this.actualPosition >= directionList.size())
                this.actualPosition = 0;
        }
    }

    @Override
    public String toString() {
        return "Snake{actualPosition=" + directionList.get(actualPosition) + ", movement=" + movement + '}';
    }
}
