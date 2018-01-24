package hw6;

import hw4.Graph;
import hw4.Edge;
import java.util.*;
import java.io.*;

public class MarvelPaths2 {
	private Graph<String, Float> graph;
	private Map<String, Set<String>> bookList;
	private Map<String, Map<String, Float>> charsToChars;
	
	/*public static void main(String[] arg) {
		String file = arg[0];
		MarvelPaths2 mp = new MarvelPaths2();

			mp.createNewGraph(file);
			System.out.println("Constructed the graph.\n");
			String path = mp.findPath("HAWK", "WOODGOD");
			System.out.println(path);
	}*/

	/** 
	 *	This is the constructor of MarvelPaths. It initializes the instance field 
	 *	with a new empty instance of your Graph ADT.
	 */
	public MarvelPaths2(){
		graph = new Graph<String, Float>();
		bookList = new HashMap<String, Set<String>>();
		charsToChars = new HashMap<String, Map<String, Float>>();
	}

	/**
	 *	@modifies: this
	 *	@effects: clears the nodelist and edgelist, leaving the graph empty
	 */
	public void clear(){
		graph.clear();
		bookList.clear();
		charsToChars.clear();
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
		Set<String> bkChars = bookList.get(bk);
		if(bkChars == null){
			bkChars = new HashSet<String>();
			bookList.put(bk, bkChars);
		}
		boolean newToBook = bkChars.add(ch);
		if(!newToBook){ return; }
		Map<String, Float> conn1 = charsToChars.get(ch);
		if( conn1 == null ){
			conn1 = new HashMap<String, Float>();
			charsToChars.put(ch, conn1);
		}
		for (String char2 : bkChars) {
			if( !ch.equals(char2) ){
				
				Float c = conn1.get(char2);
				if(c == null){
					c = 0f;
				}
				// Edge<String, Float> e1 = new Edge<String, Float>(ch, char2, 1/c);
				// Edge<String, Float> e2 = new Edge<String, Float>(char2, ch, 1/c);
				// if( !graph.containsEdge(e1) && !graph.containsEdge(e2) ){
					++c;
					Edge<String, Float> e3 = new Edge<String, Float>(ch, char2, 1/c);
					graph.addEdge(e3);
				//}
					conn1.put(char2, c);
					Map<String, Float> conn2 = charsToChars.get(char2);
					conn2.put(ch, c);
			}
		}
	}

	/**
	 *	@param: c1 The start node of the edge to be added
	 *	@param: c2 The end node of the edge to be added
	 *	@param: w The weight of the edge to be added
	 *	@modifies: this
	 *	@effects: adds the new edge to the edgelist
	 *	@throws: RuntimeException if the start-node and/or end-node
	 *			of e isn't in the graph, or if 0 > w 
	 *	@return: true if the node was successfully added to the graph, 
	 *			false if the node was already on the graph
	 */
	public boolean addEdge(String c1, String c2, float w){
		if(w < 0.0){ throw new RuntimeException("Tried to add an edge with a negative weight."); }
		graph.addNode(c1);
		graph.addNode(c2);
		Edge<String, Float> e = new Edge<String, Float>(c1, c2, w);
		return graph.addEdge(e);
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
	 *	@return: An unmodifiable reference to the map of character data
	 */
	public Map<String, Map<String, Float>> getCharData(){
		return Collections.unmodifiableMap(charsToChars);
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
		clear();
		Set<String> chars = new HashSet<String>();
		try{
			readData(filename, bookList, chars);
			for (String ch : chars) {
				graph.addNode(ch);
				charsToChars.put(ch, charsToChars.get(ch));
			}
			/*if(chars.size() == 2){
				Object[] chs = chars.toArray();
				Edge<String, Float> e = new Edge<String, Float>((String)chs[0], (String)chs[1], 1f);
				graph.addEdge(e);
			} else*/ if(chars.size() > 1){
				for(Map.Entry<String, Set<String>> entry : bookList.entrySet()) {
					//String book = entry.getKey();
					Set<String> bkChars = entry.getValue();
					for (String char1 : bkChars) {
						for (String char2 : bkChars) {
							if( !char1.equals(char2) ){
								Map<String, Float> m = charsToChars.get(char1);
								if(m == null){
									m = new HashMap<String, Float>();
									charsToChars.put(char1, m);
								}
								Float c = m.get(char2);
								if(c == null){ c = 0.0f; }
								m.put(char2, c + 1.0f);
							}
						}
					}
				}
				for (Map.Entry<String, Map<String, Float>> entry : charsToChars.entrySet()) {
					String char1 = entry.getKey();
					Map<String, Float> connections = entry.getValue();
					if(connections == null){ continue; }
					for (Map.Entry<String, Float> entry2 : connections.entrySet()) {
						String char2 = entry2.getKey();
						Float weight = entry2.getValue();
						Edge<String, Float> e1 = new Edge<String, Float>(char1, char2, 1/weight);
						Edge<String, Float> e2 = new Edge<String, Float>(char2, char1, 1/weight);
						if( !graph.containsEdge(e1) && !graph.containsEdge(e2) ){
							graph.addEdge(e1);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Reading from " + filename + ":");
		// System.out.println("Read "+chars.size()+" characters who appear in " + bookList.size() + " books.");
		// System.out.println("Graph has " + graph.getNumNodes() + " character nodes and " + graph.getNumEdges() + " edges.\n");
	}

	/** 
	 *  @param: filename The path to the "CSV" file that contains the <hero, book> pairs                                                                                                
	 *  @param: charsInBooks The Map that stores parsed <book, Set-of-heros-in-book> pairs;
	 *          usually an empty Map
	 *  @param: chars The Set that stores parsed characters; usually an empty Set.
	 *  @effects: adds parsed <book, Set-of-heros-in-book> pairs to Map charsInBooks;
	 *            adds parsed characters to Set chars
	 *  @throws: IOException if file cannot be read or file not a CSV file                                                                                     
	 */
	public static void readData(String filename, Map<String,Set<String>> charsInBooks, Set<String> chars) 
			throws IOException {

		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			 int i = line.indexOf("\",\"");
			 if ((i == -1) || (line.charAt(0)!='\"') || (line.charAt(line.length()-1)!='\"')) {
				 throw new IOException("File "+filename+" not a CSV (\"HERO\",\"BOOK\") file.");
			 }             
			 String character = line.substring(1,i);
			 String book = line.substring(i+3,line.length()-1);
			 
			 // Adds the character to the character set. If character already in, add has no effect.
			 chars.add(character);

			 // Adds the character to the set for book
			 Set<String> s = charsInBooks.get(book);
			 if (s == null) {
			   s = new HashSet<String>();
			   charsInBooks.put(book,s);
			 }
			 s.add(character);
		}
		
		reader.close();
	}


	/**
	 *  @param: start The starting node for the path
	 *  @param: dest The destination node for the path
	 *  @return: A string listing all of the nodes and edges visited along the path
	 */
	public String findPath(String start, String dest){
		if( !graph.containsNode(start) && !graph.containsNode(dest) && !start.equals(dest) ){
			return "unknown character " + start + "\nunknown character " + dest + "\n";
		} else if( !graph.containsNode(start) ){
			return "unknown character " + start + "\n";
		} else if( !graph.containsNode(dest) ){
			return "unknown character " + dest + "\n";
		}

		Path minPath = new Path(start);
		PriorityQueue<Path> pathSearchQueue = new PriorityQueue<Path>();
		Map<String, Path> finished = new TreeMap<String, Path>();
		boolean foundPath = false;

		pathSearchQueue.add(minPath);

		while(pathSearchQueue.size() != 0){
			minPath = pathSearchQueue.poll();
			String minDest = minPath.destNode;
			if( minDest.equals(dest) ){
				foundPath = true;
				break;
			}

			if( finished.containsKey(minDest) ){ continue; }
			
			Iterator<Edge<String, Float>> edgeItr = graph.edgeIterator();
			while( edgeItr.hasNext() ){
				Edge<String, Float> edg = edgeItr.next();
				if( finished.containsKey(edg.getStart()) || finished.containsKey(edg.getEnd()) ){ continue; }
				if( minDest.equals(edg.getStart()) ){
					String nxtNode = edg.getEnd();
					Path nPath = new Path(minPath);
					nPath.edges.add(edg);
					nPath.destNode = nxtNode;
					nPath.totalCost += edg.getLabel();
					pathSearchQueue.add(nPath);
				} else if( minDest.equals(edg.getEnd()) ){
					String nxtNode = edg.getStart();
					Edge<String, Float> revedg = new Edge<String, Float>(minDest, nxtNode, edg.getLabel());
					Path nPath = new Path(minPath);
					nPath.edges.add(revedg);
					nPath.destNode = nxtNode;
					nPath.totalCost += edg.getLabel();
					pathSearchQueue.add(nPath);
				}
			}

			finished.put(minDest, minPath);

		}


		String shPath = "path from " + start + " to " + dest + ":\n";
		if(!foundPath){
			return shPath + "no path found\n";
		}
		for(Edge<String, Float> e : minPath.edges) {
			String line = e.getStart() + " to " + e.getEnd() + String.format(" with weight %.3f", e.getLabel());
			shPath += line + "\n";
		}
		shPath += String.format("total cost: %.3f\n", minPath.totalCost);
		return shPath;
	}

	public class Path implements Comparable<Path>{
		public String startNode;
		public String destNode;
		public float totalCost;
		public Vector<Edge<String,Float>> edges;

		public Path(String sn){
			startNode = new String(sn);
			destNode = new String(sn); totalCost = 0f;
			edges = new Vector<Edge<String,Float>>(); 
		}
		public Path(Path p){
			totalCost = p.totalCost; 
			startNode = new String(p.startNode);
			destNode = new String(p.destNode);
			edges = new Vector<Edge<String,Float>>(p.edges);
		}

		@Override
		public int compareTo(Path p) { 
			Float a = this.totalCost;
			Float b = p.totalCost;
			return a.compareTo(b);
		}
	}

}









