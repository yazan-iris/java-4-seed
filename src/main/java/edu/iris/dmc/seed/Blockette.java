package edu.iris.dmc.seed;

public interface Blockette {

	public int getId();

	public void setId(int id);

	public int getType();

	public int getLength();
	
	public int getMaximumLength();

	public void setType(int type);

	public String getTitle();

	public void setTitle(String name);

	public String describe() throws SeedException;

	public Volume getVolume();

	public void setVolume(Volume volume);

	public String getOriginalText();

	public String toSeedString();
}
