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
			UpperBound ub = new UpperBound(tsp);
			BranchAndBound bAb = new BranchAndBound (tsp.getTsp(), ub.getBestValue());
			int [] resultado = bAb.calculate();
			String aux = "[";
			for (int i = 0; i < resultado.length - 1; i ++) {
				aux += resultado[i] + ", ";
			}
			aux += resultado[resultado.length - 1] + "]";
			System.out.println(aux);
		}
	}

}
