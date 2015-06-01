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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represent the movement (full body) of the snack.
 * <p>
 * <p>The class offer some methods to manipulate the body (see after).</p>
 *
 * @author COLLOMB J�r�mie
 * @author GRISET  Valentin
 */
public class Movement {

    /**
     * Default radius size of a body element.
     */
    private static final int defaultRadius = 5;
    /**
     * List of <code>Body</code> elements.
     */
    private LinkedList<Body> move;
    /**
     * Flag to know if a previous move was done or not. It is used to increase the snack size (every other time).
     */
    private boolean isIncreased;

    /**
     * Constructor of the body. It is only need the start point of the movement.
     *
     * @param init The initial point of the snack movement.
     */
    public Movement(Position init) {
        this.move = new LinkedList<>();
        this.isIncreased = false;

        this.move.add(new Body(init, defaultRadius));
    }

    /**
     * Method to get an unmodifiable view of the snack body.
     *
     * @return An unmodifiable view of the <code>move</code> list.
     */
    public List<Body> getMove() {
        return Collections.unmodifiableList(this.move);
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
     * @param s Snack to test with this snack body for a collision.
     *
     * @return True, if the snack cross a part of his own body, false else.
     *
     * @see this.isCrossed(List, boolean)
     */
    public boolean isCrossed(Snack s) {
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
    private boolean isCrossed(List<Body> bodyList, boolean margin) {
        int size = bodyList.size();

        if(size <= 2) return false;

        Body head = bodyList.get(size);

        // Margin size represent the queue of the snack head allow (size) but only the queue.
        int marginSize = 0;
        if(margin) marginSize = bodyList.get(size - 1).getRadius();  // The radius is the internal margin


        int i = 0;
        for(Body b : this.move) {
            if(i + marginSize >= size) {
                continue;
            }

            // Distance (square of the) between an element and the head is lower than the radius of the head. So that
            // is a collision
            if(head.distanceSquare(b) < Math.pow(b.getRadius() + head.getRadius(), 2)) {
                return true;
            }

            i++;
        }

        return false;
    }

    /**
     * Method to move the snack body.
     * <p>
     * <p>Every other time, the snack body increased it own size. Else, it move.</p>
     *
     * @param p It's the direction of the Snack (value between -1 and 1 for <code>x</code> and <code>y</code>).
     *
     * @return The position leave by the snack, else if the snack is increasing it own size, return null.
     */
    public Body move(Position p) {
        this.move.add(this.move.getLast().duplicate().translate(p));

        this.isIncreased = !this.isIncreased;

        if(!this.isIncreased) return this.move.pop();
        return null;
    }
}
