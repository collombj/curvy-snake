package fr.upem.ir1.curvysnake.controller;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Class to manage the list of bonus active in the game.
 * <p>
 * <p>The List of bonus is characterised by an <code>Rectangle</code> (or an Ellipse) and a
 * <code>Bonus</code>.</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 */
public class BonusListInGame {

    /**
     * List of Bonus associated to an Rectangle
     */
    private final LinkedList<Entry<RectangularShape, Bonus>> bonusPosition;

    /**
     * Default size of the radius action
     */
    private final static int DEFAULT_DIAMETER = Movement.defaultDiameter * 5;

    /**
     * Constructor by default to avoid warning..
     */
    public BonusListInGame() {
        this.bonusPosition = new LinkedList<>();
    }

    /**
     * Constructor to duplicate an existing Bonus.
     *
     * @param b The Bonus to duplicate.
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
    public void add(RectangularShape position, Bonus bonus) {
        this.bonusPosition.add(new Entry<>(position, bonus));
    }

    /**
     * Generate a random position and a random Bonus into the list of available Bonus.
     *
     * @return The position of the new Bonus
     */
    public Entry<RectangularShape, Bonus> random() {
        Random r = new Random();

        if(r.nextInt(1000) != 48 || this.bonusPosition.size() >= 10)
            return null;

        int x;
        int y;
        RectangularShape position;

        do {
            x = r.nextInt((int) Snake.getGameSize().getWidth() - DEFAULT_DIAMETER) + (int) Snake.getGameSize().getX();
            y = r.nextInt((int) Snake.getGameSize().getHeight() - DEFAULT_DIAMETER) + (int) Snake.getGameSize().getY();

            position = new Ellipse2D.Float(x, y, DEFAULT_DIAMETER, DEFAULT_DIAMETER);
        } while(this.intersects(position) || !Snake.getGameSize().intersects(position.getBounds2D()) ||
                        !Snake.positionIsFree(position));

        this.add(position, BonusAvailable.random());
        return (Entry<RectangularShape, Bonus>) this.bonusPosition.getLast().clone();
    }

    /**
     * Method to detect a collision between a Bonus in game and a position.
     *
     * @param shape The position to test with all Bonus in game.
     *
     * @return Tru if a collision is detected, false else.
     */
    public boolean intersects(RectangularShape shape) {
        for(Entry<RectangularShape, Bonus> entry : this.bonusPosition) {
            if(shape.intersects(entry.getKey().getBounds2D()))
                return true;
        }

        return false;
    }

    /**
     * Itertor method to create an iterator on the List of Bonus
     * @return
     */
    public Iterator<Entry<RectangularShape, Bonus>> iterator() {
        return this.bonusPosition.iterator();
    }
}
