package hw7;

import java.util.*;
import java.io.*;

public class CampusParser {

	/** 
	 *  @param: filename The path to the "CSV" file that contains the building data                                                                                                
	 *  @param: buildings The Set that stores parsed buildings
	 *  @effects: adds parsed buildings to the Set buildings
	 *	@return: the ID number of the first intersection (all ID#s below are buildings, all above are intersections)
	 *  @throws: IOException if file cannot be read or file not a CSV file                                                                                     
	 */
	@SuppressWarnings("resource")
	public static int readNodeData(String filename, Set<Building> buildings) 
			throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		int sectionbreak = -1;
		while ((line = reader.readLine()) != null) {
			String[] tokens = line.split(",");
			int i = line.indexOf(",");

			if( (i == -1) || (tokens.length != 4) ){
				throw new IOException("File "+filename+" not a CSV (name, id, xcoordinate, ycoordinate) file.");
			}
			if( i == 0 && sectionbreak <= 0 ){ 
				sectionbreak = Integer.parseInt(tokens[1]);
			}
			
			String name = tokens[0];
			String id = tokens[1];
			float xcoord = Float.parseFloat(tokens[2]);
			float ycoord = Float.parseFloat(tokens[3]);

			Building b = new Building(name, id, xcoord, ycoord);
			buildings.add(b);
		}
		reader.close();
		return sectionbreak;
	}

	/** 
	 *  @param: filename The path to the "CSV" file that contains the building data                                                                                                
	 *	@param: edgeEndPairs The List to store the parsed connections
	 *  @effects: puts the parsed pairs of building id#'s in the edgeEndPairs List
	 *  @throws: IOException if file cannot be read or file not a CSV file                                                                                 
	 */
	public static void readEdgeData(String filename,  List<Pair<String>> edgeEndPairs) 
			throws IOException {

		//@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;

		while ((line = reader.readLine()) != null) {
			int i = line.indexOf(",");            
			String b1 = line.substring(0,i);
			String b2 = line.substring(i+1);

			Pair<String> ends = new Pair<String>(b1, b2);
			edgeEndPairs.add(ends);
		}
		
		reader.close();
	}

}









