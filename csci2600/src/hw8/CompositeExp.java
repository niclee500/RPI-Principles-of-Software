package hw8;

public abstract class CompositeExp extends BooleanExp {
	protected BooleanExp left;
	protected BooleanExp right;
	
	abstract String type();
	public BooleanExp getLeft() { return left; }
	public BooleanExp getRight() { return right; }

	String printPreorder(){ 
		String l = "";
		String r = "";
		if(left != null){ l = " " + left.printPreorder(); }
		if(right != null){ r = " " + right.printPreorder(); }
		return (this.type() + l + r);
	}
	String printInorder(){ 
		String lParens = "";
		String rParens = "";
		if(left != null){
			if(this.getExpressionCode() >= left.getExpressionCode()){
				lParens = "(" + left.printInorder() + ") ";
			} else{ lParens = left.printInorder() + " "; }
		}

		if(right != null){
			if(this.getExpressionCode() >= right.getExpressionCode()){
				rParens = " (" + right.printInorder() + ")";
			} else{ rParens = " " + right.printInorder(); }
		}
		
		return (lParens + type() + rParens);
	}

	

}




