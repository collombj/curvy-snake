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

/**
 * This class represent an element of the body of the snake. An element is represent by a circle and a center
 * (Position).
 * <p>
 * <p>The class is an extension of <code>Position</code>, with a radius.</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 * @see Position
 */
public class Circle extends Position {

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
    public Circle(int x, int y, int radius) {
        super(x, y);
        this.radius = radius;
    }

    /**
     * Constructor to construct a new body element.
     *
     * @param p      The position of the center of the circle.
     * @param radius The <code>radius</code> of the circle.
     */
    public Circle(Position p, int radius) {
        super(p);
        this.radius = radius;
    }

    /**
     * Constructor to create (duplicate) a new instance of a Circle element.
     *
     * @param b Circle element to duplicate into a new instance.
     */
    public Circle(Circle b) {
        this(b.getX(), b.getY(), b.getRadius());
    }

    /**
     * Duplicator method to duplicate the instance of an instance of the class.
     *
     * @return The copy of the element.
     *
     * @see Circle(Circle)
     */
    @Override
    public Circle duplicate() {
        return new Circle(this);
    }

    /**
     * Method to move a body element with Position <code>p</code> in translation information.
     *
     * @param p The position used to translate the element.
     *
     * @return The actual instance of the object (Useful when we chain action).
     */
    public Circle translate(Position p) {
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
     *
     * @return This Circle to chain action, if necessary.
     */
    public Circle setRadius(int radius) {
        this.radius = radius;

        return this;
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
        if(!(o instanceof Circle)) return false;
        if(!super.equals(o)) return false;

        Circle circle = (Circle) o;

        return radius == circle.radius;
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
        return "Circle{x=" + getX() + ", y=" + getY() + ", radius=" + radius + '}';
    }
}
