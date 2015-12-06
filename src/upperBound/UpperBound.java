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
		 calculate2opt(tsp);
		 calculateBestValue();
		showWay();
	}
	
	/**
	 *  Método para cargar un TSP
	 * @param tsp TSP sobre el que se calcula la cota superior
	 */
	public void loadTsp (TspInstance tsp) {
		loadTsp(tsp.getTsp());
	}
	
	/**
	 * Método que calcula el valor de la cota superior
	 */
	private void calculateBestValue () {
		Double aux = 0.0;
		for (int i = 0; i < getWay().size(); i++) {
			aux += getWay().get(i).getCost();
		}
		setBestValue(aux);
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
	private void calculate2opt (Tsp tsp) {
		Double bestGain = Double.MAX_VALUE;
		int bestI = Integer.MAX_VALUE;
		int bestJ = Integer.MIN_VALUE;
		while (bestGain >= 0.0) {
			bestGain = 0.0;
			for (int i = 0; i < getWay().size(); i++) {
				for (int j = 0; j < getWay().size(); j++) {
					if (i != j) {
						Double gain = computeGain (i, j, tsp);
						if (gain < bestGain) {
							bestGain = gain;
							bestI = i;
							bestJ = j;
						}
					}
				}
			}
			if (bestI != Integer.MAX_VALUE && bestJ != Integer.MAX_VALUE) {
				exchange (bestI, bestJ, tsp);
			}
		}
	}
	
	/**
	 * Método que devuelve la ganancia del cambio de dos nodos
	 * @param node1 nodo 1
	 * @param node2 nodo 2
	 * @param tsp problema TSP
	 * @return ganancia del cambio
	 */
	private Double computeGain (final int node1, final int node2, Tsp tsp) {
		int origin1 = getWay().get(node1).getOrigin();
		int origin2 = getWay().get(node2).getOrigin();
		int destination1 = getWay().get(node1).getDestination();
		int destination2 = getWay().get(node2).getDestination();
		return ((tsp.getCost(destination1, destination2) + tsp.getCost(origin1, origin2)) -
				(tsp.getCost(origin1, destination1) + tsp.getCost(origin2, destination2)));	
	}
	
	/**
	 * Realiza el cambio entre los nodos
	 * @param node1 nodo 1
	 * @param node2 nodo 2
	 * @param tsp problema TSP
	 */
	private void exchange (final int node1, final int node2, Tsp tsp) {
		int destination1 = node1 + 1;
		int destination2 = node2 + 1;
		
		ArrayList<Step> auxWay = new ArrayList<Step> ();
		int indexAuxWay = 0;
		
		int i = 0;
		while (i <= node1) {
			if (tsp.isNodeInWay(auxWay, getWay().get(i).getOrigin()) == false) {
				auxWay.add(getWay().get(i));
				indexAuxWay++;
			}
			i++;
		}
		
		i = node2;
		while (i >= destination1) {
			if (tsp.isNodeInWay(auxWay, getWay().get(i).getOrigin()) == false) {
				auxWay.add(getWay().get(i));
				indexAuxWay++;
			}
			i--;
		}
		
		i = destination2;
		while (i < getWay().size()) {
			if (tsp.isNodeInWay(auxWay, getWay().get(i).getOrigin()) == false) {
				auxWay.add(getWay().get(i));
				indexAuxWay++;
			}
			i++;
		}
		
		for (int j = 0; j < auxWay.size(); j++) {
			setBestValue(getBestValue() - getWay().get(j).getCost() + auxWay.get(j).getCost());
			getWay().set(j, auxWay.get(j));
			System.out.println("AAAAAAAA");
		}
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
		System.out.println("Costo total --> " + getBestValue());
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
