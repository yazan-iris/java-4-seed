package edu.iris.dmc.seed.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.station.B050;

public class RecordWriter {

	private int size;
	private int index;
	private int sequence = 1;

	private char type;

	private OutputStream outputStream;

	private Map<Integer, String> map = new HashMap<>();

	public RecordWriter(OutputStream outputStream, char type) {
		this(outputStream, type, 4096);
	}

	public RecordWriter(OutputStream outputStream, char type, int size) {
		this.outputStream = outputStream;
		this.type = type;
		this.size = size;
	}

	public int getSequence() {
		return this.sequence;
	}

	public Map<Integer, String> getMap() {
		return map;
	}

	public void write(Blockette b) throws SeedException, IOException {
		if (b instanceof B050) {
			completeAndFlush();
			// start a new sequence
			map.put(sequence, ((B050) b).getStationCode());
			this.write(b, sequence++);
			return;
		}
		byte[] bytes = b.toSeedString().getBytes();
		if (bytes.length > (size - index)) {
			completeAndFlush();
			this.write(b, sequence++);
			return;
		}
		write(bytes);
	}

	public void write(byte[] bytes) throws IOException {
		outputStream.write(bytes);
		this.index = this.index + bytes.length;
	}

	private void write(Blockette b, int sequence) throws SeedException, IOException {
		write(b, sequence, false);
	}

	private void write(Blockette b, int sequence, boolean continuation) throws SeedException, IOException {
		StringBuilder builder = new StringBuilder(String.format("%06d", sequence));
		builder.append(type).append(continuation ? '*' : ' ').append(b.toSeedString());
		byte[] bytes = builder.toString().getBytes();
		outputStream.write(bytes);
		this.index = this.index + bytes.length;
	}

	public void flush() throws IOException {
		this.outputStream.flush();
	}

	private void completeAndFlush() throws IOException {
		if (index == 0) {
			return;
		}
		while (index < size) {
			outputStream.write(' ');
			index++;
		}
		flush();
		reset();
	}

	private void reset() {
		index = 0;
	}

	public void close() throws IOException {
		this.completeAndFlush();
		this.flush();
		this.reset();
		this.outputStream.close();
	}
}
