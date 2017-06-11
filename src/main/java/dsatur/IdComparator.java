package dsatur;

import java.util.Comparator;

public class IdComparator implements Comparator<Vertex> {

	@Override
	public int compare(Vertex v1, Vertex v2) {
		return v1.getId() - v2.getId();
	}

}
