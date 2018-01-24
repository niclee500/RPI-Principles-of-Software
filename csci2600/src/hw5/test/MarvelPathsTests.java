package hw5.test;

import hw5.*;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public final class MarvelPathsTests{

	private static MarvelPaths mp = null;
	private static final String chars[] = {"JONES", "HAWK", "MARVEL", "ROSS", "3DMAN", "WOODGOD"};
	private static final String books[] = {"AA2 35", "H2 252", "AVF 4", "COC 1", "WI 9", "T 208"};

	@BeforeClass
	public static void setupForGraphTests() throws Exception {
		mp = new MarvelPaths();
	}

//============================================================================
//createNewGraph

	@Test
	public void testSingleNode(){
		mp.clear();
		mp.createNewGraph("hw5/data/one_node.csv");
		assertEquals("Graph has wrong # of nodes", 1, mp.getNumNodes());
		assertEquals("Graph has edges", 0, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testTwoAndTwo(){
		mp.clear();
		mp.createNewGraph("hw5/data/two_nodes_two_edges.csv");
		assertEquals("Graph has wrong # of nodes", 2, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 2, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testSingleBook(){
		mp.clear();
		mp.createNewGraph("hw5/data/singlebook.csv");
		assertEquals("Graph has wrong # of nodes", 4, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 12, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

	@Test
	public void testCreateChain(){
		mp.clear();
		mp.createNewGraph("hw5/data/chain.csv");
		assertEquals("Graph has wrong # of nodes", 5, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 8, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 4, mp.getNumBooks());
	}

	@Test
	public void testCreateSmall(){
		mp.clear();
		mp.createNewGraph("hw5/data/small.csv");
		assertEquals("Graph has wrong # of nodes", 6, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 24, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 2, mp.getNumBooks());
	}

	@Test
	public void testCreateMedium(){
		mp.clear();
		mp.createNewGraph("hw5/data/medium.csv");
		assertEquals("Graph has wrong # of nodes", 25, mp.getNumNodes());
		assertEquals("Graph has wrong # of edges", 58, mp.getNumEdges());
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
			String expected = "path from " + chars[i] + " to " + chars[i] + ":\n";
			String out = mp.findPath(chars[i], chars[i]);
			assertEquals("Incorrect output for reflexive path", expected, out);
		}

		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[0], books[i]);
			String expected = "path from " + chars[0] + " to " + chars[0] + ":\n";
			String out = mp.findPath(chars[0], chars[0]);
			assertEquals("Incorrect output for reflexive path", expected, out);
		}

		mp.clear();
		for(int i = 0; i < 6; i++){
			mp.addCharacter(chars[i], books[0]);
			String expected = "path from " + chars[i] + " to " + chars[i] + ":\n";
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
									+ chars[i] + " to " + chars[j] + " via " + books[0] + "\n";
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
						+ chars[0] + " to " + chars[1] + " via " + books[0] + "\n"
						+ chars[1] + " to " + chars[2] + " via " + books[1] + "\n"
						+ chars[2] + " to " + chars[3] + " via " + books[2] + "\n";
		assertEquals("Incorrect chain implementation.", expected, out);
	}

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
//getNumNodes + getNumEdges

	@Test
	public void testNumNodesAndEdges(){
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
		assertEquals("Graph has wrong # of edges", 2, mp.getNumEdges());
		assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
	}

//============================================================================
//clear

	@Test
	public void testClear() {
		mp.clear();
		assertEquals("Graph has nodes after clear", 0, mp.getNumNodes());
		assertEquals("Graph has edges after clear", 0, mp.getNumEdges());
		assertEquals("Graph has books after clear", 0, mp.getNumBooks());

		mp.createNewGraph("hw5/data/two_nodes_two_edges.csv");
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
			assertEquals("Graph has wrong # of edges", i*(i+1), mp.getNumEdges());
			assertEquals("Graph has wrong # of books", 1, mp.getNumBooks());
		}

	}

//============================================================================

}












