package edu.iris.dmc.seed;

import org.slf4j.helpers.MessageFormatter;

public class SeedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6968498161740847489L;

	private byte[] bytes;
	private int index;

	public SeedException(Exception e) {
		this(e, null);
	}

	public SeedException(Exception e, byte[] bytes) {
		super(e);
		this.bytes = bytes;
	}
	
	public SeedException(Exception e, byte[] bytes,int index) {
		super(e);
		this.bytes = bytes;
		this.index=index;
	}

	public SeedException(String message) {
		super(message);
	}

	public SeedException(String message, Object... argArray) {
		super(MessageFormatter.format(message, argArray).getMessage());
	}

	public SeedException(String message, Exception e) {
		super(message, e);
	}

	public byte[] getBytes() {
		return bytes;
	}

	public int getIndex() {
		return index;
	}

}
