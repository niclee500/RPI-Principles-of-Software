package hw7;

public final class Building implements Comparable<Building>{
	private String name;
	private String id;
	private double xcoord;
	private double ycoord;

	public Building(String n, String i, double x, double y){
		name = new String(n);
		id = new String(i);
		xcoord = x;
		ycoord = y;
	}
	public Building(Building b2){
		name = new String(b2.name);
		id = new String(b2.id);
		xcoord = b2.xcoord;
		ycoord = b2.ycoord;
	}

	public boolean isIntersection(){ return (name.equals("")); }
	public String getName(){ return new String(name); }
	public String getID(){ return new String(id); }
	public double getX(){ return xcoord; }
	public double getY(){ return ycoord; }
	
	@Override
	public int compareTo(Building b) { 
		if( this.name.equals(b.name) ){
			return this.id.compareTo(b.id);
		} else{
			return this.name.compareTo(b.name);
		}
	}


}













