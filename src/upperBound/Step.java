/**
 * Clase que representa un paso del camino del algoritmo de vecino más cercano
 */

package upperBound;

public class Step {
	
	private Integer origin;						// Nodo origen
	private Integer destination;				// Nodo destino
	private Double cost;						// Coste
	
	/**
	 * Constructor 
	 * @param origin origen
	 * @param destination destino
	 * @param cost costo
	 */
	public Step (int origin, int destination, double cost) {
		setOrigin(origin);					
		setDestination(destination);		
		setCost(cost);						
	}
	
	/**
	 * @return the origin
	 */
	public Integer getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	private void setOrigin(Integer origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	public Integer getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	private void setDestination(Integer destination) {
		this.destination = destination;
	}
	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	private void setCost(Double cost) {
		this.cost = cost;
	}
	
	

}
