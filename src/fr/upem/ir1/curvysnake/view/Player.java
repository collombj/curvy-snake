package fr.upem.ir1.curvysnake.view;

import fr.upem.ir1.curvysnake.controller.Snake;

import java.awt.*;

public class Player {

    private final Snake player;
    private final Color color;

    private int score;

    private boolean alive = true;

    public Player(Snake player, Color color) {
        super();
        this.player = player;
        this.color = color;
        this.score = 0;
    }

    private void increaseScore() {
        this.score++;
    }

    public Snake getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }


    public int getScore() {
        return score;
    }

    public boolean isAlive() { return this.alive; }

    public void kill() { this.alive = false; }


}
