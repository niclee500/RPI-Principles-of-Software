package hw4;

import java.util.*;

public class Graph<N extends Comparable<N>, L extends Comparable<L>> {
	/**
	 *	<b>Graph</b> represents a <b>mutable</b> graph of nodes and edges between them.
	 *
	 *	Specification fields:
	 *  	@specfield nodelist		: Collection<N> 	// The list of nodes on the graph
	 *  	@specfield edgelist		: Collection<Edge<N,L>>  	// The list of edges on the graph
	 */
	
	private TreeSet<N> nodes;
	private TreeSet<Edge<N,L>> edges;
	private static final boolean chR = false;

    // Abstraction Function for a Graph g:                                                                                                                                      
    // nodelist = g.nodes;
    // edgelist = g.edges;                                                                                                       

    // Representation Invariant for every Graph g:                                                                                                                    
    // g.nodes != null && 
    // g.edges != null && 
    // forall Edges e in g.edges, g.nodes.contains(e.start) && g.nodes.contains(e.end)
    //
    // In other words,
    //	 * The graph always has a list of each nodes and edges, even if they're empty,
    //	 * All edges are to and from nodes on the graph

	/**
	 * Check the rep invariant
	 * @throws: RuntimeException if this violates rep invariant 
	 */
	private void checkRep() throws RuntimeException {
		if(chR){
			if(nodes == null){
				throw new RuntimeException("Graph node list is null");
			}
			if(edges == null){
				throw new RuntimeException("Graph edge list is null");
			}
			for (Edge<N,L> ed: edges) {
				if( !(nodes.contains(ed.getStart())) || !(nodes.contains(ed.getEnd())) ){
					throw new RuntimeException("An edge references a node not in the graph");
				}
			}
		}
	}

	/**
	 *	@effects: creates a new empty Graph
	 */
	public Graph(){
		nodes = new TreeSet<N>();
		edges = new TreeSet<Edge<N,L>>();
		checkRep();
	}

	/**
	 * 	@return: An iterator over the nodelist
	 */
	public Iterator<N> nodeIterator(){
		return Collections.unmodifiableSet(nodes).iterator();
	}

	/**
	 * 	@return: An iterator over the edgelist
	 */
	public Iterator<Edge<N, L>> edgeIterator(){
		return Collections.unmodifiableSet(edges).iterator();
	}
	
	/**
	 *	@param: n The new node to be added
	 *	@modifies: this
	 *	@effects: adds the new node to the nodelist
	 *	@return: true if the node was successfully added to the graph, 
	 *			false if the node was already on the graph
	 */
	public boolean addNode(N n){
		boolean b = nodes.add(n);
		checkRep();
		return b;
	}
	
	/**
	 *	@param: e The new edge to be added
	 *	@modifies: this
	 *	@effects: adds the new edge to the edgelist
	 *	@throws: RuntimeException if the start-node and/or end-node
	 *			of e isn't in the graph
	 *	@return: true if the node was successfully added to the graph, 
	 *			false if the node was already on the graph
	 */
	public boolean addEdge(Edge<N,L> e){
		N st = e.getStart();
		N en = e.getEnd();
		if( !(nodes.contains(st)) || !(nodes.contains(en)) ){
			throw new RuntimeException("Tried to add edge to/from a node not on the graph.");
		}
		boolean b = edges.add(e);
		checkRep();
		return b;
	}

	/**
	 *	@modifies: this
	 *	@effects: clears the nodelist and edgelist, leaving the graph empty
	 */
	public void clear(){
		nodes.clear();
		edges.clear();
		checkRep();
	}

	/**
	 *	@return: The number of nodes in this graph
	 */
	public int getNumNodes(){
		return nodes.size();
	}

	/**
	 *	@return: The number of edges in this graph
	 */
	public int getNumEdges(){
		return edges.size();
	}

	/**
	 *	@param: n The node to check for on the graph
	 *	@return: true if node n is on the graph, false otherwise
	 */
	public boolean containsNode(N n){
		return nodes.contains(n);
	}

	/**
	 *	@param: e The edge to check for on the graph
	 *	@return: true if edge e is on the graph, false otherwise
	 */
	public boolean containsEdge(Edge<N,L> e){
		return edges.contains(e);
	}

}











