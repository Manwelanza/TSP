/**
 * 
 */
package main;

import branchAndBound.BranchAndBound;
import tools.Timer;
import tspInstances.TspInstance;
import upperBound.UpperBound;

/**
 * @author ManuelAlejandro
 *
 */
@SuppressWarnings("unused")
public class Main {

	public static final String FICHERO = "./Files/a280.xml";
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
			Timer timer = new Timer ();
			timer.start();
			BranchAndBound bAb = new BranchAndBound (tsp.getTsp(), 600);
			int initialNode = 0;
			int [] resultado = bAb.calculate(initialNode);
			timer.stop();
			String aux = "[";
			for (int i = 0; i < resultado.length - 1; i ++) {
				aux += resultado[i] + ", ";
			}
			aux += resultado[resultado.length - 1] + "]";
			System.out.println(aux);
			System.out.println("Value: " + bAb.getCost());
			System.out.println("Time: " + timer.getFormattedTime());
			
			
			// QUITAR!!!
			for (int i = 0; i < resultado.length; i++) { // for de comprobacion
				for (int j = 0; j < resultado.length; j++) {
					if (i != j) {
						if (resultado[i] == resultado[j]) {
							System.out.println("ESTA MAL upperBound");
						}
					}
				}
			}// for de comprobacion
		}
	}

}
