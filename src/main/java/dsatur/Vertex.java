package dsatur;

import java.util.ArrayList;

public class Vertex {

	private ArrayList<Vertex> adjacent;
	private int id, color, satDegree, degree;
	private boolean hasColor;
	private ArrayList<Integer> adjacentColors;
	
	public Vertex(int id) {
		adjacent = new ArrayList<>();
		this.id = id;
		satDegree = 0;
		degree = 0;
		hasColor = false;
		adjacentColors = new ArrayList<>();
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
		//checks if the vertex was already added as adjacent
		if(!adjacent.contains(vertex)) {
			adjacent.add(vertex);
			updateDegree(true);
		}
	}
	
	public void updateAdjacents(int color) {
		//saturation degree is equal the number of >different< colors of the adjacent vertexes
		//checks if this vertex already has an adjacent vertex with @color
		for(Vertex adj : adjacent) {
			if(!adj.getAdjacentColors().contains(color) && !adj.hasColor()) { //if the adjacent already has color, it shouldn't be computed anymore
				(adj.getAdjacentColors()).add(color);
				adj.updateSatDegree();
			}
			adj.updateDegree(false);
		}
	}
	
	public ArrayList<Vertex> getAdjacent() {
		return adjacent;
	}
	
	public ArrayList<Integer> getAdjacentColors() {
		return adjacentColors;
	}
	
	@Override
	public String toString() {		
		return id + ", " + (getColor()+1);
	}
	
}
