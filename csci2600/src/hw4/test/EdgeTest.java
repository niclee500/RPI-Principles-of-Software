package hw4.test;

import hw4.*;
import static org.junit.Assert.*;
//import org.junit.BeforeClass;
import org.junit.Test;

public final class EdgeTest{

//============================================================================
//============================================================================
//Constructors

	@Test
	public void testEdgeConstructor(){
		//normal
		new Edge<String, String>("a", "b", "1");
		new Edge<String, String>("a", "b", "5");
		new Edge<String, String>("b", "c", "10");

		//same start+end node
		new Edge<String, String>("a", "a", "3");
		new Edge<String, String>("b", "b", "6");
		new Edge<String, String>("b", "b", "8");
	}

	// @Test(expected=IllegalArgumentException.class)
	// public void testEdgeConstructorWithIllegalWeight(){
	// 	new Edge("a", "a", "-1");
	// }
	
	@Test
	public void testReflexive(){
		Edge<String, String> e1 = new Edge<String, String>("a", "b", "1");
		assertFalse("e1 thinks it's reflexive", e1.isReflexive());
		Edge<String, String> e2 = new Edge<String, String>("a", "b", "5");
		assertFalse("e2 thinks it's reflexive", e2.isReflexive());

		Edge<String, String> e3 = new Edge<String, String>("a", "a", "3");
		assertTrue("e3 says it's not reflexive", e3.isReflexive());
		Edge<String, String> e4 = new Edge<String, String>("b", "b", "6");
		assertTrue("e4 says it's not reflexive", e4.isReflexive());
	}

//============================================================================
//============================================================================
//Equals and HashCode
/*	
	@Test
	public void testEqualsNullObject(){
		Edge e1 = new Edge("a", "b", "1");
		assertFalse("e1 equals null", e1.equals(null));
		Edge e2 = new Edge("a", "b", "5");
		assertFalse("e2 equals null", e2.equals(null));
	}

	@Test
	public void testEqualsNonEdgeObject(){
		int l = 1;
		String a = "a";
		Edge e1 = new Edge("a", "b", "1");
		assertFalse("e1 equals 1", e1.equals(l));
		assertFalse("e1 equals a", e1.equals(a));
	}

	@Test
	public void testEqualsAndHashCodeReflexive(){
		Edge e1 = new Edge("a", "b", "5");
		assertTrue("e1 != e1", e1.equals(e1));
		assertTrue("e1.hashCode != e1.hashCode", e1.hashCode() == e1.hashCode());
		Edge e2 = new Edge("a", "a", "3");
		assertTrue("e2 != e2", e2.equals(e2));
		assertTrue("e2.hashCode != e2.hashCode", e2.hashCode() == e2.hashCode());
	}

	@Test
	public void testEqualsAndHashCodeSymmetric(){
		Edge e1 = new Edge("a", "b", "5");
		Edge e2 = new Edge("a", "b", "5");
		Edge e3 = new Edge("a", "a", "3");
		assertTrue("e1 != e2", e1.equals(e2));
		assertTrue("e1.hashCode != e2.hashCode", e1.hashCode() == e2.hashCode());
		assertTrue("e2 != e1", e2.equals(e1));
		assertTrue("e2.hashCode != e1.hashCode", e2.hashCode() == e1.hashCode());

		assertFalse("e2 == e3", e2.equals(e3));
		assertFalse("e2.hashCode == e3.hashCode", e2.hashCode() == e3.hashCode());
		assertFalse("e3 == e2", e3.equals(e2));
		assertFalse("e3.hashCode == e2.hashCode", e3.hashCode() == e2.hashCode());
	}

	@Test
	public void testEqualsAndHashCodeGeneral(){
		Edge e1 = new Edge("a", "b", "3");
		Edge e2 = new Edge("a", "b", "4");
		Edge e3 = new Edge("a", "c", "3");
		Edge e4 = new Edge("c", "b", "3");
		Edge e5 = new Edge("a", "b", "3");

		//same start+end, different label
		assertFalse("e1 == e2", e1.equals(e2));
		assertFalse("e1.hashCode == e2.hashCode", e1.hashCode() == e2.hashCode());
		//same start+label, different end
		assertFalse("e1 == e3", e1.equals(e3));
		assertFalse("e1.hashCode == e3.hashCode", e1.hashCode() == e3.hashCode());
		//same end+label, different start
		assertFalse("e1 == e4", e1.equals(e4));
		assertFalse("e1.hashCode == e4.hashCode", e1.hashCode() == e4.hashCode());
		//all same, ie. actually equal
		assertTrue("e1 != e5", e1.equals(e5));
		assertTrue("e1.hashCode != e5.hashCode", e1.hashCode() == e5.hashCode());
	}
*/
//============================================================================
//============================================================================
//Accessors
	
	@Test
	public void testAccessors(){
		Edge<String, String> e1 = new Edge<String, String>("a", "b", "3");
		assertEquals("e1 getStart", "a", e1.getStart());
		assertEquals("e1 getEnd", "b", e1.getEnd());
		assertEquals("e1 getLabel", "3", e1.getLabel());

		Edge<String, String> e2 = new Edge<String, String>("c", "d", "6");
		assertEquals("e2 getStart", "c", e2.getStart());
		assertEquals("e2 getEnd", "d", e2.getEnd());
		assertEquals("e2 getLabel", "6", e2.getLabel());

		Edge<String, String> e3 = new Edge<String, String>("e", "f", "9");
		assertEquals("e3 getStart", "e", e3.getStart());
		assertEquals("e3 getEnd", "f", e3.getEnd());
		assertEquals("e3 getLabel", "9", e3.getLabel());
	}
}





