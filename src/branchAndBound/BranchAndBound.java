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
	public static final long TINY_PROBLEM = 600; 	// 10 minutos
	public static final long MEDIUM_PROBLEM = 3600; // 1 hora
	public static final long BIG_PROBLEM = 86400;	// 1 día
	
	private static final int MAX_SIZE_NODES_RECURSIVE = 20;
	
	private long limitedTime; 						// limite de tiempo que se ejecuta el algoritmo (nanosegundos)
	private Tsp tsp;
	private double bestValue;
	private int[] bestWay;

	/**
	 * Constructor para el algoritmo de ramificación y poda
	 * 
	 * @param tsp Problema tsp
	 */
	public BranchAndBound(Tsp tsp) {
		setTsp(tsp);
		UpperBound ub = new UpperBound(tsp);
		setBestValue(ub.getBestValue());
		setBestWay(ub.getPath());
		setLimitedTime(TINY_PROBLEM);
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
		setBestValue(ub.getBestValue());
		setBestWay(ub.getPath());
		setLimitedTime(limitedTime);
	}

	@Override
	public String toString () {
		String aux = "[";
		for (int i = 0; i < getBestWay().length - 1; i ++) {
			aux += getBestWay()[i] + ", ";
		}
		aux += getBestWay()[getBestWay().length - 1] + "]\n";
		aux += "Value: " + getBestValue();
		return aux;
	}
	
	/**
	 * Método que calcula el camino mínimo del TSP empezando por el nodo que se le haya pasado
	 *
	 * @param Nodo inicial desde el que se empieza
	 * @return Solución del TSP
	 */
	public int[] calculate(int initial) {
		int[] activeSet = new int[getTsp().getNodos()];
		for(int i = 0; i < activeSet.length; i++)
			activeSet[i] = i;
		
		Node root = new Node(null, 0, getTsp(), activeSet, initial);
		solve (root);

		return getBestWay();
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

		// Generamos un nodo aleatorio desde el que empezar
		int initialNode = (int) Math.floor(Math.random() * getTsp().getNodos());
		
		Node root = new Node(null, 0, getTsp(), active_set, initialNode);
		solve (root);

		return getBestWay();
	}

	/**
	 * Método que decide como resolver el algoritmo, si es un caso pequeño utiliza un método
	 * recursivo, pero si es grande utiliza uno iterativo
	 * 
	 * @param parent nodo inicial del algoritmo
	 */
	private void solve (Node parent) {
		if (getTsp().getNodos() <= MAX_SIZE_NODES_RECURSIVE)
			traverse (parent);
		else
			traverse2 (parent);
	}
	
	/**
	 * Método Recursivo que calcula el TSP usando el algoritmo de ramificación y poda
	 *
	 * @param nodo inicial
	 */
	private void traverse (Node parent) {
		// Generamos los hijos del nodo parent, ordenados de menor a mayor según su cota inferior
		SortedSet<Node> children = parent.generateChildren();
		
		/* En este caso no hace falta limitaciones de tiempo, debido a que este método 
		 * solo se llama en caso pequeños
		 */
		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < getBestValue()) {
					setBestValue(cost);
					setBestWay(child.getPath());
				}
			}
			else if(child.getLowerBound() <= getBestValue()) {
				solve(child);
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

		while ((timer.getTime() <= getLimitedTimeNano()) && (!stack.isEmpty())) {
			Node node = stack.pop();
			SortedSet<Node> children = node.generateChildren();
			// Ahora recorremos los hijos al revés, para que queden arriba de la pila los nodos con cota inferior más baja 
			int aux = children.size();
			for (int i = 0; i < aux; i++) {
				Node child = children.last();
				if(child.isTerminal()) {
					double cost = child.getPathCost();
					if(cost < getBestValue()) {
						setBestValue(cost);
						setBestWay(child.getPath());
					}
				}
				else if(child.getLowerBound() <= getBestValue()) {
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
	 * @return the bestValue
	 */
	public double getBestValue() {
		return bestValue;
	}

	/**
	 * @param bestValue the bestValue to set
	 */
	private void setBestValue(double bestValue) {
		this.bestValue = bestValue;
	}

	/**
	 * @return the bestWay
	 */
	private int[] getBestWay() {
		return bestWay;
	}

	/**
	 * @param bestWay the bestWay to set
	 */
	private void setBestWay(int[] bestWay) {
		this.bestWay = bestWay;
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

