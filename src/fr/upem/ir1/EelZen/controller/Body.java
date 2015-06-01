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

/**
 * This class represent an element of the body of the snack. An element is represent by a circle and a center
 * (Position).
 * <p>
 * <p>The class is an extension of <code>Position</code>, with a radius.</p>
 *
 * @author COLLOMB J�r�mie
 * @author GRISET  Valentin
 * @see Position
 */
public class Body extends Position {

    /**
     * <code>Radius</code> of the circle.
     */
    private int radius;

    /**
     * Constructor to construct a new body element.
     *
     * @param x      The <code>x</code> position of the center of the circle.
     * @param y      The <code>y</code> position of the center of the circle.
     * @param radius The <code>radius</code> of the circle.
     */
    public Body(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    /**
     * Constructor to construct a new body element.
     *
     * @param p      The position of the center of the circle.
     * @param radius The <code>radius</code> of the circle.
     */
    public Body(Position p, int radius) {
        super(p);
        this.radius = radius;
    }

    /**
     * Constructor to create (duplicate) a new instance of a Body element.
     *
     * @param b Body element to duplicate into a new instance.
     */
    public Body(Body b) {
        this(b.getX(), b.getY(), b.getRadius());
    }

    /**
     * Duplicator method to duplicate the instance of an instance of the class.
     *
     * @return The copy of the element.
     *
     * @see Body(Body)
     */
    @Override
    public Body duplicate() {
        return new Body(this);
    }

    /**
     * Method to move a body element with Position <code>p</code> in translation information.
     *
     * @param p The position used to translate the element.
     *
     * @return The actual instance of the object (Useful when we chain action).
     */
    public Body translate(Position p) {
        this.setX(this.getX() + p.getX());
        this.setY(this.getY() + p.getY());

        return this;
    }

    /**
     * Getter to obtain the <code>radius</code> value of the element.
     *
     * @return <code>Radius</code> value of the element.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Setter to set the <code>radius</code> value of the element.
     *
     * @param radius The <code>radius</code> value to set.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Method to detect if a Position <code>p</code> is inside the element.
     *
     * @param p The position to test.
     *
     * @return True, if the Position is inside the element, false else.
     */
    public boolean isInside(Position p) {
        return Math.pow(p.getX() - this.getX(), 2) + Math.pow(p.getY() - this.getY(), 2) < Math.pow(radius, 2);
    }

    /**
     * Method equals
     *
     * @param o Object to compare
     *
     * @return true if both object are equals, false else.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Body)) return false;
        if(!super.equals(o)) return false;

        Body body = (Body) o;

        return radius == body.radius;
    }

    /**
     * Method hashcode
     *
     * @return The hashcode of the element
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + radius;
        return result;
    }

    @Override
    public String toString() {
        return "Body{x=" + getX() + ", y=" + getY() + ", radius=" + radius + '}';
    }
}
