package hw4.test;

import hw4.*;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public final class GraphWrapperTest{
	
	private static String[] nodes = null;

	private static final String[] alpha = { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z" };
	private static final int NUM_NODES = 4;
	private static final int NUM_EDGES = 4;

	@BeforeClass
	public static void setupForGraphTests() throws Exception {
		nodes = new String[NUM_NODES];
		//nodes[0] = "a"; nodes[1] = "b"; nodes[2] = "c"; nodes[3] = "d";
		for(int i = 0; i < NUM_NODES; i++){
			nodes[i] = "" + alpha[i];
		}

		// edges = new Edge[NUM_EDGES];
		// edges[0] = new Edge("a", "b", "1"); edges[1] = new Edge("b", "c", "1");
		// edges[2] = new Edge("c", "d", "1"); edges[3] = new Edge("d", "a", "1");

	}

	@Test
	public void testAddNode(){
		GraphWrapper graph = new GraphWrapper();
		for(int i = 0; i < NUM_NODES; i++){
			assertTrue("Graph failed to add a new node", graph.addNode(nodes[i]));
			assertFalse("Graph.addNode allows duplicate nodes to be added", graph.addNode(nodes[i]));
		}
	}


	@Test
	public void testAddValidEdge(){
		GraphWrapper graph = new GraphWrapper();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		for(int i = 0; i < NUM_EDGES; i++){
			String parent = nodes[i];
			String label = "1";
			String child;
			if(i < NUM_NODES-1){
				child = nodes[i+1];
			} else{
				child = nodes[0];
			}

			assertTrue("Graph failed to add a new edge", graph.addEdge(parent, child, label));
			assertFalse("Graph.addEdge allows duplicate edges to be added", graph.addEdge(parent, child, label));
		}
	}

	@Test
	public void testAddInvalidEdge(){
		GraphWrapper graph = new GraphWrapper();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		boolean e1thrown = false;
		boolean e2thrown = false;
		boolean e3thrown = false;
		try{
			graph.addEdge(nodes[0], "z", "1");
		} catch(RuntimeException ne){
			e1thrown = true;
		}
		assertTrue(e1thrown);

		try{
			graph.addEdge("y", nodes[1], "1");
		} catch(RuntimeException ne){
			e2thrown = true;
		}
		assertTrue(e2thrown);

		try{
			graph.addEdge("w", "x", "1");
		} catch(RuntimeException ne){
			e3thrown = true;
		}
		assertTrue(e3thrown);
	}

	@Test
	public void testListNodes(){
		Set<String> allNodes = new HashSet<String>();
		Set<String> seenNodes = new HashSet<String>();
		GraphWrapper graph = new GraphWrapper();
		for (String nd: nodes) {
			graph.addNode(nd);
			allNodes.add(nd);
		}
		int i = 0;
		//for (String nd: graph) {
		for(Iterator<String> itr = graph.listNodes(); itr.hasNext(); ){
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
	public void testListChildren(){
		GraphWrapper graph = new GraphWrapper();
		for (int i = 0; i < NUM_NODES; i++) { graph.addNode(nodes[i]); }
		//node[0] has no children
		//node[1] has one child
		graph.addEdge(nodes[1], nodes[2], "1");
		//node[2] has two children
		graph.addEdge(nodes[2], nodes[2], "2");
		graph.addEdge(nodes[2], nodes[3], "3");

		Iterator<String> itr;

		itr = graph.listChildren(nodes[0]);
		assertFalse("nodes[0] has children", itr.hasNext());

		itr = graph.listChildren(nodes[1]);
		int ch1 = 0;
		for(; itr.hasNext(); ){
			String st = itr.next();
			assertTrue("wrong child of nodes[1]", st.equals("c(1)"));
			ch1++;
		}
		assertEquals("nodes[1] has too many children", ch1, 1);

		itr = graph.listChildren(nodes[2]);
		int ch2 = 0;
		for(; itr.hasNext(); ){
			itr.next();
			//assertEquals("nodes[2]", st != null);
			ch2++;
		}
		assertEquals("nodes[2] has too many children", ch2, 2);

	}

}












