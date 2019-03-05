package edu.iris.dmc.seed;

public class SeedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6968498161740847489L;

	private byte[] bytes;

	public SeedException(Exception e) {
		this(e, null);
	}

	public SeedException(Exception e, byte[] bytes) {
		super(e);
		this.bytes = bytes;
	}

	public SeedException(String message) {
		super(message);
	}

	public SeedException(String message, Exception e) {
		super(message, e);
	}

	public byte[] getBytes() {
		return bytes;
	}
}
