package fr.upem.ir1.curvysnake.controller;

import javax.naming.TimeLimitExceededException;

/**
 * Class which is a representation of a bonus.
 * <p>
 * <p>The different types of bonus are:</p>
 * <ol>
 * <li>Speed (accelerate or slower)</li>
 * <li>Size (increase or reduce the size)</li>
 * <li>Next Hope space (the hope size between two body elements)</li>
 * <li>Wall through</li>
 * <li>Inverse user direction</li>
 * <li>Erase all body element</li>
 * </ol>
 */
public class Bonus {

    /**
     * Default duration of the
     */
    private int duration = 5;

    private int speed = 0;
    private int size = 0;
    private int nextHope = 0;

    private boolean wallThrough = false;
    private boolean inverseDirection = false;

    private boolean eraseAll = false;

    public Bonus() {
        this.duration = 5;
    }

    public Bonus(Bonus b) {
        this.duration = b.duration;

        this.speed = b.speed;
        this.size = b.size;
        this.nextHope = b.nextHope;

        this.wallThrough = b.wallThrough;
        this.inverseDirection = b.inverseDirection;

        this.eraseAll = b.eraseAll;
    }

    public static Bonus speed(int speed) {
        Bonus b = new Bonus();
        b.speed = speed;

        return b;
    }

    public static Bonus size(int size) {
        Bonus b = new Bonus();
        b.size = size;

        return b;
    }

    public static Bonus nextHope(int nextHope) {
        Bonus b = new Bonus();
        b.nextHope = nextHope;

        return b;
    }

    public static Bonus wallThrough(boolean wallThrough) {
        Bonus b = new Bonus();
        b.wallThrough = wallThrough;

        return b;
    }

    public static Bonus inverseDirection(boolean inverseDirection) {
        Bonus b = new Bonus();
        b.inverseDirection = inverseDirection;

        return b;
    }

    public static Bonus eraseAll(boolean eraseAll) {
        Bonus b = new Bonus();
        b.eraseAll = eraseAll;

        return b;
    }

    public Bonus duplicate() {
        return new Bonus(this);
    }

    public int speed() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.speed;
    }

    public int size() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.size;
    }

    public int nextHope() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.nextHope;
    }

    public boolean wallThrough() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.wallThrough;
    }

    public boolean inverseDirection() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.inverseDirection;
    }

    public boolean eraseAll() {
        return this.eraseAll;
    }

    public void decrement() throws TimeLimitExceededException {
        this.duration--;

        if(this.duration <= 0) {
            throw new TimeLimitExceededException();
        }
    }

    public int getDuration() {
        return duration;
    }

    public Bonus setDuration(int duration) {
        this.duration = duration;

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Bonus)) return false;

        Bonus bonus = (Bonus) o;

        if(duration != bonus.duration) return false;
        if(speed != bonus.speed) return false;
        if(size != bonus.size) return false;
        if(nextHope != bonus.nextHope) return false;
        if(wallThrough != bonus.wallThrough) return false;
        if(inverseDirection != bonus.inverseDirection) return false;
        return eraseAll == bonus.eraseAll;

    }

    @Override
    public int hashCode() {
        int result = duration;
        result = 31 * result + speed;
        result = 31 * result + size;
        result = 31 * result + nextHope;
        result = 31 * result + (wallThrough ? 1 : 0);
        result = 31 * result + (inverseDirection ? 1 : 0);
        result = 31 * result + (eraseAll ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bonus{" +
                       "duration=" + duration +
                       ", speed=" + speed +
                       ", size=" + size +
                       ", nextHope=" + nextHope +
                       ", wallThrough=" + wallThrough +
                       ", inverseDirection=" + inverseDirection +
                       ", eraseAll=" + eraseAll +
                       '}';
    }
}