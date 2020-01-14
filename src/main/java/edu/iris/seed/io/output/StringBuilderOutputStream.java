package edu.iris.seed.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class StringBuilderOutputStream extends OutputStream {
	private final StringBuilder builder;

	public StringBuilderOutputStream() {
		this.builder = new StringBuilder();
	}

	@Override
	public void write(int b) throws IOException {
		builder.append((char) b);

	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public String toString() {
		if(builder==null) {
			return "";
		}
		return builder.toString();
	}

}
