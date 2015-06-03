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
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;

/**
 * This class consist to manage a snake movement and body. The class managed
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
 */
public class Snake {

    /**
     * List of direction offer by the movement
     */
    private static final List<Point> DIRECTION_LIST = Arrays.asList(new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
                                                                    new Point(0, 1), new Point(1, 1), new Point(1, 0),
                                                                    new Point(1, -1), new Point(0, -1));
    /**
     * List of Snake actually in the race
     */
    private static final List<Snake> SNAKE_LIST = new LinkedList<>();
    /**
     * Speed of the snake
     */
    private final static int defaultSpeed = 10;
    /**
     * List of element of the Snake body.
     */
    private final Movement movement;
    /**
     * Bonus list active on snake
     */
    private final LinkedList<Bonus> bonusList = new LinkedList<>();
    /**
     * Represent the ID/position in the List of the actual direction of the
     * Snake.
     */
    private int actualDirection;

    /**
     * Constructor of the class. Initialize the initial position and direction.
     *
     * @param init      The initial position of the Snake.
     * @param direction The initial direction of the Snake.
     */
    public Snake(Point init, Point direction) {
        this.actualDirection = DIRECTION_LIST.indexOf(direction);

        if(this.actualDirection == -1)
            this.actualDirection = 5;

        this.movement = new Movement(init);

        SNAKE_LIST.add(this);
    }

    /**
     * Static method to get the information about the game size
     *
     * @return The information about the game size
     */
    public static Rectangle getGameSize() {
        return Movement.getGameSize();
    }

    /**
     * Static method to specify the information about the game size
     *
     * @param rectangle New game size information
     */
    public static void setGameSize(Rectangle rectangle) {
        Movement.setGameSize(rectangle);
    }

    /**
     * Method to get the Snake list actually in game.
     *
     * @return An unmodifiable view of the snake list.
     */
    static List<Snake> getSnakeList() {
        return Collections.unmodifiableList(SNAKE_LIST);
    }

    /**
     * Move the Snake
     *
     * @return The last deleted position, or null if no position was deleted.
     *
     * @throws CollisionException     If collision with a wall or a snake (another or itself) is detected.
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     * @see Movement
     */
    public List<Ellipse2D> move() throws CollisionException, IllegalAccessException {
        List<Ellipse2D> result = new LinkedList<>();

        int speedBonus = defaultSpeed;
        int sizeBonus = 0;
        int nextHope = 0;

        boolean wallThrough = false;
        boolean inverseDirection = false;

        // Check all bonus
        for(Bonus bonus : this.bonusList) {
            speedBonus += bonus.speed();
            sizeBonus += bonus.size();
            nextHope += bonus.nextHope();

            wallThrough = wallThrough || bonus.wallThrough();
            inverseDirection = inverseDirection || bonus.inverseDirection();
        }

        // NextHope available only for the first move of this move action
        result.add(this.movement.move(DIRECTION_LIST.get(this.actualDirection), sizeBonus, nextHope, wallThrough));

        // create the movement of 'speed-1' move
        for(int i = 1 ; i < speedBonus ; i++) {
            result.add(this.movement.move(DIRECTION_LIST.get(this.actualDirection), sizeBonus, wallThrough));
        }

        return result;
    }

    /**
     * Method the List of Movement. This method return a read only instance of
     * the <code>List<Circle></code>
     *
     * @return Instance in read only of the Circle position List.
     */
    LinkedList<Ellipse2D> getMove() {
        return this.movement.getMove();
    }

    /**
     * Action of the user to change (<code>LEFT</code> or <code>RIGHT</code>) the direction (step by step only). The
     * direction is managed by the bonus action too.
     *
     * @param m The movement realize by the user
     *
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     */
    public void changeDirection(MoveTo m) throws IllegalAccessException {
        boolean inverse = false;

        // Check only for inverse direction bonus
        for(Bonus bonus : this.bonusList) {
            if(bonus.inverseDirection())
                inverse = !inverse;
        }

        if(inverse) {
            if(m == MoveTo.LEFT) m = MoveTo.RIGHT;
            else if(m == MoveTo.RIGHT) m = MoveTo.LEFT;
        }

        if(m == MoveTo.LEFT) {
            this.actualDirection--;

            if(this.actualDirection < 0)
                this.actualDirection = DIRECTION_LIST.size() - 1;
        } else if(m == MoveTo.RIGHT) {
            this.actualDirection++;

            if(this.actualDirection >= DIRECTION_LIST.size())
                this.actualDirection = 0;
        }
    }

    /**
     * Add a new bonus in the action bonus list of the snake.
     * <p>
     * <p>The <code>Bonus</code> can be empty (null).</p>
     *
     * @param b The new bonus for the snake
     *
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     */
    public void addBonus(Bonus b) throws IllegalAccessException {
        if(b == null)
            return;

        b.size();   // Test of the Exception degree
        this.bonusList.add(b);
    }

    /**
     * Method to get the head element of the Snake.
     *
     * @return The Circle showing the Snake head.
     */
    public Ellipse2D getHead() {
        return this.movement.getHead();
    }

    /**
     * Method to get the queue element of the Snake.
     *
     * @return The Circle showing the Snake queue.
     */
    public Ellipse2D getQueue() {
        return this.movement.getQueue();
    }

    /**
     * Decrement bonus time and delete if time is exceeded
     */
    public void decrement() {
        Iterator<Bonus> it = this.bonusList.iterator();
        while(it.hasNext()) {
            Bonus b = it.next();

            try {
                b.decrement();
            } catch(TimeLimitExceededException e) {
                it.remove();
            }
        }
    }

    /**
     * Clean the Body element. Keep only the head of the body
     */
    public void clean() {
        this.movement.clean();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Snake: ");
        for(Ellipse2D ellipse2D : this.getMove()) {
            s.append("(" + ellipse2D.getCenterX() + ", " + ellipse2D.getCenterY() + ", " + ellipse2D.getWidth() / 2 + "), ");
        }
        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }

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
}
