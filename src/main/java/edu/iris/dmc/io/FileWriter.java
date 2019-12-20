package edu.iris.dmc.io;

import java.io.Closeable;
import java.io.IOException;

import edu.iris.dmc.seed.Volume;

public interface FileWriter extends Closeable{

	public boolean isFileTypeSupported(FileFormat.TYPE type);

	public void write(Volume volume)throws IOException;

}
