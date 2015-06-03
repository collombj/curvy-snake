package fr.upem.ir1.curvysnake.controller;

import javax.naming.TimeLimitExceededException;

public class Bonus {

    private int duration = 5;

    private int speed = 0;
    private int size = 0;
    private int nextHope = 0;

    private boolean wallThrough = false;
    private boolean inverseDirection = false;

    private boolean eraseAll = false;

    public Bonus() {
        this(5);
    }

    public Bonus(int t) {
        this.duration = t;
    }

    public static Bonus speed(int speed) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.speed = speed;

        return b;
    }

    public Bonus size(int size) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.size = size;

        return b;
    }

    public Bonus nextHope(int nextHope) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.nextHope = nextHope;

        return b;
    }

    public Bonus wallThrough(boolean wallThrough) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.wallThrough = wallThrough;

        return b;
    }

    public Bonus inverseDirection(boolean inverseDirection) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.inverseDirection = inverseDirection;

        return b;
    }

    public Bonus eraseAll(boolean eraseAll) throws IllegalAccessException {
        Bonus b = new Bonus();
        b.eraseAll = eraseAll;

        return b;
    }

    public int speed() throws IllegalAccessException {
        return this.speed;
    }

    public int size() throws IllegalAccessException {
        return this.size;
    }

    public int nextHope() throws IllegalAccessException {
        return this.nextHope;
    }

    public boolean wallThrough() throws IllegalAccessException {
        return this.wallThrough;
    }

    public boolean inverseDirection() throws IllegalAccessException {
        return this.inverseDirection;
    }

    public boolean eraseAll() throws IllegalAccessException {
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

}