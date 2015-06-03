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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent the movement (full body) of the snack.
 * <p>
 * <p>The class offer some methods to manipulate the body (see after).</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 */
public class Movement {

    /**
     * Default radius size of a body element.
     */
    private static final int defaultRadius = 5;
    /**
     * List of <code>Circle</code> elements.
     */
    private LinkedList<Circle> move;
    /**
     * Flag to know if a previous move was done or not. It is used to increase the snack size (every other time).
     */
    private boolean isIncreased;

    /**
     * The border limit minimum of the movement (allowed - include)
     */
    private Position limitMin;

    /**
     * The border limit maximum of the movement (not allowed - exclude)
     */
    private Position limitMax;

    /**
     * Constructor of the body. It is only need the start point of the movement.
     *
     * @param init     The initial point of the snack movement.
     * @param limitMin The border limit minimum for the movement (position allowed)
     * @param limitMax The border limit maximum for the movement (position not allowed)
     */
    public Movement(Position init, Position limitMin, Position limitMax) {
        this.move = new LinkedList<>();
        this.isIncreased = false;
        this.limitMin = limitMin;
        this.limitMax = limitMax;

        this.move.add(new Circle(init, defaultRadius));
    }

    /**
     * Method to get an unmodifiable view of the snack body.
     *
     * @return An unmodifiable view of the <code>move</code> list.
     */
    public List<Circle> getMove() {
        return Collections.unmodifiableList(this.move);
    }

    /**
     * Method to get the head of the body (last added Circle).
     *
     * @return The last added Circle.
     */
    public Circle getHead() {
        return this.move.getLast().duplicate();
    }

    /**
     * Method to get queue of the body (first element in the pile - List)
     *
     * @return Return the queue element.
     */
    public Circle getQueue() {
        return this.move.getFirst();
    }

    /**
     * Method to detect if the head of the snack (the last insert into the <code>move</code> LinkedList) is
     * crossing/hitting the body rest.
     *
     * @return True, if the snack cross a part of his own body, false else.
     *
     * @see this.isCrossed(List, boolean)
     */
    public boolean isCrossed() {
        return this.isCrossed(this.move, true);
    }

    /**
     * Method to detect if the head of the snack <code>s</code> (the last insert into the <code>move</code>
     * LinkedList) is crossing/hitting the body of this .
     *
     * @param s Snake to test with this snack body for a collision.
     *
     * @return True, if the snack cross a part of his own body, false else.
     *
     * @see this.isCrossed(List, boolean)
     */
    public boolean isCrossed(Snake s) {
        return this.isCrossed(s.getMove(), false);
    }

    /**
     * Method to detect if the head (the last insert into the <code>move</code> LinkedList) of the snack body
     * (represent by <code>bodyList</code>) is crossing/hitting this body.
     *
     * @param bodyList The list of body elements to test with this list of body elements.
     * @param margin   To know if the size of the second to last element is used like a margin.
     *
     * @return True, if the snack cross a part of his own body, false else.
     *
     * @see this.isCrossed(List)
     */
    private boolean isCrossed(List<Circle> bodyList, boolean margin) {
        int size = bodyList.size();

        if(size <= 2) return false;

        Circle head = bodyList.get(size - 1);

        // Margin size represent the queue of the snack head allow (size) but only the queue.
        int marginSize = 0;
        if(margin) marginSize = bodyList.get(size - 2).getRadius();  // The radius is the internal margin


        int i = 0;
        for(Circle b : this.move) {
            if(i + marginSize >= size)
                continue;

            // Distance (square of the) between an element and the head is lower than the radius of the head. So that
            // is a collision
            if(head.distanceSquare(b) < Math.pow(b.getRadius() + head.getRadius(), 2))
                return true;

            i++;
        }

        return false;
    }

    /**
     * Method to check if the Snake head hit a border wall.
     *
     * @return True if the Snake head hit a border wall, false else.
     */
    public boolean isHittingTheWall() {
        Circle head = this.getHead();

        if(head.getX() - head.getRadius() < this.limitMin.getX() ||
                   head.getY() - head.getRadius() < this.limitMin.getY()) {
            return false;
        } else if(head.getX() + head.getRadius() >= this.limitMax.getX() ||
                          head.getY() + head.getRadius() >= this.limitMax.getY()) {
            return false;
        }

        return true;
    }

    /**
     * Method to move the snack body.
     * <p>
     * <p>Every other time, the snack body increased it own size. Else, it move.</p>
     *
     * @param direction   It's the direction of the Snake (value between -1 and 1 for <code>x</code> and <code>y</code>).
     * @param size        The bonus size needed to be added to the new body element size.
     * @param wallThrough Bonus to know if the snack is allowed to through a wall.
     *
     * @return The position leave by the snack, else if the snack is increasing it own size, return null.
     */
    public Circle move(Position direction, int size, boolean wallThrough) throws CollisionException {
        return this.move(direction, size, 0, wallThrough);
    }

    /**
     * Method to move the snack body.
     * <p>
     * <p>Every other time, the snack body increased it own size. Else, it move.</p>
     *
     * @param direction   It's the direction of the Snake (value between -1 and 1 for <code>x</code> and <code>y</code>).
     * @param size        The bonus size needed to be added to the new body element size.
     * @param nextHope    The next position (movement from the previous position) of the new body element.
     * @param wallThrough Bonus to know if the snack is allowed to through a wall.
     *
     * @return The position leave by the snack, else if the snack is increasing it own size, return null.
     */
    public Circle move(Position direction, int size, int nextHope, boolean wallThrough) throws CollisionException {
        Circle nextMove = this.move.getLast().duplicate();

        // Manage the next hope position
        for(int i = 0 ; i <= nextHope ; i++) {
            nextMove.translate(direction);
        }

        // Set the size of this body part
        nextMove.setRadius(size + defaultRadius);

        this.move.add(nextMove);

        // Check if the move generate a wall hit
        if(this.isHittingTheWall()) {
            if(wallThrough) {
                this.throughWall(nextMove);
            } else {
                throw new CollisionException(this.getHead());   // TODO Optimize the Exception
            }
        }

        // Check if the move generate a collision with himself
        if(this.isCrossed())
            throw new CollisionException();

        this.isIncreased = !this.isIncreased;

        // Every other time, the body size is increased.
        if(!this.isIncreased) return this.move.pop();
        return null;
    }

    /**
     * Method to move the head of the body to the opposite position when it hit a wall.
     *
     * @param head The head of the body which is edited.
     */
    private void throughWall(Circle head) {
        if(head.getX() + head.getRadius() < this.limitMin.getX()) {
            head.setX(this.limitMax.getX() - head.getRadius() - 1);
        }
        if(head.getY() + head.getRadius() < this.limitMin.getY()) {
            head.setY(this.limitMax.getY() - head.getRadius() - 1);
        }

        if(head.getX() - head.getRadius() >= this.limitMax.getX()) {
            head.setX(this.limitMin.getX() + head.getRadius());
        }
        if(head.getY() - head.getRadius() >= this.limitMax.getY()) {
            head.setY(this.limitMin.getY() + head.getRadius());
        }
    }

    /**
     * Clean the Body element. Keep only the head of the body
     */
    public void clean() {
        Circle head = this.move.getLast();
        this.move.clear();
        this.move.add(head);
    }

    @Override
    public String toString() {
        return "Movement{move=" + move + '}';
    }
}
