package hw8;

public final class Constant extends BooleanExp {
	Boolean value;
	Constant(Boolean value) { this.value=value; }
	public boolean getValue() { return value.booleanValue(); }
	
	boolean evaluate(Context context){ return this.getValue(); }
	String printPreorder(){ return print(); }
	String printInorder(){ return print(); }
	
	private String print(){
		String r;
		if(value.booleanValue()){
			r = "true";
		} else{
			r = "false";
		}
		return r;
	}

	int getExpressionCode(){ return Integer.MAX_VALUE; }

	Object accept(Visitor v){
		return v.visit(this);
	}

} 



