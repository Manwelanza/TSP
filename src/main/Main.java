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
			BranchAndBound bAb = new BranchAndBound (tsp.getTsp(), BranchAndBound.TINY_PROBLEM);
			int initialNode = 0;
			int [] resultado = bAb.calculate(initialNode);
			timer.stop();
			System.out.println(bAb);
			System.out.println("Time: " + timer.getFormattedTime());
		}
	}

}
