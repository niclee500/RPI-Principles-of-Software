package hw4.test;

import hw4.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public final class GraphTest{

	private static Graph<String, String> graph = null;
	private static String[] nodes = null;
	private static List<Edge<String, String>> edges = null;

	private static final String[] alpha = { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z" };
	private static final int NUM_NODES = 4;
	private static final int NUM_EDGES = 4;

	@BeforeClass
	public static void setupForGraphTests() throws Exception {
		graph = new Graph<String, String>();
		nodes = new String[NUM_NODES];
		//nodes[0] = "a"; nodes[1] = "b"; nodes[2] = "c"; nodes[3] = "d";
		for(int i = 0; i < NUM_NODES; i++){
			nodes[i] = "" + alpha[i];
		}

		edges = new ArrayList<Edge<String, String>>(NUM_EDGES);
		edges.add(new Edge<String, String>("a", "b", "1")); edges.add(new Edge<String, String>("b", "c", "1"));
		edges.add(new Edge<String, String>("c", "d", "1")); edges.add(new Edge<String, String>("d", "a", "1"));
	}

//============================================================================
//Iterators

	@Test
	public void testNodeIterator(){
		Set<String> allNodes = new HashSet<String>();
		Set<String> seenNodes = new HashSet<String>();
		graph.clear();
		assertEquals("Graph has nodes after clear", 0, graph.getNumNodes());
		assertEquals("Graph has edges after clear", 0, graph.getNumEdges());
		for (String nd: nodes) {
			graph.addNode(nd);
			allNodes.add(nd);
		}
		int i = 0;
		//for (String nd: graph) {
		for(Iterator<String> itr = graph.nodeIterator(); itr.hasNext(); ){
			String nd = itr.next();
			assertTrue("Iterator returned a node that isn't in the container: " + nd,
                       allNodes.contains(nd));
            assertFalse("Iterator returned the same node twice: " + nd,
                        seenNodes.contains(nd));
            seenNodes.add(nd);
            i++;
		}
		assertEquals("Graph node-iterator did not return enough items!",
                     i, nodes.length);
	}

	@Test
	public void testEdgeIterator(){
		Set<Edge<String, String>> allEdges = new HashSet<Edge<String, String>>();
		Set<Edge<String, String>> seenEdges = new HashSet<Edge<String, String>>();
		graph.clear();
		assertEquals("Graph has nodes after clear", 0, graph.getNumNodes());
		assertEquals("Graph has edges after clear", 0, graph.getNumEdges());
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		for (Edge<String, String> ed: edges) {
			graph.addEdge(ed);
			allEdges.add(ed);
		}
		int i = 0;
		//for (Edge ed: graph) {
		for(Iterator<Edge<String, String>> itr = graph.edgeIterator(); itr.hasNext(); ){
			Edge<String, String> ed = itr.next();
			assertTrue("Iterator returned an edge that isn't in the container: ",
                       allEdges.contains(ed));
            assertFalse("Iterator returned the same edge twice: ",
                        seenEdges.contains(ed));
            seenEdges.add(ed);
            i++;
		}
		assertEquals("Graph node-iterator did not return enough items!",
                     i, edges.size());
	}

//============================================================================
//addNode + addEdge

	@Test
	public void testAddNode(){
		graph.clear();
		int nds = 0;
		for(int i = 0; i < NUM_NODES; i++){
			assertTrue("Graph failed to add a new node", graph.addNode(nodes[i]));
			nds = graph.getNumNodes();
			assertFalse("Graph.addNode allows duplicate nodes to be added", graph.addNode(nodes[i]));
			assertEquals("Graph's number of nodes has changed, but contents have not", nds, graph.getNumNodes());
			assertTrue("Graph does not contain the node that should have just been added", graph.containsNode(nodes[i]));
		}
	}

	@Test
	public void testAddValidEdge(){
		graph.clear();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		int edgs = 0;
		for(int i = 0; i < NUM_EDGES; i++){
			assertTrue("Graph failed to add a new edge", graph.addEdge(edges.get(i)));
			edgs = graph.getNumEdges();
			assertFalse("Graph.addEdge allows duplicate edges to be added", graph.addEdge(edges.get(i)));
			assertEquals("Graph's number of edges has changed, but contents have not", edgs, graph.getNumEdges());
			assertTrue("Graph does not contain the edge that should have just been added", graph.containsEdge(edges.get(i)));
		}
	}

	@Test
	public void testAddInvalidEdge(){
		graph.clear();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		Edge<String, String> e1 = new Edge<String, String>(nodes[0], "z", "1");
		Edge<String, String> e2 = new Edge<String, String>("y", nodes[1], "1");
		Edge<String, String> e3 = new Edge<String, String>("w", "x", "1");
		boolean e1thrown = false;
		boolean e2thrown = false;
		boolean e3thrown = false;
		try{
			graph.addEdge(e1);
		} catch(RuntimeException ne){
			e1thrown = true;
		}
		assertTrue(e1thrown);

		try{
			graph.addEdge(e2);
		} catch(RuntimeException ne){
			e2thrown = true;
		}
		assertTrue(e2thrown);

		try{
			graph.addEdge(e3);
		} catch(RuntimeException ne){
			e3thrown = true;
		}
		assertTrue(e3thrown);
	}


//============================================================================
//getNumNodes + getNumEdges

	@Test
	public void testNumNodesAndEdges(){
		graph.clear();
		assertEquals("Graph has nodes after clear", 0, graph.getNumNodes());
		assertEquals("Graph has edges after clear", 0, graph.getNumEdges());
		for(int i = 0; i < NUM_NODES; i++){
			graph.addNode(nodes[i]);
			assertEquals("getNumNodes() of Graph with "+(i+1)+" node(s)", i+1, graph.getNumNodes());
		}
		for(int i = 0; i < NUM_EDGES; i++){
			graph.addEdge(edges.get(i));
			assertEquals("getNumEdges() of Graph with "+(i+1)+" edge(s)", i+1, graph.getNumEdges());
		}
	}

//============================================================================
//containsNode + containsEdge

	@Test
	public void testContainsNode(){
		graph.clear();
		for(int i = 0; i < NUM_NODES; i++){
			assertFalse("Empty graph contains a node", graph.containsNode(nodes[i]));
		}
		for(int i = 0; i < NUM_NODES; i++){
			graph.addNode(nodes[i]);
			assertTrue("Graph doesn't contain a Node that it should", graph.containsNode(nodes[i]));
			for(int j = i+1; j < NUM_NODES; j++){
				assertFalse("Graph contains a Node that has not been added yet", graph.containsNode(nodes[j]));
			}
		}
	}

	@Test
	public void testContainsEdge(){
		graph.clear();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		for(int i = 0; i < NUM_EDGES; i++){
			assertFalse("Empty graph contains an edge", graph.containsEdge(edges.get(i)));
		}
		for(int i = 0; i < NUM_EDGES; i++){
			graph.addEdge(edges.get(i));
			assertTrue("Graph doesn't contain a Edge that it should", graph.containsEdge(edges.get(i)));
			for(int j = i+1; j < NUM_EDGES; j++){
				assertFalse("Graph contains a Edge that has not been added yet", graph.containsEdge(edges.get(j)));
			}
		}
	}

//============================================================================
//clear

	@Test
	public void testClear() {
		graph.clear();
		assertEquals("Graph has nodes after clear", 0, graph.getNumNodes());
		assertEquals("Graph has edges after clear", 0, graph.getNumEdges());

		graph.addNode(nodes[0]); graph.addNode(nodes[1]);
		graph.addEdge(edges.get(0));
		graph.clear();
		assertEquals("Graph has nodes after clear", 0, graph.getNumNodes());
		assertEquals("Graph has edges after clear", 0, graph.getNumEdges());
	}

}









