	/**
	 *	@param: nds The list containing the graph node names
	 *	@param: edgs The list containing the graph edges
	 *	@requires: nds is non-null, and all Edges in edgs refer only to the node names in nds
	 *	@effects: creates a new graph with nodelist nds and edgelist edgs
	 */
	public Graph(List<String> nds, List<Edge> edgs){
		//TODO: Graph non-empty constructor
		throw new RuntimeException();
	}

	/**
	 * 	@param: g The graph with data to be copied
	 *	@effects: creates a new graph with nodelist and edgelist copied from g
	 */
	public Graph(Graph g){
		//TODO: Graph copy constructor
		throw new RuntimeException();
	}

	/**
	 *	@param: n The name of the node to check the children of
	 *	@return: A list of the names of all nodes that can be directly
	 *		reached from node n via a single edge
	 *	@throws: NonexistentNode exception if node n is not in graph
	 */
	public List<String> listChildren(String n){
		//TODO: Graph listchildren
		throw new RuntimeException();
	}
	
	/**
	 *	@param: st The name of the node the path should start from
	 *	@param: en The name of the node the path should end at
	 *	@return: A list of the edges in order that make up the shortest path from
	 *		st to en. List will be empty if there is no path. Length is determined
	 *		by the sum of the weights/labels of the edges in the path.
	 *	@throws: NonexistantNode exception if st or en are not in the graph
	 */
	public List<Edge> shortestPath(String st, String en){
		//TODO: Graph shortestPath
		throw new RuntimeException();
	}



