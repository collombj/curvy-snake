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

package fr.upem.ir1.curvysnake.controller.core;

/**
 * This class consist to manipulate a position. The position is caracterized by an <code>x</code> and an <code>y</code>.
 * <p>
 * <p>The class offer only basics method (assessors and constructors).</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 */
public class Position {

    /**
     * <code>X</code> position for the element.
     */
    private int x;

    /**
     * <code>Y</code> position for the element.
     */
    private int y;

    /**
     * Constructor to construct a new Position with <code>x</code> and <code>y</code> in attributes.
     *
     * @param x The position in X of the element.
     * @param y The position in Y of the element.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor to create (duplicate) a new instance of a Position.
     *
     * @param p Position to duplicate into a new instance.
     */
    public Position(Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    /**
     * Duplicator method to duplicate the instance of an instance of the class.
     *
     * @return The copy of the element.
     *
     * @see Position(Position)
     */
    public Position duplicate() {
        return new Position(this);
    }

    /**
     * Getter to obtain the <code>x</code> value of the position.
     *
     * @return <code>X</code> value of the position.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter to set the <code>x</code> value of the position.
     *
     * @param x Value of the position in <code>x</code>.
     *
     * @return This Circle to chain action, if necessary.
     */
    public Position setX(int x) {
        this.x = x;

        return this;
    }

    /**
     * Getter to obtain the <code>y</code> value of the position.
     *
     * @return <code>Y</code> value of the position.
     */
    public int getY() {
        return y;
    }

    /**
     * Setter to set the <code>y</code> value of the position.
     *
     * @param y Value of the position in <code>x</code>.
     *
     * @return This Circle to chain action, if necessary.
     */
    public Position setY(int y) {
        this.y = y;

        return this;
    }

    /**
     * Method to calculate the square of the distance between two point.
     * <p>
     * <p><strong>Warning:</strong> The return value is the <strong>square</strong> of the distance</p>
     *
     * @param p The other point uses for calculate the square of distance.
     *
     * @return The square of the distance between this point and the parameter point <code>p</code>.
     */
    public double distanceSquare(Position p) {
        return Math.abs(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2));
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
        if(!(o instanceof Position)) return false;

        Position position = (Position) o;

        if(x != position.x) return false;
        return y == position.y;
    }

    /**
     * Method hashcode
     *
     * @return The hashcode of the element
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
