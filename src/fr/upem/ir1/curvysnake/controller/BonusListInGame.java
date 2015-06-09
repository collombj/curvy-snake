package fr.upem.ir1.curvysnake.controller;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Class to manage the list of bonus active in the game.
 * <p>
 * <p>The List of bonus is characterised by a <code>Position(x,y)</code>, a <code>radius</code> of action and a
 * <code>Bonus</code>.</p>
 *
 * @author COLLOMB J�r�mie
 * @author GRISET  Valentin
 */
public class BonusListInGame {

    /**
     * List of Bonus associated to a position(and a radius - Ellipse2D.Float)
     */
    private final LinkedList<Entry<Shape, Bonus>> bonusPosition;

    /**
     * Default size of the radius action
     */
    private final static int DEFAULT_DIAMETER = Movement.defaultDiameter;

    /**
     * Constructor by default to avoid warning..
     */
    public BonusListInGame() {
        this.bonusPosition = new LinkedList<>();
    }

    public BonusListInGame(BonusListInGame b) {
        this.bonusPosition = b.bonusPosition;
    }

    /**
     * Add a Bonus to the list of Bonus
     *
     * @param position The Bonus Ellipse
     * @param bonus    Bonus at the position
     */
    public void add(Shape position, Bonus bonus) {
        this.bonusPosition.add(new Entry<>(position, bonus));
    }

    /**
     * Method to return bonus hit by the snake head.
     *
     * @param head THe head of the snake for the detection.
     *
     * @return Return a LinkedList<Bonus> hit by the Snake. If no bonus has been hit, return null.
     */
    public List<Entry<Shape, Bonus>> get(Shape head) {
        LinkedList<Entry<Shape, Bonus>> result = new LinkedList<>();
        Shape key;

        // If multiple bonus are close, the detection is ok
        for(Entry<Shape, Bonus> entry : this.bonusPosition) {
            key = entry.getKey();

            if(head.intersects(key.getBounds2D().getX(), key.getBounds2D().getY(),
                               key.getBounds2D().getWidth(), key.getBounds2D().getHeight())) {
                result.add(entry);
                this.bonusPosition.remove(entry);
            }
        }

        if(result.size() == 0)
            return null;
        else
            return result;
    }

    /**
     * Generate a random position adn a random Bonus into the list of available Bonus.
     *
     * @return The position of the new Bonus
     */
    public Shape random() {
        Random r = new Random();

        int x;
        int y;
        Shape position;

        do {
            x = r.nextInt(Snake.getGameSize().width) + Snake.getGameSize().x;
            y = r.nextInt(Snake.getGameSize().height) + Snake.getGameSize().y;

            position = new Ellipse2D.Float(x, y, DEFAULT_DIAMETER, DEFAULT_DIAMETER);
        } while(this.intersects(position) || !Snake.positionIsFree(position));

        this.add(position, BonusAvailable.random());

        return position;
    }

    /**
     * Method to detect a collision between a Bonus in game and a position.
     *
     * @param shape The position to test with all Bonus in game.
     *
     * @return Tru if a collision is detected, false else.
     */
    public boolean intersects(Shape shape) {
        for(Entry<Shape, Bonus> entry : this.bonusPosition) {
            if(entry.getKey().intersects(shape.getBounds2D().getX(), shape.getBounds2D().getY(),
                                         shape.getBounds2D().getWidth(), shape.getBounds2D().getHeight()))
                return true;
        }

        return false;
    }

    /**
     * ForEach Method alias for the List of Bonus in Game
     *
     * @param action The Consumer to apply to each element pf the Bonus list.
     */
    public void forEach(Consumer<? super Entry<Shape, Bonus>> action) {
        this.bonusPosition.forEach(action);
    }
}
