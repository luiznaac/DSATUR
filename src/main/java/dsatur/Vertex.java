package dsatur;

import java.util.ArrayList;

public class Vertex {

	private ArrayList<Vertex> adjacent;
	private int id;
	private int color;
	private int satDegree;
	private int degree;
	private boolean hasColor;
	
	public Vertex(int id) {
		adjacent = new ArrayList<>();
		this.id = id;
		satDegree = 0;
		degree = 0;
		hasColor = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void setColor(int color) {
		this.color = color;
		updateAdjacents();
	}
	
	public int getColor() {
		return color;
	}
	
	public void updateSatDegree() {
		satDegree++;
	}
	
	public int getSatDegree() {
		return satDegree;
	}
	
	public void updateDegree() {
		degree++;
	}
	
	public int getDegree() {
		return degree;
	}
	
	public boolean hasColor() {
		return hasColor;
	}
	
	public void addAdjacent(Vertex vertex) {
		if(!adjacent.contains(vertex)) {
			adjacent.add(vertex);
			updateDegree();
		}
	}
	
	public void updateAdjacents() {
		for(Vertex adj : adjacent)
			adj.updateSatDegree();
	}
	
	@Override
	public String toString() {
		String toPrint = "" + id;
		for(int i = 0 ; i < adjacent.size() ; i++)
			toPrint += ", " + adjacent.get(i).getId();
		return toPrint + " degree: " + getDegree();
	}
	
}
