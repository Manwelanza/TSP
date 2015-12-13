/**
 * Clase que resuelve el problema TSP usando un algoritmo de ramificación y poda
 */
package branchAndBound;

import java.util.SortedSet;
import java.util.Stack;

import tools.Timer;
import tspInstances.Tsp;
import upperBound.UpperBound;

public class BranchAndBound {
	private long limitedTime; 			// limite de tiempo que se ejecuta el algoritmo (nanosegundos)
	private Tsp tsp;
	private double best_cost;
	private int[] best_path;

	/**
	 * Constructor para el algoritmo de ramificación y poda
	 * 
	 * @param tsp Problema tsp
	 */
	public BranchAndBound(Tsp tsp) {
		setTsp(tsp);
		UpperBound ub = new UpperBound(tsp);
		setBest_cost(ub.getBestValue());
		setBest_path(ub.getPath());
		setLimitedTime(30);
	}
	
	/**
	 * Constructor para el algoritmo de ramificación y poda
	 * 
	 * @param tsp Problema tsp
	 * @param limitedTime límite de tiempo que se puede ejecutar el algoritmo
	 */
	public BranchAndBound(Tsp tsp, long limitedTime) {
		setTsp(tsp);
		UpperBound ub = new UpperBound(tsp);
		setBest_cost(ub.getBestValue());
		setBest_path(ub.getPath());
		setLimitedTime(limitedTime);
	}

	/**
	 * Método que calcula el camino mínimo del TSP empezando por el nodo que se le haya pasado
	 *
	 * @param Nodo inicial desde el que se empieza
	 * @return Solución del TSP
	 */
	public int[] calculate(int initial) {
		int[] active_set = new int[getTsp().getNodos()];
		for(int i = 0; i < active_set.length; i++)
			active_set[i] = i;
		
		Node root = new Node(null, 0, getTsp(), active_set, initial);
		traverse2(root);

		return getBest_path();
	}
	
	/**
	 * Método que calcula el camino mínimo del TSP empezando por un nodo aleatorio
	 *
	 * @return Solución del TSP
	 */
	public int[] calculate() {
		int[] active_set = new int[getTsp().getNodos()];
		for(int i = 0; i < active_set.length; i++)
			active_set[i] = i;

		int initialNode = (int) Math.floor(Math.random() * getTsp().getNodos());
		
		Node root = new Node(null, 0, getTsp(), active_set, initialNode);
		traverse(root);

		return getBest_path();
	}

	/**
	 * Método que devuelve el valor del problema TSP
	 *
	 * @return Valor del camino del TSP
	 */
	public double getCost() {
		return getBest_cost();
	}

	/**
	 * Método Recursivo que calcula el TSP usando el algoritmo de ramificación y poda
	 *
	 * @param nodo inicial
	 */
	private void traverse(Node parent) {
		// Generamos los hijos del nodo parent, ordenados de menor a mayor segun su cota inferior
		SortedSet<Node> children = parent.generateChildren();
		
		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < getBest_cost()) {
					setBest_cost(cost);
					setBest_path(child.getPath());
				}
			}
			else if(child.getLowerBound() <= getBest_cost()) {
				traverse(child);
			}
		}

	}
	
	/**
	 * Método iterativo que calcula el TSP usando el algoritmo de ramificación y poda
	 *
	 * @param nodo inicial
	 */
	private void traverse2 (Node parent) {
		Stack<Node> stack = new Stack<Node> ();
		stack.push(parent);
		Timer timer = new Timer ();
		timer.start();
		timer.stop();
		while (timer.getTime() <= getLimitedTimeNano() && !stack.isEmpty()) {
			Node node = stack.pop();
			SortedSet<Node> children = node.generateChildren();
			// Ahora recorremos los hijos al revés, para que queden arriba de la pila los nodos con cota inferior más baja 
			int aux = children.size();
			for (int i = 0; i < aux; i++) {
				Node child = children.last();
				if(child.isTerminal()) {
					double cost = child.getPathCost();
					if(cost < getBest_cost()) {
						setBest_cost(cost);
						setBest_path(child.getPath());
					}
				}
				else if(child.getLowerBound() <= getBest_cost()) {
					stack.push(child);
				}
				children.remove(child);
			} // for
			timer.stop();
		} // while
		
		// Si acabo por el tiempo, lo avisamos por consola
		if (!stack.isEmpty()) {
			System.out.println("WARNING: The algorithm has finished due to limited time");
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

	/**
	 * @return the limitTime (segundos)
	 */
	public long getLimitedTime() {
		return limitedTime * (long) 1e-9;
	}

	/**
	 * @param limitTime Tiempo máximo que se ejecutará el algoritmo (segundos)
	 */
	public void setLimitedTime(long limitedTime) {
		this.limitedTime =  limitedTime * (long) 1e+9;
	}
	
	/**
	 * 
	 * @return the limitTime (nanosegundos)
	 */
	private long getLimitedTimeNano() {
		return limitedTime;
	}
}

