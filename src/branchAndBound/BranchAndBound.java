/**
 * Clase que resuelve el problema TSP usando un algoritmo de ramificaci�n y poda
 */
package branchAndBound;

import tspInstances.Tsp;

public class BranchAndBound {
	private Tsp tsp;
	private double best_cost;
	private int[] best_path;

	/**
	 * Constructor para el algoritmo de ramificaci�n y poda
	 * 
	 * @param tsp Problema tsp
	 * @param best_cost Cota superior
	 */
	public BranchAndBound(Tsp tsp, double best_cost) {
		setTsp(tsp);
		setBest_cost(best_cost);
	}

	/**
	 * M�todo que calcula el camino m�nimo del TSP
	 *
	 * @return Soluci�n del TSP
	 */
	public int[] calculate() {
		int[] active_set = new int[getTsp().getNodos()];
		for(int i = 0; i < active_set.length; i++)
			active_set[i] = i;

		Node root = new Node(null, 0, getTsp(), active_set, 0);
		traverse(root);

		System.out.println(getBest_cost());
		return best_path;
	}

	/**
	 * M�todo que devuelve el valor del problema TSP
	 *
	 * @return Valor del camino del TSP
	 */
	public double getCost() {
		return getBest_cost();
	}

	/**
	 * M�todo que calcula el TSP usando el algoritmo de ramificaci�n y poda
	 *
	 * @param nodo inicial
	 */
	private void traverse(Node parent) {
		Node[] children = parent.generateChildren();

		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < best_cost) {
					best_cost = cost;
					best_path = child.getPath();
				}
			}
			else if(child.getLowerBound() <= best_cost) {
				traverse(child);
			}
		}
	}

	/**
	 * @return the tsp
	 */
	private Tsp getTsp() {
		return tsp;
	}

	/**
	 * @param tsp the tsp to set
	 */
	private void setTsp(Tsp tsp) {
		this.tsp = tsp;
	}

	/**
	 * @return the best_cost
	 */
	private double getBest_cost() {
		return best_cost;
	}

	/**
	 * @param best_cost the best_cost to set
	 */
	private void setBest_cost(double best_cost) {
		this.best_cost = best_cost;
	}

	/**
	 * @return the best_path
	 */
	private int[] getBest_path() {
		return best_path;
	}

	/**
	 * @param best_path the best_path to set
	 */
	private void setBest_path(int[] best_path) {
		this.best_path = best_path;
	}
}

