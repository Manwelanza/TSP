package nearestNeighbour;

import java.util.ArrayList;

import tspInstances.Tsp;

public class NearestNeighbour {
	
	private ArrayList<Integer> ub;
	private ArrayList<Integer> visited;
	
	public NearestNeighbour (Tsp tsp) {
		setVisited(new ArrayList<Integer> ());
		setUb();
		calculateUb (tsp);
	}

	private void calculateUb (Tsp tsp) {
		
	}
	
	/**
	 * @return the ub
	 */
	public ArrayList<Integer> getUb() {
		return ub;
	}

	/**
	 * @param ub the ub to set
	 */
	private void setUb(ArrayList<Integer> ub) {
		this.ub = ub;
	}

	/**
	 * @return the visited
	 */
	private ArrayList<Integer> getVisited() {
		return visited;
	}

	/**
	 * @param visited the visited to set
	 */
	private void setVisited(ArrayList<Integer> visited) {
		this.visited = visited;
	}
}
