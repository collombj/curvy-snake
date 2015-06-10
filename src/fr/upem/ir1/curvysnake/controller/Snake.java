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
import java.awt.geom.RectangularShape;
import java.util.*;
import java.util.List;

/**
 * This class consist to manage a snake movement and body. The class managed
 * also the user steering action.
 * <p>
 * <p>
 * The class Store 2 Information :
 * <ol>
 * <li>The body management</li>
 * <li>The direction management</li>
 * </ol>
 * </p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET Valentin
 * @see Movement
 * @see BonusListInGame
 */
public class Snake {
    /**
     * Aplha angle for the direction. It is a degree angle.
     */
    private int alpha = 0;
    /**
     * List of Snake actually in the race.
     */
    private static final List<Snake> SNAKE_LIST = new LinkedList<>();
    /**
     * Speed of the snake.
     */
    private final static int defaultSpeed = 3;
    /**
     * List of Bonus actually in game.
     */
    private static BonusListInGame bonusListInGame = null;

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
     * @throws IllegalArgumentException If the alpha angle is outside the limit (0 - 180 -- +/-).
     */
    public Snake(Point init, int alpha) throws IllegalArgumentException {
        if(Math.abs(alpha) > 180)
            throw new IllegalArgumentException();

        this.alpha = alpha;

        this.movement = new Movement(init);

        SNAKE_LIST.add(this);
    }

    /**
     * Destructor of the reference in the Snake list. You need to used it, if the Snake is store for collision
     * detection. But if this Snake is not used in collision detection, the method is useless.
     *
     * @param s The Snake to remove from the list of the Snake.
     *
     * @return True if the remove action is successful, false else.
     */
    public static boolean destroy(Snake s) {
        return SNAKE_LIST.remove(s);
    }

    /**
     * Static method to get the information about the game size
     *
     * @return The information about the game size
     */
    public static RectangularShape getGameSize() {
        return Movement.getGameSize();
    }

    /**
     * Static method to specify the information about the game size
     *
     * @param rectangle New game size information
     */
    public static void setGameSize(RectangularShape rectangle) {
        Movement.setGameSize(rectangle);
    }

    /**
     * Method to get the Bonus List actually in Game.
     *
     * @return The BonusListInGame with all Bonus associated to a position.
     */
    public static BonusListInGame getBonusListInGame() {
        return bonusListInGame;
    }

    /**
     * Method to set the Bonus List actually in Game for all Snake.
     *
     * @param bonus The BonusListInGame for all Snake.
     */
    public static void setBonusListInGame(BonusListInGame bonus) {
        bonusListInGame = bonus;
    }

    /**
     * Method to clean all Snake trace. This action keep only the head of the Snakes.
     */
    public static void cleanAll(List<RectangularShape> erase) {
        SNAKE_LIST.forEach(snake -> {
            snake.clean(erase);
        });
    }

    /**
     * Method to decrement all Snake bonus.
     */
    public static void decrementAll() {
        SNAKE_LIST.forEach(Snake::decrement);
    }

    /**
     * Method to detect if a position is not used by a Snake body element. The
     *
     * @param position The position to test with all Snake.
     *
     * @return True if the position is free, false else.
     */
    public static boolean positionIsFree(RectangularShape position) {
        for(Snake snake : SNAKE_LIST) {
            if(snake.movement.intersects(position)) {
                return false;
            }
        }

        return true;
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
     * @param add The list of element added with the snake body.
     *
     * @throws CollisionException       If collision with a wall or a snake (another or itself) is detected.
     * @throws IllegalAccessException   If a bonus can not be affected to a snake (ex: erase all)
     * @throws GameSizeException        If the GameSize is not set
     * @throws BonusException           If the BonusListInGame is not set
     * @throws IllegalArgumentException If "added" is null.
     * @see Movement
     */
    public void move(List<RectangularShape> add, List<RectangularShape> erase)
            throws CollisionException, IllegalAccessException, GameSizeException, BonusException, IllegalArgumentException {
        if(add == null || erase == null)
            throw new NullPointerException();

        int speedBonus = defaultSpeed;
        int sizeBonus = 0;
        int nextHope = 0;

        boolean wallThrough = false;

        // Check all bonus
        for(Bonus bonus : this.bonusList) {
            speedBonus += bonus.speed();
            sizeBonus += bonus.size();
            nextHope += bonus.nextHope();

            wallThrough = wallThrough || bonus.wallThrough();
        }

        if(speedBonus < 1) speedBonus = 1;
        if(sizeBonus < -Movement.defaultDiameter + 1) sizeBonus = -Movement.defaultDiameter + 1;
        if(nextHope > 100) nextHope = 100;
        if((int) (Math.random() * 25) == 13) nextHope = 0;

        // create the movement of 'speed-1' move
        for(int i = 0 ; i < speedBonus ; i++) {
            // NextHope available only for the first move of this move action
            if(i != 0)
                nextHope = 0;

            this.movement.move(this.getDirection(), sizeBonus, nextHope, wallThrough, erase);

            add.add(this.getHead());
            this.detectBonus(erase);
        }
    }

    private void detectBonus(List<RectangularShape> erase)
            throws BonusException, IllegalAccessException {

        if(erase == null)
            throw new NullPointerException();

        if(bonusListInGame == null)
            throw new BonusException("The BonusListInGame is not set for Snake");

        Entry<RectangularShape, Bonus> entry;
        Iterator<Entry<RectangularShape, Bonus>> it = bonusListInGame.iterator();
        while(it.hasNext()) {
            entry = it.next();

            if(entry.getKey().intersects(this.getHead().getBounds())) {
                // Delete the bonus display
                erase.add(entry.getKey());
                // if it is an erase all, erase all body element
                this.addBonus(entry.getValue(), erase);
                it.remove();
            }
        }

    }

    /**
     * Method to get the List of Movement.
     *
     * @return The Ellipse2D.Float position List.
     */
    Deque<RectangularShape> getMove() {
        return this.movement.getMove();
    }

    /**
     * Action of the user to change (<code>LEFT</code> or <code>RIGHT</code>) the direction (step by step only). The
     * direction is managed by the bonus action too.
     *
     * @param m      The movement realize by the user (LEFT or RIGHT only).
     * @param inTurn To inform if the Snake previous action is turning or not (change the angle)
     *
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     */
    public void changeDirection(MoveTo m, boolean inTurn) throws IllegalAccessException {
        boolean inverse = true;

        int angle = inTurn ? 10 : 15;

        System.out.println(angle);

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
            this.alpha += angle;

            if(this.alpha >= 360) {
                this.alpha = 0;
            }
        } else if(m == MoveTo.RIGHT) {
            this.alpha -= angle;

            if(this.alpha <= -360) {
                this.alpha = 0;
            }
        }
    }

    /**
     * Add a new bonus in the action bonus list of the snake.
     * <p>
     * <p>The <code>Bonus</code> can be empty (null).</p>
     *
     * @param b The new bonus for the snake
     */
    public void addBonus(Bonus b, List<RectangularShape> erase) {
        if(b != null) {
            if(b.eraseAll()) {
                Snake.cleanAll(erase);
            } else {
                this.bonusList.add(b);
            }
        }
    }

    /**
     * Method to get the head element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake head.
     */
    public RectangularShape getHead() {
        return this.movement.getHead();
    }

    /**
     * Method to get the queue element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake queue.
     */
    public RectangularShape getQueue() {
        return this.movement.getQueue();
    }

    /**
     * Decrement bonus time and delete if time is exceeded
     */
    private void decrement() {
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
    public void clean(List<RectangularShape> erase) {
        this.movement.clean(erase);
    }

    /**
     * Method to create a point direction from an alpha angle (degree).
     *
     * @return The Point which is representing the direction.
     */
    public Point getDirection() {
        int x = (int) Math.round(Math.cos(this.alpha * 0.017453292519943) * Movement.defaultDiameter);
        int y = (int) Math.round(Math.sin(this.alpha * 0.017453292519943) * Movement.defaultDiameter);

        return new Point(x, y);
    }

    /**
     * Method to display the Snake trace. TEST ONLY
     *
     * @return The String representation of the Snake
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Snake: ");

        this.getMove().forEach((ellipse2D) -> {
            s.append("(")
                    .append(ellipse2D.getCenterX()).append(", ")
                    .append(ellipse2D.getCenterY()).append(", ")
                    .append(ellipse2D.getWidth() / 2)
                    .append("), ");
        });
        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }
}
