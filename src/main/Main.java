/**
 * 
 */
package main;

import tspInstances.TspInstance;

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
		TspInstance tsp = new TspInstance(FICHERO);

	}

}
