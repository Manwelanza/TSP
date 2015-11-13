/**
 * 
 */
package main;

import tspInstances.TspInstance;
import upperBound.UpperBound;

/**
 * @author ManuelAlejandro
 *
 */
@SuppressWarnings("unused")
public class Main {

	public static final String FICHERO = "./Files/ft53.xml";
	/**
	 * Main principal del programa
	 * @param args argumentos que se le pasen línea de comandos
	 */
	public static void main(String[] args) {
		TspInstance tsp;
		if (args == null)
			tsp = new TspInstance ();
		else
			tsp = new TspInstance(args[0]);
		
		if (tsp.isLoad()) {
			UpperBound ub = new UpperBound(tsp);
			System.out.println(ub.getBestValue());
			ub.showWay();
		}
	}

}
