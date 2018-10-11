package edu.iris.dmc.io;

import java.io.IOException;
import java.io.OutputStream;

import edu.iris.dmc.seed.Volume;

public interface FileWriter {

	public boolean isFileTypeSupported(FileFormat.TYPE type);

	public void write(Volume volume)throws IOException;
	public void write(Volume volume, Formatter formatter)throws IOException;

}
