package hw6.test;

import java.util.*;
import java.io.*;

import hw6.*;
import hw6.MarvelPaths2.Path;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public final class MarvelPaths2Tests{

	private static MarvelPaths2 mp = null;
	private static final String chars[] = {"JONES", "HAWK", "MARVEL", "ROSS", "3DMAN", "WOODGOD"};
	private static final String books[] = {"AA2 35", "H2 252", "AVF 4", "COC 1", "WI 9", "T 208"};

	@BeforeClass
	public static void setupForGraphTests() throws Exception {
		mp = new MarvelPaths2();
	}

//============================================================================
//createNewGraph

	@Test
	public void testFakeFile(){
		mp.clear();
		boolean thrown = false;
		Map<String, Set<String>> testmap = new HashMap<String, Set<String>>();
		Set<String> testset = new HashSet<String>();
		try{
			MarvelPaths2.readData("hw6/data/fakefile.csv", testmap, testset);
		} catch(IOException e){
			thrown = true;
		}
		assertTrue("Text parsing didn't catch invalid file", thrown);
	}
	
	@Test
	public void testSingleNode(){
		mp.clear();
		mp.createNewGraph("hw6/data/one_node.csv");
		assertEquals("Graph has wrong # of nodes", 1, mp.getNumNodes());
		assertEquals("Graph has edges", 0, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testTwoNodes(){
		mp.clear();
		mp.createNewGraph("hw6/data/two_nodes.csv");
		assertEquals("Graph has wrong # of nodes", 2, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 1, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testSingleBook(){
		mp.clear();
		mp.createNewGraph("hw6/data/singlebook.csv");
		assertEquals("Graph has wrong # of nodes", 4, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 6, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testCreateChain(){
		mp.clear();
		mp.createNewGraph("hw6/data/chain.csv");
		assertEquals("Graph has wrong # of nodes", 5, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 4, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 4, mp.getNumBooks());
	}

	@Test
	public void testCreateSmall(){
		mp.clear();
		mp.createNewGraph("hw6/data/small.csv");
		assertEquals("Graph has wrong # of nodes", 6, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 11, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 2, mp.getNumBooks());
	}

	@Test
	public void testCreateMedium(){
		mp.clear();
		mp.createNewGraph("hw6/data/medium.csv");
		assertEquals("Graph has wrong # of nodes", 25, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 29, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 8, mp.getNumBooks());
	}

//============================================================================
//findPath

	@Test
	public void testUnknownChars(){
		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		String fake1 = "Bob";
		String fake2 = "Joe";
		String expected1 = "unknown character " + fake1 + "\n";
		String expected2 = "unknown character " + fake2 + "\n";
		
		String out1 = mp.findPath(chars[0], fake2);
		assertEquals("Incorrect output for unknown second character", expected2, out1);
		
		String out2 = mp.findPath(fake1, chars[0]);
		assertEquals("Incorrect output for unknown first character", expected1, out2);
		
		String out3 = mp.findPath(fake1, fake2);
		assertEquals("Incorrect output for both unknown characters", expected1 + expected2, out3);

		String out4 = mp.findPath(fake1, fake1);
		assertEquals("Incorrect output for both same unknown character", expected1, out4);
	}


	@Test
	public void testReflexivePath(){
		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[i]);
			String expected = "path from " + chars[i] + " to " + chars[i] + ":\ntotal cost: 0.000\n";
			String out = mp.findPath(chars[i], chars[i]);
			assertEquals("Incorrect output for reflexive path", expected, out);
		}

		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[0], books[i]);
			String expected = "path from " + chars[0] + " to " + chars[0] + ":\ntotal cost: 0.000\n";
			String out = mp.findPath(chars[0], chars[0]);
			assertEquals("Incorrect output for reflexive path", expected, out);
		}

		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[0]);
			String expected = "path from " + chars[i] + " to " + chars[i] + ":\ntotal cost: 0.000\n";
			String out = mp.findPath(chars[i], chars[i]);
			assertEquals("Incorrect output for reflexive path", expected, out);
		}
	}


	@Test
	public void testSameBookSimplePath(){
		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[0]);
		}

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				if(i == j){ continue; }
				String expected = "path from " + chars[i] + " to " + chars[j] + ":\n"
								+ chars[i] + " to " + chars[j] + " with weight 1.000\ntotal cost: 1.000\n";
				String out = mp.findPath(chars[i], chars[j]);
				assertEquals("Incorrect output for single-step path", expected, out);
			}
		}
	}


	@Test
	public void testChain(){
		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[1], books[0]);
		mp.addCharacter(chars[1], books[1]);
		mp.addCharacter(chars[2], books[1]);
		mp.addCharacter(chars[2], books[2]);
		mp.addCharacter(chars[3], books[2]);
		String out = mp.findPath(chars[0], chars[3]);
		String expected = "path from " + chars[0] + " to " + chars[3] + ":\n"
						+ chars[0] + " to " + chars[1] + " with weight 1.000\n"
						+ chars[1] + " to " + chars[2] + " with weight 1.000\n"
						+ chars[2] + " to " + chars[3] + " with weight 1.000\ntotal cost: 3.000\n";
		assertEquals("Incorrect chain implementation.", expected, out);
	}

/*
	@Test
	public void testEdgeAlphaSelecton(){
		mp.clear();
		mp.addCharacter(chars[0], "B");
		mp.addCharacter(chars[1], "B");
		mp.addCharacter(chars[0], "A");
		mp.addCharacter(chars[1], "A");
		mp.addCharacter(chars[0], "D");
		mp.addCharacter(chars[1], "D");
		mp.addCharacter(chars[0], "C");
		mp.addCharacter(chars[1], "C");
		String out = mp.findPath(chars[0], chars[1]);
		String expected = "path from " + chars[0] + " to " + chars[1] + ":\n"
						+ chars[0] + " to " + chars[1] + " via A\n";
		assertEquals("Incorrect book alpha selection", expected, out);
	}

	@Test
	public void testCharacterAlphaSelection(){
		mp.clear();
		mp.addCharacter("X", books[0]);
		mp.addCharacter("C", books[0]);
		mp.addCharacter("D", books[0]);
		mp.addCharacter("A", books[0]);
		mp.addCharacter("B", books[0]);
		mp.addCharacter("C", books[1]);
		mp.addCharacter("D", books[1]);
		mp.addCharacter("A", books[1]);
		mp.addCharacter("B", books[1]);
		mp.addCharacter("Y", books[1]);
		String out = mp.findPath("X", "Y");
		String expected = "path from X to Y:\nX to A via " + books[0]
						+ "\nA to Y via " + books[1] + "\n";
		assertEquals("Incorrect character alpha selection", expected, out);
	}
*/
	@Test
	public void testNoPath(){
		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[5], books[5]);
		String out = mp.findPath(chars[0], chars[5]);
		String expected = "path from " + chars[0] + " to " + chars[5] + ":\nno path found\n";
		assertEquals("Incorrect answer for no-path", expected, out);
	}


//============================================================================
//getNumNodes + getNumEdges + getNumBooks + getNumCharsWithData

	@Test
	public void testNumNodesEdgesBooks(){
		mp.clear();
		assertEquals("Graph has nodes after clear", 0, mp.getNumNodes());
		assertEquals("Graph has edges after clear", 0, mp.getNumEdges());
		assertEquals("Graph has books after clear", 0, mp.getNumBooks());
		
		mp.addCharacter(chars[0], books[0]);
		
		assertEquals("Graph has wrong # of nodes", 1, mp.getNumNodes());
		assertEquals("Graph has edges", 0, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
		
		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[1], books[0]);
		
		assertEquals("Graph has wrong # of nodes", 2, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 1, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());

		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[1], books[0]);
		mp.addCharacter(chars[0], books[1]);
		mp.addCharacter(chars[2], books[1]);
		
		assertEquals("Graph has wrong # of nodes", 3, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 2, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 2, mp.getNumBooks());
	}

	@Test
	public void testCharData(){
		mp.clear();
		Map<String, Map<String, Float>> chData = mp.getCharData();
		Map<String, Float> connections;
		assertEquals("Map has chars after clear", 0, chData.size());

		mp.addCharacter(chars[0], books[0]);
		assertEquals("Map has wrong # of chars", 1, chData.size());
		connections = chData.get(chars[0]);
		assertNotEquals("Map has no data map for " + chars[0], null, connections);
		assertEquals("Submap has " + connections.size() + " connections for " 
				+ chars[0] + " instead of 0", 0, connections.size());
		mp.addCharacter(chars[0], books[0]);
		assertEquals("(2) Map has wrong # of chars", 1, chData.size());
		connections = chData.get(chars[0]);
		assertNotEquals("(2) Map has no data map for " + chars[0], null, connections);
		assertEquals("(2) Submap has " + connections.size() + " connections for " 
				+ chars[0] + " instead of 0", 0, connections.size());

		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[1], books[0]);
		assertEquals("Map has wrong # of chars", 2, chData.size());
		for(int i = 0; i < 2; i++){
			connections = chData.get(chars[i]);
			assertNotEquals("Map has no data map for " + chars[i], null, connections);
			assertEquals("Submap has " + connections.size() + " connections for " 
					+ chars[i] + " instead of 1", 1, connections.size());
		}

		mp.clear();
		mp.addCharacter(chars[0], books[0]);
		mp.addCharacter(chars[1], books[0]);
		mp.addCharacter(chars[2], books[0]);
		assertEquals("Map has wrong # of chars", 3, chData.size());
		for(int i = 0; i < 3; i++){
			connections = chData.get(chars[i]);
			assertNotEquals("Map has no data map for " + chars[i], null, connections);
			assertEquals("Submap has " + connections.size() + " connections for " 
					+ chars[i] + " instead of 2", 2, connections.size());
		}

	}


//============================================================================
//clear

	@Test
	public void testClear() {
		mp.clear();
		assertEquals("Graph has nodes after clear", 0, mp.getNumNodes());
		assertEquals("Graph has edges after clear", 0, mp.getNumEdges());
		assertEquals("Graph has books after clear", 0, mp.getNumBooks());

		mp.createNewGraph("hw6/data/two_nodes.csv");
		mp.clear();
		assertEquals("Graph has nodes after clear", 0, mp.getNumNodes());
		assertEquals("Graph has edges after clear", 0, mp.getNumEdges());
		assertEquals("Graph has books after clear", 0, mp.getNumBooks());
	}

//============================================================================
//addCharacter

	@Test
	public void testAddCharacter(){
		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[i]);
			assertEquals("Graph has wrong # of nodes", i+1, mp.getNumNodes());
			assertEquals("Graph has wrong # of edges", 0, mp.getNumEdges());
			assertEquals("Graph has wrong # of books", i+1, mp.getNumBooks());
		}

		mp.clear();
		for(int i = 0; i < 6; i++) {
			mp.addCharacter(chars[0], books[i]);
			assertEquals("Graph has wrong # of nodes", 1, mp.getNumNodes());
			assertEquals("Graph has wrong # of edges", 0, mp.getNumEdges());
			assertEquals("Graph has wrong # of books", i+1, mp.getNumBooks());
		}

		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[0]);
			assertEquals("Graph has wrong # of nodes", i+1, mp.getNumNodes());
			assertEquals("Graph has wrong # of edges", summation(i), mp.getNumEdges());
			assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
		}
	}

	//mini recursive summation helper function
	public static int summation(int n){
		if(n == 0){ return 0; }
		return n + summation(n-1);
	}

	@Test
	public void testAddEdge(){
		mp.clear();
		boolean thrown = false;
		try{
			mp.addEdge("a", "b", -1.0f);
		} catch(RuntimeException e){
			thrown = true;
		}
		assertTrue("Didn't catch negative edge weight", thrown);
		assertEquals("No nodes should have been added", 0, mp.getNumNodes());
		assertEquals("No edges should have been added", 0, mp.getNumEdges());

		boolean added = false;
		added = mp.addEdge(chars[0], chars[1], 1.0f);
		assertTrue("Edge wasn't added to the graph", added);
		assertEquals("Graph has wrong # of nodes", 2, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 1, mp.getNumEdges());
		 
		added = mp.addEdge(chars[0], chars[1], 1.0f);
		assertFalse("Function added an invalid edge", added);
		assertEquals("Graph added duplicate nodes", 2, mp.getNumNodes());
		assertEquals("Graph added duplicate edge", 1, mp.getNumEdges());
		
		added = mp.addEdge(chars[0], chars[1], 2.0f);
		assertTrue("Edge wasn't added to the graph", added);
		assertEquals("Graph added duplicate nodes", 2, mp.getNumNodes());
		assertEquals("Graph didn't add differently weighted edge", 2, mp.getNumEdges());
		
		added = mp.addEdge(chars[0], chars[2], 1.0f);
		assertTrue("Edge wasn't added to the graph", added);
		assertEquals("Graph has wrong # of nodes", 3, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 3, mp.getNumEdges());

		added = mp.addEdge(chars[1], chars[2], 1.0f);
		assertTrue("Edge wasn't added to the graph", added);
		assertEquals("Graph has wrong # of nodes", 3, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 4, mp.getNumEdges());

	}

//============================================================================
	
	@Test
	public void testPathCompare(){
		Path a = mp.new Path("a"); a.totalCost = 0f;
		Path b = mp.new Path("b"); b.totalCost = 0f;
		
		assertEquals("a isn't equal to b when it should be", 0, a.compareTo(b));
		assertEquals("Compare isn't associative when equal", a.compareTo(b), b.compareTo(a));

		a.totalCost = 2f;
		b.totalCost = 1f;
		assertTrue("Math has failed", a.totalCost > b.totalCost);
		assertEquals("a isn't greater than b when it should be", 1, a.compareTo(b));
		assertEquals("b isn't less than a when it should be", -1, b.compareTo(a));
	}
}















