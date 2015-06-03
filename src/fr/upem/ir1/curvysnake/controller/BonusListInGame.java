package fr.upem.ir1.curvysnake.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Class to manage the list of bonus active in the game.
 *
 * <p>The List of bonus is characterised by a <code>Position(x,y)</code>, a <code>radius</code> of action and a
 * <code>Bonus</code>.</p>
 *
 * @author COLLOMB Jérémie
 * @author GRISET  Valentin
 */
public class BonusListInGame {

    /**
     * List of Bonus associated to a position(and a radius - Circle)
     */
	private LinkedList<Entry<Circle, Bonus>> bonusPosition = new LinkedList<>();

    /**
     * Default size of the radius action
     */
	private final static int defaultRadius = 5;

    /**
     * Constructor by default to avoid warning..
     */
	public BonusListInGame() {
	}

	/**
	 * Add a Bonus to the list of Bonus
	 * 
	 * @param p Position center of the bonus
	 * @param b Bonus at the position
	 */
	public void add(Position p, Bonus b) {
		this.bonusPosition.add(new Entry<>(new Circle(p, defaultRadius), b));
	}

    /**
     * Method to return bonus hit by the snake head.
     *
     * @param head THe head of the snake for the detection.
     *
     * @return Return a LinkedList<Bonus> hited by the Snake. If no bonus was hited, return null.
     */
	public List<Entry<Circle, Bonus>> get(Circle head) {
        LinkedList<Entry<Circle, Bonus>> result = new LinkedList<>();
        Circle key;

        // If multiple bonus are close, the detection is ok
        for(Entry<Circle, Bonus> entry : this.bonusPosition) {
            key = entry.getKey();

            if(head.distanceSquare(key) <= Math.pow(head.getRadius() + key.getRadius(), 2)) {
                result.add(entry);
                this.bonusPosition.remove(entry);
            }
        }

        if(result.size() == 0)
            return null;
        else
            return result;
    }

    public Position randomPosition(Position min, Position max, BonusListInGame b, Snake...s) {
    	
    	//TODO Finir la création d'un nouveau bonus a une position aléatoire
    	Random r= new Random();
    	Position p = new Position(r.nextInt(max.getX()-min.getX())+min.getX(),r.nextInt(max.getY()-min.getY())+min.getY());
    	
    	Bonus bonus = new Bonus(p,);
    	
        return new Position(0,0);
    }
}
