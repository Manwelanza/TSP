package tools;

/**
 * Clase para implementar un timer
 */
public class Timer {
	private long start;
	private long stop;
	public final static String[] units = { "μs", "ms", "s", "ks", "Ms" };

	/**
	 * Empieza a contar el timer
	 */
	public void start() {
		setStart(System.nanoTime());
	}

	/**
	 * Acaba de contar el timer
	 */
	public void stop() {
		setStop(System.nanoTime());
	}

	/**
	 * Devuelve el tiempo transcurrido en nanosegundos
	 *
	 * @return tiempo transcurrido en nanosegundos
	 */
	public long getTime() {
		return getStop() - getStart();
	}

	/**
	 * Devuelve un string con el tiempo formateado ajustando los numeros y las unidades
	 *
	 * @return tiempo formateado
	 */
	public String getFormattedTime() {
		long time = getTime();
		int unit = (int)((Math.log10(time) - 9 - 2) / 3); //-9 because nano, -2 because it chooses a unit with -1 to 2 digits
		if(unit > 2)
			unit = 2;
		else if(unit < -2)
			unit = -2;

		return (time / Math.pow(10, unit * 3 + 9)) + units[unit + 2]; //+9 because nano, +2 because arrays can't have negative indices
	}

	/**
	 * @return the start
	 */
	private long getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	private void setStart(long start) {
		this.start = start;
	}

	/**
	 * @return the stop
	 */
	private long getStop() {
		return stop;
	}

	/**
	 * @param stop the stop to set
	 */
	private void setStop(long stop) {
		this.stop = stop;
	}
}
