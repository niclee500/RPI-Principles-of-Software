package hw7;

import java.util.*;

public final class BuildingObserver implements Observer {
	private CampusData cpd;
	
	public BuildingObserver(CampusData campuspaths){
		cpd = campuspaths;
		campuspaths.addObserver(this); 
	}

	public void update(Observable obj, Object arg) {
		if(obj != cpd){ System.out.println("B_Obsv: Not my object!"); return; }
		if ( arg instanceof Building ) {
			Building bldg = (Building) arg;
			if(bldg.isIntersection()){ return; }
			System.out.println(bldg.getName() + "," + bldg.getID());
		} else if ( arg instanceof RuntimeException ) {
			RuntimeException e = (RuntimeException)arg;
			System.out.println("Unknown building: [" + e.getMessage() + "]");
		}
	 }

}




