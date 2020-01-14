package edu.iris.seed;

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

	public SeedException(Exception e, byte[] bytes, int type) {
		this(bytes, type, e.getMessage());
	}

	public SeedException(String message) {
		super(message);
	}

	public SeedException(String message, Object obj) {
		super(MessageFormatter.format(message, obj).getMessage());
	}

	public SeedException(String message, Object obj1, Object obj2) {
		super(MessageFormatter.format(message, obj1, obj2).getMessage());
	}

	public SeedException(String message, Object obj1, Object obj2, Object obj3) {
		this(message, new Object[] { obj1, obj2, obj3 });
	}

	public SeedException(String message, Object obj1, Object obj2, Object obj3, Object obj4) {
		this(message, new Object[] { obj1, obj2, obj3, obj4 });
	}

	public SeedException(byte[] bytes, int type, String message) {
		this("Error creating blockette of type {}.  " + message + " [" + new String(bytes) + "]",
				new Object[] { type, message, bytes });
	}

	public SeedException(byte[] bytes, int type, String message, Object obj1, Object obj2) {
		this("Error creating blockette of type {}.  " + message + " [" + new String(bytes) + "]",
				new Object[] { type, obj1, obj2 });
	}

	public SeedException(byte[] bytes, int type, String message, Object obj1, Object obj2, Object obj3) {
		this("Error creating blockette of type {}.  " + message + System.lineSeparator() + new String(bytes),
				new Object[] { type, obj1, obj2, obj3 });
	}

	public SeedException(byte[] bytes, int type, String message, Object obj1, Object obj2, Object obj3, Object obj4) {
		this("Error creating blockette of type {}.  " + message + System.lineSeparator() + new String(bytes),
				new Object[] { type, obj1, obj2, obj3, obj4 });
	}

	public SeedException(String message, Object[] array) {
		super(MessageFormatter.arrayFormat(message, array).getMessage());
	}

	public SeedException(String message, Exception e) {
		super(message, e);
	}

	public void add(byte[]bytes) {
		this.bytes=bytes;
	}
	public byte[] getBytes() {
		return bytes;
	}

	public int getIndex() {
		return index;
	}

}
