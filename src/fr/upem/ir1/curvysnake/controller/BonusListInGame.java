package fr.upem.ir1.curvysnake.controller;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
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
 * @author COLLOMB Jérémie
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

    /**
     * Constructor to recreate a new BonusList from an existing one.
     *
     * @param b The BonusList to duplicate.
     */
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
     * @return Return a LinkedList<Bonus> hited by the Snake. If no bonus was hited, return null.
     */
    public List<Entry<Shape, Bonus>> get(Ellipse2D.Float head) {
        LinkedList<Entry<Shape, Bonus>> result = new LinkedList<>();
        Shape key;

        // If multiple bonus are close, the detection is ok
        for(Entry<Shape, Bonus> entry : this.bonusPosition) {
            key = entry.getKey();

            if(head.intersects(key.getBounds().x, key.getBounds().y, key.getBounds().width, key.getBounds().height)) {
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
     * Method to create randomly (position and bonus type).
     *
     * @return The position of the new Bonus.
     */
    public Shape randomPosition() {
        Random r = new Random();

        int x;
        int y;
        Shape position = null;

       /* do {
            x = r.nextInt(Snake.getGameSize().width) + Snake.getGameSize().x;
            y = r.nextInt(Snake.getGameSize().height) + Snake.getGameSize().y;

            position = new Ellipse2D.Float(x, y, DEFAULT_DIAMETER, DEFAULT_DIAMETER);
        } while(this.intersects(position) || !Snake.positionIsFree(position));*/

        this.add(position, BonusAvailable.random());

        return position;
    }

    /**
     * Method to detect if two Bonus are intersects.
     *
     * @param positionShape The Ellipse to test with the BonusList.
     *
     * @return True if an intersection is realized, false else.
     */
    public boolean intersects(Shape positionShape) {
        for(Entry<Shape, Bonus> entry : this.bonusPosition) {
            if(entry.getKey().intersects(positionShape.getBounds().x, positionShape.getBounds().y, positionShape.getBounds().width, positionShape.getBounds().height))
                return true;
        }

        return false;
    }

    public Iterator<Entry<Shape, Bonus>> iterator() {
        return this.bonusPosition.iterator();
    }
}
