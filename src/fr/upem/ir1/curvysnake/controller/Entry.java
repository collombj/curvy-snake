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

import java.util.Map;

/**
 * Class to manage an association of to element.
 *
 * <p>It is a simple association of a <code>Key</code> and a <code>Value</code>. It is a simplified version of an
 * element of an HashMap.</p>
 *
 * <p><strong>This class allow you to edit only the Value field.</strong></p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 *
 * @see <a href="http://stackoverflow.com/a/3110644">Source example - Jesper</a>
 */
public final class Entry<K, V> implements Map.Entry<K, V> {
    /**
     * The Key of the element.
     */
    private final K key;

    /**
     * The Value associated to the Key.
     */
    private V value;

    /**
     * Constructor of a new element. Create a couple of Key-Value.
     *
     * @param key The Key of the element.
     * @param value The Value associated to the element Key.
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Method to get the Key associated to this element.
     *
     * @return The Key value.
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * Method to get the Value associated to this element Key.
     *
     * @return THe Value of this element.
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Method to set the Value of an associated Key element.
     *
     * @param value The Value to set to this element.
     *
     * @return The Value set.
     */
    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    /**
     * Method to test if an element is equals to this element.
     *
     * @param o The object to test with this element.
     *
     * @return True if both elements are equals, false else.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Entry)) return false;

        Entry<?, ?> entry = (Entry<?, ?>) o;

        if(!key.equals(entry.key)) return false;
        return value.equals(entry.value);

    }

    /**
     * Method to calculate the HashCode the element.
     *
     * @return The HashCode value of the element.
     */
    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" + "key=" + key + ", value=" + value + '}';
    }
}