/**
 * 
 */
package edu.iris.seed;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Suleiman
 *
 */
public interface Record<T extends Blockette> extends SeedObject {

	public boolean isContinuation();

	public SeedHeader getHeader();

	//public byte[] getBytes();
	
	public List<T> blockettes();
	
	public void clear();
	public int size();
	
	public T add(T t) throws SeedException;

	public T get(int... type);
	
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException;
}
