package hw7;

import java.util.*;

public class CampusPaths {

	public static void main(String[] args) {
		String nodeDataFilename = "hw7/data/RPI_map_data_Nodes.csv";
		String edgeDataFilename = "hw7/data/RPI_map_data_Edges.csv";
		CampusData cpd = new CampusData(nodeDataFilename, edgeDataFilename);
		new BuildingObserver(cpd);
		new PathObserver(cpd);
		
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			String input = scan.nextLine();
			if(input.equals("q")){
				break;
			} else if(input.equals("m")){
				System.out.println("Available Menu Commands:");
				System.out.println("--'b' to print all buildings alphabetically");
				System.out.println("--'r' enter two building names/ids to get directions for the shortest path between them");
				System.out.println("--'m' to list menu items");
				System.out.println("--'q' to quit the game");
			} else if(input.equals("r")){
				//prompts for two building names/ids and gives directions
				System.out.print("First building id/name, followed by Enter: ");
				String start = scan.nextLine();
				System.out.print("Second building id/name, followed by Enter: ");
				String end = scan.nextLine();
				cpd.findMinPath(start, end);
			} else if(input.equals("b")){
				//prints all buildings alphabetically: name,id
				cpd.listBuildingsByName();
			} else{
				System.out.println("Unknown option");
			}
		}
		scan.close();
	}

}






