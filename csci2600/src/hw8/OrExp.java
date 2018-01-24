package hw8;

public final class OrExp extends CompositeExp {
	OrExp(BooleanExp leftExp, BooleanExp rightExp) {
		left = leftExp;
		right = rightExp;
	}

	boolean evaluate(Context context){ 
		boolean lVal = left.evaluate(context);
		boolean rVal = right.evaluate(context);
		return lVal || rVal;
	}

	String type(){ return "OR"; }
	int getExpressionCode(){ return BooleanExp.OR; }

	Object accept(Visitor v){
		return v.visit(this);
	}
	
}



