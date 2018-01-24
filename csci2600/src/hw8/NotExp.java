package hw8;

public final class NotExp extends CompositeExp {
	NotExp(BooleanExp exp) {
		left = null;
		right = exp;
	}

	boolean evaluate(Context context){ 
		boolean rVal = right.evaluate(context);
		return !rVal;
	}

	String type(){ return "NOT"; }
	int getExpressionCode(){ return BooleanExp.NOT; }

	Object accept(Visitor v){
		return v.visit(this);
	}

}



