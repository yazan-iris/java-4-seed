package edu.iris.seed;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import edu.iris.seed.record.Header.Type;

public class SeedOutputStream {
	//OutputStream o;
	BufferedOutputStream o;
	// int sequence;
	int count;
	int index;
	int recordLength;
	int sequence;
	private Type typeIdentifier;

	public SeedOutputStream(OutputStream o, int length, int sequence, Type typeIdentifier) throws SeedException {
		if (length < 256 || length > 32768) {
			throw new SeedException("Invalid record length {}, expected a length between 256 and 32768", length);
		}

		if ((length & (length - 1)) != 0) {
			throw new SeedException("Invalid record length {}, should be a power of 2", length);
		}
		this.o = new BufferedOutputStream(o);
		//this.o = o;
		this.recordLength = length;
		this.sequence = sequence;
		this.typeIdentifier = typeIdentifier;
	}

	public SeedOutputStream write(List<? extends Blockette> list) throws IOException {
		if (list == null || list.isEmpty()) {
			return this;
		}
		for (Blockette b : list) {
			write(b);
		}
		return this;
	}

	public SeedOutputStream write(Blockette b) throws IOException {
		if (b != null) {
			try {
				write(b.toSeedString().getBytes(StandardCharsets.US_ASCII));
				o.flush();
			} catch (SeedException e) {
				throw new IOException(e);
			}
		} else {
			// do something
		}

		return this;
	}

	private SeedOutputStream write(byte[] bytes) throws IOException {
		int len = bytes.length;
		if (index == 0) {
			o.write(nextSequene(false));
			index = 8;
		}
		if (index > recordLength - 7) {
			fill();
			o.write(nextSequene(false));
			index = 8;
		}
		for (int i = 0; i < len; i++, index++) {
			if (index >= recordLength) {
				o.write(nextSequene(true));
				index = 8;
			}
			o.write(bytes[i]);
		}
		o.flush();
		return this;
	}

	byte[] nextSequene(boolean continuation) {
		return String.format("%06d%c%c", sequence++, typeIdentifier.valueAsChar(), continuation ? '*' : ' ').getBytes();
	}

	/**
	 * Will fill record with empty space and return this record ending sequence
	 * 
	 * @return
	 * @throws IOException
	 */
	public int flush() throws IOException {
		fill();
		o.flush();
		return sequence;
	}

	private void fill() throws IOException {
		for (; index < recordLength; index++) {
			o.write((byte) 32);
		}
		index = 0;
	}
}
