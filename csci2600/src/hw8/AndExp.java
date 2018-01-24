package hw8;

public final class AndExp extends CompositeExp {
	AndExp(BooleanExp leftExp, BooleanExp rightExp) {
		left = leftExp;
		right = rightExp;
	}

	boolean evaluate(Context context){ 
		boolean lVal = left.evaluate(context);
		boolean rVal = right.evaluate(context);
		return lVal && rVal;
	}

	String type(){ return "AND"; }

	int getExpressionCode(){ return BooleanExp.AND; }

	Object accept(Visitor v){
		return v.visit(this);
	}

}




