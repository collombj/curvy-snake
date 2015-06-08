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
import fr.upem.ir1.curvysnake.controller.MoveTo;

import javax.naming.TimeLimitExceededException;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
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
    private final static int defaultSpeed = 10;
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
     * @param init          The initial position of the Snake.
     * @param alpha         The initial alpha direction of the Snake.
     * @param inStorageList The boolean to store (or not) the reference of the Snake in the static list of collision
     *                      detection.
     *
     * @throws IllegalArgumentException If the alpha angle is outside the limit (0 - 180 -- +/-).
     */
    public Snake(Point init, int alpha, boolean inStorageList) throws IllegalArgumentException {
        if(Math.abs(alpha) > 180)
            throw new IllegalArgumentException();

        this.alpha = alpha;

        this.movement = new Movement(init);

        if(inStorageList)
            SNAKE_LIST.add(this);
    }

    /**
     * Constructor of the class. Initialize the initial position and direction. The Snake is not store in the main
     * list of Snake (used for Snake collision).
     *
     * @param init  The initial position of the Snake.
     * @param alpha The initial alpha direction of the Snake.
     *
     * @throws IllegalArgumentException If the alpha angle is outside the limit (0 - 180 -- +/-).
     */
    public Snake(Point init, int alpha) throws IllegalArgumentException {
        this(init, alpha, false);
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
    private static void cleanAll() {
        SNAKE_LIST.forEach(Snake::clean);
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
    public static boolean positionIsFree(Ellipse2D.Float position) {
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
     * @param added The list of element added with the snake body.
     *
     * @return The last deleted position, or null if no position was deleted.
     *
     * @throws CollisionException     If collision with a wall or a snake (another or itself) is detected.
     * @throws IllegalAccessException If a bonus can not be affected to a snake (ex: erase all)
     * @throws GameSizeException      If the GameSize is not set
     * @throws BonusException         If the BonusListInGame is not set
     * @see Movement
     */
    public List<Ellipse2D.Float> move(List<Ellipse2D.Float> added) throws CollisionException, IllegalAccessException, GameSizeException,
                                                                                  BonusException, IllegalArgumentException {
        if(added == null)
            throw new IllegalArgumentException();

        List<Ellipse2D.Float> result = new LinkedList<>();

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

        // create the movement of 'speed-1' move
        for(int i = 0 ; i < speedBonus ; i++) {
            // NextHope available only for the first move of this move action
            if(i != 0)
                nextHope = 0;

            result.add(this.movement.move(this.getDirection(), sizeBonus, nextHope, wallThrough));
            added.add(this.getHead());
            result.addAll(this.detectBonus());
        }

        return result;
    }

    private List<Ellipse2D.Float> detectBonus() throws BonusException, IllegalAccessException {
        if(bonusListInGame == null)
            throw new BonusException("The BonusListInGame is not set for Snake");

        LinkedList<Ellipse2D.Float> result = new LinkedList<>();

        bonusListInGame.forEach((entry) -> {
            result.add(entry.getKey());
            this.addBonus(entry.getValue());
        });

        return result;
    }

    /**
     * Method to get the List of Movement.
     *
     * @return The Ellipse2D.Float position List.
     */
    LinkedList<Ellipse2D.Float> getMove() {
        return this.movement.getMove();
    }

    /**
     * Action of the user to change (<code>LEFT</code> or <code>RIGHT</code>) the direction (step by step only). The
     * direction is managed by the bonus action too.
     *
     * @param m The movement realize by the user (LEFT or RIGHT only).
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
    public void addBonus(Bonus b) {
        if(b == null)
            return;

        if(b.eraseAll()) {
            // TODO revoir cette partie en fonction des modifications
            Snake.cleanAll();
            this.clean();
        } else
            this.bonusList.add(b);
    }

    /**
     * Method to get the head element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake head.
     */
    public Ellipse2D.Float getHead() {
        return this.movement.getHead();
    }

    /**
     * Method to get the queue element of the Snake.
     *
     * @return The Ellipse2D.Float showing the Snake queue.
     */
    public Ellipse2D.Float getQueue() {
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
    public void clean() {
        //TODO revoir le système de nettoyage
        this.movement.clean();
    }

    /**
     * Method to create a point direction from an alpha angle (degree).
     *
     * @return The Point which is representing the direction.
     */
    public Point getDirection() {
        double x = Math.acos(this.alpha) * Movement.defaultDiameter / 3;
        double y = Math.asin(this.alpha) * Movement.defaultDiameter / 3;

        return new Point((int) x, (int) y);
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
            s.append("(" + ellipse2D.getCenterX() + ", " + ellipse2D.getCenterY() + ", " + ellipse2D.getWidth() / 2 + "), ");
        });
        s.deleteCharAt(s.length() - 1);

        return s.toString();
    }
}
