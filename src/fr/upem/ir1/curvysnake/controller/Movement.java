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

import fr.upem.ir1.curvysnake.controller.exception.CollisionException;
import fr.upem.ir1.curvysnake.controller.exception.GameSizeException;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.*;
import java.util.List;

/**
 * This class represent the movement (full body) of the snake.
 * <p>
 * <p>The class offer some methods to manipulate the body (see after).</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 */
class Movement {

    /**
     * Default diameter size of a body element.
     */
    public static final int defaultDiameter = 10;
    /**
     * The border limit minimum of the movement (allowed - include)
     */
    private static RectangularShape gameSize = null;
    /**
     * List of <code>RectangularShape</code> (body) elements.
     */
    private final Deque<RectangularShape> move = new LinkedList<>();
    /**
     * Flag to know if a previous move was done or not. It is used to increase the snake size (every other time).
     */
    private boolean isIncreased = false;

    /**
     * Constructor of the body. It is only need the start body part.
     *
     * @param init The initial point of the snake movement.
     */
    Movement(Point init) {
        Ellipse2D.Float aFloat = new Ellipse2D.Float((float) init.x - defaultDiameter / 2,
                                                     (float) init.y - defaultDiameter / 2,
                                                     defaultDiameter, defaultDiameter);
        this.move.add(aFloat);
    }

    /**
     * Static method to get the information about the game size
     *
     * @return The information about the game size
     */
    static RectangularShape getGameSize() {
        return gameSize;
    }

    /**
     * Static method to specify the information about the game size
     *
     * @param rectangle New game size information
     */
    static void setGameSize(RectangularShape rectangle) {
        gameSize = rectangle;
    }

    /**
     * Method to get the snake body.
     *
     * @return The snake body.
     */
    Deque<RectangularShape> getMove() {
        return this.move;
    }

    /**
     * Method to get the head of the snake.
     *
     * @return The last added Ellipse2D.Float.
     */
    RectangularShape getHead() {
        return (RectangularShape) this.move.getLast().clone();
    }

    /**
     * Method to get queue of the snake.
     *
     * @return Return the queue element.
     */
    RectangularShape getQueue() {
        return (RectangularShape) this.move.getFirst().clone();
    }

    /**
     * Method to check if the Snake head hit a body part of a snake (another or itself).
     *
     * @return True if the Snake head hit another snake, false else.
     */
    boolean intersects() {
        for(Snake snake : Snake.getSnakeList()) {
            if(this.intersects(snake.getMove()))
                return true;
        }

        return false;
    }

    /**
     * Method to test if a position is intersecting this Snake body.
     *
     * @param position The position to test with this Snake body.
     *
     * @return True if an intersection is detected, false else.
     */
    boolean intersects(RectangularShape position) {
        for(RectangularShape shape : this.move) {
            if(position.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Method to check if the Snake head hit a body part of a snake (another or itself).
     *
     * @param bodyList The body of the other Snake to check with.
     *
     * @return True if the Snake head hit another snake, false else.
     */
    public boolean intersects(Deque<RectangularShape> bodyList) {
        RectangularShape head = this.getHead();

        boolean himself = false;
        if(this.move == bodyList) himself = true;

        int i = 0;
        int sizeOther = bodyList.size();
        for(RectangularShape shape : bodyList) {
            if(head.intersects(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight()))
                if(!himself || sizeOther - i > head.getWidth())
                    return true;

            i++;
        }

        return false;
    }

    /**
     * Method to check if the Snake head hit a border wall.
     *
     * @return True if the Snake head hit a border wall, false else.
     *
     * @throws GameSizeException If the GameSize is not set.
     */
    public boolean isHittingTheWall() throws GameSizeException {
        if(gameSize == null)
            throw new GameSizeException();

        RectangularShape head = this.getHead();
        return !gameSize.contains(head.getX(), head.getY(), head.getWidth(), head.getHeight());
    }

    /**
     * Method to move the snake body.
     * <p>
     * <p>Every other time, the snake body increased it own size. Else, it move.</p>
     *
     * @param direction   It's the direction of the Snake (value between -1 and 1 for <code>x</code> and <code>y</code>).
     * @param size        The bonus size needed to be added to the new body element size.
     * @param nextHope    The next position (movement from the previous position) of the new body element.
     * @param wallThrough Bonus to know if the snake is allowed to through a wall.
     * @param erase       The list of element erased from the snake body.
     *
     * @throws CollisionException If collision with a wall or a snake (another or itself) is detected.
     * @throws GameSizeException  If the GameSize is not set
     */
    public void move(Point direction, int size, int nextHope, boolean wallThrough, List<RectangularShape> erase) throws
            CollisionException, GameSizeException {

        if(erase == null)
            throw new NullPointerException();

        Rectangle nextHead = this.move.getLast().getBounds();

        // Manage the next hope position
        for(int i = 0 ; i <= nextHope ; i++) {
            nextHead.translate(direction.x / 2 * (nextHope + 1), direction.y / 2 * (nextHope + 1));
        }

        // Set the new size of the body element
        size += defaultDiameter;
        nextHead.x += nextHead.width - size;
        nextHead.y += nextHead.height - size;
        nextHead.height = size;
        nextHead.width = size;

        // Transform rectangle to ellipse
        RectangularShape nextMove = new Ellipse2D.Float(nextHead.x, nextHead.y, nextHead.width, nextHead.height);


        this.move.add(nextMove);

        // Check if the move generate a wall hit
        if(this.isHittingTheWall()) {
            if(wallThrough) {
                this.throughWall(nextMove);
            } else {
                // this.move.removeLast();
                throw new CollisionException();
            }
        }

        // Check if the move generate a collision with himself
        if(this.intersects())
            throw new CollisionException();

        this.isIncreased = !this.isIncreased;

        // Every other time, the body size is increased.
        if(!this.isIncreased)
            erase.add(this.move.removeFirst());
    }

    /**
     * Method to move the head of the body to the opposite position when it hit a wall.
     *
     * @param head The head of the body which is edited.
     *
     * @throws GameSizeException If the GameSize is not set
     */
    public void throughWall(RectangularShape head) throws GameSizeException {
        Rectangle bounds = head.getBounds();

        if(gameSize == null)
            throw new GameSizeException();

        if(bounds.getX() <= gameSize.getX()) {
            bounds.x = (int) gameSize.getX() + (int) gameSize.getWidth() - (int) bounds.getWidth() - 1;
        }
        if(bounds.getY() <= gameSize.getY()) {
            bounds.y = (int) gameSize.getY() + (int) gameSize.getHeight() - (int) bounds.getHeight() - 1;
        }

        if(bounds.getX() + bounds.getWidth() > gameSize.getX() + gameSize.getWidth() - 1) {
            bounds.x = (int) gameSize.getX() + 1;
        }
        if(bounds.getY() + bounds.getHeight() > gameSize.getY() + gameSize.getHeight() - 1) {
            bounds.y = (int) gameSize.getY() + 1;
        }

        head.setFrame(bounds);
    }

    /**
     * Clean the Body element. Keep only the head of the body
     *
     * @param erase The list of element erased from the snake body.
     */
    public void clean(List<RectangularShape> erase) {
        if(erase == null)
            throw new NullPointerException();

        if(this.move.size() == 1)
            return;

        erase.addAll(this.move);

        RectangularShape head = this.move.getLast();
        this.move.clear();
        this.move.add(head);
    }
}
