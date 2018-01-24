package hw7;

import hw4.Graph;
import hw4.Edge;

import java.io.*;
import java.util.*;

public class CampusData extends Observable implements Iterable<Building>{
	private Graph<Building, Double> graph;
	private Map<String, Building> buildingsByID;
	
	public CampusData(){
		graph = new Graph<Building, Double>();
		buildingsByID = new TreeMap<String, Building>();
	}
	public CampusData(String nodeFilename, String edgeFilename){
		graph = new Graph<Building, Double>();
		buildingsByID = new TreeMap<String, Building>();
		buildGraph(nodeFilename, edgeFilename);
	}

	/** 
	 *	Creates a new graph with data from nodeFilename and edgeFilename, where 
	 *	nodeFilename and edgeFilename are data files formatted to contain building
	 *	location information and connections between buildings, respectively. 
	 *	@param: nodeFilename The name of the file with building data to be read from
	 *	@param: edgeFilename The name of the file with path/connection data to be read from
	 *	@effects: creates a new graph from the data in the files
	 */
	public void buildGraph(String nodeFilename, String edgeFilename){
		clear();
		graph = new Graph<Building, Double>();
		Set<Building> buildings = new TreeSet<Building>();
		try{
			CampusParser.readNodeData(nodeFilename, buildings);
			for (Building bldg : buildings) {
				graph.addNode(bldg);
				buildingsByID.put(bldg.getID(), bldg);
			}
			List<Pair<String>> edgeEndPairs = new ArrayList<Pair<String>>();
			CampusParser.readEdgeData(edgeFilename, edgeEndPairs);
			for (Pair<String> pair : edgeEndPairs) {
				Building b1 = buildingsByID.get(pair.left);
				Building b2 = buildingsByID.get(pair.right);
				Double dist = new Double(Calculations.getDistance(b1, b2));
				graph.addEdge( new Edge<Building, Double>(b1, b2, dist) );
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public Building findBuilding(String nameOrID){
		Building building = null;
		boolean isID = true;
		try{
			Integer.parseInt(nameOrID);
		} catch(NumberFormatException nfe){ isID = false; }
		if(isID){
			building = buildingsByID.get(nameOrID);
		} else{
			for (Building bldg : this) {
				if( nameOrID.equals(bldg.getName()) ){
					building = bldg;
					break;
				}
			}
		}
		return building;
	}

	public Path findMinPath(String start, String end){
		Building b1 = findBuilding(start);
		Building b2 = findBuilding(end);
		boolean invalidBuilding = false;
		if(b1 == null){ setChanged(); notifyObservers(new RuntimeException(start)); invalidBuilding = true; }
		if(b2 == null){ setChanged(); notifyObservers(new RuntimeException(end)); invalidBuilding = true; }
		if(invalidBuilding){ Building b = new Building(start,start,-1,-1); return new Path(b, b); }
		Path minPath = Calculations.findPath(b1, b2, graph);
		setChanged();
		notifyObservers(minPath);
		return minPath;
	}

	public void listBuildingsByName(){
		for (Building bldg : this) {
			setChanged();
			notifyObservers(bldg);
		}
	}

	public void listBuildingsByID(){
		for (String id : buildingsByID.keySet()) {
			Building bldg = buildingsByID.get(id);
			setChanged();
			notifyObservers(bldg);
		}
	}

	public String getCompassDirection(String start, String end){
		Building b1 = findBuilding(start);
		Building b2 = findBuilding(end);
		boolean invalidBuilding = false;
		if(b1 == null){ setChanged(); notifyObservers(new RuntimeException(start)); invalidBuilding = true; }
		if(b2 == null){ setChanged(); notifyObservers(new RuntimeException(end)); invalidBuilding = true; }
		if(invalidBuilding){ return ""; }

		double angle = Calculations.getAngleDegrees(b1, b2);
		String dir = "";
		if( -157.5 <= angle && angle < -112.5 ){ dir = "NorthWest"; }
		else if( -112.5 <= angle && angle < -67.5 ){ dir = "West"; }
		else if( -67.5 <= angle && angle < -22.5 ){ dir = "SouthWest"; }
		else if( -22.5 <= angle && angle < 22.5 ){ dir = "South"; }
		else if( 22.5 <= angle && angle < 67.5 ){ dir = "SouthEast"; }
		else if( 67.5 <= angle && angle < 112.5 ){ dir = "East"; }
		else if( 112.5 <= angle && angle < 157.5 ){ dir = "NorthEast"; }
		else if( 157.5 <= angle || angle < -157.5 ){ dir = "North"; }
		//dir = String.format(dir + " (%.3f degrees)", angle);
		return dir;
	}

	/**
	 * 	@return: An iterator over the set of buildings (the nodes in the graph)
	 */
	public Iterator<Building> iterator(){
		return graph.nodeIterator();
	}

	/**
	 *	@modifies: this
	 *	@effects: clears the buildinglist and the graph
	 */
	public void clear(){
		graph.clear();
		buildingsByID.clear();
	}

}

//=======================================================
// Graph member functions
/*
Iterator<N> nodeIterator()
Iterator<Edge<N, L>> edgeIterator()
boolean addNode(N n)
boolean addEdge(Edge<N,L> e)
void clear()
int getNumNodes()
int getNumEdges()
boolean containsNode(N n)
containsEdge(Edge<N,L> e)
*/

//=======================================================
// Edge member functions 
/*
Edge(N st, N en, L lb)
Edge(Edge<N,L> e2)
boolean isReflexive()
N getStart()
N getEnd()
L getLabel()
Edge<N,L> flip()
int compareTo(Edge<N,L> ed)
*/






















