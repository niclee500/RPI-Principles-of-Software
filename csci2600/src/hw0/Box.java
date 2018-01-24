/**
 * This is part of HW0: Environment Setup and Java Introduction.
 */
package hw0;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a container can be used to contain Balls. The key
 * difference between a BallContainer and a Box is that a Box has a
 * finite volume. Once a box is full, a client cannot put in more Balls.
 */
public class Box implements Iterable<Ball> {

    /**
     * ballContainer is used to internally store balls for this Box
     */
    private BallContainer ballContainer;
    private double maxV;

    /**
     * Constructor that creates a new box.
     * @param maxVolume Total volume of balls that this box can contain.
     */
    public Box(double maxVolume) {
        ballContainer = new BallContainer();
        maxV = maxVolume;
    }

    /**
     * Implements the Iterable interface for this box.
     * @return an Iterator over the Ball objects contained
     * in this box.
     */
    public Iterator<Ball> iterator() {
    	return ballContainer.iterator();
    	
    	/* Ignore this:
    	 * Iterator<Ball> nItr = new Iterator<Ball>() {
    		private Iterator<Ball> itr;
    		private Ball start, end;
    		public Iterator<Ball>(){ itr = ballContainer.iterator(); }
    		public boolean hasNext(){ return false; }
    		public Ball next(){ return new Ball(2); }
    		public void remove(){}
    	};*/
    }
    
    /**
     * This method is used to add Ball objects to this box of
     * finite volume.  The method returns true if a ball is
     * successfully added to the box, i.e., ball is not already in the
     * box and if the box is not already full; and it returns false,
     * if ball is already in the box or if the box is too full to
     * contain the new ball.
     * @param b Ball to be added.
     * @return true if ball was successfully added to the box,
     * i.e. ball is not already in the box and if the box is not
     * already full. Returns false, if ball is already in the box or
     * if the box is too full to contain the new ball.
     */
    public boolean add(Ball b) {
        if( ballContainer.contains(b) ){ return false; }
        else{
        	if( (b.getVolume() + getVolume()) > maxV ){ return false; }
        	else{
        		ballContainer.add(b);
        		return true;
        	}
        }
    }

    /**
     * This method returns an iterator that returns all the balls in
     * this box in ascending size, i.e., return the smallest Ball
     * first, followed by Balls of increasing size.
     * @return an iterator that returns all the balls in this box in
     * ascending size.
     */
    public Iterator<Ball> getBallsFromSmallest() {
    	Iterator<Ball> itr = iterator();
        List<Ball> orderedBalls = new LinkedList<Ball>();
        while( itr.hasNext() ){
    		Ball b = itr.next();
    		orderedBalls.add(b);
    	}
        Collections.sort(orderedBalls, new BallComparator());
        return Collections.unmodifiableList(orderedBalls).iterator();
    }
    
    static class BallComparator implements Comparator<Ball>{
    	public int compare(Ball b1, Ball b2){
    		double vol1 = b1.getVolume();
    		double vol2 = b2.getVolume();
    		if(vol1 == vol2){
    			return 0;
    		} else if(vol1 < vol2){
    			return -1;
    		} else{
    			return 1;
    		}
    	}
    }

    /**
     * Removes a ball from the box. This method returns
     * <tt>true</tt> if ball was successfully removed from the
     * container, i.e. ball is actually in the box. You cannot
     * remove a Ball if it is not already in the box and so ths
     * method will return <tt>false</tt>, otherwise.
     * @param b Ball to be removed.
     * @return true if ball was successfully removed from the box,
     * i.e. ball is actually in the box. Returns false, if ball is not
     * in the box.
     */
    public boolean remove(Ball b) {
        return ballContainer.remove(b);
    }

    /**
     * Each Ball has a volume. This method returns the total volume of
     * all the Balls in the box.
     * @return the volume of the contents of the box.
     */
    public double getVolume() {
       return ballContainer.getVolume();
    }

    /**
     * Returns the number of Balls in this box.
     * @return the number of Balls in this box.
     */
    public int size() {
        return ballContainer.size();
    }

    /**
     * Empties the box, i.e. removes all its contents.
     */
    public void clear() {
        ballContainer.clear();
    }

    /**
     * This method returns <tt>true</tt> if this box contains
     * the specified Ball. It will return <tt>false</tt> otherwise.
     * @param b Ball to be checked if its in box
     * @return true if this box contains the specified Ball. Returns
     * false, otherwise.
     */
    public boolean contains(Ball b) {
        return ballContainer.contains(b);
    }

}
