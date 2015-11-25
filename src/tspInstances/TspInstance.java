/**
 * Clase que representa la entrada de un TSP (TSP instance)
 */
package tspInstances;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author ManuelAlejandro
 * @email Manwelanza@gmail.com
 *
 */
@SuppressWarnings("unused")
public class TspInstance {

	private Tsp tsp;				// Contiene el verdadero problema TSP, matriz de costos y número de nodos
	private String name;			// Nombre
	private String source;			// Fuente
	private String description;		// Descripción del problema
	private int precision;			// Precisión de los costos
	private boolean load;			// Si hay algún TSP cargado

	public TspInstance (String fichero) {
		leerFicheroXML(fichero);
	}
	
	public TspInstance () {
		setLoad(false);
	}
	
	/**
	 * Método que lee y parsea un fichero XML con los datos de un TSP
	 * @param fichero fichero xml para leer
	 */
	@SuppressWarnings("rawtypes")
	private void leerFicheroXML (String fichero) {
		int nodos = 0;
		ArrayList<Double> aux = new ArrayList<Double>();
		// Creamos el parseador de XML de la libreria JDOM2
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File (fichero);
		try {
			// Creamos el documento
			Document document = (Document) builder.build (xmlFile);
			// Obtenemos la raiz (travellingSalesmanProblemInstance)
			Element rootNode = document.getRootElement();
			// Obtenemos los hijos de la raiz
			List list = rootNode.getChildren();
			Element element = (Element) list.get(0);
			setName(element.getValue());
			// Obtenemos el atributo source
			element = (Element) list.get(1);
			setSource(element.getValue());
			// Obtenemos el atributo descripción
			element = (Element) list.get(2);
			setDescription(element.getValue());
			// Obtenemos el atributo precisión 
			element = (Element) list.get(3);
			setPrecision(Integer.parseInt(element.getValue()));
			// Ahora vamos a obtener la matriz de costos
			element = (Element) list.get(5);
			list = element.getChildren("vertex");
			nodos = list.size();
			int contador = 0;
			for (int i = 0; i < list.size(); i++){
				element = (Element) list.get(i);
				List auxList = element.getChildren("edge");
				for (int j = 0; j < nodos; j++) {
					if ((nodos > auxList.size()) && (j == i)) {
						aux.add(Double.MAX_VALUE);
					}
					else {
						element = (Element) auxList.get(contador);
						contador++;
						aux.add(Double.parseDouble(element.getAttributeValue("cost")));
					}
				}
				contador = 0;
			}
			setTsp(new Tsp (aux, nodos));
			setLoad(true);			
		}catch (IOException io) {
			System.err.println (io.getMessage());
		} catch (JDOMException jdomex) {
			System.err.println (jdomex.getMessage());
		}
	}

	/**
	 * @return the tsp
	 */
	public Tsp getTsp() {
		return tsp;
	}

	/**
	 * @param tsp the tsp to set
	 */
	private void setTsp(Tsp tsp) {
		this.tsp = tsp;
	}

	/**
	 * @return the name
	 */
	private String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the source
	 */
	private String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	private void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the description
	 */
	private String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the precision
	 */
	private int getPrecision() {
		return precision;
	}

	/**
	 * @param precision the precision to set
	 */
	private void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * @return the load
	 */
	public boolean isLoad() {
		return load;
	}

	/**
	 * @param load the load to set
	 */
	private void setLoad(boolean load) {
		this.load = load;
	}

}
