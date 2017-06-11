package dsatur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	
	private static ArrayList<Vertex> graph, possibleVertex, computedVertex;
	private static ArrayList<Boolean> colors;
	private static String pathIn, pathOut;
	private static FileReader fr;
	private static BufferedReader textReader;
	private static FileWriter fw;
	private static BufferedWriter textWriter;
	private static String line, id;
	private static Vertex tempVertex; //1 - while building the graph: the vertex it will be associated adjacent vertexes to
									  //2 - while running the algorithm: the vertex it is working with
	
	public static void main(String[] args) throws IOException {
		
		buildGraph();
		computeGraph();
		saveGraph();
		
	}
	
	public static void saveGraph() throws IOException {
	
		pathOut = "src/main/resources/out.csv";
		fw = new FileWriter(pathOut);
		textWriter = new BufferedWriter(fw);
		String out = "";
		
		for(Vertex v : computedVertex) {
			out += v + "\n";
		}
		
		textWriter.write(out);
		textWriter.close();
		fw.close();
		
	}
	
	public static void computeGraph() {
		
		computedVertex = new ArrayList<>(); 
		possibleVertex = new ArrayList<>();
		colors = new ArrayList<>();
		
		//first iteration only takes the vertex with the highest degree to compute
		graph.sort(new DegreeComparator()); //sort the vertexes according to their degree
		tempVertex = graph.get(0); //the first element is the one with the highest degree
		tempVertex.setColor(0); //sets the first color to it
		colors.add(0, true); //update the list of colors
		tempVertex.updateAdjacents(); //update its adjacent vertexes
		computedVertex.add(tempVertex); //add it to the list of computed vertexes
		graph.remove(tempVertex); //remove it from the main graph (now the graph is the remaining "subgraph")
		
		while(!graph.isEmpty()) {
			//the following iterations takes the vertex with the highest SATURATION degree
			graph.sort(new SatDegreeComparator()); //sort the vertexes according to their saturation degree
			int satAux = graph.get(0).getSatDegree(); //the first element is the one with the highest degree
			for(Vertex v : graph) { //but there might be more then one vertex with the same saturation degree 
				if(v.getSatDegree() == satAux)
					possibleVertex.add(v); //creates a list of the vertexes with the highest saturation degree
			}
			possibleVertex.sort(new DegreeComparator()); //sort it according to its degree, so there aren't any ties whatsoever
			tempVertex = possibleVertex.get(0); //maybe there're vertexes with the same degree as well but it doesn't matter, we can pick anyone then
			possibleVertex.clear(); //the vertex was already chosen, we can clear this list
			for(Vertex v : tempVertex.getAdjacent()) { //looks which colors the adjacent vertexes already have and sets that color as unavailable (false) 
				if(v.hasColor())
					colors.set(v.getColor(), false);
			}
			
			boolean hasAvailableColor = false; 
			int availableColor = 0;
			for(int i = 0 ; i < colors.size() && !hasAvailableColor ; i++) { //looks after the minimal color it can have
				if(colors.get(i) == true) {
					hasAvailableColor = true;
					availableColor = i;
				}
			}
			
			if(!hasAvailableColor) { //if there's none, then create a new color
				tempVertex.setColor(colors.size());
				colors.add(colors.size(), true);
			} else {
				tempVertex.setColor(availableColor); //else set the minimal to the vertex
			}
			
			tempVertex.updateAdjacents();
			computedVertex.add(tempVertex);
			graph.remove(tempVertex);
			
			for(int i = 0 ; i < colors.size() ; i++)
				colors.set(i, true); //it assumes that the colors are all available for the next vertex
		}
		
		computedVertex.sort(new IdComparator()); //sort the vertexes according to its id
		
	}
	
	public static void buildGraph() throws IOException {
		
		graph = new ArrayList<>();
		pathIn = "src/main/resources/in.csv";
		fr = new FileReader(pathIn);
		textReader = new BufferedReader(fr);
		id = "";
		
		while((line = textReader.readLine()) != null) {
			tempVertex = null;
			line += '\n'; //ensure it has an EOF
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
		
		textReader.close();
		fr.close();
		
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
