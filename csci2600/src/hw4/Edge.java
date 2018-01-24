package hw4;

public final class Edge<N extends Comparable<N>, L extends Comparable<L>> implements Comparable<Edge<N,L>>{
	/**
	 *	<b>Edge</b> represents an <b>immutable</b> directed labeled edge from 
	 *	one node on a graph to another, with a specified positive weight.
	 *
	 *	Specification fields:
	 *  	@specfield start-node	: N 	// The starting node of the edge.
	 *  	@specfield end-node		: N 	// The ending node of the edge.
	 *		@specfield length		: L	// The length/weight of the edge.
	 */

	private final N start;
	private final N end;
	private final L label;
	private static final boolean chR = false;

	// Abstraction Function for an Edge e:                                                                                                                                      
    // 	start-node = e.start;
    // 	end-node = e.end;
    // 	length = e.label;                                                                                                                                

    // Representation Invariant for every Edge e:                                                                                                                             
    // 	e.start != null &&
    // 	e.end != null &&
    // 	e.label != null &&
    // 	if(int w = parseInt(e.label)) then w >= 0
    //
    // 	In other words,
    // 	  * All edges must have non-null end nodes
    //	  * If the label is an integer, it must be non-negative

	/**
	 * Check the rep invariant
	 * @throws: RuntimeException if this violates rep invariant 
	 */
	private void checkRep() throws RuntimeException {
		if(chR){
			if(start == null){
				throw new RuntimeException("Edge has no start node");
			}
			if(end == null){
				throw new RuntimeException("Edge has no end node");
			}
			if(label == null){
				throw new RuntimeException("Edge has no label");
			}
		}
	}

	/**
	*	@param: s The start-node of the new Edge
	*	@param: e The end-node of the new Edge
	*	@param: l The lbl of the new Edge
	*	@effects: creates a new edge from s to e with label l
	*/
	public Edge(N st, N en, L lb){
		start = st;
		end = en;
		label = lb;
		checkRep();
	}

	/**
	*	@param: e The Edge to copy
	*	@effects: creates a new edge with the same start, end, and label as e
	*/
	public Edge(Edge<N,L> e2){
		start = e2.start;
		end = e2.end;
		label = e2.label;
		checkRep();
	}
	
	/**
	 *	@return: true if start-node == end-node, false otherwise
	 */
	public boolean isReflexive(){
		return start.equals(end);
	}
	
	/**
	 *	@return: This Edge's start-node
	 */
	public N getStart(){
		return start;
	}
	
	/**
	 *	@return: This Edge's start-node
	 */
	public N getEnd(){
		return end;
	}
	
	/**
	 *	@return: This Edge's length/weight
	 */
	public L getLabel(){
		return label;
	}

	/**
	 *	@return: A new Edge with the same label as the current Edge, but with the end points reversed
	 */
	public Edge<N,L> flip(){
		return new Edge<N,L>(end, start, label);
	}

	/** 
    *   Compares two Edges, first by start node, then end node, then label
    *   @param ed The Edge to be compared.
    *   @requires ed != null
    *   @return a negative number if this < ed,
    *   		0 if this = ed,
    *   		a positive number if this > ed.
    */
    @Override
    public int compareTo(Edge<N,L> ed) {
        if ( this.start.equals(ed.start) && this.end.equals(ed.end) ) {
            return this.label.compareTo(ed.label);
        } else if ( this.start.equals(ed.start) ) {
            return this.end.compareTo(ed.end);
        } else{
            return this.start.compareTo(ed.start);
        }
    }
	
}









