package hw7;

import java.util.*;

public final class PathObserver implements Observer {
	private CampusData cpd;
	
	public PathObserver(CampusData campuspaths){
		cpd = campuspaths;
		campuspaths.addObserver(this); 
	}

	public void update(Observable obj, Object arg) {
		if ( arg instanceof Path ) {
			Path path = (Path)arg;
			Building start = path.getStart();
			Building goal = path.getGoal();
			if(start.isIntersection() || goal.isIntersection()){
				if(start.getID().equals(goal.getID())){
					System.out.println("Unknown building: [" + start.getID() + "]");
				} else{ 
					if (start.isIntersection()){
						System.out.println("Unknown building: [" + start.getID() + "]");
					}
					if (goal.isIntersection()) {
						System.out.println("Unknown building: [" + goal.getID() + "]");
					}
				}
			} else if( goal.compareTo(path.getEnd()) != 0 ){
				System.out.println("There is no path from " + start.getName() + " to " + goal.getName() + ".");
			} else{
				String previous = start.getName();
				System.out.println("Path from " + start.getName() + " to " + goal.getName() + ":");
				for (Building bldg : path) {
					if(bldg.compareTo(start) == 0){ continue; }
					String name = bldg.getName(); String intersect = "";
					if(bldg.isIntersection()){ name = bldg.getID(); intersect = "Intersection "; }
					String dir = cpd.getCompassDirection(previous, name);
					System.out.println("\tWalk " + dir + " to (" + intersect + name + ")");
					previous = name;
				}
				System.out.println(String.format("Total distance: %.3f pixel units.", path.getCost()));
			}
			
		}

	}
	

}




