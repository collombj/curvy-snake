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

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Deque;
import java.util.LinkedList;

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
     * List of <code>Ellipse2D.Float</code> (body) elements.
     */
    private final Deque<Shape> move = new LinkedList<>();
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
     * Method to get the snake body.
     *
     * @return The snake body.
     */
    Deque<Shape> getMove() {
        return this.move;
    }

    /**
     * Method to get the head of the snake.
     *
     * @return The last added Ellipse2D.Float.
     */
    Shape getHead() {
        Ellipse2D.Float e = (Ellipse2D.Float) this.move.getLast();

        return (Shape) e.clone();
    }

    /**
     * Method to get queue of the snake.
     *
     * @return Return the queue element.
     */
    Shape getQueue() {
        Ellipse2D.Float e = (Ellipse2D.Float) this.move.getFirst();

        return (Shape) e.clone();
    }

    /**
     * Method to test if a body element is intersecting this Snake body.
     *
     * @param position The Shape to test with this Snake body.
     *
     * @return True if an intersection is detected, false else.
     */
    boolean intersects(Shape position) {
        for(Shape shape : this.move) {
            if(position.intersects(shape.getBounds().x, shape.getBounds().y,
                                   shape.getBounds().width, shape.getBounds().height)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the Snake head hit a body part of itself.
     *
     * @return True if the Snake head hit another snake, false else.
     */
    private boolean intersects() {
        Shape head = this.getHead();
        int i = 0;
        int size = this.move.size();

        for(Shape shape : this.move) {
            if(head.intersects(shape.getBounds().x, shape.getBounds().y,
                               shape.getBounds().width, shape.getBounds().height))
                if(size - i > head.getBounds().width)
                    return true;

            i++;
        }

        return false;
    }

    /**
     * Method to move the snake body.
     * <p>
     * <p>Every other time, the snake body increased it own size. Else, it move.</p>
     *
     * @param direction It's the direction of the Snake (value between -1 and 1 for <code>x</code> and <code>y</code>).
     * @param size      The bonus size needed to be added to the new body element size.
     * @param nextHope  The next position (movement from the previous position) of the new body element.
     *
     * @return The position leave by the snake, else if the snake is increasing it own size, return null.
     *
     * @throws CollisionException If collision with himself is detected.
     */
    public Shape move(Point direction, int size, int nextHope) throws CollisionException {
        Rectangle nextHead = this.move.getLast().getBounds();

        // Set the new size of the body element
        size += defaultDiameter;
        if(nextHead.width != size && nextHead.height != size) {
            nextHead.translate(nextHead.width - size, nextHead.height - size);
            nextHead.width = size;
            nextHead.height = size;
        }

        // Manage the next hope position
        for(int i = 0 ; i <= nextHope ; i++) {
            nextHead.translate(direction.x, direction.y);
        }

        // Transform rectangle to ellipse
        Ellipse2D.Float nextMove = new Ellipse2D.Float(nextHead.x, nextHead.y, nextHead.width, nextHead.height);

        this.move.add(nextMove);

        // Check if the move generate a collision with himself
        if(this.intersects())
            throw new CollisionException();

        this.isIncreased = !this.isIncreased;

        // Every other time, the body size is increased.
        if(!this.isIncreased) return this.move.pop();
        return null;
    }


    /**
     * Clean the Body element. Keep only the head of the body
     */
    public void clean() {
        Shape head = this.move.getLast();
        this.move.clear();
        this.move.add(head);
    }

    @Override
    public String toString() {
        return "Movement{move=" + move + '}';
    }
}
