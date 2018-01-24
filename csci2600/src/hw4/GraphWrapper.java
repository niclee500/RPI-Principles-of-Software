package hw4;

import java.util.*;

public class GraphWrapper {
	private Graph<String, String> graph;
	private TreeSet<String> children;

	/** 
	 *	This is the constructor of GraphWrapper. It initializes the instance field 
	 *	with a new empty instance of your Graph ADT.
	 */
	public GraphWrapper(){
		graph = new Graph<String, String>();
		children = new TreeSet<String>();
	}


	/** 
	 *	Adds a node represented by the string nodeData to your graph. If an 
	 *	identical node already exists in the graph, the output of addNode is 
	 *	not defined, that is, it is left at your discretion.
	 */
	public boolean addNode(String nodeData){
		return graph.addNode(nodeData);
	}


	/** 
	 *	Creates an edge from parentNode to childNode with label edgeLabel in your 
	 *	graph. If either of the nodes does not exist in the graph, the output of 
	 *	this command is not defined. If an identical edge (same parent, child, and 
	 *	label) already exists, the output of this command is not defined either, 
	 *	as it is left to your discretion whether to allow identical edges in your 
	 *	implementation.
	 */
	public boolean addEdge(String parentNode, String childNode, String edgeLabel){
		return graph.addEdge(new Edge<String, String>(parentNode, childNode, edgeLabel));
	}


	/** 
	 *	This operation has no effect on your graph. It returns an iterator which
	 *	returns the nodes in lexicographical (alphabetical) order.
	 */
	public Iterator<String> listNodes(){
		return graph.nodeIterator();
	}

	/** 
	 *	This operation has no effect on your graph. It returns iterator which 
	 *	returns the list of childNode(edgeLabel) in lexicographical (alphabetical) 
	 *	order by node name and secondarily by edge label. childNode(edgeLabel) 
	 *	means there is an edge with label edgeLabel from parentNode to childNode. 
	 *	If there are multiple edges from parentNode to some childNode, there should 
	 *	be separate entry for each edge. If there is a reflexive edge, 
	 *	parentNode(edgeLabel) should be in the list.
	 */
	public Iterator<String> listChildren(String parentNode){
		children.clear();
		String child;
		Iterator<Edge<String, String>> etr = graph.edgeIterator();
		for (; etr.hasNext();) {
			Edge<String, String> ed = etr.next();
			if(parentNode.equals(ed.getStart())){
				child = ed.getEnd() + "(" + ed.getLabel() + ")";
				children.add(child);
			}
		}
		return children.iterator();
	}


}









