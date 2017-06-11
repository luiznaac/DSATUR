package dsatur;

import java.util.Comparator;

public class SatDegreeComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v2.getSatDegree() - v1.getSatDegree();
	}

}
