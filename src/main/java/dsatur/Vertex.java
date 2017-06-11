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
		hasColor = true;
	}
	
	public int getColor() {
		return color;
	}
	
	public void updateSatDegree() {
		//when an adjacent vertex receives a color, the saturation of this.vertex increases 
		satDegree++;
	}
	
	public int getSatDegree() {
		return satDegree;
	}
	
	public void updateDegree(boolean op) {
		if(op)
			degree++;
		else
			degree--;
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
			updateDegree(true);
		}
	}
	
	public void updateAdjacents() {
		for(Vertex adj : adjacent) {
			adj.updateSatDegree();
			adj.updateDegree(false);
		}
	}
	
	public ArrayList<Vertex> getAdjacent() {
		return adjacent;
	}
	
	@Override
	public String toString() {		
		return id + ", " + (getColor()+1);
	}
	
}
