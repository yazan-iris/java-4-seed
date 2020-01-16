/**
 * 
 */
package edu.iris.seed;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import edu.iris.seed.record.Header;

/**
 * @author Suleiman
 *
 */
public interface Record<T extends Blockette> {

	public boolean isContinuation();

	public Header getHeader();

	public byte[] getBytes();

	public T add(T t) throws SeedException;

	public T get(int... type);

	public List<T> getAll();
	
	public boolean isEmpty();
	
	public int size();
	
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException;
}
