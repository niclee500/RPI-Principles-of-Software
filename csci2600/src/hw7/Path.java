package hw7;

import hw4.Edge;
import java.util.*;

public class Path extends Observable implements Comparable<Path>, Iterable<Building>{
	private Building goal;
	private Double totalCost;
	private Vector<Building> buildings;
	private Vector<Edge<Building,Double>> edges;

	public Path(Building sn, Building g){
		goal = g;
		totalCost = 0.0;
		buildings = new Vector<Building>();
		buildings.add(sn);
		edges = new Vector<Edge<Building,Double>>(); 
	}
	public Path(Path p){
		goal = p.goal;
		totalCost = p.totalCost; 
		buildings = new Vector<Building>(p.buildings);
		edges = new Vector<Edge<Building,Double>>(p.edges);
	}

	public Building getStart(){ return buildings.firstElement(); }
	public Building getEnd(){ return buildings.lastElement(); }
	public Building getGoal(){ return goal; }
	public Double getCost(){ return totalCost; }
	public int getPathLength(){ return edges.size(); }
	public void doSomething(){ 
		this.setChanged();
		notifyObservers(totalCost); System.out.println("I'm doing something!"); }

	public boolean addEdgeToPath(Edge<Building, Double> e){
		Building newEnd; Edge<Building, Double> newEdge;
		if( getEnd().compareTo(e.getStart()) == 0 ){ newEnd = e.getEnd(); newEdge = e; } 
		else if( getEnd().compareTo(e.getEnd()) == 0 ){ newEnd = e.getStart(); newEdge = e.flip(); }
		else{ return false; }
		totalCost += e.getLabel();
		buildings.add(newEnd);
		edges.add(newEdge);
		return true;
	}

	@Override
	public int compareTo(Path p) { 
		Double a = this.totalCost;
		Double b = p.totalCost;
		return a.compareTo(b);
	}

	/**
	 * 	@return: An iterator over the set of buildings (the nodes in the graph)
	 */
	public Iterator<Building> iterator(){
		return buildings.iterator();
	}

}












