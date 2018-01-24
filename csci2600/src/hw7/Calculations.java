package hw7;

import hw4.Edge;
import hw4.Graph;
import java.util.*;

public class Calculations {

	public static double getDistance(double x1, double y1, double x2, double y2){
		double dx = x2 - x1;
		double dy = y2 - y1;
		double distance = (double)Math.sqrt( (dx*dx) + (dy*dy) );
		return distance;
	}
	public static double getDistance(Building b1, Building b2){
		double dx = b2.getX() - b1.getX();
		double dy = b2.getY() - b1.getY();
		double distance = Math.sqrt( (dx*dx) + (dy*dy) );
		return distance;
	}

	public static double getAngleDegrees(double x1, double y1, double x2, double y2){
		double dx = x2 - x1;
		double dy = y2 - y1;
		double angle = Math.toDegrees(Math.atan2(dx, dy));
		return angle;
	}
	public static double getAngleDegrees(Building b1, Building b2){
		double dx = b2.getX() - b1.getX();
		double dy = b2.getY() - b1.getY();
		double angle = Math.toDegrees(Math.atan2(dx, dy));
		return angle;
	}

	/**
	 *  @param: start The starting node for the path
	 *  @param: dest The destination node for the path
	 *	@param: graph The graph with which to connect start and end
	 *  @return: A Path from start to end
	 */
	public static Path findPath(Building start, Building dest, Graph<Building, Double> graph){
		Path minPath = new Path(start, dest);
		if( !graph.containsNode(start) || !graph.containsNode(dest) ){ return minPath; }
		
		PriorityQueue<Path> pathSearchQueue = new PriorityQueue<Path>();
		Map<Building, Path> finished = new TreeMap<Building, Path>();

		pathSearchQueue.add(minPath);

		while(pathSearchQueue.size() != 0){
			minPath = pathSearchQueue.poll();
			Building minDest = minPath.getEnd();
			if( minDest.compareTo(dest) == 0 ){ break; }

			if( finished.containsKey(minDest) ){ continue; }
			
			Iterator<Edge<Building, Double>> edgeItr = graph.edgeIterator();
			while( edgeItr.hasNext() ){
				Edge<Building, Double> edg = edgeItr.next();
				if( finished.containsKey(edg.getStart()) || finished.containsKey(edg.getEnd()) ){ continue; }
				Path nPath = new Path(minPath);
				nPath.addEdgeToPath(edg);
				pathSearchQueue.add(nPath);
			}
			finished.put(minDest, minPath);
		}
		return minPath;
	}


}












