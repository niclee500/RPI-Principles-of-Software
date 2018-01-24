package hw8;

/**
 * 
 * BooleanExp is an immutable class. It represents boolean constants, boolean variables,
 * Or boolean expressions and And boolean expressions.
 *
 */
public abstract class BooleanExp {

	final static int CONST = 0;
	final static int VAR = 1;
	final static int OR = 2;
	final static int AND = 3;
	final static int NOT = 4;
	
	// Rep invariant:
	// exprCode is AND || OR || CONST || VAR
	// if exprCode == AND || exprCode == OR then left != null and right != null and value = null and varString = null
	// else if exprCode == CONST then left == null and right == null and value != null and varString == null
	// else if exprCode == VAR then left == null and right == null and value == null and varString != null
	
	// private BooleanExp left;
	// private BooleanExp right;
	// private int exprCode;
	// private Boolean value;
	// private String varString;
	
	// public BooleanExp getLeft() {
	// 	return left;
	// }
	// public BooleanExp getRight() {
	// 	return right;
	// }
	// public int getExpressionCode() { 
	// 	return exprCode;
	// }
	// public boolean getValue() {
	// 	return value.booleanValue();
	// }
	// public String getVarString() {
	// 	return varString;
	// }
	
	// public BooleanExp(int exprCode, BooleanExp left, BooleanExp right, Boolean value, String varString) {
	// 	this.left = left;
	// 	this.right = right;
	// 	this.value = value;
	// 	this.varString = varString;
	// 	this.exprCode = exprCode; 
	// }

	abstract Object accept(Visitor v);
	abstract int getExpressionCode();
	abstract boolean evaluate(Context context);
	abstract String printPreorder();
	abstract String printInorder();

	/**
	 * @return: String corresponding to Preorder of this BooleanExp
	 * E.g., if BooleanExp is CONST with value true, result is "true"
	 * If BooleanExp is AND with left VAR "x" and right VAR "x", 
	 * result is "AND x y"  
	 */
	public String toString() {
		return this.printPreorder();
		// StringBuffer result = new StringBuffer();	
		// switch (getExpressionCode()) {
		// 	case BooleanExp.AND: 
		// 		result.append("AND ");
		// 		result.append(getLeft().toString());
		// 		result.append(" ");
		// 		result.append(getRight().toString());
		// 		break;
		// 	case BooleanExp.OR:
		// 		result.append("OR ");
		// 		result.append(getLeft().toString());
		// 		result.append(" ");
		// 		result.append(getRight().toString());
		// 		break;
		// 	case BooleanExp.CONST:
		// 		result.append(getValue());
		// 		break;
		// 	case BooleanExp.VAR:
		// 		result.append(getVarString());
		// 		break;
		// }
		// return result.toString();
	}
			
}






