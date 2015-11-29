/**
 * Clase que representas un TSP
 */
package tspInstances;

import java.util.ArrayList;

import upperBound.Step;

/**
 * @author ManuelAlejandro
 * @email Manwelanza@gmail.com
 *
 */
public class Tsp {
	
	private ArrayList<Double> costs;			// Vector con los valores de todos los costos de las aristas
	private int nodos;							// Número de filas y de columnas
	
	/**
	 * Constructor de la clase
	 * @param costs Array con todos los costos de las aristas
	 * @param nodos Número de filas y de columnas de la matriz
	 */
	public Tsp (ArrayList<Double> costs, int nodos) {
		setCosts(costs);
		setNodos(nodos);
		System.out.println(costs.size());
		int aux = nodos * nodos;
		System.out.println("nodos: " + nodos);
		System.out.println("nodos * nodos: " + aux);
	}
	
	/**
	 * Método que coge el coste de la fila y columna que sea
	 * @param row fila, que será el nodo del que parte la arista
	 * @param column columna, que será el nodo al que llega la arista
	 * @return costo de la arista
	 */
	public Double getCost (int row, int column) {
		double valor;
		if (((row < getNodos()) && (row >= 0)) && 
			((column < getNodos()) && (column >= 0))) {
			valor = getCosts().get(getNodos() * row + column);
		}
		else {
			System.err.println("Fila o columna incorrectas, devolviendo el valor 0.0");
			valor = 0.0;
		}
		return valor;
	}
	
	/**
	 * Método que tan solo imprime la fila 1, para poder comprobar que funciona correctamente
	 * @param fila fila a mostrar
	 */
	public void mostrarFila(final int fila) {
		System.out.println("Tiene un total de: " + getNodos() + " nodos");
		System.out.println("Nodo " + fila + ":");
		for (int i = 0; i < getNodos(); i++) {
				System.out.println(getCost(fila, i));
		}
	}
	
	public boolean isNodeInWay (ArrayList<Step> way, final int node) {
		for (int i = 0; i < way.size(); i++) {
			if (way.get(i).getOrigin() == node) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return the costs
	 */
	private ArrayList<Double> getCosts() {
		return costs;
	}
	/**
	 * @param costs the costs to set
	 */
	private void setCosts(ArrayList<Double> costs) {
		this.costs = costs;
	}
	/**
	 * @return the nodos
	 */
	public int getNodos() {
		return nodos;
	}
	/**
	 * @param nodos the nodos to set
	 */
	private void setNodos(int nodos) {
		this.nodos = nodos;
	}
}
