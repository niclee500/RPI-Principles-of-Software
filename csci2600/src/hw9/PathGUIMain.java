package hw9;

import hw7.CampusData;

import java.awt.*;
import javax.swing.*;

public class PathGUIMain{
	
	public static void main(String[] args){
		String nodeDataFilename = "hw7/data/RPI_map_data_Nodes.csv";
		String edgeDataFilename = "hw7/data/RPI_map_data_Edges.csv";
		CampusData cpd = new CampusData(nodeDataFilename, edgeDataFilename);
		
		JFrame frame = new JFrame("RPI Campus Map");
		frame.setSize(800, 800);
		
		JPanel map = new MapBackgroundPanel(cpd);
		map.setPreferredSize(new Dimension(600, 600));
		JScrollPane scroll = new JScrollPane(map);
		scroll.setPreferredSize(new Dimension(600, 600));
		frame.add(scroll, BorderLayout.CENTER);
		
		JPanel input = new InputPanel(cpd, (MapBackgroundPanel)map);
		input.setPreferredSize(new Dimension(700, 100));
		frame.add(input, BorderLayout.NORTH);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
}


