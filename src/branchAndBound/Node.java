/**
 * Clase que implementa un nodo para aplicar el algoritmo branch and bound
 */
package branchAndBound;

import tspInstances.Tsp;

public class Node {
	private Node parent;
	private double parent_cost;
	private Tsp tsp;
	private int[] active_set;
	private int index;

	/**
	 * Constructor de un nuevo nodo
	 *
	 * @param parent Padre de este nodo
	 * @param parent_cost Coste entre el padre y este nodo
	 * @param tsp Problema tsp
	 * @param active_set Todos los nodos que estan siendo calculados (incluido este)
	 * @param index Índice de este nodo
	 */
	public Node(Node parent, double parent_cost, Tsp tsp, int[] active_set, int index) {
		setParent (parent);
		setParent_cost (parent_cost);
		setTsp (tsp);
		setActive_set (active_set);
		setIndex (index);
	}

	/**
	 * Comprueba si este nodo es terminal
	 *
	 * @return devuelve si es terminal o no
	 */
	public boolean isTerminal() {
		return getActive_set().length == 1;
	}

	/**
	 * Método que devuelve los nodos hijos de este.
	 *
	 * @precondition el nodo no es terminal
	 * @return array con todos los hijos
	 */
	public Node[] generateChildren() {
		Node[] children = new Node[getActive_set().length - 1];

		int[] new_set = new int[getActive_set().length - 1];
		int i = 0;
		for(int location : getActive_set()) {
			if(location == getIndex())
				continue;

			new_set[i] = location;
			i++;
		}

		for(int j = 0; j < children.length; j++)
			children[j] = new Node(this, getTsp().getCost(index, new_set[j]), getTsp(), new_set, new_set[j]);

		return children;
	}

	
	/**
	 * Obtener el camino hasta aquí
	 * 
	 * @return Camino
	 */
	public int[] getPath() {
		int depth = getTsp().getNodos() - getActive_set().length + 1;
		int[] path = new int[depth];
		getPathIndex(path, depth - 1);
		return path;
	}

	/**
	 * Método recursivo para rellenar el array del camino desde este nodo
	 *
	 * @param path Array con el camino
	 * @param i Índice de este nodo
	 */
	public void getPathIndex(int[] path, int i) {
		path[i] = getIndex();
		if(getParent () != null)
			getParent().getPathIndex(path, i - 1);
	}

	/**
	 * Método para obtener la cota inferior de este nodo
	 *
	 * @return Cota inferior
	 */
	public double getLowerBound() {
		double value = 0;

		if(getActive_set().length == 2)
			return getPathCost() + getTsp().getCost(getActive_set()[0], getActive_set()[1]);

		for(int location : getActive_set()) {
			double low1 = Double.MAX_VALUE;
			double low2 = Double.MAX_VALUE;

			for(int other: getActive_set()) {
				if(other == location)
					continue;

				double cost = getTsp().getCost(location, other);
				if(cost < low1) {
					low2 = low1;
					low1 = cost;
				}
				else if(cost < low2) {
					low2 = cost;
				}
			}

			value += low1 + low2;
		}

		return getParentCost() + value / 2;
	}

	/**
	 * Método que devuelve el coste de todo el camino hasta este nodo
	 *
	 * @return Coste del camino hasta aquí
	 */
	public double getPathCost() {
		return getTsp().getCost(0, index) + getParentCost();
	}

	/**
	 * Método para obtener el coste del camino del padre de este nodo.
	 *
	 * @return Coste
	 */
	public double getParentCost() {
		if(parent == null)
			return 0;

		return parent_cost + parent.getParentCost();
	}

	/**
	 * @return the parent
	 */
	private Node getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	private void setParent(Node parent) {
		this.parent = parent;
	}

	/**
	 * @return the parent_cost
	 */
	private double getParent_cost() {
		return parent_cost;
	}

	/**
	 * @param parent_cost the parent_cost to set
	 */
	private void setParent_cost(double parent_cost) {
		this.parent_cost = parent_cost;
	}

	/**
	 * @return the active_set
	 */
	private int[] getActive_set() {
		return active_set;
	}

	/**
	 * @param active_set the active_set to set
	 */
	private void setActive_set(int[] active_set) {
		this.active_set = active_set;
	}

	/**
	 * @return the index
	 */
	private int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	private void setIndex(int index) {
		this.index = index;
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
}
