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
import fr.upem.ir1.curvysnake.controller.exception.CollisionException;
import fr.upem.ir1.curvysnake.controller.exception.GameSizeException;

import javax.naming.TimeLimitExceededException;
import java.awt.*;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
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
     * Aplha angle for the direction
     */
    private int alpha = 0;
    /**
     * Speed of the snake.
     */
    private final static int defaultSpeed = 10;
    /**
     * List of element of the Snake body.
     */
    private final Movement movement;
    /**
     * Bonus list active on snake.
     */
    private final List<Bonus> bonusList = new LinkedList<>();

    /**
     * Constructor of the class. Initialize the initial position and direction.
     *
     * @param init  The initial position of the Snake.
     * @param alpha The initial alpha direction of the Snake.
     *
     * @throws IllegalArgumentException If the alpha angle is outside the limit (0 - 180).
     */
    public Snake(Point init, int alpha) throws IllegalArgumentException {
        if(Math.abs(alpha) > 180)
            throw new IllegalArgumentException();

        this.alpha = alpha;

        this.movement = new Movement(init);
    }

    /**
     * Move the Snake
     *
     * @return The last deleted position, or null if no position was deleted.
     *
     * @throws CollisionException     If collision with a wall or a snake (another or itself) is detected.
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     * @throws GameSizeException      If the GameSize is not set
     * @throws BonusException         If the BonusListInGame is not set
     * @see Movement
     */
    public Displayer move(Rectangle gameSize) throws IllegalAccessException, CollisionException {
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

        // Create the movement
        Displayer result = new Displayer();
        for(int i = 0 ; i < speedBonus ; i++) {
            // NextHope available only for the first move of this move action
            if(i != 0)
                nextHope = 0;

            result.oldShape.add(this.movement.move(this.getDirection(), sizeBonus, nextHope));

            if(gameSize.intersects(this.getHead().getBounds2D())) {
                // collision de mur
            }

                result.newShape.add(this.getHead());
            result.oldShape.addAll();
        }

        return result;
    }

    /**
     * Method to get the List of Movement.
     *
     * @return The Ellipse2D.Float position List.
     */
    Deque<Shape> getMove() {
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
            this.alpha--;

            if(this.alpha < -180)
                this.alpha = 179;
        } else if(m == MoveTo.RIGHT) {
            this.alpha++;

            if(this.alpha >= -179)
                this.alpha = 0;
        }
    }

    /**
     * Add a new bonus in the action bonus list of the snake.
     * <p>
     * <p>The <code>Bonus</code> can be empty (null).</p>
     *
     * @param b The new bonus for the snake
     */
    public void addBonus(Bonus b) throws BonusException {
        if(b == null)
            throw new BonusException();

        if(b.eraseAll())
            this.clean();
        else
            this.bonusList.add(b);
    }

    /**
     * Method to get the head element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake head.
     */
    public Shape getHead() {
        return this.movement.getHead();
    }

    /**
     * Method to get the queue element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake queue.
     */
    public Shape getQueue() {
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

        this.getMove().forEach((shapeConsumer) -> {
            s.append("(" + shapeConsumer.getBounds().getCenterX() + ", " + shapeConsumer.getBounds().getCenterY() + ", " +
                             shapeConsumer.getBounds().getWidth() / 2 + "), ");
        });

        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }


    public Point getDirection() {
        double x = Math.acos(this.alpha) * Movement.defaultDiameter / 2;
        double y = Math.asin(this.alpha) * Movement.defaultDiameter / 2;

        return new Point((int) x, (int) y);
    }

    /**
     * Detect if a collision is realised with him self
     *
     * @param position The head to check if the body
     *
     * @return True if collision is realised, false else.
     */
    public boolean intersects(Shape position) {
        return this.movement.intersects(position);
    }

    /**
     * Method to move the head of the body to the opposite position when it hit a wall.
     *
     * @param head     The head of the body which is edited.
     * @param gameSize The game information for the shift
     */
    private static void throughWall(Shape head, Rectangle gameSize) {
        if(head.getBounds().x < gameSize.x) {
            head.getBounds().x = gameSize.x + gameSize.width - 1;
        }
        if(head.getBounds().y < gameSize.y) {
            head.getBounds().y = gameSize.y + gameSize.height - 1;
        }

        if(head.getBounds().x >= gameSize.x + gameSize.width - 1) {
            head.getBounds().x = gameSize.x;
        }
        if(head.getBounds().y >= gameSize.y + gameSize.height - 1) {
            head.getBounds().y = gameSize.y;
        }
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
