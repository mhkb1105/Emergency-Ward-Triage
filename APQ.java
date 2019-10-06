package A2Q2;

import java.util.*;

/**
 * Adaptible priority queue using location-aware entries in a min-heap, based on
 * an extendable array.  The order in which equal entries were added is preserved.
 *
 * @author jameselder
 * @param <E> The entry type.
 */
public class APQ<E> {

    private final ArrayList<E> apq; //will store the min heap
    private final Comparator<E> comparator; //to compare the entries
    private final Locator<E> locator;  //to locate the entries within the queue

    /**
     * Constructor
     * @param comparator used to compare the entries
     * @param locator used to locate the entries in the queue
     * @throws NullPointerException if comparator or locator parameters are null
     */
    public APQ(Comparator<E> comparator, Locator<E> locator) throws NullPointerException {
        if (comparator == null || locator == null) {
            throw new NullPointerException();
        }
        apq = new ArrayList<>();
        apq.add(null); //dummy value at index = 0
        this.comparator = comparator;
        this.locator = locator;
    }

    /**
     * Inserts the specified entry into this priority queue.
     *
     * @param e the entry to insert
     * @throws NullPointerException if parameter e is null
     */
    public void offer(E e) throws NullPointerException {
    	if ( e == null){
    		throw new NullPointerException();
    	}
    		apq.add(e);
    		locator.set(e, this.size());
        	upheap(this.size());
    }

   /**
     * Removes the entry at the specified location.
     *
     * @param pos the location of the entry to remove
     * @throws BoundaryViolationException if pos is out of range
     */
    public void remove(int pos) throws BoundaryViolationException {
    	if ((pos > this.size()) || (pos <= 0)){
			throw new BoundaryViolationException();
    	}
    			
    	E last = apq.remove(this.size());
    	if(pos != this.size() + 1){
    		apq.set(pos, last);
        	downheap(pos);
    	}
    }

   /**
     * Removes the first entry in the priority queue.
     */
    public E poll() {
    	E first = apq.get(1);
    	apq.remove(first);
        return first;
    }

  /**
     * Returns but does not remove the first entry in the priority queue.
     */
     public E peek() {
        if (isEmpty()) {
            return null;
        }
        return apq.get(1);
    }

   public boolean isEmpty() {
        return (size() == 0); 
    }

    public int size() {
        return apq.size() - 1; //dummy node at location 0
    }


    /**
     * Shift the entry at pos upward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void upheap(int pos) {
    	 if (pos < 2){
         	return;
         }
     	int parentpos = pos / 2;
        
         E parent = apq.get(parentpos);
         E child = apq.get(pos);
         
         while( comparator.compare(parent, child) == 1 ){
         	this.swap(parentpos, pos);
         	child = apq.get(pos);
         }
         
    }

    /**
     * Shift the entry at pos downward in the heap to restore the minheap property
     * @param pos the location of the entry to move
     */
    private void downheap(int pos) {
    	while (2 * pos + 1 < apq.size()){
    		int leftbranch = 2 * pos;
    		int rightbranch = 2 * pos + 1;
    		
    		if (leftbranch < this.size() && (comparator.compare(apq.get(leftbranch), apq.get(pos)) < 0 )){
    			this.swap(leftbranch, pos);
    			downheap(leftbranch);
    		}
    		
    		else {
    			this.swap(rightbranch, pos);
    			downheap(rightbranch);
    		}
    	
    	}
    	
    	
    }

    /**
     * Swaps the entries at the specified locations.
     *
     * @param pos1 the location of the first entry 
     * @param pos2 the location of the second entry 
     */
    private void swap(int pos1, int pos2) {
    	E Node1 = apq.get(pos1);
    	E Node2 = apq.get(pos2);
    	apq.set(pos2, Node1);
    	locator.set(Node1, pos2);
    	apq.set(pos1, Node2);
    	locator.set(Node2, pos1);
    }
}