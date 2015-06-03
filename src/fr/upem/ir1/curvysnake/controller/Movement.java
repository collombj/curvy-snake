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

    private Position limitMin;
    private Position limitMax;

    /**
     * Constructor of the body. It is only need the start point of the movement.
     *
     * @param init The initial point of the snack movement.
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

        Circle head = bodyList.get(size-1);

        // Margin size represent the queue of the snack head allow (size) but only the queue.
        int marginSize = 0;
        if(margin) marginSize = bodyList.get(size-2).getRadius();  // The radius is the internal margin


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

    public boolean isHittingTheWall() {
        
    	Circle head= this.getHead();
    	
    	if(
    			head.getX()-head.getRadius()<this.limitMin.getX() || 
    			head.getY()-head.getRadius()<this.limitMin.getY()
    		){
    		return false;
    	}
    	if(
    			head.getX()+head.getRadius()>=this.limitMax.getX() ||
    			head.getY()+head.getRadius()>=this.limitMax.getY()
    		){
    		return false;
    	}	
        return true;
    }

    /**
     * Method to move the snack body.
     * <p>
     * <p>Every other time, the snack body increased it own size. Else, it move.</p>
     *
     * @param p It's the direction of the Snake (value between -1 and 1 for <code>x</code> and <code>y</code>).
     *
     * @return The position leave by the snack, else if the snack is increasing it own size, return null.
     */
    public Circle move(Position p, int size, boolean wallThrough) throws CollisionException {
        return this.move(p, size, 0, wallThrough);
    }

    public Circle move(Position p, int size, int nextHope, boolean wallThrough) throws CollisionException {
        Circle nextMove = this.move.getLast().duplicate();

        // Manage the next hope position
        for(int i = 0 ; i <= nextHope ; i++) {
            nextMove.translate(p);
        }

        // Set the size of this body part
        nextMove.setRadius(size + defaultRadius);

        this.move.add(nextMove);
        
       
        	if(this.isHittingTheWall()){
        		 if(wallThrough){
        			 this.throughWall(nextMove);
        		 }
        		 else{
                     throw new CollisionException(this.getHead());
        		 }
                
            }        	
        

        if(this.isCrossed())
            throw new CollisionException();

        this.isIncreased = !this.isIncreased;

        if(!this.isIncreased) return this.move.pop();
        return null;
    }
    private void throughWall(Circle head){
    	
    	if(head.getX()+head.getRadius()<this.limitMin.getX()){
    		head.setX(this.limitMax.getX()-head.getRadius()-1);
    	}
		if(head.getY()+head.getRadius()<this.limitMin.getY()){
			head.setY(this.limitMax.getY()-head.getRadius()-1);		
		    	}
		if(head.getX()-head.getRadius()>=this.limitMax.getX()){
			head.setX(this.limitMin.getX()+head.getRadius());
		}
		if(head.getY()-head.getRadius()>=this.limitMax.getY()){
			head.setY(this.limitMin.getY()+head.getRadius());
		}				
    	
    }

    @Override
    public String toString() {
        return "Movement{move=" + move + '}';
    }
}
