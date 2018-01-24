package hw9;

import hw7.Building;
import hw7.CampusData;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class InputPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	String defaultEntry = "Select building...";
	MapBackgroundPanel display;
	Vector<String> buildingList;
	JComboBox<String> fromDropdown, toDropdown;
	
	public InputPanel(CampusData cpd, MapBackgroundPanel map){
		makeBuildingList(cpd);
		display = map;
		setLayout(new GridBagLayout());
		
		JLabel fromLabel = new JLabel("Path from: ");
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0; c1.gridy = 0; c1.anchor = GridBagConstraints.LINE_END;
		add(fromLabel, c1);
		
		fromDropdown = new JComboBox<String>(buildingList);
		fromDropdown.addActionListener(new DropdownListener("start", fromDropdown));
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 1; c2.gridy = 0;
		add(fromDropdown, c2);
		
		JLabel toLabel = new JLabel(" to: ");
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 0; c3.gridy = 1; c3.anchor = GridBagConstraints.LINE_END;
		add(toLabel, c3);
		
		toDropdown = new JComboBox<String>(buildingList);
		toDropdown.addActionListener(new DropdownListener("end", toDropdown));
		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 1; c4.gridy = 1;
		add(toDropdown, c4);
		
		Button findButton = new Button("Find Path");
		findButton.addActionListener( new ActionListener(){
						public void actionPerformed(ActionEvent e){
							String start = (String)fromDropdown.getSelectedItem();
							String end = (String)toDropdown.getSelectedItem();
							display.updatePath(start, end);
						}
					});
		GridBagConstraints c5 = new GridBagConstraints();
		c5.gridx = 2; c5.gridy = 0; c5.gridheight = 2; c5.fill = GridBagConstraints.BOTH;
		add(findButton, c5);
		
		Button resetButton = new Button("Reset");
		resetButton.addActionListener( new ActionListener(){ public void actionPerformed(ActionEvent e) { reset(); } } );
		GridBagConstraints c6 = new GridBagConstraints();
		c6.gridx = 3; c6.gridy = 0; c6.gridheight = 2; c6.fill = GridBagConstraints.BOTH;
		add(resetButton, c6);
		
	}
	
	public void reset(){
		fromDropdown.setSelectedItem(defaultEntry);
		toDropdown.setSelectedItem(defaultEntry);
		display.resetMap();
	}
	
	private void makeBuildingList(CampusData cpd){
		buildingList = new Vector<String>();
		buildingList.add(defaultEntry);
		for(Building bldg : cpd){
			if(bldg.isIntersection()){ continue; }
			buildingList.add(bldg.getName());
		}
	}
	
	private class DropdownListener implements ActionListener{
		JComboBox<String> list;
		final String whichEnd;
		
		public DropdownListener(String startend, JComboBox<String> lst){
			whichEnd = startend; list = lst;
		}
		public void actionPerformed(ActionEvent e){
			String name = (String) list.getSelectedItem();
			if(name.equals(defaultEntry)){ display.clearSelection(whichEnd); }
			else{ display.selectionUpdate(whichEnd, name); }
		}
		
	}
	
}







