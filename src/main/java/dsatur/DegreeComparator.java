package dsatur;

import java.util.Comparator;

public class DegreeComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v2.getDegree() - v1.getDegree();
	}

}
