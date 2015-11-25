package upperBound;

import java.util.ArrayList;

import tspInstances.Tsp;
import tspInstances.TspInstance;

public class UpperBound {
	
	public static final int ITERATIONS_NN = 10;
	
	private Double bestValue;
	private ArrayList<Integer> visited;
	private ArrayList<Step> way;
	
	/**
	 * Constructor para calcular la cota superior de un problema TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	public UpperBound (Tsp tsp) {
		loadTsp(tsp);
	}
	
	/**
	 * Constructor para calcular la cota superior de un problema TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	public UpperBound (TspInstance tsp) {
		loadTsp(tsp.getTsp());
	}
	
	/**
	 * Constructor por defecto
	 */
	public UpperBound () {
		setBestValue (1E100);
		setVisited (new ArrayList<Integer> ());
		setWay (new ArrayList<Step> ());
	}
	
	/**
	 * Método para cargar un TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	public void loadTsp (Tsp tsp) {
		 setBestValue (1E100);
		 setVisited (new ArrayList<Integer> ());
		 setWay (new ArrayList<Step> ());
		 int contador = 0;
		 Double auxBestValue = Double.MAX_VALUE;
		 ArrayList<Step> auxWay = new ArrayList<Step> ();
		 calculateNN (tsp);
		 while (contador < ITERATIONS_NN) {
			 auxBestValue = getBestValue();
			 auxWay = copyArray(getWay());
			 calculateNN (tsp);
			 if (auxBestValue < getBestValue()) {
				 setBestValue(auxBestValue);
				 setWay(copyArray(auxWay));
				 contador ++;
			 }
			 else {
				 contador = 0;
			 }
		 }
		 
	}
	
	/**
	 *  Método para cargar un TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	public void loadTsp (TspInstance tsp) {
		loadTsp(tsp.getTsp());
	}

	/**
	 * Método para calcular la cota superior de un TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	private void calculateNN (Tsp tsp) {
		Double contador = 0.0;
		int nodos = tsp.getNodos();
		getVisited().clear();
		getWay().clear();
		int nodoActual = (int) Math.floor(Math.random() * nodos); // Vertice arbitrario
		getVisited().add(nodoActual);
		double auxMin = Double.MAX_VALUE;
		int auxNodo = -1;
		while (getVisited().size() < nodos) {
			for (int i = 0; i < nodos; i++) {
				if ((auxMin > tsp.getCost(nodoActual, i)) && 
						(!getVisited().contains(i))) {
					auxMin = tsp.getCost(nodoActual, i);
					auxNodo = i;
				}
			}
			if (auxNodo != nodoActual) {
				getWay().add(new Step (nodoActual, auxNodo, tsp.getCost(nodoActual, auxNodo)));
				contador += tsp.getCost(nodoActual, auxNodo);
				nodoActual = auxNodo;
				getVisited().add(nodoActual);
			}
			auxMin = Double.MAX_VALUE;
		}
		// Añadimos el último paso, entre el último nodo visitado y el primero
		getWay().add(new Step (nodoActual, getWay().get(0).getOrigin(), tsp.getCost(nodoActual, getWay().get(0).getOrigin())));
		contador += tsp.getCost(nodoActual, getWay().get(0).getOrigin());
		setBestValue(contador);
	}
	
	/**
	 * Método para calcular el algoritmo de refinamiento 2 OPT
	 */
	private void calculate2OPT () {
		
	}
	
	/**
	 * Método que devuelve una copia del array
	 * @param array array que copiará
	 * @return array copiado
	 */
	private ArrayList<Step> copyArray (ArrayList<Step> array) {
		ArrayList<Step> aux = new ArrayList<Step> ();
		for (int i = 0; i < array.size(); i++) {
			aux.add(array.get(i));
		}
		return aux;
	}

	/**
	 * Método que muestra el camino formado
	 */
	public void showWay () {
		System.out.println("Origen-->Destino = costo");
		for (int i = 0; i < getWay().size(); i++) {
			System.out.println(getWay().get(i).getOrigin() + "-->" + 
								getWay().get(i).getDestination() + " = " +
								getWay().get(i).getCost());
		}
	}
	
	/**
	 * @return the bestValue
	 */
	public Double getBestValue() {
		return bestValue;
	}

	/**
	 * @param bestValue the bestValue to set
	 */
	private void setBestValue(Double bestValue) {
		this.bestValue = bestValue;
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

	/**
	 * @return the way
	 */
	public ArrayList<Step> getWay() {
		return way;
	}

	/**
	 * @param way the way to set
	 */
	private void setWay(ArrayList<Step> way) {
		this.way = way;
	}
}
