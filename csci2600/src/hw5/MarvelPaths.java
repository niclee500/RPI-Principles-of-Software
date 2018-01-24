package hw5;

import hw4.Graph;
import hw4.Edge;
import java.util.*;
import java.io.*;

public class MarvelPaths {
	private Graph<String, String> graph;
	private TreeMap<String, Set<String>> bookList;
	
	/*public static void main(String[] arg) {
		String file = arg[0];
		MarvelPaths mp = new MarvelPaths();

			mp.createNewGraph(file);
			System.out.println("Constructed the graph.\n");
			String path = mp.findPath("HAWK", "WOODGOD");
			System.out.println(path);
	}*/

	/** 
	 *	This is the constructor of MarvelPaths. It initializes the instance field 
	 *	with a new empty instance of your Graph ADT.
	 */
	public MarvelPaths(){
		graph = new Graph<String, String>();
		bookList = new TreeMap<String, Set<String>>();
	}

	/**
	 *  @param: ch The name of the character to be added
	 *  @param: bk The name of the book the character was in
	 *  @modifies: this
	 *  @effects: adds the new character to the graph, adds the book to the 
	 *              booklist (if new) and adds edges to and from all other 
	 *              characters that have appeared in the given book
	 */
	public void addCharacter(String ch, String bk){
		graph.addNode(ch);
		if( bookList.containsKey(bk) ){
			Set<String> bkChars = bookList.get(bk);
			for (String char2 : bkChars) {
				if( !ch.equals(char2) ){
					Edge<String, String> e1 = new Edge<String, String>(ch, char2, bk);
					Edge<String, String> e2 = new Edge<String, String>(char2, ch, bk);
					graph.addEdge(e1);
					graph.addEdge(e2);
				}
			}
			bkChars.add(ch);
		} else{
			bookList.put(bk, new HashSet<String>());
			bookList.get(bk).add(ch);
		}
	}

	/**
	 *	@modifies: this
	 *	@effects: clears the nodelist and edgelist, leaving the graph empty
	 */
	public void clear(){
		graph.clear();
		bookList.clear();
	}

	/**
	 *	@return: The number of nodes in this graph
	 */
	public int getNumNodes(){
		return graph.getNumNodes();
	}

	/**
	 *	@return: The number of edges in this graph
	 */
	public int getNumEdges(){
		return graph.getNumEdges();
	}

	/**
	 *  @return: The number of books in the path set
	 */
	public int getNumBooks(){
		return bookList.size();
	}

	/** 
	 *	The method creates a brand new graph in the instance field in MarvelPaths and 
	 *	populates the graph from filename, where filename is a data file of the format 
	 *	defined for marvel.csv and is located in the hw5/data directory of your project.
	 *	@param: filename The name of the data file to be read from
	 *	@effects: creates a new graph from the data in the file
	 *	
	 */
	public void createNewGraph(String filename){
		graph.clear();
		bookList.clear();
		//String fullfilepath = "hw5/data/" + filename;
		Set<String> chars = new HashSet<String>();
		try{
			MarvelParser.readData(filename, bookList, chars);
			for(String ch : chars) {
				graph.addNode(ch);
			}
			for(Map.Entry<String, Set<String>> entry : bookList.entrySet()) {
				String book = entry.getKey();
				Set<String> bkChars = entry.getValue();
				for (String char1 : bkChars) {
					for (String char2 : bkChars) {
						if( !char1.equals(char2) ){
							Edge<String, String> e1 = new Edge<String, String>(char1, char2, book);
							Edge<String, String> e2 = new Edge<String, String>(char2, char1, book);
							graph.addEdge(e1);
							graph.addEdge(e2);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Read "+chars.size()+" characters who appear in "+charsInBooks.keySet().size() +" books.");
		//System.out.println("Graph has " + graph.getNumNodes() + " character nodes and " + graph.getNumEdges() + " relationships between them.");
	}


	/**
	 *	@param: node1 The starting node for the path
	 *	@param: node2 The destination node for the path
	 *	@return: A string listing all of the nodes and edges visited along the path
	 */
	public String findPath(String node1, String node2){
		if( !graph.containsNode(node1) && !graph.containsNode(node2) && !node1.equals(node2) ){
			return "unknown character " + node1 + "\nunknown character " + node2 + "\n";
		} else if( !graph.containsNode(node1) ){
			return "unknown character " + node1 + "\n";
		} else if( !graph.containsNode(node2) ){
			return "unknown character " + node2 + "\n";
		}

		LinkedList<Edge<String, String>> path = new LinkedList<Edge<String, String>>();
		PriorityQueue<String> nodeSearchQueue = new PriorityQueue<String>();
		Map<String, LinkedList<Edge<String, String>>> visitedNodes = new HashMap<String, LinkedList<Edge<String, String>>>();
		boolean foundPath = false;

		nodeSearchQueue.add(node1);
		visitedNodes.put(node1, new LinkedList<Edge<String, String>>());

		while(nodeSearchQueue.size() != 0){
			String currNode = nodeSearchQueue.poll();
			if( currNode.equals(node2) ){
				path = visitedNodes.get(currNode);
				foundPath = true;
				break;
			}
			boolean inClump = false;
			Iterator<Edge<String, String>> edgeItr = graph.edgeIterator();
			while( edgeItr.hasNext() ){
				Edge<String, String> edg = edgeItr.next();
				
				if( currNode.equals(edg.getStart()) ){
					inClump = true;
					String nextNode = edg.getEnd();
					LinkedList<Edge<String, String>> currPath = visitedNodes.get(currNode);
					LinkedList<Edge<String, String>> nextPath = new LinkedList<Edge<String, String>>(currPath);
					nextPath.addLast(edg);
					if( !visitedNodes.containsKey(nextNode) ){
						visitedNodes.put(nextNode, nextPath);
						nodeSearchQueue.add(nextNode);
					} else{
						if( visitedNodes.get(nextNode).size() > nextPath.size() ){
							visitedNodes.put(nextNode, nextPath);
						}
					}
				} 
				else if( !currNode.equals(edg.getStart()) && inClump ){
					inClump = false;
					break;
				}
			}
		}
		String shPath = "path from " + node1 + " to " + node2 + ":\n";
		if(!foundPath){
			return shPath + "no path found\n";
		}
		for(Edge<String, String> e : path) {
			String line = e.getStart() + " to " + e.getEnd() + " via " + e.getLabel();
			shPath += line + "\n";
		}
		return shPath;
	}


}









