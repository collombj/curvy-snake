package fr.upem.ir1.curvysnake.controller.core;

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
    private final LinkedList<Entry<Ellipse2D.Float, Bonus>> bonusPosition;

    /**
     * Default size of the radius action
     */
    private final static int DEFAULT_DIAMETER = 10;

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
    public void add(Ellipse2D.Float position, Bonus bonus) {
        this.bonusPosition.add(new Entry<>(position, bonus));
    }

    /**
     * Method to return bonus hit by the snake head.
     *
     * @param head THe head of the snake for the detection.
     *
     * @return Return a LinkedList<Bonus> hited by the Snake. If no bonus was hited, return null.
     */
    public List<Entry<Ellipse2D.Float, Bonus>> get(Ellipse2D.Float head) {
        LinkedList<Entry<Ellipse2D.Float, Bonus>> result = new LinkedList<>();
        Ellipse2D.Float key;

        // If multiple bonus are close, the detection is ok
        for(Entry<Ellipse2D.Float, Bonus> entry : this.bonusPosition) {
            key = entry.getKey();

            if(head.intersects(key.x, key.y, key.width, key.height)) {
                result.add(entry);
                this.bonusPosition.remove(entry);
            }
        }

        if(result.size() == 0)
            return null;
        else
            return result;
    }

    public Ellipse2D.Float randomPosition() {
        Random r = new Random();

        int x;
        int y;
        Ellipse2D.Float position = null;

        do {
            x = r.nextInt(Snake.getGameSize().width) + Snake.getGameSize().x;
            y = r.nextInt(Snake.getGameSize().height) + Snake.getGameSize().y;

            position = new Ellipse2D.Float(x, y, DEFAULT_DIAMETER, DEFAULT_DIAMETER);
        } while(this.intersects(position) || !Snake.positionIsFree(position));

        this.add(position, BonusAvailable.random());

        return position;
    }

    public boolean intersects(Ellipse2D.Float aFloat) {
        for(Entry<Ellipse2D.Float, Bonus> entry : this.bonusPosition) {
            if(entry.getKey().intersects(aFloat.x, aFloat.y, aFloat.width, aFloat.height))
                return true;
        }

        return false;
    }

    public void forEach(Consumer<? super Entry<Ellipse2D.Float, Bonus>> action) {
        this.bonusPosition.forEach(action);
    }
}
