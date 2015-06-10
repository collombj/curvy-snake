package fr.upem.ir1.curvysnake.view;

import fr.upem.ir1.curvysnake.controller.Snake;

import java.awt.*;

public class Player {

	/**
	 * player is a snake with a position and an alpha
	 */
    private final Snake player;
    /**
     * color is the color of the snake during the game
     */
    private final Color color;

    /**
     * score is the value of the player score after a game
     */
    private int score;

    /**
     * alive is the status of the player
     */
    private boolean alive = true;

    /**
     * 	Constructor of the class. Initialize the initial Snake and color
     * 
     * @param player
     * @param color
     */
    public Player(Snake player, Color color) {
        super();
        this.player = player;
        this.color = color;
        this.score = 0;
    }
    
    /**
     * Increase the value of the player's score
     */
    private void increaseScore() {
        this.score++;
    }
    
    /**
     * 
     * @return the status of the snake
     */
    public Snake getPlayer() {
        return player;
    }

    /**
     * 
     * @return the color of the snake
     */
    public Color getColor() {
        return color;
    }

    /**
     * 
     * @return s
     */
    public int getScore() {
        return score;
    }

    /**
     * 
     * 
     * @return the status of the player alive:true, dead:false
     */
    public boolean isAlive() { return this.alive; }

    /**
     * Kill the snake
     */
    public void kill() { this.alive = false; }


}
