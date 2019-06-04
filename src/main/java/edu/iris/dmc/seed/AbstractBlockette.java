package edu.iris.dmc.seed;

import java.nio.charset.StandardCharsets;

public abstract class AbstractBlockette {
	protected int id;
	protected int type;
	protected String title;
	protected String originalText;

	private int length;

	protected Volume volume;

	private Blockette parent;

	public AbstractBlockette(int type, String title) {
		this(type, title, 9999);
	}

	public AbstractBlockette(int type, String title, int length) {
		this.type = type;
		this.title = title;
		this.length = length;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String describe() throws SeedException {
		return BlocketteDefinition.describe(this.type);
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public Blockette getParent() {
		return parent;
	}

	public void setParent(Blockette parent) {
		this.parent = parent;
	}

	public String getOriginalText() {
		return originalText;
	}

	public int getSize() throws SeedException {
		String string = this.toSeedString();
		if (string == null || string.isEmpty()) {
			return 0;
		}
		return string.getBytes(StandardCharsets.US_ASCII).length;
	}

	public int getLength() {
		return this.length;
	}

	public boolean isOverFlown() throws SeedException {
		return this.getSize() > this.length;
	}

	@Override
	public String toString() {
		try {
			return toSeedString();
		} catch (SeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public abstract String toSeedString() throws SeedException;
}
