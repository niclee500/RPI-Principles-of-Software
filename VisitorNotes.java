public static void main(String[] args) {
	BooleanExp myExp = 
		new AndExp( 
			new OrExp( new VarExp("x"), new VarExp("y") ),
			new VarExp("z")
		); // (x || y) && z
	Visitor v = new CounterVisitor();

	myExp.accept(v);
}

abstract class BooleanExp {
	abstract void accept(Visitor v);
}

class Constant extends BooleanExp {
	private boolean const;
	Constant(boolean const) { this.const=const; }
	void accept(Visitor v){
		v.visit(this);
	}
}

class VarExp extends BooleanExp {
	String varname;
	VarExp(String var) { this.varname = var; }
	void accept(Visitor v){
		v.visit(this);
	}
} 

class AndExp extends BooleanExp {
	private BooleanExp leftExp;
	private BooleanExp rightExp;
	AndExp(BooleanExp left, BooleanExp right) {
		leftExp = left;
		rightExp = right;
	}
	void accept(Visitor v) {
		leftExp.accept(v);
		rightExp.accept(v);
		v.visit(this);
	}
}

class OrExp extends BooleanExp {
	private BooleanExp leftExp;
	private BooleanExp rightExp;
	OrExp(BooleanExp left, BooleanExp right) {
		leftExp = left;
		rightExp = right;
	}
	void accept(Visitor v) {
		leftExp.accept(v);
		rightExp.accept(v);
		v.visit(this);
	}
}

class NotExp extends BooleanExp {
	private BooleanExp exp;
	NotExp(BooleanExp left) {
		exp = left;
	}
	void accept(Visitor v) {
		exp.accept(v);
		v.visit(this);
	}
} 

class Visitor {
	void visit(Constant e);
	void visit(VarExp e);
	void visit(NotExp e);
	void visit(AndExp e);
	void visit(OrExp e);
}

class CounterVisitor implements Visitor {
	int count = 0;
	void visit(Constant e){ count++; }
	void visit(VarExp e){ count++; }
	void visit(NotExp e){ count++; }
	void visit(AndExp e){ count++; }
	void visit(OrExp e){ count++; }
}

class EvaluateVisitor implements Visitor {
	// state, needed to evaluate
	Context ctx; ???
	boolean* state = null; ???
	boolean* left = null; ???
	boolean* right = null; ???

	EvaluateVisitor(Context c){ ctx = c; }
	void visit(Constant e){

	}
	void visit(VarExp e){
		ctx.lookup(e.varname);
	}
	void visit(NotExp e){
		
	}
	void visit(AndExp e){
		
	}
	void visit(OrExp e){
		
	}
}













