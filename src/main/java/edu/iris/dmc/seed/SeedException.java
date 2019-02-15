package edu.iris.dmc.seed;

public class SeedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6968498161740847489L;

	public SeedException(Exception e) {
		super(e);
	}

	public SeedException(String message) {
		super(message);
	}

	public SeedException(String message, Exception e) {
		super(message, e);
	}
}
