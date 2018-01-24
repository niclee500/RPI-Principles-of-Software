package hw8.test;

import hw8.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

public class ExpressionTest {

	private static ExpressionParser parser = null;
	private static HashMap<String,Boolean> contextMap = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	parser = new ExpressionParser();
    	contextMap = new HashMap<String,Boolean>();
    	contextMap.put("x", true);
    	contextMap.put("y", false);
    	contextMap.put("z", true);
    	contextMap.put("w", false);
    }

	@Test
	public void test0() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("x");
		parser.init(e,contextMap);
		assertEquals(e.toString(),"x");
		assertEquals(parser.evaluate(),true);
		assertEquals(parser.print(false),"x");
		assertEquals(parser.print(true),"x"); //ANA: new
	}
    
	@Test
	public void test1() {
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("true");
		parser.init(e,contextMap);
		assertEquals(e.toString(),"true");
		assertEquals(parser.evaluate(),true);
		assertEquals(parser.print(false),"true");
		assertEquals(parser.print(true),"true"); //ANA: new
	}
	
	@Test
	public void test2() {
		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("OR AND x y z");
		assertEquals(e.toString(),"OR AND x y z");
		parser.init(e, contextMap);
		assertEquals(parser.evaluate(),true);
		assertEquals(parser.print(false),"x AND y OR z");
		assertEquals(parser.print(true),"OR AND x y z"); //ANA: new
	}

	@Test
	public void test3() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND OR x y OR x w");
		assertEquals(e.toString(),"AND OR x y OR x w");
		parser.init(e, contextMap);
		assertEquals(parser.evaluate(),true);
		assertEquals(parser.print(false),"(x OR y) AND (x OR w)");
		assertEquals(parser.print(true),"AND OR x y OR x w");

	}

	@Test
	public void test4() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("NOT x");
		assertEquals(e.toString(),"NOT x");
		parser.init(e, contextMap);
		assertEquals(parser.evaluate(),false);
		assertEquals(parser.print(false),"NOT x");

	}
	
	@Test
	public void test5() {		
		ExpressionParser.position = 0;
		BooleanExp e = ExpressionParser.parse("AND NOT x OR x w");
		assertEquals(e.toString(),"AND NOT x OR x w");
		parser.init(e, contextMap);
		assertEquals(parser.evaluate(),false);
		assertEquals(parser.print(false),"NOT x AND (x OR w)");

	}
	
}
