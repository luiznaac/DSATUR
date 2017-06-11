package dsatur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	
	private static ArrayList<Vertex> graph;
	private static ArrayList<Vertex> possibleVertexes;
	private static boolean isFirstIteration;
	private static String path;
	private static FileReader fr;
	private static BufferedReader textReader;
	private static String line, id;
	private static Vertex tempVertex; //the vertex it will be associated adjacent vertexes to
	
	public static void main(String[] args) throws IOException {
		
		buildGraph();
		graph.sort(new DegreeComparator());
		
		for(Vertex vertex : graph) {
			System.out.println(vertex);
		}
		
	}
	
	public static void buildGraph() throws IOException {
		
		graph = new ArrayList<>();
		path = "src/main/resources/entry.csv";
		fr = new FileReader(path);
		textReader = new BufferedReader(fr);
		id = "";
		
		while((line = textReader.readLine()) != null) {
			tempVertex = null;
			line += '\n'; //ensure it has and EOF
			for(int i = 0 ; i < line.length() ; i++){
				//gets the id reading only numbers, if something outside the number's range is found then it 
				//means that it's read the whole id for that vertex and will continue to add that it to the graph
				if((line.charAt(i) >= 48) && (line.charAt(i) <= 57)) { //between 0 and 9 on the ASCII table
					id += line.charAt(i);
				}
				//after getting the id
				else if(id.length() > 0) {
					//if null then it is the first vertex of the line
					if(tempVertex == null) {
						tempVertex = new Vertex(Integer.parseInt(id));
						//was this vertex already added to the graph?
						if(!graphContainsVertex(tempVertex))
							graph.add(tempVertex);
						else
							tempVertex = instanceOfVertex(tempVertex);
					} else {
						Vertex auxVertex = new Vertex(Integer.parseInt(id)); //the adjacent vertex
						if(!graphContainsVertex(auxVertex))
							graph.add(auxVertex);
						else {
							auxVertex = instanceOfVertex(auxVertex);
						}
						tempVertex.addAdjacent(auxVertex);
						auxVertex.addAdjacent(tempVertex);
					}
					id = "";
				}
			}
		}
	}
	
	public static boolean graphContainsVertex(Vertex vertex) {
		boolean has = false;
		for(Vertex v : graph) {
			if(!has)
				has = (v.getId() == vertex.getId());
		}
		return has;
	}
	
	public static Vertex instanceOfVertex(Vertex vertex) {
		for(Vertex v : graph) {
			if(v.getId() == vertex.getId())
				return v;
		}
		return null;
	}
	
}
