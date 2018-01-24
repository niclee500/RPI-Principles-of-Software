package hw8;

import java.util.*;

/** An immutable class, stores variable to {true,false} mappings
 */
class Context {

	HashMap<String,Boolean> map = new HashMap<String,Boolean>();
	boolean lookup(String var) {
		return map.get(var).booleanValue();
	}
	public Context(HashMap<String,Boolean> m) {
		map = new HashMap<>(m);
	}
}

interface Visitor {
	Object visit(Constant e);
	Object visit(VarExp e);
	Object visit(NotExp e);
	Object visit(AndExp e);
	Object visit(OrExp e);
}

class EvaluateVisitor implements Visitor {
	// state, needed to evaluate
	Context ctx;

	EvaluateVisitor(Context c){ ctx = c; }

	public Object visit(Constant e){
		return e.value;
	}
	public Object visit(VarExp e){
		return ctx.lookup(e.varString);
	}
	public Object visit(NotExp e){
    	Boolean rightValue = (Boolean) e.right.accept(this);
    	if(rightValue.booleanValue()){ return Boolean.FALSE; }
    	else{ return Boolean.TRUE; }
	}
	public Object visit(AndExp e){
		Boolean leftValue  = (Boolean) e.left .accept(this);
    	Boolean rightValue = (Boolean) e.right.accept(this);
    	if(leftValue.booleanValue() && rightValue.booleanValue()){ return Boolean.TRUE; }
    	else{ return Boolean.FALSE; }
	}
	public Object visit(OrExp e){
		Boolean leftValue  = (Boolean) e.left .accept(this);
    	Boolean rightValue = (Boolean) e.right.accept(this);
    	if(leftValue.booleanValue() || rightValue.booleanValue()){ return Boolean.TRUE; }
    	else{ return Boolean.FALSE; }
	}
}

class PrintInorderVisitor implements Visitor {
	public Object visit(Constant e){
		String r;
		if(e.value.booleanValue()){ r = "true"; }
		else{ r = "false"; }
		return r;
	}
	public Object visit(VarExp e){
		return e.varString;
	}
	public Object visit(NotExp e){
    	String rightValue = (String) e.right.accept(this);
		String rParens = "";
		if(e.getExpressionCode() >= e.right.getExpressionCode()){
			rParens = " (" + rightValue + ")";
		} else{ rParens = " " + rightValue; }
		return "NOT" + rParens;
	}
	public Object visit(AndExp e){
		String leftValue  = (String) e.left .accept(this);
    	String rightValue = (String) e.right.accept(this);
    	String lParens = "";
		String rParens = "";
		if(e.getExpressionCode() >= e.left.getExpressionCode()){
			lParens = "(" + leftValue + ") ";
		} else{ lParens = leftValue + " "; }

		if(e.getExpressionCode() >= e.right.getExpressionCode()){
			rParens = " (" + rightValue + ")";
		} else{ rParens = " " + rightValue; }
		return (lParens + "AND" + rParens);
	}
	public Object visit(OrExp e){
		String leftValue  = (String) e.left .accept(this);
    	String rightValue = (String) e.right.accept(this);
    	String lParens = "";
		String rParens = "";
		if(e.getExpressionCode() >= e.left.getExpressionCode()){
			lParens = "(" + leftValue + ") ";
		} else{ lParens = leftValue + " "; }

		if(e.getExpressionCode() >= e.right.getExpressionCode()){
			rParens = " (" + rightValue + ")";
		} else{ rParens = " " + rightValue; }
		return (lParens + "OR" + rParens);
	}
}

/**
 * Class ExpressionParser encapsulates a Boolean expression and a Context in 
 * which the expression is evaluated
 */
public class ExpressionParser {
	
	private BooleanExp expression;
	private Context context;
	
	/**
	 * This method is part of public interface. DO NOT REMOVE or change signature.
	 * 
	 * @param e is encapsulated Boolean expression
	 * @param hm is String->Boolean context map
	 */	
	public void init(BooleanExp e, HashMap<String,Boolean> hm) {
		expression = e;
		context = new Context(hm);
	}
	
		
	/** 
	  *  This method is part of public interface. DO NOT REMOVE or change signature.
	  * 
	  *  @param: str the string expression in preorder. E.g., AND OR x y z represents (x OR y) AND z
	  *  @return: returns the corresponding Boolean expression or null if str is invalid 
	  *  static "position" is used to avoid passing "position" as argument to the recursive calls
	  *  str must be a sequence of white-space separated strings, e.g., "OR x y", "AND OR x y OR z w"
	 */	
	public static int position;	
	public static BooleanExp parse(String str) {
		BooleanExp retExp;
		if (position >= str.length()) {
			return null;
		}
		String token;
		int i = str.indexOf(" ",position);

		// Read the next token from String str.
		if (i != -1)
			token = str.substring(position,i+1);
		else 
			token = str.substring(position);
		
		// Advance "position" beyond token
		position += token.length();

		// If token is AND, parse the left operand into "left", 
		// then parse the right operand into "right" and create 
		// an And Boolean Expression
		if (token.equals("AND ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) { 
				return null;
			}
			retExp = new AndExp(left,right);
		}
		else if (token.equals("OR ")) {
			BooleanExp left = parse(str);
			BooleanExp right = parse(str);
			if ((left == null) || (right == null)) {
				return null;
			}
			retExp = new OrExp(left,right);			
		}
		else if (token.equals("NOT ")){
			BooleanExp exp = parse(str);
			retExp = new NotExp(exp);
		}
		else if (token.equals("true") || token.equals("true ")) {
			retExp = new Constant(Boolean.TRUE);
		}
		else if (token.equals("false") || token.equals("false ")) {
			retExp = new Constant(Boolean.FALSE);
		}
		// Otherwise, the token is a variable (e.g., x, xyz). 
		// Get rid of the white space if necessary 
		else { 
		    if (token.charAt(token.length()-1)==' ') {
		    	token = token.substring(0,token.length()-1);
		    }
		    retExp = new VarExp(token);
		}
		return retExp;
			
	}
	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @return boolean value of the enclosed expression,
	 *         evaluated in Context context.
	 */	
	public boolean evaluate() {
		return evaluate(context, expression);		
	}

	/**
	 * This method is part of public interface. Do not remove or change signature.
	 * 
	 * @param preorder  If True, returns expression in Preorder, False returns Inorder 
	 * @return string corresponding to Preorder of expression if preorder is True
	 * 	   string corresponding to Inorder of expression otherwise
	 */	
	public String print(boolean preorder) {
		return print(preorder,expression);
	}
	
	/**
	 * 
	 * @param preorder
	 * @param exp
	 * @return string corresponding to Preorder of exp if preorder is True
	 * 	   string corresponding to Inorder of exp otherwise
	 */	
	private String print(boolean preorder, BooleanExp exp) {
		//StringBuffer result = new StringBuffer();
		if (preorder) { // print in Preorder
			return exp.printPreorder();
			/*switch (exp.getExpressionCode()) {
				case BooleanExp.AND: 
					result.append("AND ");
					result.append(print(preorder,exp.getLeft()));
					result.append(" ");
					result.append(print(preorder,exp.getRight()));
					break;
				case BooleanExp.OR:
					result.append("OR ");
					result.append(print(preorder,exp.getLeft()));
					result.append(" ");
					result.append(print(preorder,exp.getRight()));
					break;
				case BooleanExp.CONST:
					result.append(exp.getValue());
					break;
				case BooleanExp.VAR:
					result.append(exp.getVarString());
					break;
			}*/
		}			
		else { // print Inorder, getting rid of redundant parentheses
			return exp.printInorder();
			/*switch (exp.getExpressionCode()) {
				case BooleanExp.AND: 
					if (exp.getLeft().getExpressionCode() > BooleanExp.AND) {
						// if left operand is an expression of equal or lower precedence, parens are needed 
						result.append("("); result.append(print(preorder,exp.getLeft())); result.append(")");
					}
					else {
						// otherwise, i.e., of higher precedence, no parens needed
						result.append(print(preorder,exp.getLeft())); 
					}
					result.append(" AND ");
					if (exp.getRight().getExpressionCode() >= BooleanExp.AND) {
						result.append("("); result.append(print(preorder,exp.getRight())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getRight())); 
					}				
					break;
				case BooleanExp.OR:
					if (exp.getLeft().getExpressionCode() > BooleanExp.OR) {
						result.append("("); result.append(print(preorder,exp.getLeft())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getLeft())); 
					}
					result.append(" OR ");
					if (exp.getRight().getExpressionCode() >= BooleanExp.OR) {
						result.append("("); result.append(print(preorder,exp.getRight())); result.append(")");
					}
					else {
						result.append(print(preorder,exp.getRight())); 
					}				
					break;				
				case BooleanExp.CONST:
					result.append(exp.getValue());
					break;
				case BooleanExp.VAR:
					result.append(exp.getVarString());
					break;
			}*/
		}
		//return result.toString();
	}

	/**
	 * 
	 * @param context
	 * @param exp
	 * @return value of BooleanExp exp in Context context
	 */
	private boolean evaluate(Context context, BooleanExp exp) {
		
		// if (exp.getExpressionCode() == BooleanExp.AND) {
		// 		return evaluate(context, exp.getLeft()) && 
		// 				evaluate(context, exp.getRight()); 
		// }
		// else if (exp.getExpressionCode() == BooleanExp.OR) {
		// 		return evaluate(context, exp.getLeft()) || 
		// 				evaluate(context, exp.getRight());
		// }
		// else if (exp.getExpressionCode() == BooleanExp.CONST) {
		// 		return exp.getValue();
		// }
		// else if (exp.getExpressionCode() == BooleanExp.VAR) {
		// 		return context.lookup(exp.getVarString());
		// }
		return exp.evaluate(context);		
	}

		
	public boolean visitorEvaluate() {
		Visitor v = new EvaluateVisitor(context);
		Boolean b = (Boolean)expression.accept(v);
		return b.booleanValue();
	}
	
	public String visitorPrint() {
		Visitor v = new PrintInorderVisitor();
		String s = (String)expression.accept(v);
		return s;
	}
	
	
}











