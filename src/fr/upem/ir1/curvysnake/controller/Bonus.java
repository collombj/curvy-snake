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
public class Bonus implements Cloneable {

    /**
     * Default duration of the Bonus.
     */
    private int duration = 200;

    /**
     * Speed bonus.
     */
    private int speed = 0;
    /**
     * Size bonus.
     */
    private int size = 0;
    /**
     * Distance between the previous head and the new head.
     */
    private int nextHope = 0;

    /**
     * Allow the Snake to through a wall, or not.
     */
    private boolean wallThrough = false;
    /**
     * Inverse button direction.
     */
    private boolean inverseDirection = false;

    /**
     * Bonus to eraseAll body (except the head) of all Snake.
     */
    private boolean eraseAll = false;

    /**
     * Default constructor. Initialize the time duration to 5.
     */
    private Bonus() {

    }

    /**
     * Constructor to duplicate a previous Bonus instance. (Only used to clone())
     *
     * @param b Bonus to duplicate.
     */
    private Bonus(Bonus b) {
        this.duration = b.duration;

        this.speed = b.speed;
        this.size = b.size;
        this.nextHope = b.nextHope;

        this.wallThrough = b.wallThrough;
        this.inverseDirection = b.inverseDirection;

        this.eraseAll = b.eraseAll;
    }

    /**
     * Method to create a new instance of Bonus with a speed attributes (only).
     *
     * @param speed The speed Bonus.
     *
     * @return A new instance of Bonus, with a speed.
     */
    public static Bonus speed(int speed) {
        Bonus b = new Bonus();
        b.speed = speed;

        return b;
    }

    /**
     * Method to create a new instance of Bonus with a size attributes (only).
     *
     * @param size The size Bonus.
     *
     * @return A new instance of Bonus, with a size.
     */
    public static Bonus size(int size) {
        Bonus b = new Bonus();
        b.size = size;

        return b;
    }

    /**
     * Method to create a new instance of Bonus with a nextHope attributes (only).
     *
     * @param nextHope The nextHope Bonus.
     *
     * @return A new instance of Bonus, with a nextHope.
     */
    public static Bonus nextHope(int nextHope) {
        Bonus b = new Bonus();
        b.nextHope = nextHope;

        return b;
    }

    /**
     * Method to create a new instance of Bonus with a wall through attributes (only).
     *
     * @param wallThrough The wall through Bonus.
     *
     * @return A new instance of Bonus, with a wall through.
     */
    public static Bonus wallThrough(boolean wallThrough) {
        Bonus b = new Bonus();
        b.wallThrough = wallThrough;

        return b;
    }

    /**
     * Method to create a new instance of Bonus with an inverse direction attributes (only).
     *
     * @param inverseDirection The inverse direction Bonus.
     *
     * @return A new instance of Bonus, with an inverse direction.
     */
    public static Bonus inverseDirection(boolean inverseDirection) {
        Bonus b = new Bonus();
        b.inverseDirection = inverseDirection;

        return b;
    }

    /**
     * Method to create a new instance of Bonus with an erase all attributes (only).
     *
     * @param eraseAll The erase all Bonus.
     *
     * @return A new instance of Bonus, with an erase all.
     */
    public static Bonus eraseAll(boolean eraseAll) {
        Bonus b = new Bonus();
        b.eraseAll = eraseAll;

        return b;
    }

    /**
     * Method to get the speed attribute value.
     *
     * @return The speed value.
     *
     * @throws IllegalAccessException If the bonus is an eraseAll bonus.
     */
    public int speed() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.speed;
    }

    /**
     * Method to get the size attribute value.
     *
     * @return The size value.
     *
     * @throws IllegalAccessException If the bonus is an eraseAll bonus.
     */
    public int size() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.size;
    }

    /**
     * Method to get the nextHope attribute value.
     *
     * @return The nextHope value.
     *
     * @throws IllegalAccessException If the bonus is an eraseAll bonus.
     */
    public int nextHope() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.nextHope;
    }

    /**
     * Method to get the wallThrough attribute value.
     *
     * @return The wallThrough value.
     *
     * @throws IllegalAccessException If the bonus is an eraseAll bonus.
     */
    public boolean wallThrough() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.wallThrough;
    }

    /**
     * Method to get the inverseDirection attribute value.
     *
     * @return The inverseDirection value.
     *
     * @throws IllegalAccessException If the bonus is an eraseAll bonus.
     */
    public boolean inverseDirection() throws IllegalAccessException {
        if(this.eraseAll) throw new IllegalAccessException();
        return this.inverseDirection;
    }

    /**
     * Method to get the eraseAll attribute value.
     *
     * @return The eraseAll value.
     */
    public boolean eraseAll() {
        return this.eraseAll;
    }

    /**
     * Decrement time of this Bonus.
     *
     * @throws TimeLimitExceededException If a Bonus have it own time under 0.
     */
    public void decrement() throws TimeLimitExceededException {
        this.duration--;

        if(this.duration <= 0) {
            throw new TimeLimitExceededException();
        }
    }

    /**
     * Method to get the duration of this Bonus.
     *
     * @return The duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Method to set the duration of a Bonus.
     *
     * @param duration The new duration
     *
     * @return The Bonus to offer a chain action.
     */
    public Bonus setDuration(int duration) {
        this.duration = duration;

        return this;
    }

    public boolean isA(BonusAvailable bonus) {
        if(speed != bonus.get().speed) return false;
        if(size != bonus.get().size) return false;
        if(nextHope != bonus.get().nextHope) return false;
        if(wallThrough != bonus.get().wallThrough) return false;
        if(inverseDirection != bonus.get().inverseDirection) return false;
        return eraseAll == bonus.get().eraseAll;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Bonus)) return false;

        Bonus bonus = (Bonus) o;

        if(speed != bonus.speed) return false;
        if(size != bonus.size) return false;
        if(nextHope != bonus.nextHope) return false;
        if(wallThrough != bonus.wallThrough) return false;
        if(inverseDirection != bonus.inverseDirection) return false;
        return eraseAll == bonus.eraseAll;

    }

    @Override
    public int hashCode() {
        int result = speed;
        result = 31 * result + size;
        result = 31 * result + nextHope;
        result = 31 * result + (wallThrough ? 1 : 0);
        result = 31 * result + (inverseDirection ? 1 : 0);
        result = 31 * result + (eraseAll ? 1 : 0);
        return result;
    }

    /**
     * Clone method to duplicate the Bonus.
     *
     * @return The Bonus duplicated.
     */
    @Override
    public Object clone() {
        Object b = new Bonus(this);
        return b;
    }

    /**
     * Method to display the Bonus. TEST ONLY
     *
     * @return The String representation of the Bonus.
     */
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