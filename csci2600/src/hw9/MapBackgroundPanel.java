package hw9;

import hw7.CampusData;
import hw7.Building;
import hw7.Path;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MapBackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	final int xborder = 60; final int yborder = 70;
	final int mapwidth = 2052; final int mapheight = 1930;
	int viewbuffer = 20;
	Dimension original;
	
	CampusData cpd;
	BufferedImage mapBackground;
	BufferedImage croppedMap;
	Vector<Line2D> drawpath;
	boolean startSelected = false, endSelected = false;
	int startx = 0, starty = 0; int endx = 0, endy = 0;
	int viewminx, viewminy, viewmaxx, viewmaxy;
	
	public MapBackgroundPanel(CampusData cd){
		original = new Dimension(getWidth(), getHeight());
		cpd = cd;
		BufferedImage fullImage = new BufferedImage(1, 1, 1);
		try{
			fullImage = ImageIO.read(new File("hw9/data/RPI_campus_map_2010_extra_nodes_edges.png"));
			croppedMap = ImageIO.read(new File("hw9/data/RPI_campus_map_2010_extra_nodes_edges2.png"));
		} catch(IOException e){
			System.out.println("Failed to load background image");
		}
		mapBackground = fullImage.getSubimage(xborder, yborder, mapwidth, mapheight);
		resetMap();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//calculateView();
		Graphics2D g2 = (Graphics2D) g;
		//BufferedImage view = mapBackground.getSubimage(viewminx, viewminy, viewmaxx-viewminx, viewmaxy-viewminy);
		g2.drawImage(mapBackground, 0, 0, getWidth(), getHeight(), viewminx, viewminy, viewmaxx, viewmaxy, null);
		//g2.drawImage(croppedMap, 0, 0, getWidth(), getHeight(), 0, 0, croppedMap.getWidth(), croppedMap.getHeight(), null);
		int dotSize = 9;
		BasicStroke s = new BasicStroke(2f); 
		g2.setStroke(s);
		for(Line2D line : drawpath){
			g2.setColor(Color.blue);
			//g2.draw(line);
			g2.drawLine(scaleX(line.getX1()), scaleY(line.getY1()), scaleX(line.getX2()), scaleY(line.getY2()));
			int pointSize = 2*dotSize/3;
			Ellipse2D dot = new Ellipse2D.Double(scaleX(line.getX1())-pointSize/2, scaleY(line.getY1())-pointSize/2, pointSize, pointSize);
			Ellipse2D dot2 = new Ellipse2D.Double(scaleX(line.getX2())-pointSize/2, scaleY(line.getY2())-pointSize/2, pointSize, pointSize);
			g2.fill(dot);
			g2.fill(dot2);
		}
		if(startSelected){
			Ellipse2D dot = new Ellipse2D.Double(scaleX(startx)-dotSize/2, scaleY(starty)-dotSize/2, dotSize, dotSize);
			g2.setColor(Color.green);
			g2.fill(dot);
			g2.setColor(Color.black);
			g2.draw(dot);
		}
		if(endSelected){
			Ellipse2D dot = new Ellipse2D.Double(scaleX(endx)-dotSize/2, scaleY(endy)-dotSize/2, dotSize, dotSize);
			g2.setColor(Color.red);
			g2.fill(dot);
			g2.setColor(Color.black);
			g2.draw(dot);
		}
	}
	
	private int scaleX(double origX){
		return (int)Math.round((origX-xborder)*((double)getWidth()/mapwidth));
	}
	private int scaleY(double origY){
		return (int)Math.round((origY-yborder)*((double)getHeight()/mapheight));
	}
	
	public void resetMap(){
		startSelected = endSelected = false;
		startx = starty = endx = endy = -1;
		drawpath = new Vector<Line2D>();
		resetPath();
		setPreferredSize(original);
		repaint();
	}
	
	private void resetPath(){
		drawpath.clear();
		viewminx = viewminy = 0;
		viewmaxx = mapwidth; viewmaxy = mapheight;
	}
	
	public void selectionUpdate(String startend, String buildingName){
		resetPath();
		Building building = cpd.findBuilding(buildingName);
		if(startend.equals("start")){ 
			startx = (int)Math.round(building.getX());
			starty = (int)Math.round(building.getY());
			startSelected = true;
		}
		if(startend.equals("end")){ 
			endx = (int)Math.round(building.getX());
			endy = (int)Math.round(building.getY());
			endSelected = true;
		}
		repaint();
	}
	
	private void calculateView(){
		if(drawpath.size() == 0){ resetPath(); }
		else{
			viewminx = (int)Math.min(drawpath.firstElement().getX1(), drawpath.lastElement().getX2()) - viewbuffer;
			viewminy = (int)Math.min(drawpath.firstElement().getY1(), drawpath.lastElement().getY2()) - viewbuffer;
			viewmaxx = (int)Math.max(drawpath.firstElement().getX1(), drawpath.lastElement().getX2()) + viewbuffer;
			viewmaxy = (int)Math.max(drawpath.firstElement().getY1(), drawpath.lastElement().getY2()) + viewbuffer;
			viewminx = Math.max(0, viewminx); viewminy = Math.max(0, viewminy);
			viewmaxx = Math.min(viewmaxx, mapwidth); viewmaxy = Math.min(viewmaxy, mapheight);
		}
	}
	
	public void updatePath(String begin, String end){
		Path path = cpd.findMinPath(begin, end);
		drawpath.clear();
		Building start = path.getStart();
		Building goal = path.getGoal();
		if( goal.compareTo(path.getEnd()) != 0 ){ return; }
		Point2D previous = new Point2D.Double(start.getX(), start.getY());
		for(Building bldg : path){
			if(bldg.compareTo(start) == 0){ continue; }
			Point2D current = new Point2D.Double(bldg.getX(), bldg.getY());
			Line2D line = new Line2D.Double(previous, current);
			drawpath.add(line);
			previous = current;
		}
		repaint();
	}
	
	public void clearSelection(String startend){
		if(startend.equals("start")){ startSelected = false; }
		if(startend.equals("end")){ endSelected = false; }
		repaint();
	}
	
}







