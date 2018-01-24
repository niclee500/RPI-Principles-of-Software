package hw8;

public final class VarExp extends BooleanExp {
	String varString;
	VarExp(String var) { this.varString = var; }
	public String getVarString() { return varString; }

	boolean evaluate(Context context){ return context.lookup(varString); }
	String printPreorder(){ return varString; }
	String printInorder(){ return varString; }

	int getExpressionCode(){ return Integer.MAX_VALUE; }

	Object accept(Visitor v){
		return v.visit(this);
	}

}





