package hw4;

import java.util.*;

public class Node {
	/**
	 *	<b>Node</b> represents a <b>mutable</b> node in a directed labeled multigraph.
	 *
	 *	Specification fields:
	 *  	@specfield nodename		: String 				// The name/label of the node
	 *  	@specfield childlist	: Collection<String> 	// The names of all the children of the node
	 */


	/**
	 *	@param: n The name of the new Node
	 *	@effects: creates a new node with nodename n and an empty list of children
	 */
	public Node(String n){
		//TODO: Node constructor
		throw new RuntimeException();
	}

	/**
	 *	@param: e The edge that connects this node to the new child
	 *	@modifies: this
	 *	@effects: adds the child and edge/path data from e into the childlist
	 */
	public void addChild(Edge e){
		//TODO: Node addChild
		throw new RuntimeException();
	}
	
	/**
	 *	Compares two Nodes
	 *	@param: o The Node to be compared
	 *	@return: true if this Node has the same name as o
	 *			false otherwise (including if o is null or isn't a Node object) 
	 */
	@Override
	public boolean equals(Object o){
		//TODO: Node equals
		throw new RuntimeException();
		// if(o == null){ return false; }
		// if( !(o instanceof Edge) ){ return false; }
		// Edge e = (Edge) o;
		// return [stuff];
	}
	
	/**
	 *	@return: An integer unique to this Node and any equal to it
	 */
	@Override
	public int hashCode(){
		//TODO: Node hashcode
		throw new RuntimeException();
	}
	
	/**
	 *	@return: This Node's name
	 */
	public String getName(){
		//TODO: Node getname
		throw new RuntimeException();
	}
	
	/**
	 *	@return: An iterator which returns the children of the node in lexographical order
	 */
	public Iterator<String> iterator(){
		//TODO: Node iterator
		throw new RuntimeException();
	}
	
}









